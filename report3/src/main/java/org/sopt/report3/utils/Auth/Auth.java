package org.sopt.report3.utils.Auth;

import java.lang.annotation.*;

@Target(ElementType.METHOD) //메소드ㅇㅔ 적용
@Retention(RetentionPolicy.RUNTIME) // 런타임시까지 ㅊ참조 가능
@Documented//  Java Doc에도 표시
@Inherited // 상속가능
public @interface Auth {
}
