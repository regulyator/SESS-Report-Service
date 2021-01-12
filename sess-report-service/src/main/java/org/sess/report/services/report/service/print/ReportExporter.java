package org.sess.report.services.report.service.print;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

public interface ReportExporter {

    File fillAndExportReportFile(String reportName, Map<String, Object> reportParamsMap, InputStream reportData);

    byte[] fillAndExportReportBytes(String reportName, Map<String, Object> reportParamsMap, InputStream reportData);

}
