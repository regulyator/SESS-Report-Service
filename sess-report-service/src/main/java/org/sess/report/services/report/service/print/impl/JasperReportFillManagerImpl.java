package org.sess.report.services.report.service.print.impl;

import net.sf.jasperreports.engine.*;
import org.sess.report.services.report.service.print.JasperReportFillManager;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * заполняет отчет данными из dataSource
 */
@Service
@Primary
public class JasperReportFillManagerImpl implements JasperReportFillManager {
    @Override
    public JasperPrint fillReport(JasperReport jasperReport, Map<String, Object> parameters, JRDataSource dataSource) {
        try {
            return JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        } catch (JRException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public JasperPrint fillReport(JasperReport jasperReport, Map<String, Object> parameters) {
        return this.fillReport(jasperReport, parameters, new JREmptyDataSource());
    }
}
