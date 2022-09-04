package com.example.sweater.sevice;

import com.example.sweater.model.Message;

import java.util.List;

public interface MessageService {

    List<Message> getAll();

    Message addMessage(Message message);

    List<Message> findByTag(String filter);
}
