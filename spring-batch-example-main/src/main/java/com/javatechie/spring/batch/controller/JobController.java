package com.javatechie.spring.batch.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/eodReports")
public class JobController {

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    @Qualifier("transactionReport")
    private Job transactionReportJob;

    @Autowired
    @Qualifier("linkingReport")
    private Job linkingReportJob;

    @Autowired
    @Qualifier("refundReport")
    private Job refundReportJob;

    @PostMapping("/transactionReport/{reconDate}/{participant}")
    public void transactionReport(@PathVariable("reconDate") String reconDate ,
                                  @PathVariable("participant") String participant) {

        JobParameters jobParameters = new JobParametersBuilder().addString("reconDate" , reconDate)
                .addString("participant", participant)
                .addLong("startAt", System.currentTimeMillis()).toJobParameters();
        try {
            jobLauncher.run(transactionReportJob, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            e.printStackTrace();
        }
    }


    @PostMapping("/linkReport/{reconDate}/{participant}")
    public void linkReport(@PathVariable("reconDate") Date reconDate ,
                                  @PathVariable("participant") String participant) {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis()).toJobParameters();
        try {
            jobLauncher.run(linkingReportJob, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/refundReport/{reconDate}/{participant}")
    public void refundReport(@PathVariable("reconDate") Date reconDate ,
                             @PathVariable("participant") String participant) {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis()).toJobParameters();
        try {
            jobLauncher.run(refundReportJob, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            e.printStackTrace();
        }
    }
}
