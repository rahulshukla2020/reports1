package com.javatechie.spring.batch.linkingreport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data

public class Customer {

    private Long id;

    private String firstName;
    private String lastName;
    private LocalDateTime birthdate;

}
