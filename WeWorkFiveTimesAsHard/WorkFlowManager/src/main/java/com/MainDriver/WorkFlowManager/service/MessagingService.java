package com.MainDriver.WorkFlowManager.service;

import com.MainDriver.WorkFlowManager.model.messaging.Message;

import java.util.List;

public interface MessagingService {
    void saveMessage(Message message, String from, String to);
    void deleteMessage(String username, Integer messageID);
    Message getByUsernameAndMessageId(String username, Integer messageId);
    List<Message> getUsersMessages(String username);
    List<Message>  getByUserWhereFromIsLike(String username, String fromLike);
}
