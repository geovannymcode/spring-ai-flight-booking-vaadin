package com.geovannycode.springfly.views;

import com.geovannycode.springfly.model.BookingDetails;
import com.geovannycode.springfly.model.BookingStatus;
import com.geovannycode.springfly.service.BookingService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;

import java.time.format.DateTimeFormatter;

public class BookingsView extends VerticalLayout {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MMM dd, yyyy");

    private final Grid<BookingDetails> grid;
    private final BookingService bookingService;

    public BookingsView(BookingService bookingService) {
        this.bookingService = bookingService;

        addClassName("bookings-view");
        setSizeFull();
        setPadding(false);
        setSpacing(false);

        // Header
        H2 title = new H2("✈ SpringFly Bookings");

        Icon refreshIcon = VaadinIcon.REFRESH.create();
        refreshIcon.getStyle().set("color", "white");
        Button refreshButton = new Button(refreshIcon);
        refreshButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        refreshButton.addClickListener(e -> refreshBookings());

        HorizontalLayout header = new HorizontalLayout(title, refreshButton);
        header.addClassName("bookings-header");
        header.setWidthFull();
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        // Grid
        grid = new Grid<>(BookingDetails.class, false);
        configureGrid();

        add(header, grid);
        expand(grid);
        refreshBookings();
    }

    private void configureGrid() {
        grid.addClassName("bookings-grid");
        grid.setSizeFull();
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        grid.addColumn(BookingDetails::bookingNumber)
            .setHeader("Number")
            .setAutoWidth(true)
            .setSortable(true)
            .setFlexGrow(0);

        grid.addColumn(bd -> bd.firstName() + " " + bd.lastName())
            .setHeader("Name")
            .setAutoWidth(true)
            .setSortable(true);

        grid.addColumn(bd -> bd.date().format(DATE_FORMAT))
            .setHeader("Date")
            .setAutoWidth(true)
            .setSortable(true);

        grid.addColumn(new ComponentRenderer<>(bd -> createStatusBadge(bd.bookingStatus())))
            .setHeader("Status")
            .setAutoWidth(true);

        grid.addColumn(BookingDetails::from)
            .setHeader("From")
            .setAutoWidth(true)
            .setSortable(true);

        grid.addColumn(BookingDetails::to)
            .setHeader("To")
            .setAutoWidth(true)
            .setSortable(true);

        grid.addColumn(BookingDetails::seatNumber)
            .setHeader("Seat")
            .setAutoWidth(true)
            .setSortable(true);

        grid.addColumn(BookingDetails::bookingClass)
            .setHeader("Class")
            .setAutoWidth(true)
            .setSortable(true);
    }

    private Div createStatusBadge(BookingStatus status) {
        Div badge = new Div();
        badge.setText(status.name());
        badge.addClassName("status-badge");

        switch (status) {
            case CONFIRMED -> badge.addClassName("confirmed");
            case COMPLETED -> badge.addClassName("completed");
            case CANCELLED -> badge.addClassName("cancelled");
        }

        return badge;
    }

    private void refreshBookings() {
        grid.setItems(bookingService.getAllBookings());
    }

}
