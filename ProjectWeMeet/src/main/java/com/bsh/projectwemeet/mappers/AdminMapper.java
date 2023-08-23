package com.bsh.projectwemeet.mappers;

import com.bsh.projectwemeet.entities.UserEntity;
import com.bsh.projectwemeet.models.PagingModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AdminMapper {

    UserEntity[] selectUserAll();

    int selectCount(@Param(value = "searchCriterion") String searchCriterion,
                    @Param(value = "searchQuery") String searchQuery); //select문의 id와 같기때문에 셀렉트된 데이터의 개수 모두 가져오기

    UserEntity[] selectByPage(@Param(value = "pagingModel") PagingModel pagingModel,
                              @Param(value = "searchCriterion") String searchCriterion,
                              @Param(value = "searchQuery") String searchQuery);
    int deleteUser(@Param(value = "email")String email);
}
