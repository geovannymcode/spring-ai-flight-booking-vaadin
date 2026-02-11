package com.geovannycode.springfly.agents;

import com.geovannycode.springfly.config.PromptConfig.AgentPrompt;
import com.geovannycode.springfly.service.ValidationTools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

@Component
public class PaymentAgent {

    private static final Logger log = LoggerFactory.getLogger(PaymentAgent.class);
    private final ChatClient chatClient;
    private final String promptVersion;

    public PaymentAgent(
            ChatClient.Builder chatClientBuilder,
            ChatMemory chatMemory,
            VectorStore vectorStore,
            ValidationTools validationTools,
            AgentPrompt paymentAgentPrompt) {

        this.promptVersion = paymentAgentPrompt.version();

        log.info("Initializing PaymentAgent with prompt version: {}", promptVersion);

        this.chatClient = chatClientBuilder
                .defaultSystem(paymentAgentPrompt.content())
                .defaultAdvisors(
                    MessageChatMemoryAdvisor.builder(chatMemory).build(),
                    QuestionAnswerAdvisor.builder(vectorStore).build()
                )
                .defaultTools(validationTools)
                .build();
    }

    public String getPromptVersion() {
        return promptVersion;
    }

    /**
     * Handles payment and refund-related customer requests.
     *
     * @param chatId unique conversation identifier
     * @param userMessage customer message
     * @return agent response
     */
    public String handle(String chatId, String userMessage) {
        log.info("PaymentAgent handling request for chat {}", chatId);

        return chatClient.prompt()
                .user(userMessage)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, chatId + "-payment"))
                .call()
                .content();
    }
}
