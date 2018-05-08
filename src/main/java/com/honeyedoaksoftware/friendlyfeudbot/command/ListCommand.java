package com.honeyedoaksoftware.friendlyfeudbot.command;

import com.honeyedoaksoftware.friendlyfeudbot.repository.ChallengeRepository;
import com.honeyedoaksoftware.friendlyfeudbot.util.BotUtils;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Log4j
@Component
public class ListCommand implements Command {

	public static final String COMMAND_TEXT = "list";

	private static final String HELP_COMMAND = "help";
	private static final String ALL_COMMAND = "all";
	private static final String MINE_COMMAND = "mine";
	private static final String CHALLENGER_COMMAND = "challenger";
	private static final String DEFENDER_COMMAND = "defender";
	private static final String REFEREE_COMMAND = "referee";

	// A map of commands mapping from command string to the functional impl
	private Map<String, Command> commandMap = new HashMap<>();

	private void buildCommandMap() {
		commandMap.put(HELP_COMMAND, (event, args) -> BotUtils.sendMessage(event.getChannel(), "Help function not yet implemented"));
		commandMap.put(ALL_COMMAND, (event, args) -> BotUtils.sendMessage(event.getChannel(), challengeRepository.findByGuildId(event.getGuild().getLongID()).toString()));
		commandMap.put(MINE_COMMAND, (event, args) -> BotUtils.sendMessage(event.getChannel(), challengeRepository.findApplicableTo(event.getGuild().getLongID(), event.getAuthor().getLongID()).toString()));
		commandMap.put(CHALLENGER_COMMAND, (event, args) -> BotUtils.sendMessage(event.getChannel(), challengeRepository.findByGuildIdAndChallengerUserId(event.getGuild().getLongID(), event.getAuthor().getLongID()).toString()));
		commandMap.put(DEFENDER_COMMAND, (event, args) -> BotUtils.sendMessage(event.getChannel(), challengeRepository.findByGuildIdAndDefenderUserId(event.getGuild().getLongID(), event.getAuthor().getLongID()).toString()));
		commandMap.put(REFEREE_COMMAND, (event, args) -> BotUtils.sendMessage(event.getChannel(), challengeRepository.findByGuildIdAndRefereeUserId(event.getGuild().getLongID(), event.getAuthor().getLongID()).toString()));
	}

	private ChallengeRepository challengeRepository;

	@Autowired
	public ListCommand(ChallengeRepository challengeRepository) {
		this.challengeRepository = challengeRepository;
		buildCommandMap();
	}

	@Override
	public void runCommand(MessageReceivedEvent event, String[] args) {
		IChannel channel = event.getChannel();
		log.debug("Feuds list requested! " + BotUtils.BOT_NAME + " received the following arguments: " + Arrays.toString(args));

		if (args.length == 0) {
			commandMap.get(HELP_COMMAND).runCommand(event, args);
		} else {
			String command = args[0].toLowerCase();

			if (commandMap.containsKey(command)) {
				commandMap.get(command).runCommand(event, args);
			} else {
				commandMap.get(HELP_COMMAND).runCommand(event, args);
			}
		}
	}
}
