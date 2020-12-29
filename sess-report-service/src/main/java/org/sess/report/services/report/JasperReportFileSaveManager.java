package org.sess.report.services.report;

import net.sf.jasperreports.engine.JasperPrint;
import org.sess.report.services.report.enums.JasperReportExportFormat;

import java.io.File;

public interface JasperReportFileSaveManager {

    File saveReport(JasperPrint jasperPrint, JasperReportExportFormat exportFormat);

    File saveReport(JasperPrint jasperPrint, JasperReportExportFormat exportFormat, String dirSavePath, String fileSaveName);
}
