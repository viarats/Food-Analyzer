package com.fmi.food_analyzier.client;

import com.google.inject.BindingAnnotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@BindingAnnotation
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@interface ClientHandler {}
