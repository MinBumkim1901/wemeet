package com.bsh.projectwemeet.controllers;

import com.bsh.projectwemeet.entities.EventEntity;
import com.bsh.projectwemeet.entities.QnaAnswerEntity;
import com.bsh.projectwemeet.entities.QnaEntity;
import com.bsh.projectwemeet.entities.UserEntity;
import com.bsh.projectwemeet.enums.InsertQnaResult;
import com.bsh.projectwemeet.enums.SendRegisterContactCodeResult;
import com.bsh.projectwemeet.models.PagingModel;
import com.bsh.projectwemeet.services.AdminService;
import com.bsh.projectwemeet.services.QnaService;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/")
public class QnaController {

    private final QnaService qnaService;

    public QnaController(QnaService qnaService) {
        this.qnaService = qnaService;
    }

    @RequestMapping(value = "qna",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getQna(@RequestParam(value = "p", defaultValue = "1",required = false)int requestPage,
                               @RequestParam(value = "c",defaultValue = "",required = false)String searchCriterion,
                               @RequestParam(value = "q", defaultValue = "", required = false) String searchQuery,
                               HttpSession session){
        ModelAndView modelAndView = new ModelAndView("home/qna/qna");
        int searchResultCount = this.qnaService.getCount(searchCriterion,searchQuery,session);
        int searchResultByAdmin = this.qnaService.getCountByAdmin(searchCriterion,searchQuery);
        UserEntity user = qnaService.checkAdmin(session); // 유저정보 확인

        PagingModel pagingModel = new PagingModel(
                AdminService.PAGE_COUNT,
                this.qnaService.getCount(searchCriterion,searchQuery,session),
                requestPage); //관리자가 아닌 사용자의 페이징

        PagingModel pagingModelAdmin = new PagingModel(
                AdminService.PAGE_COUNT,
                this.qnaService.getCountByAdmin(searchCriterion,searchQuery),
                requestPage); //관리자의 페이징

        QnaEntity[] qna = this.qnaService.getByPage(pagingModel,searchCriterion,searchQuery,session);
        QnaEntity[] qnaAdmin = this.qnaService.getByPageAdmin(pagingModelAdmin,searchCriterion,searchQuery);
        //관리자 계정으로 접속시 모든 Qna 항목 확인 가능한 서비스


        modelAndView.addObject("user",user);
        modelAndView.addObject("qna",qna);
        modelAndView.addObject("qnaAdmin",qnaAdmin);
        modelAndView.addObject("pagingModel",pagingModel);
        modelAndView.addObject("pagingModelAdmin",pagingModelAdmin);
        modelAndView.addObject("searchQuery",searchQuery);
        modelAndView.addObject("searchCriterion",searchCriterion);
        modelAndView.addObject("searchResult",searchResultCount);
        modelAndView.addObject("searchResultByAdmin",searchResultByAdmin);

        return modelAndView;
    } //qna 주소



    @RequestMapping(value = "qnaWriter",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getQnaWriter(HttpSession session){
        ModelAndView modelAndView = new ModelAndView("home/qna/qnaWriter");
        UserEntity user = qnaService.checkAdmin(session); // 유저정보 확인
        modelAndView.addObject("user",user);

        return modelAndView;
    } //qna 주소

    @RequestMapping(value = "qnaWriter",
            method = RequestMethod.POST,
            produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public ModelAndView postQnaWriter(HttpSession session, QnaEntity qna){
        boolean result = this.qnaService.putQna(qna,session);
        ModelAndView modelAndView = new ModelAndView("home/qna/qnaWriter");
        modelAndView.addObject("qna",qna);

        if (result) {
            modelAndView.setViewName("redirect:/qnaView?index=" + qna.getIndex());
        } else {
            modelAndView.setViewName("home/qna/qnaWriter");
            modelAndView.addObject("result", result);
        }
        return modelAndView;
    }

    @RequestMapping(value = "qnaAnswer",
            method = RequestMethod.POST,
            produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String postQnaAnswer(@RequestParam(value = "index") int index,QnaEntity qnaEntity, QnaAnswerEntity qna){
        boolean result = this.qnaService.qnaAnswer(qnaEntity,qna,index);
        return String.valueOf(result);
    }


    @RequestMapping(value = "qnaView",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public ModelAndView getQnaView(@RequestParam(value = "index") int index, HttpSession session){
      ModelAndView modelAndView = new ModelAndView("home/qna/qnaView");
        UserEntity loginUser = (UserEntity) session.getAttribute("user");
        QnaEntity article = this.qnaService.readQna(index);
        QnaAnswerEntity answer = this.qnaService.readAnswerQna(index);
        modelAndView.addObject("article", article);
        modelAndView.addObject("answer", answer);
        modelAndView.addObject("user", loginUser);
      return modelAndView;
    }

    @RequestMapping(value = "deleteQna", method = RequestMethod.DELETE, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String deleteQnaView(@RequestParam(value = "index") int index) {
        boolean result = this.qnaService.deleteQnaAndAnswer(index);
        return String.valueOf(result);
    }


    @RequestMapping(value = "qnaView/patch",
            method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getQnaViewPatch(@RequestParam(value = "index") int index, HttpSession session){
        ModelAndView modelAndView = new ModelAndView("home/qna/qnaPatch");
        UserEntity loginUser = (UserEntity) session.getAttribute("user");
        QnaEntity article = this.qnaService.patchArticle(index,session);
        modelAndView.addObject("article", article);
        modelAndView.addObject("user", loginUser);
        return modelAndView;
    }

    @RequestMapping(value = "qnaView/patch",
            method = RequestMethod.PATCH,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String patchQnaViewPatch(QnaEntity qna) {
        boolean result = this.qnaService.patchArticleQna(qna);
        return String.valueOf(result);
    }
}
