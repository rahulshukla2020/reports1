package com.javatechie.spring.batch.repository;

import com.javatechie.spring.batch.entity.customerReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerReportRepository extends JpaRepository<customerReport,Integer> {
}
