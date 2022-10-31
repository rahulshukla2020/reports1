package com.javatechie.spring.batch.config;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import com.javatechie.spring.batch.model.Customer;
import org.springframework.jdbc.core.RowMapper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class CustomerRowMapper implements RowMapper<Customer> {
    private static final DateTimeFormatter DT_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");


    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        //@// @formatter:off
        return Customer.builder()
                .id(rs.getLong("id"))
                .firstName(rs.getString("firstName"))
                .lastName(rs.getString("lastName"))
              //  .birthdate(LocalDateTime.parse(rs.getString("birthdate"), DT_FORMAT))
                .build();
        // @formatter:on
    }



}
