package com.geovannycode.springfly.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.theme.lumo.LumoUtility;

@StyleSheet("styles.css")
public class MainLayout extends AppLayout {

    public MainLayout() {
        DrawerToggle toggle = new DrawerToggle();

        H1 title = new H1("✈ SpringFly Airlines");
        title.getStyle()
            .set("font-size", "var(--lumo-font-size-l)")
            .set("margin", "0")
            .set("padding-left", "8px");

        SideNav nav = new SideNav();
        nav.addItem(new SideNavItem("Chat", DashboardView.class, VaadinIcon.COMMENTS.create()));
        nav.addItem(new SideNavItem("About", AboutView.class, VaadinIcon.INFO_CIRCLE.create()));

        Scroller scroller = new Scroller(nav);
        scroller.setClassName(LumoUtility.Padding.SMALL);

        addToDrawer(scroller);
        addToNavbar(toggle, title);

        setPrimarySection(Section.DRAWER);
    }
}
