package com.jk.schoo.management.spring;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PageConfigurator;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

@Route(value = "login")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class LoginView extends HorizontalLayout implements PageConfigurator, AfterNavigationObserver {

	@Override
	public void configurePage(InitialPageSettings settings) {

	}

	@Autowired
	public LoginView(AuthenticationManager authenticationManager, ApplicationProperties applicationProperties) {
		Label title = new Label(applicationProperties.getApplicationName());
		Image logo = new Image("frontend/images/logo.png", "");
		TextField userNameTF = new TextField("User Name");
		userNameTF.setValue("admin@vaadin.com");
		userNameTF.setRequired(Boolean.TRUE);
		PasswordField passwordTF = new PasswordField("Password");
		passwordTF.setValue("admin");
		passwordTF.setRequired(Boolean.TRUE);
		Button button = new Button("Login");
		VerticalLayout loginContainer = new VerticalLayout();
		loginContainer.setWidth("100%");
		VerticalLayout loginForm = new VerticalLayout();
		setClassName("login-bg");
		loginForm.add(logo, title, userNameTF, passwordTF, button);
		loginForm.setSizeUndefined();
		loginContainer.add(loginForm);
		loginForm.setAlignItems(Alignment.CENTER);
		add(loginContainer);
		setSizeFull();
		loginContainer.setAlignItems(Alignment.CENTER);
		setAlignItems(Alignment.CENTER);
		setVerticalComponentAlignment(Alignment.CENTER);
		button.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
			@Override
			public void onComponentEvent(ClickEvent<Button> buttonClickEvent) {
				if (true) {
					try {
						if(!(userNameTF.isInvalid() && passwordTF.isInvalid())) {
							Authentication auth = new UsernamePasswordAuthenticationToken(userNameTF.getValue(), passwordTF.getValue());
							Authentication authenticated = authenticationManager.authenticate(auth);
							SecurityContextHolder.getContext().setAuthentication(authenticated);
							UI.getCurrent().navigate("offering");
						}else{
							Notification.show("Authentication error", 1000, Notification.Position.BOTTOM_CENTER);
						}
					} catch (AuthenticationException aExc) {
						Notification.show("Authentication error", 1000, Notification.Position.BOTTOM_CENTER);
					}
				}
			}
		});
	}

	@Override
	public void afterNavigation(AfterNavigationEvent event) {
		boolean error = event.getLocation().getQueryParameters().getParameters().containsKey("error");
		if(error){
			Notification.show("Authentication error", 1000, Notification.Position.BOTTOM_CENTER);
		}
	}

}
