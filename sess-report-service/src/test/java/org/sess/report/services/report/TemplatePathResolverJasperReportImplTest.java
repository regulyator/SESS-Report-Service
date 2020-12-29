package org.sess.report.services.report;

import org.junit.jupiter.api.Test;
import org.sess.report.services.report.impl.TemplatePathResolverJasperReportImpl;
import org.springframework.test.util.ReflectionTestUtils;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class TemplatePathResolverJasperReportImplTest {

    @Test
    void getAllTemplates() {
        String[] s = new String[1];
        s[0]  = "./files/templates";
        TemplatePathResolver templatePathResolver = new TemplatePathResolverJasperReportImpl();
        ReflectionTestUtils.setField(templatePathResolver, "reportLocations",s);

        assertEquals(0, templatePathResolver.getAllTemplates().size());
        Path path = templatePathResolver.getTemplatePath("USER_ACTIVITY_REPORT");
        assertEquals(".\\files\\templates\\USER_ACTIVITY_REPORT.jrxml", path.toString());
        assertEquals(1, templatePathResolver.getAllTemplates().size());
    }

    @Test
    void getTemplatePath() {
        String[] s = new String[1];
        s[0]  = "./files/templates";
        TemplatePathResolver templatePathResolver = new TemplatePathResolverJasperReportImpl();
        ReflectionTestUtils.setField(templatePathResolver, "reportLocations",s);

        Path path = templatePathResolver.getTemplatePath("USER_ACTIVITY_REPORT");
        assertEquals(".\\files\\templates\\USER_ACTIVITY_REPORT.jrxml", path.toString());

        path = templatePathResolver.getTemplatePath("SOME_NON_EXIST_REPORT");
        assertNull(path);
    }
}