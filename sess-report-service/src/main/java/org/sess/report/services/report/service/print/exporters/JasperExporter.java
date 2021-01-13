package org.sess.report.services.report.service.print.exporters;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import org.sess.report.services.report.service.enums.JasperReportExportFormat;

import java.io.File;

public interface JasperExporter {

    JasperReportExportFormat getExporterType();

    void exportJasperPrint(JasperPrint jasperPrint, File tmpFile) throws JRException;
}
