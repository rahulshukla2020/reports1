package com.javatechie.spring.batch.config;

import com.javatechie.spring.batch.entity.customerReport;
import com.javatechie.spring.batch.repository.CustomerReportRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerWriter implements ItemWriter<customerReport> {

    @Autowired
    private CustomerReportRepository customerReportRepository;

    @Override
    public void write(List<? extends customerReport> list) throws Exception {
        System.out.println("Thread Name : -"+Thread.currentThread().getName());
        customerReportRepository.saveAll(list);
    }
}
