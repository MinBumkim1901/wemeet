package com.bsh.projectwemeet.mappers;


import com.bsh.projectwemeet.entities.NoticeWriterArticleEntity;
import com.bsh.projectwemeet.entities.NoticeWriterImagesEntity;
import com.bsh.projectwemeet.entities.QnaEntity;
import com.bsh.projectwemeet.entities.UserEntity;
import com.bsh.projectwemeet.models.PagingModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface NoticeMapper {

    UserEntity selectCheckUser(@Param(value = "email")String email);

    int selectNoticePagingByCount(@Param(value = "searchCriterion") String searchCriterion,
                             @Param(value = "searchQuery") String searchQuery);
    //select문의 id와 같기때문에 셀렉트된 데이터의 개수 모두 가져오기Criterion);
    //사용자의 이메일 값을 통해 사용자의 세션으로 접속시 사용자의 Qna만 확인 가능하다.


    NoticeWriterArticleEntity[] selectNoticePaging(@Param(value = "pagingModel") PagingModel pagingModel,
                                 @Param(value = "searchCriterion") String searchCriterion,
                                 @Param(value = "searchQuery") String searchQuery);
    //사용자의 이메일 값을 통해 사용자의 세션으로 접속시 사용자의 Qna만 확인 가능하다.

    NoticeWriterArticleEntity[] selectCountArticle();

    int insertArticle(NoticeWriterArticleEntity article);

    int insertImage(NoticeWriterImagesEntity image);

    NoticeWriterArticleEntity selectArticleByIndex(@Param(value = "index") int index);

    NoticeWriterArticleEntity selectArticleByPatchIndex(@Param(value = "index") int index);

    NoticeWriterImagesEntity selectImage(@Param(value = "index") int index);


    int deleteArticleByIndex(@Param(value = "index") int index);

    int updateArticleContent(NoticeWriterArticleEntity noticeWriterArticle);
}
