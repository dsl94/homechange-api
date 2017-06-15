package com.homechange.api.rest

import com.homechange.api.error.ErrorMessage
import com.homechange.api.error.MessageException
import com.homechange.api.rest.dto.message.MessageResponseDTO
import com.homechange.api.rest.dto.message.ReplyMessageDTO
import com.homechange.api.rest.dto.message.SendMessageDTO
import com.homechange.api.service.MessageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import javax.validation.Valid

/**
 * Created by Nemanja on 5/21/17.

 * Controller for message entity
 */
@RestController
@RequestMapping("/api/sec")
class MessageController {

    @Autowired
    private val messageService: MessageService? = null

    @RequestMapping(value = "/sendmessage", method = arrayOf(RequestMethod.POST), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun sendMessage(@Valid @RequestBody messageDTO: SendMessageDTO): ResponseEntity<Any> {
        try {
            return ResponseEntity.ok(messageService?.sendMessage(messageDTO))
        } catch (e: MessageException) {
            return ResponseEntity.badRequest().body(ErrorMessage(e.errorCode, e.message?:""))
        }

    }

    @RequestMapping(value = "/messagereply", method = arrayOf(RequestMethod.POST), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun replyOnMessage(@Valid @RequestBody replyMessageDTO: ReplyMessageDTO): ResponseEntity<Any> {
        try {
            return ResponseEntity.ok(messageService?.replyOnMessage(replyMessageDTO))
        } catch (e: MessageException) {
            return ResponseEntity.badRequest().body(ErrorMessage(e.errorCode, e.message?:""))
        }

    }

    @RequestMapping(value = "/usermessages", method = arrayOf(RequestMethod.GET), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun getUserMessages(): ResponseEntity<Any> {
        try {
            return ResponseEntity.ok(messageService?.getUsersMessages())
        } catch (e: MessageException) {
            return ResponseEntity.badRequest().body(ErrorMessage(e.errorCode, e.message?:""))
        }
    }
}
