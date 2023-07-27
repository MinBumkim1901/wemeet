package com.bsh.projectwemeet.services;

import com.bsh.projectwemeet.entities.ProfileEntity;
import com.bsh.projectwemeet.mappers.ProfileMapper;
import com.bsh.projectwemeet.mappers.RegisterMapper;
import com.bsh.projectwemeet.entities.RegisterContactCodeEntity;
import com.bsh.projectwemeet.entities.UserEntity;
import com.bsh.projectwemeet.enums.RegisterResult;
import com.bsh.projectwemeet.enums.SendRegisterContactCodeResult;
import com.bsh.projectwemeet.enums.VerifyRegisterContactCodeResult;
import com.bsh.projectwemeet.utils.CryptoUtil;
import com.bsh.projectwemeet.utils.NCloudUtil;


import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Service
public class RegisterService {

    private final RegisterMapper registerMapper;
    private final ProfileMapper profileMapper;

    @Autowired
    public RegisterService(RegisterMapper registerMapper, ProfileMapper profileMapper) {
        this.registerMapper = registerMapper;
        this.profileMapper = profileMapper;
    }



    public SendRegisterContactCodeResult sendRegisterContactCodeResult(RegisterContactCodeEntity registerContactCode){
        if (registerContactCode == null
        || registerContactCode.getContact() == null
        || !registerContactCode.getContact().matches("^(010)(\\d{8})$")){ //잘못된 전화번호 or 값이 입력되어 있지 않을때
            return SendRegisterContactCodeResult.FAILURE;
        }

        if (this.registerMapper.selectUserByContact(registerContactCode.getContact()) !=null){ //사용중인 연락처일 때
            return SendRegisterContactCodeResult.FAILURE_DUPLICATE;
        }
        
        String code = RandomStringUtils.randomNumeric(6); //랜덤 숫자 6자리 (인증번호)
        String salt = CryptoUtil.hashSha512(String.format("%s%s%f%f", //비밀번호 암호화
                registerContactCode.getCode(),
                code,
                Math.random(),
                Math.random()));
        Date createdAt = new Date(); //시간제한을 걸기위한 Date값
        Date expiresAt = DateUtils.addMinutes(createdAt,5); //시간제한 5분
        registerContactCode.setCode(code).setSalt(salt).setCreatedAt(createdAt).setExpiresAt(expiresAt).setExpired(false);

        NCloudUtil.sendSms(registerContactCode.getContact(), String.format("[We Meet 회원가입] 인증번호[%s]를 입력해주세요",registerContactCode.getCode()));

        return this.registerMapper.insertRegisterContactCode(registerContactCode)>0
                ? SendRegisterContactCodeResult.SUCCESS
                : SendRegisterContactCodeResult.FAILURE;
    }



    public VerifyRegisterContactCodeResult verifyRegisterContactCodeResult(RegisterContactCodeEntity registerContactCode){
        registerContactCode = this.registerMapper.selectRegisterContactCodeByContactSalt(registerContactCode);
        if (registerContactCode == null){
            return VerifyRegisterContactCodeResult.FAILURE;
        }
        if (new Date().compareTo(registerContactCode.getExpiresAt())>0){
            return VerifyRegisterContactCodeResult.FAILURE_EXPIRED; //인증번호 만료
        }
        registerContactCode.setExpired(true);
        return this.registerMapper.updateRegisterCode(registerContactCode)>0
                ? VerifyRegisterContactCodeResult.SUCCESS
                : VerifyRegisterContactCodeResult.FAILURE;
    }


    public RegisterResult register(UserEntity user,RegisterContactCodeEntity registerContactCode,ProfileEntity profile)throws NoSuchAlgorithmException {

        if (this.registerMapper.selectUserByEmail(user.getEmail()) != null) {
            return RegisterResult.FAILURE_DUPLICATE_EMAIL; //사용중인 이메일
        }
        if (this.registerMapper.selectUserByContact(user.getContact()) != null) {
            return RegisterResult.FAILURE_DUPLICATE_CONTACT; //사용중인 연락처
        }
        if (this.registerMapper.selectUserByNickname(user.getNickname()) != null) {
            return RegisterResult.FAILURE_DUPLICATE_NICKNAME; //사용 중인 닉네임
        }
        registerContactCode = this.registerMapper.selectRegisterContactCodeByContactSalt(registerContactCode);
        if (registerContactCode == null || !registerContactCode.isExpired()) {
            return RegisterResult.FAILURE;
        }
        user.setPassword(CryptoUtil.hashSha512(user.getPassword())); //저장되는 패스워드 암호화


        String defaultProfileImagePath = "src/main/resources/static/resources/images/profileImages/icons8-male-user-96.png";
        //기본이미지 루트

        try {
            // 이미지 파일을 바이트 배열로 읽어옴
            byte[] defaultProfileImageBytes = Files.readAllBytes(Paths.get(defaultProfileImagePath));

            // ProfileEntity 생성
            profile.setEmail(user.getEmail())
                    .setCreatedAt(new Date())
                    .setProfileThumbnail(defaultProfileImageBytes)
                    .setProfileThumbnailMime("image/png")
                    .setIntroduceText("");// 이미지의 MIME 타입을 설정해야 합니다.

        } catch (IOException e) {
            // 파일 읽기 오류 처리
            e.printStackTrace();
        } //회원가입시 같이 프로필 추가되게

        return this.registerMapper.insertUser(user) > 0 && profileMapper.insertProfile(profile) > 0
                ? RegisterResult.SUCCESS
                : RegisterResult.FAILURE;
    }



}
