package com.jk.schoo.management.spring.transaction.report.outstanding;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import com.jk.schoo.management.spring.Properties;
import com.jk.schoo.management.spring.report.domain.Reportable;
import com.jk.schoo.management.spring.report.util.ReportUtil;
import com.jk.schoo.management.spring.student.service.StudentService;
import com.vaadin.flow.spring.annotation.SpringComponent;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.UUID;

/**
 * Created by jayamalk on 7/9/2018.
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class OutStandingBalanceReport implements Reportable {

    @Autowired
    private StudentService studentService;

    public OutStandingBalanceReport() {
    }

    @Override
    public String getTitle() {
        return "Student - Outstanding Balances";
    }

    @Override
    public String generate(Object metaData) {

        StyleBuilder titleStyle=new StyleBuilder(true);
        titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
        titleStyle.setFont(new Font(20, Font._FONT_GEORGIA, true));

        StyleBuilder subTitleStyle=new StyleBuilder(true);
        subTitleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
        subTitleStyle.setFont(new Font(Font.MEDIUM, Font._FONT_GEORGIA, true));

        FastReportBuilder drb = new FastReportBuilder();
        drb.setAllowDetailSplit(false);
        String fileName = UUID.randomUUID().toString();
        try {
            drb .setTitle(getTitle())
                .setTitleStyle(titleStyle.build())
                .setSubtitle("This report was generated at " + new Date())
                .setSubtitleStyle(subTitleStyle.build())
                .setPrintBackgroundOnOddRows(true)
                .setUseFullPageWidth(true);
            //Columns
            Style detailNumberStyle = ReportUtil.createDetailNumberStyle();
            Style detailTextStyle = ReportUtil.createDetailTextStyle();
            Style headerStyle = ReportUtil.createHeaderStyle();
            AbstractColumn columnBranch = ColumnBuilder.getNew()
                    .setColumnProperty("branch", String.class.getName())
                    .setTitle("Branch").setWidth(new Integer(30))
                    .setStyle(detailTextStyle).setHeaderStyle(headerStyle)
                    .build();
            AbstractColumn columnStudentID = ColumnBuilder.getNew()
                .setColumnProperty("id", Long.class.getName())
                .setTitle("Student ID").setWidth(new Integer(30))
                .setStyle(detailNumberStyle).setHeaderStyle(headerStyle)
                .build();
            AbstractColumn columnName = ColumnBuilder.getNew()
                .setColumnProperty("name", String.class.getName())
                .setTitle("Name").setWidth(new Integer(30))
                .setStyle(detailTextStyle).setHeaderStyle(headerStyle)
                .build();

            drb.addColumn(columnBranch);
            drb.addColumn(columnStudentID);
            drb.addColumn(columnName);

            JRDataSource dataSource = new JRBeanCollectionDataSource(studentService.getAll());
            ReportUtil.exportReportPdf(drb, Properties.EXPORT_PATH + File.separator + fileName + ".pdf", dataSource);
        }catch (JRException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileName + ".pdf";
    }

    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }


}
