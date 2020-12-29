package org.sess.report.services.report.impl;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import org.sess.report.services.report.JasperReportCompileManager;
import org.sess.report.services.report.TemplatePathResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Primary
public class JasperReportCompileManagerImpl implements JasperReportCompileManager {
    private final TemplatePathResolver templatePathResolver;
    private final Map<String, JasperReport> jasperReportMap = new HashMap<>(5);

    @Autowired
    public JasperReportCompileManagerImpl(TemplatePathResolver templatePathResolver) {
        this.templatePathResolver = templatePathResolver;
    }

    @Override
    public JasperReport getOrCompileReport(String reportName) {
        if (jasperReportMap.containsKey(reportName)) {
            return jasperReportMap.get(reportName);
        } else {
            JasperReport jasperReport = compileReport(reportName);
            return Objects.nonNull(jasperReport) ? jasperReport : null;
        }
    }

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
