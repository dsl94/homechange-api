package com.homechange.api.rest;

import com.homechange.api.error.ErrorMessage;
import com.homechange.api.error.HomeException;
import com.homechange.api.rest.dto.home.CreateHomeRequestDTO;
import com.homechange.api.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by Nemanja on 5/15/17.
 *
 * Controller for home entity
 */
@RestController
@RequestMapping("/api")
public class HomeController {

	@Autowired
	private HomeService homeService;

	@RequestMapping(value = "/sec/addhome", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity createHome(@Valid @RequestBody CreateHomeRequestDTO homeDTO) {
		try{
			return ResponseEntity.ok(homeService.create(homeDTO));
		} catch (HomeException ex) {
			return ResponseEntity.badRequest().body(new ErrorMessage(ex.getErrorCode(), ex.getMessage()));
		}
	}

	@RequestMapping(value = "/sec/deletehome", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity deleteHome(@RequestParam Long id) {
		try {
			homeService.delete(id);
			return new ResponseEntity(HttpStatus.OK);
		} catch (HomeException e) {
			return ResponseEntity.badRequest().body(new ErrorMessage(e.getErrorCode(), e.getMessage()));
		}
	}
}
