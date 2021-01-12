package org.sess.report.services.report.service.print.impl;

import org.apache.commons.io.FilenameUtils;
import org.sess.report.services.report.service.print.TemplatePathResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * ищет отчеты в директории шаблонов и ведет кэш
 */
@Primary
@Service
public class TemplatePathResolverJasperReportImpl implements TemplatePathResolver {
    private static final String JASPER_FILE_EXTENSION = "jrxml";
    private final Map<String, Path> reportTemplatesMap = new HashMap<>(10);
    @Value("${org.sess.report.service.templates.locations:./files/templates}")
    private String[] reportLocations;

    /**
     * @return отдает мапу со всеми отчетами в дирректории
     */
    @Override
    public Map<String, Path> getAllTemplates() {
        scanReportTemplateDir();
        return Collections.unmodifiableMap(reportTemplatesMap);
    }

    /**
     * возвращает путь до отчета по его имени, если такое есть в мапе или пытается найти
     *
     * @param templateName имя отчета
     * @return путь
     */
    @Override
    public Path getTemplatePath(String templateName) {
        if (reportTemplatesMap.containsKey(templateName)) {
            return reportTemplatesMap.get(templateName);
        } else {
            Path templatePath = tryResolveTemplatePath(templateName);
            if (Objects.nonNull(templatePath)) {
                reportTemplatesMap.put(templateName, templatePath);
                return templatePath;
            } else {
                return null;
            }
        }
    }

    /**
     * ищет в директории шаблонов отчеты
     * @param templateName имя отчета
     * @return путь
     */
    private Path tryResolveTemplatePath(String templateName) {
        for (String reportLocation : reportLocations) {
            File dir = new File(reportLocation);
            File[] files = dir.listFiles();
            if (dir.exists()
                    && dir.isDirectory()
                    && Objects.nonNull(files)) {
                for (File file : files) {
                    if (file.isFile() && file.getName().equalsIgnoreCase(templateName + "." + JASPER_FILE_EXTENSION)) {
                        return file.toPath();
                    }
                }
            }
        }
        return null;
    }

    /**
     * сканируем всю дирректорию с отчетами и добавляем в кэш
     */
    private void scanReportTemplateDir() {
        for (String reportLocation : reportLocations) {
            File dir = new File(reportLocation);
            File[] files = dir.listFiles();
            if (dir.exists()
                    && dir.isDirectory()
                    && Objects.nonNull(files)) {
                for (File file : files) {
                    if (file.isFile() && FilenameUtils.getExtension(file.getName()).equalsIgnoreCase(JASPER_FILE_EXTENSION)) {
                        addReportToMap(FilenameUtils.removeExtension(file.getName()), file.toPath());
                    }
                }
            }
        }
    }

    private void addReportToMap(String name, Path path) {
        reportTemplatesMap.put(name, path);
    }
}
