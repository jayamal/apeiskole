package com.jk.schoo.management.spring;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PageConfigurator;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * The main view contains a simple label element and a template element.
 */
@HtmlImport("styles/shared-styles.html")
@Route
@UIScope
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class MainView extends VerticalLayout implements RouterLayout, BeforeEnterObserver, AfterNavigationObserver, PageConfigurator {

    private Tabs tabs;
    private List<String> routes;
    private String defaultRoute = "invoice";
    private String logoutRoute = "logout";
    private String currentRoute;
    private ApplicationProperties applicationProperties;

    @Autowired
    public MainView(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
        init();
    }

    public void init(){
        Image logo = new Image("frontend/images/logo.png", "");
        HorizontalLayout appHeader = new HorizontalLayout();
        Label titleLbl = new Label(applicationProperties.getApplicationName());
        titleLbl.setWidth("300px");
        //Navigation
        routes = new ArrayList<>();
        routes.add("customer");
        routes.add("offering");
        routes.add("payment_term");
        routes.add("invoice");
        Tab customerTab = new Tab("Customers");
        Tab offeringTab = new Tab("Products & Services");
        Tab invoicesTab = new Tab("Invoices");
        Tab paymentTermsTab = new Tab("Payment Terms");
        tabs = new Tabs(customerTab, offeringTab, paymentTermsTab, invoicesTab);
        Button btnLogout = new Button("Logout");
        btnLogout.setWidth("100px");
        appHeader.add(logo);
        appHeader.add(titleLbl);
        appHeader.add(tabs);
        appHeader.add(btnLogout);
        appHeader.setAlignItems(Alignment.CENTER);
        add(appHeader);
        tabs.setWidth("100%");
        appHeader.setClassName("app-header");
        tabs.addSelectedChangeListener(e -> navigate());
        btnLogout.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            @Override
            public void onComponentEvent(ClickEvent<Button> buttonClickEvent) {
                UI.getCurrent().getPage().executeJavaScript("location.assign('logout')");
            }
        });
        setPadding(Boolean.FALSE);
        setMargin(Boolean.FALSE);
        setSpacing(Boolean.FALSE);
        setSizeFull();
    }

    private void navigate() {
        int selectedIndex = tabs.getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < routes.size()) {
            String route = routes.get(selectedIndex);
            if (!route.equals(currentRoute)) {
                UI.getCurrent().navigate(route);
            }
        }
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        System.out.println("Before Event");
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        String route = afterNavigationEvent.getLocation().getFirstSegment().isEmpty() ? defaultRoute : afterNavigationEvent.getLocation().getFirstSegment();
        currentRoute = route;
        tabs.setSelectedIndex(routes.indexOf(route));
    }

    @Override
    public void configurePage(InitialPageSettings initialPageSettings) {
    }
}
