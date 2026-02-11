package com.geovannycode.springfly.views;

import com.geovannycode.springfly.model.Booking;
import com.geovannycode.springfly.service.BookingService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Flight Bookings | SpringFly Airlines")
public class BookingsView extends VerticalLayout {

    private final Grid<Booking> grid;
    private final BookingService bookingService;

    public BookingsView(BookingService bookingService) {
        this.bookingService = bookingService;

        addClassName("bookings-view");
        setSizeFull();
        setPadding(true);
        setSpacing(true);

        // Header
        H2 header = new H2("✈️ Flight Bookings Dashboard");
        header.addClassNames(LumoUtility.Margin.Bottom.MEDIUM);

        // Create grid
        grid = new Grid<>(Booking.class, false);
        configureGrid();

        add(header, grid);
        refreshBookings();
    }

    private void configureGrid() {
        grid.addClassName("bookings-grid");
        grid.setSizeFull();
        grid.addThemeVariants(
            GridVariant.LUMO_ROW_STRIPES,
            GridVariant.LUMO_COLUMN_BORDERS
        );

        // Configure columns
        grid.addColumn(Booking::getBookingNumber)
            .setHeader("Booking #")
            .setAutoWidth(true)
            .setSortable(true);

        grid.addColumn(booking -> booking.getPassenger().getFullName())
            .setHeader("Passenger")
            .setAutoWidth(true)
            .setSortable(true);

        grid.addColumn(Booking::getFrom)
            .setHeader("From")
            .setAutoWidth(true)
            .setSortable(true);

        grid.addColumn(Booking::getTo)
            .setHeader("To")
            .setAutoWidth(true)
            .setSortable(true);

        grid.addColumn(Booking::getDate)
            .setHeader("Flight Date")
            .setAutoWidth(true)
            .setSortable(true);

        grid.addColumn(Booking::getSeatNumber)
            .setHeader("Seat")
            .setAutoWidth(true)
            .setSortable(true);

        grid.addColumn(Booking::getBookingClass)
            .setHeader("Class")
            .setAutoWidth(true)
            .setSortable(true);

        grid.addColumn(booking -> {
            var status = booking.getBookingStatus();
            return switch (status) {
                case CONFIRMED -> "✅ " + status;
                case CANCELLED -> "❌ " + status;
                case MODIFIED -> "🔄 " + status;
                case PENDING -> "⏳ " + status;
            };
        })
            .setHeader("Status")
            .setAutoWidth(true)
            .setSortable(true);

        grid.addColumn(booking -> booking.getPassenger().email())
            .setHeader("Email")
            .setAutoWidth(true);

        grid.addColumn(booking -> booking.getPassenger().phoneNumber())
            .setHeader("Phone")
            .setAutoWidth(true);
    }

    private void refreshBookings() {
        grid.setItems(bookingService.getAllBookings());
    }

}
