package org.sess.report.services.report.service.print.impl;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sess.report.services.report.service.print.JasperReportFileSaveManager;
import org.sess.report.services.report.service.enums.JasperReportExportFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Objects;
import java.util.UUID;

/**
 * сохраняет выполненные отчеты
 */
@Service
@Primary
public class JasperReportFileSaveManagerImpl implements JasperReportFileSaveManager {
    private final Logger LOGGER = LogManager.getLogger(JasperReportFileSaveManagerImpl.class);
    @Value("${org.sess.report.service.tmp.locations:./files/execute/tmp}")
    private String jasperTmpExportDirLocation;
    private File tmpSaveDir;

    /**
     * сохраняем отчет
     * @param jasperPrint отчет
     * @param exportFormat формат для сохранения
     * @return file
     */
    @Override
    public File saveReport(JasperPrint jasperPrint, JasperReportExportFormat exportFormat) {
        try {
            if (checkTmpSaveDir()) {
                File tmpFile = new File(tmpSaveDir.getAbsolutePath() + "/" + UUID.randomUUID() + "." + resolveExportExtension(exportFormat));
                saveFile(jasperPrint, tmpFile, exportFormat);
                return tmpFile;
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }

    }

    private void saveFile(JasperPrint jasperPrint, File tmpFile, JasperReportExportFormat exportFormat) throws JRException {
        switch (exportFormat) {
            case PDF:
                JasperExportManager.exportReportToPdfFile(jasperPrint, tmpFile.getAbsolutePath());
            default:
                JasperExportManager.exportReportToPdfFile(jasperPrint, tmpFile.getAbsolutePath());
        }

    }

    private String resolveExportExtension(JasperReportExportFormat exportFormat) {
        switch (exportFormat) {
            case PDF:
                return "pdf";
            default:
                return "pdf";
        }
    }

    /**
     * проверяем есть ли темповая дирректория(если нет то пытаемся создать)
     * @return результат проверки
     */
    private boolean checkTmpSaveDir() {
        if (Objects.isNull(tmpSaveDir)) {
            tmpSaveDir = new File(jasperTmpExportDirLocation);
        }
        if (!tmpSaveDir.exists()) {
            return tmpSaveDir.mkdirs();
        } else {
            return true;
        }
    }

    //TODO кастомное сохранение
    @Override
    public File saveReport(JasperPrint jasperPrint, JasperReportExportFormat exportFormat, String dirSavePath, String fileSaveName) {
        return null;
    }

    /**
     * переодически чистим дирректорию от выполненных отчетов
     */
    @Scheduled(cron = "0 0/30 * * * *")
    private void cleanTmpDir() {
        try {
            if (checkTmpSaveDir()) {
                LOGGER.info("Try clean tmp report dir!");
                FileUtils.cleanDirectory(tmpSaveDir);
            }
        } catch (Exception ex) {
            LOGGER.info("Error when try clean tmp report dir!", ex);
        }
    }
}
