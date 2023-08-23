package com.bsh.projectwemeet.mappers;

import com.bsh.projectwemeet.entities.QnaAnswerEntity;
import com.bsh.projectwemeet.entities.QnaEntity;
import com.bsh.projectwemeet.entities.UserEntity;
import com.bsh.projectwemeet.models.PagingModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface QnaMapper {

    int insertQna(QnaEntity qnaEntity);

    int insertQnaAnswer(QnaAnswerEntity qnaAnswer);

    int selectQnaCountByUser(@Param(value = "searchCriterion") String searchCriterion,
                             @Param(value = "searchQuery") String searchQuery,
                             @Param(value = "email") String email);
    //select문의 id와 같기때문에 셀렉트된 데이터의 개수 모두 가져오기Criterion);
    //사용자의 이메일 값을 통해 사용자의 세션으로 접속시 사용자의 Qna만 확인 가능하다.


    QnaEntity[] selectByPageUser(@Param(value = "pagingModel") PagingModel pagingModel,
                                 @Param(value = "searchCriterion") String searchCriterion,
                                 @Param(value = "searchQuery") String searchQuery,
                                 @Param(value = "email")String email);
    //사용자의 이메일 값을 통해 사용자의 세션으로 접속시 사용자의 Qna만 확인 가능하다.

    int selectQnaCountByAdmin(@Param(value = "searchCriterion") String searchCriterion,
                             @Param(value = "searchQuery") String searchQuery);
    //select문의 id와 같기때문에 셀렉트된 데이터의 개수 모두 가져오기Criterion);
    //관리자의 계정으로 접속시 Qna 목록 전부 확인 가능

    QnaEntity[] selectByPageAdmin(@Param(value = "pagingModel") PagingModel pagingModel,
                                 @Param(value = "searchCriterion") String searchCriterion,
                                 @Param(value = "searchQuery") String searchQuery);
    //관리자의 계정으로 접속시 Qna 목록 전부 확인 가능

    QnaEntity[] selectQnaByEmail(@Param(value = "email")String email);

    QnaEntity[] selectQnaByAdmin();

    QnaEntity selectQnaByIndex(@Param(value = "index") int index);

    QnaAnswerEntity selectQnaAnswerByIndex(@Param(value = "index") int index);

    UserEntity selectCheckUser(@Param("email")String email);

    int deleteQnaByIndex(@Param(value = "index")int index);

    int deleteAnswerByIndex(@Param(value = "qnaIndex")int qnaIndex);

    int updateQnaAnswer(QnaEntity qna);

    int updateQna(QnaEntity qna);

}
