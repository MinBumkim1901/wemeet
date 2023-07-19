package com.bsh.projectwemeet.services;

import com.bsh.projectwemeet.entities.ArticleEntity;
import com.bsh.projectwemeet.mappers.CompleteMapper;
import com.bsh.projectwemeet.models.PagingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class CompleteService {

    public final CompleteMapper completeMapper;

    public static final int PAGE_COUNT=6;

    @Autowired
    public CompleteService(CompleteMapper completeMapper) {
        this.completeMapper = completeMapper;
    }

    public int getCountCategory(String category){
        return this.completeMapper.selectCountCategory(category);
    }

    public ArticleEntity[] getCountCategoryByPage(PagingModel pagingModel, String category){

        return this.completeMapper.selectCountCategoryByPage(pagingModel,category);
    }


    public ArticleEntity readArticle(int index) {

        ArticleEntity article = this.completeMapper.selectArticleByIndex(index);
        // 주어진 index를 사용하여 articleMapper에서 해당 index에 해당하는 게시글을 조회합니다.

        if (article != null && !article.isDeleted()) {
            // 게시글이 존재하고 삭제되지 않은 경우에만 처리합니다.

            article.setView(article.getView() + 1);
            // 게시글의 조회수(view)를 1 증가시킵니다.

            this.completeMapper.updateArticle(article);
            // 조회수를 업데이트합니다.
        }
        return article;
        //결과적으로 삭제되지않거나
        //결과적으로 삭제되지않거나
    } //게시판 나타내기

}
