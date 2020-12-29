package org.sess.report.services.report;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JsonDataSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JasperReportExporterJsonTest {
    @Mock
    JasperReportCompileManager jasperReportCompileManager;
    @Mock
    JasperReportFillManager jasperReportFillManager;
    @Mock
    JasperReportFileSaveManager jasperReportFileSaveManager;


    @Test
    void fillAndExportReportFile() throws JRException {
        String reportData = "[{\n" +
                "\t\"EVENT_NAME\": \"Событие 1\",\n" +
                "\t\"EVENT_START_DTIME\": \"\",\n" +
                "\t\"EVENT_END_DTIME\": \"\",\n" +
                "\t\"EVENT_DISTANTION\": 3.5\n" +
                "}, {\n" +
                "\t\"EVENT_NAME\": \"Событие 2\",\n" +
                "\t\"EVENT_START_DTIME\": \"\",\n" +
                "\t\"EVENT_END_DTIME\": \"\",\n" +
                "\t\"EVENT_DISTANTION\": 3.0\n" +
                "}, {\n" +
                "\t\"EVENT_NAME\": \"Событие 3\",\n" +
                "\t\"EVENT_START_DTIME\": \"\",\n" +
                "\t\"EVENT_END_DTIME\": \"\",\n" +
                "\t\"EVENT_DISTANTION\": 10.5\n" +
                "}]";
        try (ByteArrayInputStream iostream = new ByteArrayInputStream(reportData.getBytes())) {
            JasperReport compiledReport = JasperCompileManager.compileReport(".\\files\\templates\\USER_ACTIVITY_REPORT.jrxml");
            when(jasperReportCompileManager.getOrCompileReport(anyString())).thenReturn(compiledReport);
            //when(jasperReportFillManager.fillReport(any(), any(), any())).thenReturn(JasperFillManager.fillReport(compiledReport, new HashMap<String, Object>(), new JsonDataSource(iostream)));
            ReportExporter reportExporter = new JasperReportExporterJson(jasperReportCompileManager,
                    jasperReportFillManager,
                    jasperReportFileSaveManager);
            when(jasperReportFileSaveManager.saveReport(any(), any())).thenReturn(any());


            File d = reportExporter.fillAndExportReportFile("USER_ACTIVITY_REPORT",
                    new HashMap<>(),
                    iostream);
        } catch (IOException e) {
            fail();
        }

    }

    @Test
    void fillAndExportReportBytes() {

    }
}