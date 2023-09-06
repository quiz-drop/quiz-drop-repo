package com.sparta.quizdemo;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {
    String username() default "tester1";

    String password() default "Test!@12";

    String nickname() default "testnick";

    String zip_code() default "testzipcode";

    String address1() default "testaddress1";

    String address2() default "testaddress2";

    String email() default "test@test.com";

    boolean admin() default false;

    String adminToken() default "testToken";
}
