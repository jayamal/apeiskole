package com.jk.schoo.management.spring.report.util;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.*;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.Transparency;
import com.vaadin.flow.server.InputStreamFactory;
import com.vaadin.flow.server.StreamResource;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import java.awt.*;
import java.io.*;
import java.util.HashMap;

/**
 * Created by jayamalk on 7/10/2018.
 */
public class ReportUtil {

    private ReportUtil() {
    }

    public static void exportReportPdf(FastReportBuilder drb, String path, JRDataSource dataSource) throws JRException, FileNotFoundException {
        DynamicReport dynamicReport = drb.build();
        JasperPrint jasperPrint;
        if(dataSource != null) {
            jasperPrint = DynamicJasperHelper.generateJasperPrint(dynamicReport, new ClassicLayoutManager(), dataSource, new HashMap());
        } else {
            jasperPrint = DynamicJasperHelper.generateJasperPrint(dynamicReport, new ClassicLayoutManager(), new HashMap());
        }

        JRPdfExporter exporter = new JRPdfExporter();
        File outputFile = new File(path);
        File parentFile = outputFile.getParentFile();
        if(parentFile != null) {
            parentFile.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream(outputFile);
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, fos);
        exporter.exportReport();
    }

    public static StreamResource exportReportPdfAsStream(FastReportBuilder drb, String path, JRDataSource dataSource) throws JRException, FileNotFoundException {
        DynamicReport dynamicReport = drb.build();
        JasperPrint jasperPrint;
        if(dataSource != null) {
            jasperPrint = DynamicJasperHelper.generateJasperPrint(dynamicReport, new ClassicLayoutManager(), dataSource, new HashMap());
        } else {
            jasperPrint = DynamicJasperHelper.generateJasperPrint(dynamicReport, new ClassicLayoutManager(), new HashMap());
        }

        JRPdfExporter exporter = new JRPdfExporter();

        ByteArrayOutputStream targetStream = new ByteArrayOutputStream();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, targetStream);
        exporter.exportReport();

        StreamResource streamSource = new StreamResource("invoice", new InputStreamFactory() {
            @Override
            public InputStream createInputStream() {
                return new ByteArrayInputStream(targetStream.toByteArray());
            }

            @Override
            public boolean requiresLock() {
                return false;
            }
        });

        return streamSource;

    }

    public static Style createHeaderStyle() {
        StyleBuilder sb=new StyleBuilder(true);
        sb.setFont(Font.VERDANA_MEDIUM_BOLD);
        sb.setBorder(Border.THIN());
        sb.setBorderBottom(Border.PEN_2_POINT());
        sb.setBorderColor(Color.BLACK);
        sb.setBackgroundColor(Color.LIGHT_GRAY);
        sb.setTextColor(Color.BLACK);
        sb.setHorizontalAlign(HorizontalAlign.CENTER);
        sb.setVerticalAlign(VerticalAlign.MIDDLE);
        sb.setTransparency(Transparency.OPAQUE);
        return sb.build();
    }

    public static Style createDetailTextStyle(){
        StyleBuilder sb=new StyleBuilder(true);
        sb.setFont(Font.VERDANA_MEDIUM);
        sb.setBorder(Border.DOTTED());
        sb.setBorderColor(Color.BLACK);
        sb.setTextColor(Color.BLACK);
        sb.setHorizontalAlign(HorizontalAlign.LEFT);
        sb.setVerticalAlign(VerticalAlign.MIDDLE);
        sb.setPaddingLeft(5);
        return sb.build();
    }

    public static Style createDetailItemNameStyle(){
        StyleBuilder sb=new StyleBuilder(true);
        sb.setFont(Font.VERDANA_MEDIUM_BOLD);
        sb.setBorder(Border.THIN());
        sb.setTextColor(Color.BLACK);
        sb.setHorizontalAlign(HorizontalAlign.LEFT);
        sb.setVerticalAlign(VerticalAlign.MIDDLE);
        sb.setPadding(5);
        return sb.build();
    }

    public static Style createDetailItemValueStyle(){
        StyleBuilder sb=new StyleBuilder(true);
        sb.setFont(Font.VERDANA_MEDIUM);
        sb.setBorder(Border.THIN());
        sb.setTextColor(Color.BLACK);
        sb.setHorizontalAlign(HorizontalAlign.LEFT);
        sb.setVerticalAlign(VerticalAlign.MIDDLE);
        sb.setPadding(5);
        return sb.build();
    }

    public static Style createDetailItemHeaderStyle() {
        StyleBuilder sb = new StyleBuilder(true);
        sb.setBorder(Border.NO_BORDER());
        sb.setFont(Font.VERDANA_SMALL);
        sb.setTransparency(Transparency.TRANSPARENT);
        return sb.build();
    }

    public static Style createDetailNumberStyle(){
        StyleBuilder sb=new StyleBuilder(true);
        sb.setFont(Font.VERDANA_MEDIUM);
        sb.setBorder(Border.DOTTED());
        sb.setBorderColor(Color.BLACK);
        sb.setTextColor(Color.BLACK);
        sb.setHorizontalAlign(HorizontalAlign.RIGHT);
        sb.setVerticalAlign(VerticalAlign.MIDDLE);
        sb.setPaddingRight(5);
        return sb.build();
    }

}
