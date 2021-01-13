package org.sess.report.services.report.service.print;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import org.sess.report.services.report.service.enums.JasperReportExportFormat;

import java.io.File;

public interface JasperExportManagerProcessor {

    void exportJasperPrint(JasperPrint jasperPrint, File tmpFile, JasperReportExportFormat exportFormat) throws JRException;
}
