package org.sopt.report3.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SignUpReq {
    private String name;
    private String part;
    private String password;

    private String profileUrl;
    private MultipartFile profile;


}