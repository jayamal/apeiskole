package com.jk.schoo.management.spring.report.ui;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.server.StreamResource;
import org.vaadin.alejandro.PdfBrowserViewer;

/**
 * Created by jayamalk on 8/22/2018.
 */
public class ReportViewer extends Dialog {

    public ReportViewer(StreamResource streamResource) {
        PdfBrowserViewer viewer = new PdfBrowserViewer(streamResource);
        viewer.setHeight("100%");
        add(viewer);
    }
}
