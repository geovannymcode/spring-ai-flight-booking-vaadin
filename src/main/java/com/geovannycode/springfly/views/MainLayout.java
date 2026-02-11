package com.geovannycode.springfly.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;

public class MainLayout extends AppLayout {

    public MainLayout() {
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("SpringFly Airlines");
        logo.addClassNames(
            LumoUtility.FontSize.LARGE,
            LumoUtility.Margin.MEDIUM
        );

        Span badge = new Span("AI-Powered");
        badge.getElement().getThemeList().add("badge success");

        var header = new HorizontalLayout(new DrawerToggle(), logo, badge);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidthFull();
        header.addClassNames(
            LumoUtility.Padding.Vertical.NONE,
            LumoUtility.Padding.Horizontal.MEDIUM
        );

        addToNavbar(header);
    }

    private void createDrawer() {
        SideNav nav = new SideNav();

        SideNavItem bookingsItem = new SideNavItem(
            "Flight Bookings",
            BookingsView.class,
            VaadinIcon.AIRPLANE.create()
        );

        SideNavItem chatItem = new SideNavItem(
            "AI Assistant",
            ChatView.class,
            VaadinIcon.CHAT.create()
        );

        nav.addItem(bookingsItem, chatItem);

        addToDrawer(nav);
    }
}
