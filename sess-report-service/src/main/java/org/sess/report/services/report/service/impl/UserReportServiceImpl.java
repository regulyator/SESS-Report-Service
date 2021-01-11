package org.sess.report.services.report.service.impl;

import org.sess.report.services.report.ReportExporter;
import org.sess.report.services.report.service.UserReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

@Service
@Primary
public class UserReportServiceImpl implements UserReportService {
    private final static String USER_ACTIVITY_REPORT_NAME = "USER_ACTIVITY_REPORT";
    private final static String USER_ACTIVITY_SAMPLE_DATA = "[{\n" +
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
    private final ReportExporter reportExporter;

    @Autowired
    public UserReportServiceImpl(ReportExporter reportExporter) {
        this.reportExporter = reportExporter;
    }


    @Override
    public byte[] getUserActivityReport(String userId) {
        try (ByteArrayInputStream ioStreamData = new ByteArrayInputStream(getUserActivityData(userId).getBytes())) {
            return reportExporter.fillAndExportReportBytes(USER_ACTIVITY_REPORT_NAME, new HashMap<>(), ioStreamData);
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    private String getUserActivityData(String userId) {
        if (Objects.equals(userId, "test")) {
            return USER_ACTIVITY_SAMPLE_DATA;
        } else {
            return "";
        }
    }


}
