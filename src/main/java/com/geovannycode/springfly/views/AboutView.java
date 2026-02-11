package com.geovannycode.springfly.views;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("About")
@Route(value = "about", layout = MainLayout.class)
public class AboutView extends VerticalLayout {

    public AboutView() {
        addClassName("about-view");
        setSizeFull();
        setAlignItems(FlexComponent.Alignment.CENTER);
        getStyle().set("padding", "40px 20px");

        // Hero section
        Div heroCard = new Div();
        heroCard.addClassName("about-hero");

        H2 heroTitle = new H2("✈ SpringFly Airlines");
        heroTitle.addClassName("about-hero-title");

        Paragraph heroDesc = new Paragraph(
            "Sistema inteligente de gestión de reservas de vuelos con asistente conversacional " +
            "impulsado por IA. Construido con arquitectura multi-agente para ofrecer una experiencia " +
            "de soporte al cliente automatizada y eficiente."
        );
        heroDesc.addClassName("about-hero-desc");

        heroCard.add(heroTitle, heroDesc);

        // Tech stack cards
        HorizontalLayout techCards = new HorizontalLayout();
        techCards.addClassName("about-tech-cards");
        techCards.setWidthFull();
        techCards.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        techCards.add(
            createTechCard(VaadinIcon.COGS, "Spring Boot 3.5", "Backend robusto con Spring Framework"),
            createTechCard(VaadinIcon.DESKTOP, "Vaadin Flow 24", "UI server-side en Java puro"),
            createTechCard(VaadinIcon.BRAIN, "Spring AI", "Integración con modelos de lenguaje"),
            createTechCard(VaadinIcon.USERS, "Multi-Agente", "Supervisor, Booking, Payment, Escalation")
        );

        // Architecture section
        Div archCard = new Div();
        archCard.addClassName("about-card");

        H3 archTitle = new H3("🏗 Arquitectura");
        archTitle.addClassName("about-card-title");

        Paragraph archDesc = new Paragraph(
            "La aplicación implementa un patrón de agentes supervisados donde un agente supervisor " +
            "analiza las solicitudes del cliente y las enruta al agente especializado correspondiente: " +
            "BookingAgent para gestión de reservas, PaymentAgent para consultas de pagos y tarifas, " +
            "y EscalationAgent para quejas y problemas complejos. " +
            "Se utiliza RAG (Retrieval-Augmented Generation) con PGVector para consultar los términos de servicio."
        );

        archCard.add(archTitle, archDesc);

        // Author section
        Div authorCard = new Div();
        authorCard.addClassName("about-card");

        H3 authorTitle = new H3("👨‍💻 Desarrollador");
        authorTitle.addClassName("about-card-title");

        Paragraph authorDesc = new Paragraph(
            "Desarrollado por Geovanny Mendoza, ingeniero de software con más de 12 años de experiencia " +
            "en desarrollo backend con Java y Kotlin. Líder del grupo de usuarios de Java en Barranquilla " +
            "y conferencista en eventos como JConf y DevFest."
        );

        Anchor link = new Anchor("https://geovannycode.com", "geovannycode.com");
        link.setTarget("_blank");
        link.addClassName("about-link");

        authorCard.add(authorTitle, authorDesc, link);

        add(heroCard, techCards, archCard, authorCard);
    }

    private Div createTechCard(VaadinIcon vaadinIcon, String title, String description) {
        Div card = new Div();
        card.addClassName("about-tech-card");

        Icon icon = vaadinIcon.create();
        icon.addClassName("about-tech-icon");

        Span titleSpan = new Span(title);
        titleSpan.addClassName("about-tech-title");

        Span descSpan = new Span(description);
        descSpan.addClassName("about-tech-desc");

        card.add(icon, titleSpan, descSpan);
        return card;
    }
}
