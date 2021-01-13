package org.sess.report.services.report.service.enums;

public enum JasperReportExportFormat {
    PDF("pdf"),
    HTML("html"),
    DEFAULT("html");

    private String extensionValue;

    JasperReportExportFormat(String extensionValue) {
        this.extensionValue = extensionValue;
    }

    public String getExtensionValue() {
        return extensionValue;
    }
}
