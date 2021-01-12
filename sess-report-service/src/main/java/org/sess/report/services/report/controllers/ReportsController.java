package org.sess.report.services.report.controllers;

import org.sess.report.services.report.service.UserReportService;
import org.sess.report.services.report.service.print.TemplatePathResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("sees/services/report")
public class ReportsController {

    private final UserReportService userReportService;
    private final TemplatePathResolver templatePathResolver;

    @Autowired
    public ReportsController(UserReportService userReportService, TemplatePathResolver templatePathResolver) {
        this.userReportService = userReportService;
        this.templatePathResolver = templatePathResolver;
    }

    /**
     * формирует и отдает отчет активности пользователя
     * @param userID пользователь
     * @return
     */
    @GetMapping(
            value = "/activity/user/{userID}",
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    public @ResponseBody
    byte[] getActivityReportPDFByUser(@PathVariable String userID) {
        byte[] printBytes = userReportService.getUserActivityReport(userID);
        if (printBytes.length <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Error when getActivityReportPDFByUser!"
            );
        }
        return printBytes;
    }

    /**
     * @return имена всех доступных отчетов
     */
    //TODO должна возвращаться еще и мета по отчетам (по какому набору данных, описание и т.д.)
    @GetMapping(value = "/all")
    public List<String> getAllReportsName() {
        return new ArrayList<>(templatePathResolver.getAllTemplates().keySet());
    }
}
