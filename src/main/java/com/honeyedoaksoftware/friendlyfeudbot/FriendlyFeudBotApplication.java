package com.honeyedoaksoftware.friendlyfeudbot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FriendlyFeudBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(FriendlyFeudBotApplication.class, args);
    }

    @Autowired
    public MainRunner mainRunner;
}
