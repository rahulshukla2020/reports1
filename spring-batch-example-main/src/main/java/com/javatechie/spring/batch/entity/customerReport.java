package com.javatechie.spring.batch.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "CUSTOMER_REPORT")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class customerReport {


    @Lob
    private byte[] report;
    @Id
    private Long id;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
