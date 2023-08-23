package com.bsh.projectwemeet.mappers;

import com.bsh.projectwemeet.entities.*;
import com.bsh.projectwemeet.models.PagingModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FaqMapper {

    UserEntity selectCheckUser(@Param(value = "email")String email);
    //관리자인지 확인하는 쿼리

    int selectFaqPagingByCount(@Param(value = "searchQuery") String searchQuery);

    FaqEntity[] selectFaqPaging(@Param(value = "pagingModel") PagingModel pagingModel,
                                @Param(value = "searchQuery") String searchQuery);

    int insertArticle(FaqEntity faq);


    FaqEntity selectArticleByIndex(@Param(value = "index") int index);

    FaqEntity selectArticleByPatchIndex(@Param(value = "index") int index);

    int deleteArticleByIndex(@Param(value = "index") int index);

    int updateArticleContent(FaqEntity faq);
}
