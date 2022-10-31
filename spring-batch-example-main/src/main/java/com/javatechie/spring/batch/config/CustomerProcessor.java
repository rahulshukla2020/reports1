package com.javatechie.spring.batch.config;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.javatechie.spring.batch.entity.customerReport;
import com.javatechie.spring.batch.model.Customer;
import org.springframework.batch.item.ItemProcessor;


import javax.xml.bind.JAXBContext;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class CustomerProcessor implements ItemProcessor<Customer, customerReport> {

    ObjectMapper xmlMapper = new XmlMapper();
    customerReport custommerReport = new customerReport();
    StringBuilder xml = new StringBuilder();
    int i =0;
   //  xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
    @Override
    public customerReport process(Customer customer) throws Exception {
System.out.print("process");
        return
                createXMLData(customer, custommerReport);
           //     custommerReport.setReport(xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(customer).getBytes())
                //.lastName(rs.getString("lastName"))
                //  .birthdate(LocalDateTime.parse(rs.getString("birthdate"), DT_FORMAT))
                //.build();
    }

    private customerReport createXMLData(Customer custommer, customerReport custommerReport) throws JsonProcessingException {
        System.out.print("createXMLData");

        if (i <= 3) {
            xml.append(xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(custommer));
            i++;


        }
        custommerReport.setId(1L);
        custommerReport.setReport(xml.toString().getBytes());
       // xml =null;
        return custommerReport;
    }
}
