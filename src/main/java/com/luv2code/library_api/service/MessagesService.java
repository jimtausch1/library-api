package com.luv2code.library_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luv2code.library_api.entity.Message;
import com.luv2code.library_api.model.AdminQuestionRequest;
import com.luv2code.library_api.repositories.MessageRepository;

import java.util.Optional;

@Service
@Transactional
public class MessagesService {

  private MessageRepository messageRepository;

  @Autowired
  public MessagesService(MessageRepository messageRepository) {
    this.messageRepository = messageRepository;
  }

  public Page<Message> getMessages(String userEmail) throws Exception {
    Pageable pageable = PageRequest.of(0, 5);
    Page<Message> messages = messageRepository.findByUserEmail(userEmail, pageable);
    return messages;
  }

  public void postMessage(Message messageRequest, String userEmail) {
    Message message = new Message(messageRequest.getTitle(), messageRequest.getQuestion());
    message.setUserEmail(userEmail);
    messageRepository.save(message);
  }

  public void putMessage(AdminQuestionRequest adminQuestionRequest, String userEmail) throws Exception {
    Optional<Message> message = messageRepository.findById(adminQuestionRequest.getId());
    if (!message.isPresent()) {
      throw new Exception("Message not found");
    }

    message.get().setAdminEmail(userEmail);
    message.get().setResponse(adminQuestionRequest.getResponse());
    message.get().setClosed(true);
    messageRepository.save(message.get());
  }

}
