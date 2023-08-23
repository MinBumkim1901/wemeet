package com.bsh.projectwemeet.mappers;

import com.bsh.projectwemeet.entities.*;
import com.bsh.projectwemeet.models.PagingModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EventMapper {

    UserEntity selectCheckUser(@Param(value = "email")String email);
    //관리자인지 확인하는 쿼리

    int selectEventPagingByCount(@Param(value = "searchQuery") String searchQuery);

    EventEntity[] selectEventPaging(@Param(value = "pagingModel") PagingModel pagingModel,
                                    @Param(value = "searchQuery") String searchQuery);

    int insertArticle(EventEntity event);

    int insertImage(EventImagesEntity eventImages);

    EventEntity[] selectCountArticle();

    EventEntity selectArticleByIndex(@Param(value = "index") int index);

    EventEntity selectArticleByPatchIndex(@Param(value = "index") int index);

    EventImagesEntity selectImage(@Param(value = "index") int index);


    int deleteArticleByIndex(@Param(value = "index") int index);

    int updateArticleContent(EventEntity event);

}
