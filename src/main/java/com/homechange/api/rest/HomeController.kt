package com.homechange.api.rest

import com.homechange.api.error.ErrorMessage
import com.homechange.api.error.HomeException
import com.homechange.api.rest.dto.home.CreateHomeRequestDTO
import com.homechange.api.rest.dto.home.HomeResponseDTO
import com.homechange.api.service.HomeService
import com.homechange.api.util.Utils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import javax.validation.Valid

/**
 * Created by Nemanja on 5/15/17.

 * Controller for home entity
 */
@RestController
@RequestMapping("/api")
class HomeController {

    @Autowired
    private val homeService: HomeService? = null

    @RequestMapping(value = "/sec/addhome", method = arrayOf(RequestMethod.POST), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun createHome(@Valid @RequestBody homeDTO: CreateHomeRequestDTO): ResponseEntity<Any> {
        try {
            return ResponseEntity.ok(homeService?.create(homeDTO))
        } catch (ex: HomeException) {
            return ResponseEntity.badRequest().body(ErrorMessage(ex.errorCode, ex.message?:""))
        }

    }

    @RequestMapping(value = "/sec/deletehome", method = arrayOf(RequestMethod.DELETE), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun deleteHome(@RequestParam id: Long?): ResponseEntity<Any> {
        try {
            homeService?.delete(id)
            return ResponseEntity(HttpStatus.OK)
        } catch (e: HomeException) {
            return ResponseEntity.badRequest().body(ErrorMessage(e.errorCode, e.message?:""))
        }

    }

    @RequestMapping(value = "/homedetails", method = arrayOf(RequestMethod.GET), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun homeDetails(@RequestParam(value = "username", required = false) username: String,
                    @RequestParam(value = "homeId", required = false) homeId: Long?): ResponseEntity<Any> {
        try {
            Utils.validateBothParamsNotPresent(username, homeId)
            Utils.validateBothParamsPresent(username, homeId)
            if (homeId != null) {
                return ResponseEntity.ok(homeService?.searchById(homeId))
            }
            return ResponseEntity.ok(homeService?.searchByUser(username))
        } catch (e: HomeException) {
            return ResponseEntity.badRequest().body(ErrorMessage(e.errorCode, e.message?:""))
        }

    }
}
