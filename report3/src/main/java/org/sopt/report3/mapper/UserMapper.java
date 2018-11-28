package org.sopt.report3.mapper;

import org.apache.ibatis.annotations.*;
import org.sopt.report3.dto.User;

import org.sopt.report3.model.SignUpReq;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface UserMapper {

    //모든 회원 리스트 조회
    @Select("SELECT * FROM new_user")
    List<User> findAll();

    //이름과 비밀번호로 로그인
    @Select("SELECT * FROM user WHERE name = #{name} AND password = #{password}")
    User findByNameAndPassword(@Param("name") final String name, @Param("password") final String password);

    //회원 이름으로 조회
    @Select("SELECT * FROM new_user WHERE name = #{name}")
    List<User> findByName(@Param("name") final String name);

    //회원 파트로 조회
    @Select("SELECT * FROM new_user WHERE part = #{part}")
    List<User> findByPart(@Param("part") final String part);

    //회원 이름 & 파트로 조회
    @Select("SELECT * FROM new_user WHERE name = #{name} AND part = #{part}")
    User findByPartAndName(@Param("name") final String name, @Param("part") final String part);


    //회원 id로 조회
    @Select("SELECT * FROM new_user WHERE userIdx = #{userIdx}")
    User findByUserIdx(@Param("userIdx") final int userIdx);

    //회원 등록, AUto Increment는 회원고유번호
    //auto increment 값을 박아오고 싶으면 리턴값을 int(Auto Increment 컬럼타입)으로 하면된다
    @Insert("INSERT INTO user(name,part,profileUrl) VALUES(#{signUpReq.name}, #{signUpReq.part},#{signUpReq.profileUrl})")
    @Options(useGeneratedKeys = true, keyColumn = "user.userIdx") //ai(Auto Increment)값을 명시한거
    int save(@Param("signUpReq") final SignUpReq signUpReq);



    //회원 id로 회원 정보 수정
    @Update("UPDATE new_user SET name =#{user.name}, part=#{user.part} WHERE userIdx = #{userIdx}")
    void update(@Param("userIdx") final int userIdx, @Param("user") final User user);

    //회원 id로 회원 삭제
    @Delete("DELETE FROM new_user where userIdx = #{userIdx}")
    void deleteByUserIdx(@Param("userIdx") final int userIdx);
}









