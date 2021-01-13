package org.sess.report.services.report.service.print.impl;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sess.report.services.report.service.enums.JasperReportExportFormat;
import org.sess.report.services.report.service.print.JasperExportManagerProcessor;
import org.sess.report.services.report.service.print.JasperReportFileSaveManager;
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
public class JasperReportFileSaveManagerImpl implements JasperReportFileSaveManager {
    private final Logger LOGGER = LogManager.getLogger(JasperReportFileSaveManagerImpl.class);
    private final String jasperTmpExportDirLocation;
    private final JasperExportManagerProcessor jasperExportManagerProcessor;
    private File tmpSaveDir;

    public JasperReportFileSaveManagerImpl(@Value("${org.sess.report.service.tmp.locations:./files/execute/tmp}") String jasperTmpExportDirLocation,
                                           JasperExportManagerProcessor jasperExportManagerProcessor) {
        this.jasperTmpExportDirLocation = jasperTmpExportDirLocation;
        this.jasperExportManagerProcessor = jasperExportManagerProcessor;
        checkTmpSaveDir();
    }

    /**
     * сохраняем отчет
     *
     * @param jasperPrint  отчет
     * @param exportFormat формат для сохранения
     * @return file
     */
    @Override
    public File saveReport(JasperPrint jasperPrint, JasperReportExportFormat exportFormat) {
        try {
            if (checkTmpSaveDir()) {
                String fileName = String.format("%s%s%s", UUID.randomUUID(), ".", exportFormat.getExtensionValue());
                File tmpFile = new File(tmpSaveDir.getAbsolutePath(), fileName);
                saveFile(jasperPrint, tmpFile, exportFormat);
                return tmpFile;
            } else {
                return null;
            }
        } catch (Exception ex) {
            LOGGER.error("error save report!", ex);
            return null;
        }

    }

    private void saveFile(JasperPrint jasperPrint, File tmpFile, JasperReportExportFormat exportFormat) throws JRException {
        jasperExportManagerProcessor.exportJasperPrint(jasperPrint, tmpFile, exportFormat);

    }

    /**
     * проверяем есть ли темповая дирректория(если нет то пытаемся создать)
     *
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
