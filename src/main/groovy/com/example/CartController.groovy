package com.example;

import com.example.binding.ShoppingCartId
import groovy.transform.CompileStatic
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@CompileStatic
@Controller("/cart")
class CartController {

    @Get("/")
    HttpResponse<String> loadCart(@ShoppingCartId Long id) {
        HttpResponse.ok("Session:${id}".toString())
    }
}
