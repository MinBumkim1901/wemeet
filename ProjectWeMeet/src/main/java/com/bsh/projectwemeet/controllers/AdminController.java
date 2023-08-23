package com.bsh.projectwemeet.controllers;

import com.bsh.projectwemeet.entities.UserEntity;
import com.bsh.projectwemeet.models.PagingModel;
import com.bsh.projectwemeet.services.AdminService;
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
public class AdminController {

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    private final AdminService adminService;

    @RequestMapping(value = "admin",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView getUser(@RequestParam(value = "p", defaultValue = "1",required = false)int requestPage,
                                @RequestParam(value = "c",defaultValue = "",required = false)String searchCriterion,
                                @RequestParam(value = "q", defaultValue = "", required = false) String searchQuery,
                                HttpSession session){
        ModelAndView modelAndView = new ModelAndView("home/Admin/admin");
        int searchResultCount = this.adminService.getCount(searchCriterion, searchQuery);
        UserEntity user = (UserEntity)session.getAttribute("user");

        PagingModel pagingModel = new PagingModel(
                AdminService.PAGE_COUNT,
                this.adminService.getCount(searchCriterion, searchQuery),
                requestPage); //객체화
        UserEntity[] userEntities = this.adminService.getByPage(pagingModel,searchCriterion,searchQuery);

        modelAndView.addObject("user",user);
        modelAndView.addObject("allUser",userEntities);
        modelAndView.addObject("pagingModel",pagingModel);
        modelAndView.addObject("searchQuery",searchQuery);
        modelAndView.addObject("searchCriterion",searchCriterion);
        modelAndView.addObject("searchResult",searchResultCount);
        return modelAndView;
    } //관리자 페이지//

    @RequestMapping(value = "admin",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String deleteUser(@RequestParam("email")String email){
        boolean deleteResult = adminService.deleteUser(email);
        return String.valueOf(deleteResult);
    } //관리자 페이지//

}
