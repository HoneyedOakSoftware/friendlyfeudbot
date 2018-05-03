package com.honeyedoaksoftware.friendlyfeudbot;

import com.honeyedoaksoftware.friendlyfeudbot.util.BotUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import sx.blah.discord.api.IDiscordClient;

@Component
public class BotClientRunner implements CommandLineRunner {

    @Value("${friendlyfeudbot.botusertoken}")
    private String botUserToken;

    @Override
    public void run(String... args) throws Exception {
        if (StringUtils.isBlank(botUserToken)) {
            System.out.println("Please enter the bots token in the application.properties file with key 'friendlyfeudbot.botusertoken'");
            return;
        }

        IDiscordClient cli = BotUtils.getBuiltDiscordClient(botUserToken);

        // Register a listener via the EventSubscriber annotation which allows for organisation and delegation of events
        cli.getDispatcher().registerListener(new CommandHandler());

        // Only login after all events are registered otherwise some may be missed.
        cli.login();
    }
}
