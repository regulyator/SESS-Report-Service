package org.sess.report.services.report.service;

import org.sess.report.services.report.service.external.CoreUserDataExchanger;
import org.sess.report.services.report.service.print.ReportExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Objects;

/**
 * основной сервис отчетности
 */
@Service
public class UserReportServiceImpl implements UserReportService {
    private final static String USER_ACTIVITY_REPORT_NAME = "USER_ACTIVITY_REPORT";
    //набор данных для примера
    private final static String USER_ACTIVITY_SAMPLE_DATA_PATH = "./Sample_user_data.json";
    private final ReportExporter reportExporter;
    private final CoreUserDataExchanger coreUserDataExchanger;

    @Autowired
    public UserReportServiceImpl(ReportExporter reportExporter, CoreUserDataExchanger coreUserDataExchanger) {
        this.reportExporter = reportExporter;
        this.coreUserDataExchanger = coreUserDataExchanger;
    }


    /**
     * возвращает отчет по активности пользователя
     * @param userId пользователь
     * @return byte[] отчета
     */
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
            return readSampleUserData();
        } else {
            return coreUserDataExchanger.getUserEventsJson(userId);
        }
    }

    //данные для примера
    private String readSampleUserData() {
        try {
            Path fileName = Path.of(USER_ACTIVITY_SAMPLE_DATA_PATH);
            return Files.readString(fileName);
        } catch (IOException e) {
            return "";
        }
    }


}
