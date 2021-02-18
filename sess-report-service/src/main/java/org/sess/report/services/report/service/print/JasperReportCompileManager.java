package org.sess.report.services.report.service.print;

import net.sf.jasperreports.engine.JasperReport;

import java.util.List;

public interface JasperReportCompileManager {

    JasperReport getOrCompileReport(String reportName);

    JasperReport compileReport(String reportName);
}
