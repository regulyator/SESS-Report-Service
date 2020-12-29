package org.sess.report.services.report;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Primary
@Service
public class TemplatePathResolverJasperReportImpl implements TemplatePathResolver {
    private static final String JASPER_FILE_EXTENSION = "jrxml";
    private final Map<String, Path> reportTemplatesMap = new HashMap<>(10);
    @Value("${org.sess.report.service.templates.locations:./files/templates}")
    private String[] reportLocations;


    @Override
    public Map<String, Path> getAllTemplates() {
        return Collections.unmodifiableMap(reportTemplatesMap);
    }

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
}
