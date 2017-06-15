package com.homechange.api.rest

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

import java.util.HashMap

/**
 * Created by Nemanja on 5/14/17.

 * Class that handles all exceptions not catched explicitly
 */
@EnableWebMvc
@ControllerAdvice
class ServiceExceptionHandler : ResponseEntityExceptionHandler() {

    override fun handleNoHandlerFoundException(ex: NoHandlerFoundException, headers: HttpHeaders, status: HttpStatus,
                                               request: WebRequest): ResponseEntity<Any> {
        val responseBody = HashMap<String, String>()
        responseBody.put("path", request.contextPath)
        responseBody.put("message", "The URL you have reached is not in service at this time (404).")
        return ResponseEntity(responseBody, HttpStatus.NOT_FOUND)
    }
}