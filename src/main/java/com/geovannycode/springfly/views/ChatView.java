package com.geovannycode.springfly.views;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import com.geovannycode.springfly.service.ChatService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class ChatView extends VerticalLayout {

    private final ChatService chatService;
    private final VerticalLayout messagesContainer;
    private final TextField messageField;
    private final String chatId;

    public ChatView(ChatService chatService) {
        this.chatService = chatService;
        this.chatId = UUID.randomUUID().toString();

        addClassName("chat-view");
        setSizeFull();
        setPadding(false);
        setSpacing(false);

        // Header
        Div headerDiv = new Div();
        headerDiv.addClassName("chat-header");
        headerDiv.setWidthFull();
        H2 headerTitle = new H2("🤖 SpringFly Concierge");
        headerDiv.add(headerTitle);

        // Messages container with scroll
        messagesContainer = new VerticalLayout();
        messagesContainer.addClassName("messages-container");
        messagesContainer.setPadding(true);
        messagesContainer.setSpacing(false);
        messagesContainer.getStyle()
            .set("overflow-y", "auto")
            .set("flex-grow", "1");

        // Input area
        messageField = new TextField();
        messageField.setPlaceholder("Message");
        messageField.setWidthFull();
        messageField.setClearButtonVisible(true);

        Button sendButton = new Button(VaadinIcon.PAPERPLANE.create());
        sendButton.addClassName("chat-send-button");
        sendButton.addClickShortcut(Key.ENTER);
        sendButton.addClickListener(e -> sendMessage());

        HorizontalLayout inputLayout = new HorizontalLayout(messageField, sendButton);
        inputLayout.addClassName("chat-input-area");
        inputLayout.setWidthFull();
        inputLayout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        inputLayout.expand(messageField);

        // Layout assembly
        add(headerDiv, messagesContainer, inputLayout);
        expand(messagesContainer);
    }

    private void sendMessage() {
        String userMessage = messageField.getValue().trim();
        if (userMessage.isEmpty()) {
            return;
        }

        // Clear input field
        messageField.clear();
        messageField.focus();

        // Add user message to UI
        addMessage(userMessage, true);

        // Show loading indicator
        Div loadingIndicator = createLoadingIndicator();
        messagesContainer.add(loadingIndicator);
        scrollToBottom();

        // Get AI response
        getUI().ifPresent(ui -> ui.access(() -> {
            try {
                String aiResponse = chatService.chat(chatId, userMessage);

                // Remove loading indicator
                messagesContainer.remove(loadingIndicator);

                // Add AI response to UI
                addMessage(aiResponse, false);
                scrollToBottom();

            } catch (Exception e) {
                messagesContainer.remove(loadingIndicator);
                Notification.show(
                    "Error communicating with AI assistant. Please try again.",
                    3000,
                    Notification.Position.MIDDLE
                ).addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        }));
    }

    private void addMessage(String text, boolean isUser) {
        Div messageDiv = new Div();
        messageDiv.addClassName("message-bubble");
        messageDiv.addClassName(isUser ? "user" : "assistant");

        // Sender header with avatar
        Div senderDiv = new Div();
        senderDiv.addClassName("message-sender");

        Span avatar = new Span(isUser ? "Y" : "A");
        avatar.addClassName("sender-avatar");
        avatar.addClassName(isUser ? "user" : "assistant");

        String sender = isUser ? "You" : "Assistant";
        String timeStr = LocalTime.now().format(DateTimeFormatter.ofPattern("M/d/yy, h:mm a"));

        Span senderName = new Span(sender);
        Span timeSpan = new Span(timeStr);
        timeSpan.addClassName("message-time");

        if (isUser) {
            senderDiv.add(senderName, timeSpan, avatar);
        } else {
            senderDiv.add(avatar, senderName, timeSpan);
        }

        // Content
        Div contentDiv = new Div();
        contentDiv.getStyle().set("white-space", "pre-wrap");
        contentDiv.setText(text);

        messageDiv.add(senderDiv, contentDiv);
        messagesContainer.add(messageDiv);
    }

    private Div createLoadingIndicator() {
        Div loadingDiv = new Div();
        loadingDiv.addClassName("message-bubble");
        loadingDiv.addClassName("assistant");
        loadingDiv.addClassName("loading-indicator");

        Div senderDiv = new Div();
        senderDiv.addClassName("message-sender");
        Span avatar = new Span("A");
        avatar.addClassName("sender-avatar");
        avatar.addClassName("assistant");
        Span name = new Span("Assistant");
        senderDiv.add(avatar, name);

        Div contentDiv = new Div();
        contentDiv.setText("Thinking...");

        loadingDiv.add(senderDiv, contentDiv);
        return loadingDiv;
    }

    private void scrollToBottom() {
        getUI().ifPresent(ui -> ui.access(() -> {
            messagesContainer.getElement().executeJs(
                "this.scrollTop = this.scrollHeight;"
            );
        }));
    }
}
