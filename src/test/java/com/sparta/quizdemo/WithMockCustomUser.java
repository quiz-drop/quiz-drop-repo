package com.sparta.quizdemo;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {

    String username() default "test-user";

    String nickname() default "test-nick";

    String password() default "test-pass";

//    String email() default "test@test.com";

    String zipcode() default "test-zipcode";

    String address1() default "test-address1";

    String address2() default "test-address2";
}
