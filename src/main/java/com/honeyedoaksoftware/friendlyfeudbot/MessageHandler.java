package com.honeyedoaksoftware.friendlyfeudbot;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;

public class MessageHandler {

    @EventSubscriber
    public void challengeMesageEvent(MessageReceivedEvent event) throws DiscordException, MissingPermissionsException {
        IMessage message = event.getMessage();
        if (message.getContent().startsWith(BotUtils.BOT_PREFIX.concat("challenge"))) {

            BotUtils.sendMessage(event.getMessage().getChannel(), "Challenge send! " + BotUtils.BOT_NAME + " is working.");
        }
    }

    @EventSubscriber
    public void ListMesageEvent(MessageReceivedEvent event) throws DiscordException, MissingPermissionsException {
        IMessage message = event.getMessage();
        if (message.getContent().startsWith(BotUtils.BOT_PREFIX.concat("list"))) {

            BotUtils.sendMessage(event.getMessage().getChannel(), "Challenge List Requested! " + BotUtils.BOT_NAME + " is working.");
        }
    }

    @EventSubscriber
    public void winDecideMesageEvent(MessageReceivedEvent event) throws DiscordException, MissingPermissionsException {
        IMessage message = event.getMessage();
        if (message.getContent().startsWith(BotUtils.BOT_PREFIX.concat("winner"))) {

            BotUtils.sendMessage(event.getMessage().getChannel(), "Challenge winner declared! " + BotUtils.BOT_NAME + " is working.");
        }
    }

    @EventSubscriber
    public void helpMesageEvent(MessageReceivedEvent event) throws DiscordException, MissingPermissionsException {
        IMessage message = event.getMessage();
        if (message.getContent().startsWith(BotUtils.BOT_PREFIX.concat("help"))) {

            BotUtils.sendMessage(event.getMessage().getChannel(), BotUtils.BOT_NAME + " help function not implemented yet.");
        }
    }


}
