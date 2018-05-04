package com.honeyedoaksoftware.friendlyfeudbot.command;

import com.honeyedoaksoftware.friendlyfeudbot.util.BotUtils;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;

import java.util.Arrays;
import java.util.List;

public interface ListCommand extends Command {

    @Override
    default void runCommand(MessageReceivedEvent event, String[] args) {
        IChannel channel = event.getChannel();
        BotUtils.sendMessage(channel, "Feuds list requested! " + BotUtils.BOT_NAME + " received the following arguments: " + Arrays.toString(args));
    }
}
