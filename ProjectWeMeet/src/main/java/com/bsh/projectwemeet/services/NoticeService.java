package com.bsh.projectwemeet.services;

import com.bsh.projectwemeet.entities.NoticeWriterArticleEntity;
import com.bsh.projectwemeet.entities.NoticeWriterImagesEntity;
import com.bsh.projectwemeet.entities.QnaEntity;
import com.bsh.projectwemeet.entities.UserEntity;
import com.bsh.projectwemeet.enums.PatchNoticeViewResult;
import com.bsh.projectwemeet.mappers.NoticeMapper;
import com.bsh.projectwemeet.models.PagingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

@Service
public class NoticeService {

    private final NoticeMapper noticeMapper;

    public static final int PAGE_COUNT = 6; //최대페이지 6개로 설정


    @Autowired
    public NoticeService(NoticeMapper noticeMapper) {
        this.noticeMapper = noticeMapper;
    }

    public UserEntity CheckUser(HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        if (user == null) {
            return null;
        }
        UserEntity check = noticeMapper.selectCheckUser(user.getEmail());
        return check;
    }

    public int getCount(String searchCriterion, String searchQuery){
        return this.noticeMapper.selectNoticePagingByCount(searchCriterion, searchQuery);
    } //사용자의 세션 관련해 Qna 목록 확인 가능

    public NoticeWriterArticleEntity[] getByPage(PagingModel pagingModel, String searchCriterion,
                                 String searchQuery) {
        NoticeWriterArticleEntity[] noticePaging = this.noticeMapper.selectNoticePaging(pagingModel,searchCriterion,searchQuery);
        return noticePaging;
    } //사용자의 세션 관련해 Qna 목록 확인 가능

    public NoticeWriterArticleEntity[] getCountArticle() {
        return this.noticeMapper.selectCountArticle();
    }



    public boolean putNoticeWriter(HttpSession session,HttpServletRequest request, NoticeWriterArticleEntity noticeWriterArticle) {
        UserEntity loginUser = (UserEntity) session.getAttribute("user");
        noticeWriterArticle.setCreatedAt(new Date())
                .setClientIp(request.getRemoteAddr())
                .setClientUa(request.getHeader("User-Agent"))
                .setNickname(loginUser.getNickname());
        return this.noticeMapper.insertArticle(noticeWriterArticle) > 0;
    }

    // 내용에 이미지 파일이 있을때
    public NoticeWriterArticleEntity readArticle(int index) {
        NoticeWriterArticleEntity article = this.noticeMapper.selectArticleByIndex(index);
        return article;
    }

    //    ckeditor에 그림 넣기
    public NoticeWriterImagesEntity putImage(HttpServletRequest request, MultipartFile file) throws IOException {
        NoticeWriterImagesEntity image = new NoticeWriterImagesEntity()
                .setCreatedAt(new Date())
                .setClientIp(request.getRemoteAddr())
                .setClientUa(request.getHeader("User-Agent"))
                .setName(file.getOriginalFilename())
                .setSize((file.getSize()))
                .setContentType(file.getContentType())
                .setData(file.getBytes());
        this.noticeMapper.insertImage(image);
        return image;

    }

    //    ckeditor에 넣은 그림을 표시
    public NoticeWriterImagesEntity getImage(int index) {
        return this.noticeMapper.selectImage(index);
    }


    public boolean deleteNoticeView(int index, HttpSession session) {
        UserEntity loginUser = (UserEntity) session.getAttribute("user");
        if (loginUser.isAdmin() != true) {
            return false; //사용자가 관리자가 아니라면
        }
        return this.noticeMapper.deleteArticleByIndex(index) > 0;
    }

    //    수정한다면 원래 있던 내용들을 그대로 표시하기
    public NoticeWriterArticleEntity getPatchIndexArticle(int index, HttpSession session) {
        UserEntity loginUser = (UserEntity) session.getAttribute("user");
        if (loginUser.isAdmin() != true) {
            return null; //사용자가 관리자가 아니라면
        }
        return this.noticeMapper.selectArticleByPatchIndex(index);
    }

    public PatchNoticeViewResult UpdateArticle(NoticeWriterArticleEntity noticeWriterArticle, HttpSession session, HttpServletRequest request) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        if (user == null) {
            return PatchNoticeViewResult.FAILURE;
        }
        noticeWriterArticle.setClientIp(request.getRemoteAddr())
                .setClientUa(request.getHeader("User-Agent"))
                .setCreatedAt(new Date());
        return this.noticeMapper.updateArticleContent(noticeWriterArticle) > 0
                ? PatchNoticeViewResult.SUCCESS
                : PatchNoticeViewResult.FAILURE;
    }


}
