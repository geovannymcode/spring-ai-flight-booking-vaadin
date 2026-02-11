package com.geovannycode.springfly.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.geovannycode.springfly.agents.SupervisorAgent;

@Service
public class ChatService {

     private static final Logger log = LoggerFactory.getLogger(ChatService.class);

    private final SupervisorAgent supervisorAgent;

    public ChatService(SupervisorAgent supervisorAgent) {
        this.supervisorAgent = supervisorAgent;
        log.info("ChatService initialized with multi-agent architecture");
        log.info("Supervisor prompt version: {}", supervisorAgent.getPromptVersion());
    }

    public String chat(String chatId, String userMessage) {
        log.info("Processing chat {} with message: {}", chatId, userMessage);

        try {
            String response = supervisorAgent.handle(chatId, userMessage);
            log.info("Successfully processed chat {}", chatId);
            return response;
        } catch (Exception e) {
            log.error("Error processing chat {}: {}", chatId, e.getMessage(), e);
            return "I apologize, but I'm experiencing technical difficulties. Please try again or contact our support team directly.";
        }
    }
}
