package com.example.binding

import groovy.transform.CompileStatic
import io.micronaut.context.annotation.AliasFor
import io.micronaut.core.bind.annotation.Bindable

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.Target

import static java.lang.annotation.RetentionPolicy.RUNTIME

@CompileStatic
@Target([ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE])
@Retention(RUNTIME)
@Bindable
@interface ShoppingCartId {
    @AliasFor(annotation = Bindable.class, member = "value")
    String value() default ""
}
