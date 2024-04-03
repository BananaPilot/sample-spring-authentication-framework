package com.bananapilot.samplespringauthenticationframework.filtes.annotations;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FloorLevelAuthorization {
    public String floorRole();
}
