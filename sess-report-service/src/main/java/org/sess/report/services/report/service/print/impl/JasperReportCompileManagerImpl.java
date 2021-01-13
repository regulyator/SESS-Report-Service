package org.sess.report.services.report.service.print.impl;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import org.sess.report.services.report.service.print.JasperReportCompileManager;
import org.sess.report.services.report.service.print.TemplatePathResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
 * компилирует отчеты джаспера, ведет кэш скомпилированных отчетов
 */
@Service
public class JasperReportCompileManagerImpl implements JasperReportCompileManager {
    private final TemplatePathResolver templatePathResolver;
    private final Map<String, JasperReport> jasperReportMap = new HashMap<>(5);

    @Autowired
    public JasperReportCompileManagerImpl(TemplatePathResolver templatePathResolver) {
        this.templatePathResolver = templatePathResolver;
    }

    /**
     * компилируем или отдаем ранее скомпилированный отчет по имени
     * @param reportName имя отчета
     * @return скомпилированный отчет
     */
    @Override
    public JasperReport getOrCompileReport(String reportName) {
        if (jasperReportMap.containsKey(reportName)) {
            return jasperReportMap.get(reportName);
        } else {
            JasperReport jasperReport = compileReport(reportName);
            return Objects.nonNull(jasperReport) ? jasperReport : null;
        }
    }

    /**
     * компилируем (если был ранее скомпилирован то перекомпилим) отчет
     * @param reportName имя отчета
     * @return скомпилированный отчет
     */
    @Override
    public JasperReport compileReport(String reportName) {
        JasperReport compiledReport = null;
        Path reportTemplatePath = templatePathResolver.getTemplatePath(reportName);
        if (Objects.isNull(reportTemplatePath)) {
            return null;
        } else {
            try {
                compiledReport = JasperCompileManager.compileReport(reportTemplatePath.toString());
                jasperReportMap.put(reportName, compiledReport);
            } catch (JRException e) {
                e.printStackTrace();
            }
        }

        return compiledReport;
    }
}
