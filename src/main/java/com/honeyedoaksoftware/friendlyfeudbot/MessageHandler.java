package com.honeyedoaksoftware.friendlyfeudbot;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;

import java.util.Arrays;

public class MessageHandler {

    private static final String HELP_COMMAND = "help";
    private static final String WINNER_COMMAND = "winner";
    private static final String LIST_COMMAND = "list";
    private static final String CHALLENGE_COMMAND = "challenge";

    public void handleHelpCommand(IChannel channel, String[] commandArgs) throws DiscordException, MissingPermissionsException {
        BotUtils.sendMessage(channel, BotUtils.BOT_NAME + " help function not implemented yet. Received the following arguments: " + Arrays.toString(commandArgs));
    }

    public void handleWinCommand(IChannel channel, String[] commandArgs) throws DiscordException, MissingPermissionsException {
        BotUtils.sendMessage(channel, "Win decided!! " + BotUtils.BOT_NAME + " received the following arguments: " + Arrays.toString(commandArgs));
    }

    public void handleListCommand(IChannel channel, String[] commandArgs) throws DiscordException, MissingPermissionsException {
        BotUtils.sendMessage(channel, "Feuds list requested! " + BotUtils.BOT_NAME + " received the following arguments: " + Arrays.toString(commandArgs));
    }


    public void handleChallengeCommand(IChannel channel, String[] commandArgs) throws DiscordException, MissingPermissionsException {
        BotUtils.sendMessage(channel, "Feuds list requested! " + BotUtils.BOT_NAME + " received the following arguments: " + Arrays.toString(commandArgs));
    }

    @EventSubscriber
    public void feudMessageHandler(MessageReceivedEvent event) throws DiscordException, MissingPermissionsException {
        String[] messageParts = BotUtils.getMessageParts(event);

        if (messageParts.length >= 1 && messageParts[0].startsWith(BotUtils.BOT_PREFIX.concat(BotUtils.BOT_TAG))) {
            String[] botArgs = Arrays.copyOfRange(messageParts, 1, messageParts.length);

            if (botArgs.length == 0) {
                handleHelpCommand(event.getChannel(), botArgs);
            } else {
                String command = botArgs[0].toLowerCase();
                String[] commandArgs = Arrays.copyOfRange(botArgs, 1, botArgs.length);

                switch (command) {
                    case CHALLENGE_COMMAND:
                        handleChallengeCommand(event.getChannel(), commandArgs);
                        break;
                    case WINNER_COMMAND:
                        handleWinCommand(event.getChannel(), commandArgs);
                        break;
                    case LIST_COMMAND:
                        handleListCommand(event.getChannel(), commandArgs);
                        break;
                    case HELP_COMMAND:
                    default:
                        handleHelpCommand(event.getChannel(), commandArgs);
                }
            }
        }
    }
}
