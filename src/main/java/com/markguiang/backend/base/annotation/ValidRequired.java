package com.markguiang.backend.base.annotation;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented

@Valid
@NotNull
public @interface ValidRequired {
}