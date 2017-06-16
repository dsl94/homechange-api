package com.homechange.api.rest

import com.homechange.api.error.ErrorCode
import com.homechange.api.error.ErrorMessage
import com.homechange.api.error.UserException
import com.homechange.api.rest.dto.user.UserRequestDTO
import com.homechange.api.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.*

import javax.validation.Valid

/**
 * Created by Nemanja on 5/14/17.

 * Rest controller for User entity
 */
@RestController
@RequestMapping("/api")
class UserController {

    @Autowired
    private val userService: UserService? = null

    @RequestMapping(value = "/register", method = arrayOf(RequestMethod.POST), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun register(@Valid @RequestBody userRequestDTO: UserRequestDTO): ResponseEntity<Any> {
        try {
            return ResponseEntity.ok(userService?.save(userRequestDTO))
        } catch (e: UserException) {
            return ResponseEntity.badRequest().body(ErrorMessage(e.errorCode, e.message?:""))
        }

    }

    @RequestMapping(value = "/user/findbyid", method = arrayOf(RequestMethod.GET), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun findUserById(@RequestParam("id") id: Long?): ResponseEntity<Any> {
        try {
            return ResponseEntity.ok(userService?.get(id))
        } catch (e: UserException) {
            return ResponseEntity.badRequest().body(ErrorMessage(e.errorCode, e.message?:""))
        }

    }

    @RequestMapping(value = "/user/findbyemail", method = arrayOf(RequestMethod.GET), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun findUserByEmail(@RequestParam("email") email: String): ResponseEntity<Any> {
        try {
            return ResponseEntity.ok(userService?.getByEmail(email))
        } catch (e: UserException) {
            return ResponseEntity.badRequest().body(ErrorMessage(e.errorCode, e.message?:""))
        }

    }

    @RequestMapping(value = "/user/findbyusername", method = arrayOf(RequestMethod.GET), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun findUserByUsername(@RequestParam("username") username: String): ResponseEntity<Any> {
        try {
            return ResponseEntity.ok(userService?.get(username))
        } catch (e: UserException) {
            return ResponseEntity.badRequest().body(ErrorMessage(e.errorCode, e.message?:""))
        }

    }

    @RequestMapping(value = "/sec/myprofile", method = arrayOf(RequestMethod.GET), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun loggedInUserProfile(): ResponseEntity<Any> {
        try {
            return ResponseEntity.ok(userService?.loggedInUserProfile())
        } catch (e: UserException) {
            return ResponseEntity.badRequest().body(ErrorMessage(e.errorCode, e.message?:""))
        }

    }

    @RequestMapping(value = "/profile", method = arrayOf(RequestMethod.GET), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun publicUserProfile(@RequestParam("username") username: String): ResponseEntity<Any> {
        try {
            return ResponseEntity.ok(userService?.publicInUserProfile(username))
        } catch (e: UserException) {
            return ResponseEntity.badRequest().body(ErrorMessage(e.errorCode, e.message?:""))
        }

    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMyException(exception: Exception): ResponseEntity<Any> {
        return ResponseEntity.badRequest().body(ErrorMessage(ErrorCode.INVALID_PARAMETERS,
                "Some of mandatory parameters are not present"))
    }
}
