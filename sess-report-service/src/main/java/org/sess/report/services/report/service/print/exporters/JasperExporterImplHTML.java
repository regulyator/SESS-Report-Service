package org.sess.report.services.report.service.print.exporters;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.sess.report.services.report.service.enums.JasperReportExportFormat;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class JasperExporterImplHTML implements JasperExporter {
    @Override
    public JasperReportExportFormat getExporterType() {
        return JasperReportExportFormat.HTML;
    }

    @Override
    public void exportJasperPrint(JasperPrint jasperPrint, File tmpFile) throws JRException {
        JasperExportManager.exportReportToHtmlFile(jasperPrint, tmpFile.getAbsolutePath());
    }
}
