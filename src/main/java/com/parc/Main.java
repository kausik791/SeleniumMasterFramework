package com.parc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        Employee e1 = Employee.builder()
                .name("John Doe")
                .age(30)
                .email("kausik")
                .build();
        Employee e2 = Employee.builder()
                .name("John Doe")
                .age(30)
                .email("kausik")
                .phone("1234567890")
                .build();
        logger.info("Employee 1: {}, {}, {}", e1.getName(), e1.getAge(), e1.getEmail());
        logger.info("Employee 2: {}, {}, {}, {}", e2.getName(), e2.getAge(), e2.getEmail(), e2.getPhone());
    }
}
