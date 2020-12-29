package org.sess.report.services.report;

import net.sf.jasperreports.engine.JasperReport;

public interface JasperReportCompileManager {

    JasperReport getOrCompileReport(String reportName);

    JasperReport compileReport(String reportName);
}
