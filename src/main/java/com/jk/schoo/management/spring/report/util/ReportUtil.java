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
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
