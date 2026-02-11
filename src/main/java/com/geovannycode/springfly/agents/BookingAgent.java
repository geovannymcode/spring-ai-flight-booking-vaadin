package com.geovannycode.springfly.agents;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

import com.geovannycode.springfly.config.PromptConfig.AgentPrompt;
import com.geovannycode.springfly.service.BookingTools;
import com.geovannycode.springfly.service.ValidationTools;

@Component
public class BookingAgent {

    private static final Logger log = LoggerFactory.getLogger(BookingAgent.class);
    private final ChatClient chatClient;
    private final String promptVersion;

    public BookingAgent(
            ChatClient.Builder chatClientBuilder,
            ChatMemory chatMemory,
            VectorStore vectorStore,
            BookingTools bookingTools,
            ValidationTools validationTools,
            AgentPrompt bookingAgentPrompt) {

        this.promptVersion = bookingAgentPrompt.version();

        log.info("Initializing BookingAgent with prompt version: {}", promptVersion);

        this.chatClient = chatClientBuilder
                .defaultSystem(bookingAgentPrompt.content())
                .defaultAdvisors(
                    MessageChatMemoryAdvisor.builder(chatMemory).build(),
                    QuestionAnswerAdvisor.builder(vectorStore).build()
                )
                .defaultTools(
                    bookingTools,
                    validationTools
                )
                .build();
    }

    public String getPromptVersion() {
        return promptVersion;
    }

    /**
     * Handles booking-related customer requests.
     *
     * @param chatId unique conversation identifier
     * @param userMessage customer message
     * @return agent response
     */
    public String handle(String chatId, String userMessage) {
        log.info("BookingAgent handling request for chat {}", chatId);

        return chatClient.prompt()
                .user(userMessage)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, chatId + "-booking"))
                .call()
                .content();
    }
}
