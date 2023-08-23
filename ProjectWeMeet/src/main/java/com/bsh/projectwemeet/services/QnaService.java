package com.bsh.projectwemeet.services;

import com.bsh.projectwemeet.entities.QnaAnswerEntity;
import com.bsh.projectwemeet.entities.QnaEntity;
import com.bsh.projectwemeet.entities.UserEntity;
import com.bsh.projectwemeet.mappers.QnaMapper;
import com.bsh.projectwemeet.models.PagingModel;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Service
public class QnaService {

    public static final int PAGE_COUNT = 6; //최대페이지 6개로 설정

    private final QnaMapper qnaMapper;


    public QnaService(QnaMapper qnaMapper) {
        this.qnaMapper = qnaMapper;
    }

    public UserEntity checkAdmin(HttpSession session) {
        UserEntity loginUser = (UserEntity) session.getAttribute("user");
        if(loginUser == null){
            return null;
        }
        return this.qnaMapper.selectCheckUser(loginUser.getEmail());
    }

    public boolean putQna(QnaEntity qna, HttpSession session){
        UserEntity loginUser = (UserEntity) session.getAttribute("user");

        qna.setEmail(loginUser.getEmail())
                .setAnswerFlag(false)
                .setCreatedAt(new Date())
                .setNickname(loginUser.getNickname());

        return this.qnaMapper.insertQna(qna) > 0;
    }

    public boolean qnaAnswer(QnaEntity qna,QnaAnswerEntity qnaAnswer,int index){
        qna.setAnswerFlag(true);
        qnaAnswer.setQnaIndex(index)
                .setCreatedAt(new Date());
        return this.qnaMapper.insertQnaAnswer(qnaAnswer) > 0 && this.qnaMapper.updateQnaAnswer(qna) >0;
    }

    public int getCount(String searchCriterion, String searchQuery,HttpSession session){
        UserEntity loginUser = (UserEntity) session.getAttribute("user");
        if(loginUser == null){
            return 0;
        }
        return this.qnaMapper.selectQnaCountByUser(searchCriterion, searchQuery,loginUser.getEmail());
    } //사용자의 세션 관련해 Qna 목록 확인 가능

    public QnaEntity[] getByPage(PagingModel pagingModel,String searchCriterion,
                                 String searchQuery,HttpSession session) {
        UserEntity loginUser = (UserEntity) session.getAttribute("user");
        if(loginUser == null){
            return null;
        }
        QnaEntity[] Qna = this.qnaMapper.selectByPageUser(pagingModel,searchCriterion,searchQuery, loginUser.getEmail());
        return Qna;
    } //사용자의 세션 관련해 Qna 목록 확인 가능

    public int getCountByAdmin(String searchCriterion, String searchQuery){
        return this.qnaMapper.selectQnaCountByAdmin(searchCriterion, searchQuery);
    } //사용자의 세션 관련해 Qna 목록 확인 가능

    public QnaEntity[] getByPageAdmin(PagingModel pagingModel,String searchCriterion,
                                 String searchQuery) {
        QnaEntity[] Qna = this.qnaMapper.selectByPageAdmin(pagingModel,searchCriterion,searchQuery);
        return Qna;
    } //사용자의 세션 관련해 Qna 목록 확인 가능


    public QnaEntity[] readQnaByEmail(HttpSession session){
        UserEntity loginUser = (UserEntity) session.getAttribute("user");
        if(loginUser == null){
            return null;
        }
        QnaEntity[] qna =qnaMapper.selectQnaByEmail(loginUser.getEmail());
        return qna;
    }

    public QnaEntity[] readQnaByAdmin(){
        return this.qnaMapper.selectQnaByAdmin();
    }

    public QnaEntity readQna(int index) {
        QnaEntity article = this.qnaMapper.selectQnaByIndex(index);
        return article;
    }

    public QnaAnswerEntity readAnswerQna(int index) {
        QnaAnswerEntity answer = this.qnaMapper.selectQnaAnswerByIndex(index);
        return answer;
    }

    public boolean deleteQnaAndAnswer(int index) {
        return this.qnaMapper.deleteQnaByIndex(index) > 0 && this.qnaMapper.deleteAnswerByIndex(index) > 0;
    }


    public QnaEntity patchArticle(int index,HttpSession session) {
        UserEntity loginUser = (UserEntity) session.getAttribute("user");
        return this.qnaMapper.selectQnaByIndex(index);
    }

    public boolean patchArticleQna(QnaEntity qna) {
      return this.qnaMapper.updateQna(qna) > 0;
    }



}
