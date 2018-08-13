package com.jk.schoo.management.spring.transaction.report;

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
import com.jk.schoo.management.spring.transaction.domain.Transaction;
import com.vaadin.flow.spring.annotation.SpringComponent;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by jayamalk on 7/9/2018.
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class StudentFeeInvoice{

    @Autowired
    private StudentService studentService;

    public StudentFeeInvoice() {
    }

    public String generate(Transaction transaction) {

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
            drb .setTitle(transaction.getFee().getReferenceIdDisplayName())
                .setTitleStyle(titleStyle.build())
                .setSubtitle(transaction.getInitiatedDateTime().toString())
                .setSubtitleStyle(subTitleStyle.build())
                .setPrintBackgroundOnOddRows(true)
                .setUseFullPageWidth(true);
            //Columns
            Style detailNumberStyle = ReportUtil.createDetailNumberStyle();
            Style detailTextStyle = ReportUtil.createDetailTextStyle();
            Style headerStyle = ReportUtil.createHeaderStyle();
            AbstractColumn columnBranch = ColumnBuilder.getNew()
                    .setColumnProperty("id", String.class.getName())
                    .setTitle("Invoice No").setWidth(new Integer(30))
                    .setStyle(detailTextStyle).setHeaderStyle(headerStyle)
                    .build();

            drb.addColumn(columnBranch);

            List<Transaction> transactionList = new ArrayList<>();
            transactionList.add(transaction);

            JRDataSource dataSource = new JRBeanCollectionDataSource(transactionList);
            ReportUtil.exportReportPdf(drb, Properties.EXPORT_PATH + File.separator + fileName + ".pdf", dataSource);
        }catch (JRException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileName + ".pdf";
    }

}
