package com.jk.schoo.management.spring.transaction.report;

import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.ImageBanner;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.ImageScaleMode;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import com.jk.schoo.management.spring.Properties;
import com.jk.schoo.management.spring.report.util.ReportUtil;
import com.jk.schoo.management.spring.student.service.StudentService;
import com.jk.schoo.management.spring.transaction.domain.Transaction;
import com.jk.schoo.management.spring.transaction.report.invoice.DetailItem;
import com.vaadin.flow.server.StreamResource;
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
import java.util.List;
import java.util.UUID;

/**
 * Created by jayamalk on 7/9/2018.
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class StudentFeeInvoice{

    public StudentFeeInvoice() {
    }

    public StreamResource generate(Transaction transaction) {

        StreamResource streamResource = null;

        List<DetailItem> detailItems = new ArrayList<>();
        detailItems.add(new DetailItem("Student", transaction.getStudent().getReferenceIdDisplayName()));
        detailItems.add(new DetailItem("Payment Type", transaction.getPaymentType().getName()));
        detailItems.add(new DetailItem("Payment Reference", transaction.getPaymentTypeReference() != null ? transaction.getPaymentTypeReference() : "-"));
        detailItems.add(new DetailItem("Payment Date/Time", transaction.getInitiatedDateTime().toString()));
        detailItems.add(new DetailItem("Issuer", transaction.getInitiator()));
        detailItems.add(new DetailItem("Amount (LKR)", String.valueOf(transaction.getAmount())));

        String signatureAccepted = "(Receiver Signature)";
        String receiverAccepted = "(Issuer Signature)";

        StyleBuilder titleStyle=new StyleBuilder(true);
        titleStyle.setHorizontalAlign(HorizontalAlign.RIGHT);
        titleStyle.setFont(new Font(Font.BIG, Font._FONT_TIMES_NEW_ROMAN, true));

        StyleBuilder subTitleStyle=new StyleBuilder(true);
        subTitleStyle.setHorizontalAlign(HorizontalAlign.LEFT);
        subTitleStyle.setFont(new Font(Font.BIG, Font._FONT_TIMES_NEW_ROMAN, true));

        FastReportBuilder drb = new FastReportBuilder();
        drb.setAllowDetailSplit(false);
        String fileName = UUID.randomUUID().toString();
        try {
            drb .setTitle("# " + transaction.getId())
                .setTitleStyle(titleStyle.build())
                .setSubtitle(transaction.getFee().getReferenceIdDisplayName())
                .setSubtitleStyle(subTitleStyle.build())
                .setRightMargin(30)
                .setBottomMargin(30)
                .setUseFullPageWidth(true)
                .addAutoText(signatureAccepted + "                " + receiverAccepted, AutoText.POSITION_FOOTER, AutoText.ALIGNMENT_RIGHT, 1000)
                 .addImageBanner("logo.png", new Integer(66), new Integer(65), ImageBanner.ALIGN_LEFT, ImageScaleMode.FILL)
                .setPageSizeAndOrientation(new Page(420, 595, Boolean.FALSE));
            Style detailNameStyle = ReportUtil.createDetailItemNameStyle();
            Style detailValueStyle = ReportUtil.createDetailItemValueStyle();
            Style headerStyle = ReportUtil.createDetailItemHeaderStyle();
            AbstractColumn columnName= ColumnBuilder.getNew()
                    .setColumnProperty("name", String.class.getName())
                    .setTitle("")
                    .setWidth(20)
                    .setStyle(detailNameStyle).setHeaderStyle(headerStyle)
                    .build();
            AbstractColumn columnValue= ColumnBuilder.getNew()
                    .setColumnProperty("value", String.class.getName())
                    .setTitle("")
                    .setStyle(detailValueStyle).setHeaderStyle(headerStyle)
                    .build();

            drb.addColumn(columnName);
            drb.addColumn(columnValue);

            JRDataSource dataSource = new JRBeanCollectionDataSource(detailItems);
            streamResource = ReportUtil.exportReportPdfAsStream(drb, Properties.EXPORT_PATH + File.separator + fileName + ".pdf", dataSource);
        }catch (JRException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return streamResource;

    }

}
