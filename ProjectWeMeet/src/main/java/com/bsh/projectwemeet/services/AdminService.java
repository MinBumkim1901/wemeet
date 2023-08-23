package com.bsh.projectwemeet.services;

import com.bsh.projectwemeet.entities.UserEntity;
import com.bsh.projectwemeet.mappers.AdminMapper;
import com.bsh.projectwemeet.models.PagingModel;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    public static final int PAGE_COUNT = 6; //최대페이지 6개로 설정

    private final AdminMapper adminMapper;

    public AdminService(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    public UserEntity[] allSelect(){
        return this.adminMapper.selectUserAll();
    }

    public int getCount(String searchCriterion, String searchQuery){
        return this.adminMapper.selectCount(searchCriterion, searchQuery);
    } // 게시글 전부의 갯수 나타내는 메서드

    public UserEntity[] getByPage(PagingModel pagingModel, String searchCriterion, String searchQuery) {
        UserEntity[] userEntities = this.adminMapper.selectByPage(pagingModel, searchCriterion, searchQuery);
        return userEntities;
    } //페이지얻게하는 메서드

    public boolean deleteUser(String email){
        return this.adminMapper.deleteUser(email) > 0;
    }
}
