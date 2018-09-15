package com.jk.schoo.management.spring;

import com.vaadin.flow.component.notification.Notification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by jayamalk on 7/6/2018.
 */
@Configuration
@PropertySource("classpath:application.properties")
public class ApplicationProperties {

    public static final int NOTIFICATION_DELAY = 5000;
    public static final String EXPORT_PATH = "reports";
    public static final Notification.Position NOTIFICATION_POSITION = Notification.Position.MIDDLE;

    @Value( "${application.global.name}" )
    private String applicationName;

    public String getApplicationName() {
        return applicationName;
    }
}
