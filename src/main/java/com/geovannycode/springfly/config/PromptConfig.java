package com.geovannycode.springfly.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class PromptConfig {

    @Bean
    public AgentPrompt supervisorAgentPrompt(
            @Value("${app.prompt.supervisor-agent}") String filename,
            @Value("classpath:prompts/${app.prompt.supervisor-agent}") Resource resource) {
        return loadPrompt(resource, filename);
    }

    @Bean
    public AgentPrompt bookingAgentPrompt(
            @Value("${app.prompt.booking-agent}") String filename,
            @Value("classpath:prompts/${app.prompt.booking-agent}") Resource resource) {
        return loadPrompt(resource, filename);
    }

    @Bean
    public AgentPrompt paymentAgentPrompt(
            @Value("${app.prompt.payment-agent}") String filename,
            @Value("classpath:prompts/${app.prompt.payment-agent}") Resource resource) {
        return loadPrompt(resource, filename);
    }

    @Bean
    public AgentPrompt escalationAgentPrompt(
            @Value("${app.prompt.escalation-agent}") String filename,
            @Value("classpath:prompts/${app.prompt.escalation-agent}") Resource resource) {
        return loadPrompt(resource, filename);
    }

    private AgentPrompt loadPrompt(Resource resource, String filename) {
        try {
            String content = resource.getContentAsString(StandardCharsets.UTF_8);
            String version = filename.replace(".md", "");
            return new AgentPrompt(content, version);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load prompt: " + filename, e);
        }
    }

    /**
     * Record to hold agent prompt content and version.
     */
    public record AgentPrompt(String content, String version) {
    }
}

