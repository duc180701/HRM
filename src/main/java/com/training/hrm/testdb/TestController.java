package com.training.hrm.testdb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.SQLException;

@RestController
public class TestController {

    @Autowired
    private DataSourceInitializer dataSourceInitializer;

    @GetMapping("/test")
    public ResponseEntity<Object> executeSqlScript() {
        try {
            dataSourceInitializer.afterPropertiesSet();
            return new ResponseEntity<>("SQL script executed successfully.", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to execute SQL script: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
