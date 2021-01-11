package org.sess.report.services.report.controllers;

import org.sess.report.services.report.service.UserReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("sees/services/report")
public class ReportsController {

    private final UserReportService userReportService;

    @Autowired
    public ReportsController(UserReportService userReportService) {
        this.userReportService = userReportService;
    }

    @GetMapping(
            value = "/user/activity/{userID}",
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
}
