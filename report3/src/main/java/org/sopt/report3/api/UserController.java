package org.sopt.report3.api;

import lombok.extern.slf4j.Slf4j;
import org.sopt.report3.dto.User;
import org.sopt.report3.model.SignUpReq;
import org.sopt.report3.service.UserService;
import org.sopt.report3.utils.Auth.Auth;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import static org.sopt.report3.model.DefaultRes.FAIL_DEFAULT_RES;



@Slf4j
@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService) {  //생성자
        this.userService = userService;
    }


    /** 이름으로 회원정보 조회
     * 파트로 회원정보 조회
     * 이름&파트로 회원정보 조회*/
    @GetMapping("")
    public ResponseEntity getUser(
            @RequestParam(value="name",required = false) final Optional<String> name,
            @RequestParam(value="part",required = false) final Optional<String> part){

        try {

            //이름으로만 조회
            if(name.isPresent() && !part.isPresent()) return new ResponseEntity<>(userService.findByName(name.get()), HttpStatus.OK);
            //파트만으로만 조회
            if(!name.isPresent() &&part.isPresent()) return new ResponseEntity<>(userService.findByPart(part.get()), HttpStatus.OK);
            //이름과 파트로 조회
            if(name.isPresent() && part.isPresent()) return new ResponseEntity<>(userService.findByPartAndName((name.get()), (part.get())), HttpStatus.OK);

            // 둘다 없다면 모든회원정보 보여줌
            return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);

        }catch (Exception e) {

            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /** 회원가입*/
    @PostMapping("")
    public ResponseEntity signUp(
            SignUpReq signUpReq, @RequestPart(value = "profile",required = false) final MultipartFile profile) {

        try {
            if(profile != null) signUpReq.setProfile(profile);
            return new ResponseEntity<>(userService.save(signUpReq), HttpStatus.OK);

        }catch (Exception e) {

            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

     /**로그인을 통한 인증과정을 성공한다면 회원정보 수정가능*/
    @Auth
    @PutMapping("/{userIdx}")
    public ResponseEntity updateUser(
            @PathVariable(value = "userIdx") final int userIdx,
            @RequestBody final User user) {
        try {

            return new ResponseEntity<>(userService.update(userIdx, user), HttpStatus.OK);

        }catch (Exception e) {

            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    /**로그인을 통한 인증과정을 성공한다면 회원탈퇴가능*/
    @Auth
    @DeleteMapping("/{userIdx}")
    public ResponseEntity deleteUser(@PathVariable(value = "userIdx") final int userIdx) {

        try {

            return new ResponseEntity<>(userService.deleteByUserIdx(userIdx), HttpStatus.OK);

        }catch (Exception e) {

            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
