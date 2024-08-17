package com.luv2code.library_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.library_api.entity.Message;
import com.luv2code.library_api.model.AdminQuestionRequest;
import com.luv2code.library_api.service.MessagesService;
import com.luv2code.library_api.utils.ExtractJWT;

@RestController
@RequestMapping("/api/messages")
public class MessagesController {

    private MessagesService messagesService;

    @Autowired
    public MessagesController(MessagesService messagesService) {
        this.messagesService = messagesService;
    }

    @GetMapping("/secure/search")
    public Page<Message> getMessages(@RequestHeader(value = "Authorization") String token)
            throws Exception {
        String userEmail = ExtractJWT.emailJWTExtraction(token);
        return messagesService.getMessages(userEmail);
    }

    @GetMapping("/secure/search/open")
    public Page<Message> getOpenMessages(@RequestHeader(value = "Authorization") String token)
            throws Exception {
        return messagesService.getOpenMessages(false);
    }

    @PostMapping("/secure/add/message")
    public void postMessage(@RequestHeader(value = "Authorization") String token,
            @RequestBody Message messageRequest) {
        String userEmail = ExtractJWT.emailJWTExtraction(token);
        messagesService.postMessage(messageRequest, userEmail);
    }

    @PutMapping("/secure/admin/message")
    public void putMessage(@RequestHeader(value = "Authorization") String token,
            @RequestBody AdminQuestionRequest adminQuestionRequest) throws Exception {
        String userEmail = ExtractJWT.emailJWTExtraction(token);
        String admin = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
        if (admin == null || !admin.equals("admin")) {
            throw new Exception("Administration page only.");
        }
        messagesService.putMessage(adminQuestionRequest, userEmail);
    }

}