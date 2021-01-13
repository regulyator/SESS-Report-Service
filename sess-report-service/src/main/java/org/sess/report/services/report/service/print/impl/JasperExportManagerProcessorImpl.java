package org.sess.report.services.report.service.print.impl;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sess.report.services.report.service.enums.JasperReportExportFormat;
import org.sess.report.services.report.service.print.JasperExportManagerProcessor;
import org.sess.report.services.report.service.print.exporters.JasperExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class JasperExportManagerProcessorImpl implements JasperExportManagerProcessor {
    private final Logger LOGGER = LogManager.getLogger(JasperExportManagerProcessorImpl.class);
    private final Map<JasperReportExportFormat, JasperExporter>
            jasperExportersMap = new HashMap<>();

    @Autowired
    public JasperExportManagerProcessorImpl(@NonNull Collection<JasperExporter> jasperExporters) {
        fillExportersMap(jasperExporters);

    }

    @Override
    public void exportJasperPrint(JasperPrint jasperPrint, File tmpFile, JasperReportExportFormat exportFormat) throws JRException {
        if (jasperExportersMap.containsKey(exportFormat)) {
            jasperExportersMap.get(exportFormat).exportJasperPrint(jasperPrint, tmpFile);
        } else {
            try {
                jasperExportersMap.get(JasperReportExportFormat.DEFAULT).exportJasperPrint(jasperPrint, tmpFile);
            } catch (Exception ex) {
                LOGGER.error("Error when try exportJasperPrint with DEFAULT exporter!", ex);
            }
        }
    }

    private void fillExportersMap(Collection<JasperExporter> jasperExporters) {
        jasperExporters.forEach(jasperExporter -> {
            jasperExportersMap.put(jasperExporter.getExporterType(), jasperExporter);
        });
    }
}
