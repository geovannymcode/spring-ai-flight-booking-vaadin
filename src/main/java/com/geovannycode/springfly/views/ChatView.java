package com.geovannycode.springfly.views;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import com.geovannycode.springfly.service.ChatService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

@Route(value = "chat", layout = MainLayout.class)
@PageTitle("AI Assistant | SpringFly Airlines")
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
        H2 header = new H2("🤖 SpringFly AI Assistant");
        header.addClassNames(LumoUtility.Margin.MEDIUM, LumoUtility.Padding.MEDIUM);

        // Welcome message
        Div welcomeDiv = new Div();
        welcomeDiv.addClassNames(
            LumoUtility.Padding.MEDIUM,
            LumoUtility.Background.CONTRAST_5
        );
        welcomeDiv.add(new Paragraph(
            "👋 Hello! I'm your SpringFly Airlines AI assistant. " +
            "I can help you with booking inquiries, flight changes, cancellations, and more. " +
            "How can I assist you today?"
        ));

        // Messages container with scroll
        messagesContainer = new VerticalLayout();
        messagesContainer.addClassName("messages-container");
        messagesContainer.setPadding(true);
        messagesContainer.setSpacing(true);
        messagesContainer.getStyle()
            .set("overflow-y", "auto")
            .set("flex-grow", "1")
            .set("background-color", "var(--lumo-contrast-5pct)");

        // Input area
        messageField = new TextField();
        messageField.setPlaceholder("Type your message here...");
        messageField.setWidthFull();
        messageField.setClearButtonVisible(true);

        Button sendButton = new Button("Send", VaadinIcon.PAPERPLANE.create());
        sendButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        sendButton.addClickShortcut(Key.ENTER);
        sendButton.addClickListener(e -> sendMessage());

        HorizontalLayout inputLayout = new HorizontalLayout(messageField, sendButton);
        inputLayout.setWidthFull();
        inputLayout.setPadding(true);
        inputLayout.setSpacing(true);
        inputLayout.getStyle()
            .set("background-color", "var(--lumo-base-color)")
            .set("border-top", "1px solid var(--lumo-contrast-10pct)");

        // Layout assembly
        add(header, welcomeDiv, messagesContainer, inputLayout);
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

        // Get AI response (in a real app, this should be async)
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
        messageDiv.addClassName("message");
        messageDiv.getStyle()
            .set("padding", "12px 16px")
            .set("border-radius", "8px")
            .set("max-width", "70%")
            .set("margin", "4px")
            .set("align-self", isUser ? "flex-end" : "flex-start")
            .set("background-color", isUser
                ? "var(--lumo-primary-color-10pct)"
                : "var(--lumo-contrast-10pct)");

        String timeStr = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        String sender = isUser ? "You" : "AI Assistant";

        Div headerDiv = new Div();
        headerDiv.getStyle()
            .set("font-size", "var(--lumo-font-size-s)")
            .set("color", "var(--lumo-secondary-text-color)")
            .set("margin-bottom", "4px");
        headerDiv.setText(sender + " • " + timeStr);

        Div contentDiv = new Div();
        contentDiv.getStyle().set("white-space", "pre-wrap");
        contentDiv.setText(text);

        messageDiv.add(headerDiv, contentDiv);
        messagesContainer.add(messageDiv);
    }

    private Div createLoadingIndicator() {
        Div loadingDiv = new Div();
        loadingDiv.addClassName("loading-indicator");
        loadingDiv.getStyle()
            .set("padding", "12px 16px")
            .set("border-radius", "8px")
            .set("max-width", "70%")
            .set("margin", "4px")
            .set("align-self", "flex-start")
            .set("background-color", "var(--lumo-contrast-10pct)")
            .set("color", "var(--lumo-secondary-text-color)");

        loadingDiv.setText("🤖 AI Assistant is thinking...");
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
