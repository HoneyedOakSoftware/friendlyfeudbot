package com.honeyedoaksoftware.friendlyfeudbot;

import com.honeyedoaksoftware.friendlyfeudbot.command.*;
import com.honeyedoaksoftware.friendlyfeudbot.util.BotUtils;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandHandler {

    private static final String HELP_COMMAND = "help";
    private static final String WINNER_COMMAND = "winner";
    private static final String LIST_COMMAND = "list";
    private static final String CHALLENGE_COMMAND = "challenge";

    // A static map of commands mapping from command string to the functional impl
    private static Map<String, Command> commandMap = new HashMap<>();

    static {
        commandMap.put(HELP_COMMAND, ((event, args) -> new HelpCommand() {
        }.runCommand(event, args)));
        commandMap.put(WINNER_COMMAND, ((event, args) -> new WinCommand() {
        }.runCommand(event, args)));
        commandMap.put(LIST_COMMAND, ((event, args) -> new ListCommand() {
        }.runCommand(event, args)));
        commandMap.put(CHALLENGE_COMMAND, ((event, args) -> new ChallengeCommand() {
        }.runCommand(event, args)));
    }

    @EventSubscriber
    public void feudMessageHandler(MessageReceivedEvent event) throws DiscordException, MissingPermissionsException {
        String[] messageParts = BotUtils.getMessageParts(event);

        if (messageParts.length >= 1 && messageParts[0].startsWith(BotUtils.BOT_PREFIX.concat(BotUtils.BOT_TAG))) {
            String[] botArgs = Arrays.copyOfRange(messageParts, 1, messageParts.length);

            if (botArgs.length == 0) {
                commandMap.get(HELP_COMMAND).runCommand(event, Arrays.asList(botArgs));
            } else {
                String command = botArgs[0].toLowerCase();
                List<String> commandArgs = Arrays.asList(Arrays.copyOfRange(botArgs, 1, botArgs.length));

                if (commandMap.containsKey(command)) {
                    commandMap.get(command).runCommand(event, commandArgs);
                } else {
                    commandMap.get(HELP_COMMAND).runCommand(event, commandArgs);
                }
            }
        }
    }
}
