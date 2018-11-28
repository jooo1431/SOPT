package org.sopt.report3.model;
import lombok.*;

import org.sopt.report3.utils.ResponseMessage;

import org.sopt.report3.utils.StatusCode;



/**

 * Created by ds on 2018-11-05.

 */



@Data //getter setter 있음
@Builder //builder도 있음
@AllArgsConstructor // 모든필드에 대한 생성자 있음

public class DefaultRes<T> { //

    private int status; //숫자로 상태나타냄
    private String message; //상태 메세지
    private T data; //객체 반환 혹은 얻어오는용


    public DefaultRes(final int status, final String message) {

        this.status = status;
        this.message = message;
        this.data = null;
    }


    public static<T> DefaultRes<T> res(final int status, final String message) {

        return res(status, message, null);
    }



    public static<T> DefaultRes<T> res(final int status, final String message, final T t) {

        return DefaultRes.<T>builder()
                .data(t)
                .status(status)
                .message(message)
                .build();
    }


    public static final DefaultRes FAIL_DEFAULT_RES = new DefaultRes(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);

}