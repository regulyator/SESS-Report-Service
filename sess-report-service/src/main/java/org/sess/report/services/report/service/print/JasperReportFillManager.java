package org.sess.report.services.report.service.print;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import java.util.Map;

public interface JasperReportFillManager {

    JasperPrint fillReport(JasperReport jasperReport,
                           Map<String, Object> parameters,
                           JRDataSource dataSource);

    JasperPrint fillReport(JasperReport jasperReport,
                           Map<String, Object> parameters);
}
