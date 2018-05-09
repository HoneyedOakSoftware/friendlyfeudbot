package com.honeyedoaksoftware.friendlyfeudbot;

import com.honeyedoaksoftware.friendlyfeudbot.command.*;
import com.honeyedoaksoftware.friendlyfeudbot.util.BotUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class CommandHandler {

    // A map of commands mapping from command string to the functional impl
    private Map<String, Command> commandMap = new HashMap<>();

    @Autowired
    public CommandHandler(HelpCommand helpCommand, GrantCommand grantCommand, ListCommand listCommand, ChallengeCommand challengeCommand, StatsCommand statsCommand) {
        buildCommandMap(helpCommand, grantCommand, listCommand, challengeCommand, statsCommand);
    }

    private void buildCommandMap(HelpCommand helpCommand, GrantCommand grantCommand, ListCommand listCommand, ChallengeCommand challengeCommand, StatsCommand statsCommand) {
        commandMap.put(HelpCommand.COMMAND_TEXT, (helpCommand));
        commandMap.put(GrantCommand.COMMAND_TEXT, (grantCommand));
        commandMap.put(ListCommand.COMMAND_TEXT, (listCommand));
        commandMap.put(ChallengeCommand.COMMAND_TEXT, (challengeCommand));
        commandMap.put(StatsCommand.COMMAND_TEXT, (statsCommand));
    }

    @EventSubscriber
    public void feudMessageHandler(MessageReceivedEvent event) throws DiscordException, MissingPermissionsException {
        String[] messageParts = BotUtils.getMessageParts(event);

        if (messageParts.length >= 1 && messageParts[0].startsWith(BotUtils.BOT_PREFIX.concat(BotUtils.BOT_TAG))) {
            String[] botArgs = Arrays.copyOfRange(messageParts, 1, messageParts.length);

            if (botArgs.length == 0) {
                commandMap.get(HelpCommand.COMMAND_TEXT).runCommand(event, botArgs);
            } else {
                String command = botArgs[0].toLowerCase();
                String[] commandArgs = Arrays.copyOfRange(botArgs, 1, botArgs.length);

                if (commandMap.containsKey(command)) {
                    commandMap.get(command).runCommand(event, commandArgs);
                } else {
                    commandMap.get(HelpCommand.COMMAND_TEXT).runCommand(event, commandArgs);
                }
            }
        }
    }
}
