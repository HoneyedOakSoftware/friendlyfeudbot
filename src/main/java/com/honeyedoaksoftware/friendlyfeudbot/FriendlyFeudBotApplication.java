package com.honeyedoaksoftware.friendlyfeudbot;

import com.honeyedoaksoftware.friendlyfeudbot.command.ChallengeCommand;
import com.honeyedoaksoftware.friendlyfeudbot.command.HelpCommand;
import com.honeyedoaksoftware.friendlyfeudbot.command.ListCommand;
import com.honeyedoaksoftware.friendlyfeudbot.command.WinCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FriendlyFeudBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(FriendlyFeudBotApplication.class, args);
    }

    @Autowired
    public CommandLineRunner botClientRunner;

    @Autowired
    public ChallengeCommand challengeCommand;

    @Autowired
    public ListCommand listCommand;

    @Autowired
    public WinCommand winCommand;

    @Autowired
    public HelpCommand helpCommand;
}
