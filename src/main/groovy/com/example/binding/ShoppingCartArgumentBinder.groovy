package com.example.binding

import groovy.transform.CompileStatic
import io.micronaut.core.convert.ArgumentConversionContext
import io.micronaut.core.convert.ConversionService
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.bind.binders.AnnotatedRequestArgumentBinder
import io.micronaut.http.cookie.Cookie
import io.micronaut.jackson.serialize.JacksonObjectSerializer
import javax.inject.Singleton

@CompileStatic
@Singleton
class ShoppingCartArgumentBinder implements AnnotatedRequestArgumentBinder<ShoppingCartId, Object> {

    private final ConversionService<?> conversionService
    private final JacksonObjectSerializer objectSerializer

    ShoppingCartArgumentBinder(ConversionService<?> conversionService, JacksonObjectSerializer objectSerializer) {
        this.conversionService = conversionService
        this.objectSerializer = objectSerializer
    }

    @Override
    Class<ShoppingCartId> getAnnotationType() {
        return ShoppingCartId.class
    }

    @Override
    BindingResult<Object> bind(ArgumentConversionContext<Object> context, HttpRequest<?> source) {
        String parameterName = context.getAnnotationMetadata().stringValue(ShoppingCartId).orElse(context.getArgument().getName())
        Cookie cookie = source.getCookies().get("shoppingCart")

        if (cookie != null) {
            Optional<Map<String, Object>> cookieValue

            cookieValue = objectSerializer.deserialize(cookie.getValue().getBytes(), Argument.mapOf(String, Object))

            if(cookieValue.isPresent()) {
                Optional<Object> value = conversionService.convert(cookieValue.get().get(parameterName), context)

                return new BindingResult<Object>() {
                    @Override
                    Optional<Object> getValue() {
                        return value
                    }
                }

            }
        }

        return BindingResult.EMPTY
    }
}
