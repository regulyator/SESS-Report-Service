package org.sess.report.services.report;

import java.nio.file.Path;
import java.util.Map;

public interface TemplatePathResolver {

    Map<String, Path> getAllTemplates();

    Path getTemplatePath(String templateName);

}
