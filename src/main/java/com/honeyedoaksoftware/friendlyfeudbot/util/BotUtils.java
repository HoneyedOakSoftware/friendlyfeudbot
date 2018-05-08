package com.honeyedoaksoftware.friendlyfeudbot.util;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RequestBuffer;

import java.util.Optional;

public class BotUtils {

	// Constants for use throughout the bot
	public static final String BOT_PREFIX = "!";
	public static final String BOT_TAG = "feud";
	public static final String BOT_NAME = "Friendly Feuds";

	// Handles the creation and getting of a IDiscordClient object for a token
	public static IDiscordClient getBuiltDiscordClient(String token) {

		// The ClientBuilder object is where you will attach your params for configuring the instance of your bot.
		// Such as withToken, setDaemon etc
		return new ClientBuilder()
				.withToken(token)
				.build();
	}

	// Helper functions to make certain aspects of the bot easier to use.
	public static void sendMessage(IChannel channel, String message) {
		// This might look weird but it'll be explained in another page.
		RequestBuffer.request(() -> {
			try {
				channel.sendMessage(message);
			} catch (DiscordException e) {
				System.err.println("Message could not be sent with error: ");
				e.printStackTrace();
			}
		});
	}

	public static String[] getMessageParts(MessageEvent event) {
		return event.getMessage().getContent().split(" ");
	}

	public static String[] getMessageParts(IMessage message) {
		return message.getContent().split(" ");
	}

	public static Optional<Long> getIdFromText(String s) {
		Long id = null;

		String temp = s.trim();
		if (temp.matches("<@\\d*>")) {
			id = Long.parseLong(temp.substring(2, temp.length() - 1));
		}

		return Optional.ofNullable(id);
	}

	public static String userLongIdToMention(long userLongId) {
		return "<@".concat(String.valueOf(userLongId)).concat(">");
	}
}
