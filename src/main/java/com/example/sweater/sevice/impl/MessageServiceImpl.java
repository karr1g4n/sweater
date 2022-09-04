package com.example.sweater.sevice.impl;

import com.example.sweater.model.Message;
import com.example.sweater.repository.MessageRepository;
import com.example.sweater.sevice.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

    private MessageRepository messageRepository;

    @Override
    public List<Message> getAll() {
        return messageRepository.findAll();
    }

    @Override
    public Message addMessage(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public List<Message> findByTag(String filter) {
        return messageRepository.findByTag(filter);
    }
}
