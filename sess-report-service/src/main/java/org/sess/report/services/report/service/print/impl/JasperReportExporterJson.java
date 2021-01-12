package org.sess.report.services.report.service.print.impl;

import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JsonDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sess.report.services.report.service.enums.JasperReportExportFormat;
import org.sess.report.services.report.service.print.JasperReportCompileManager;
import org.sess.report.services.report.service.print.JasperReportFileSaveManager;
import org.sess.report.services.report.service.print.JasperReportFillManager;
import org.sess.report.services.report.service.print.ReportExporter;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Map;
import java.util.Objects;

/**
 * экспортирует отчеты
 */
@Service
@Primary
public class JasperReportExporterJson implements ReportExporter {
    private final Logger LOGGER = LogManager.getLogger(JasperReportExporterJson.class);
    private final JasperReportCompileManager jasperReportCompileManager;
    private final JasperReportFillManager jasperReportFillManager;
    private final JasperReportFileSaveManager jasperReportFileSaveManager;
    private final static String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    public JasperReportExporterJson(JasperReportCompileManager jasperReportCompileManager,
                                    JasperReportFillManager jasperReportFillManager,
                                    JasperReportFileSaveManager jasperReportFileSaveManager) {
        this.jasperReportCompileManager = jasperReportCompileManager;
        this.jasperReportFillManager = jasperReportFillManager;
        this.jasperReportFileSaveManager = jasperReportFileSaveManager;
    }

    @Override
    public File fillAndExportReportFile(String reportName, Map<String, Object> reportParamsMap, InputStream reportData) {
        try {

            JasperReport report = jasperReportCompileManager.getOrCompileReport(reportName);
            if (Objects.nonNull(report)) {
                JsonDataSource dataSource = new JsonDataSource(reportData);
                dataSource.setDatePattern(DATE_FORMAT);
                return jasperReportFileSaveManager.saveReport(
                        jasperReportFillManager.fillReport(report, reportParamsMap, dataSource), JasperReportExportFormat.PDF);
            }
            return null;
        } catch (Exception ex) {
            LOGGER.error("Error fillAndExportReportFile! {}", reportName, ex);
            return null;
        }
    }

    @Override
    public byte[] fillAndExportReportBytes(String reportName, Map<String, Object> reportParamsMap, InputStream reportData) {
        try {
            File print110File = this.fillAndExportReportFile(reportName, reportParamsMap, reportData);
            if (Objects.nonNull(print110File)
                    && print110File.exists()) {
                return Files.readAllBytes(print110File.toPath());
            } else {
                return new byte[0];
            }
        } catch (Exception ex) {
            LOGGER.error("Error fillAndExportReportBytes! {}", reportName, ex);
            return new byte[0];
        }
    }
}
