package com.geovannycode.springfly.views;

import com.geovannycode.springfly.service.BookingService;
import com.geovannycode.springfly.service.ChatService;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("")
@PageTitle("SpringFly")
@StyleSheet("styles.css")
public class MainLayout extends HorizontalLayout {

    public MainLayout(BookingService bookingService, ChatService chatService) {
        addClassName("main-layout");
        setSizeFull();

        BookingsView bookingsView = new BookingsView(bookingService);
        bookingsView.getStyle()
            .set("flex", "2")
            .set("min-width", "0");

        ChatView chatView = new ChatView(chatService, bookingService);
        chatView.setOnBookingChanged(bookingsView::refreshBookings);
        chatView.getStyle()
            .set("flex", "1")
            .set("min-width", "320px")
            .set("max-width", "420px");

        add(bookingsView, chatView);
    }
}
