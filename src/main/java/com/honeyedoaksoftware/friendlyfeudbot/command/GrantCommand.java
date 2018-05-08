package com.honeyedoaksoftware.friendlyfeudbot.command;

import com.honeyedoaksoftware.friendlyfeudbot.Model.Challenge;
import com.honeyedoaksoftware.friendlyfeudbot.repository.ChallengeRepository;
import com.honeyedoaksoftware.friendlyfeudbot.util.BotUtils;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;

import java.util.Arrays;
import java.util.Optional;

@Log4j
@Component
public class GrantCommand implements Command {

	public static final String COMMAND_TEXT = "grant";

	private ChallengeRepository challengeRepository;

	@Autowired
	public GrantCommand(ChallengeRepository challengeRepository) {
		this.challengeRepository = challengeRepository;
	}

	@Override
	public void runCommand(MessageReceivedEvent event, String[] args) {
		IChannel channel = event.getChannel();
		long guildId = event.getGuild().getLongID();

		log.debug("Win decided!! " + BotUtils.BOT_NAME + " received the following arguments: " + Arrays.toString(args));

		if (!args[0].matches("[A-Z0-9]{6}")) {
			BotUtils.sendMessage(channel, "You need to pass the challenge code as first argument");
			return;
		}

		String challengeCode = args[0];

		Optional<Challenge> challengeOpt = challengeRepository.findByGuildIdAndChallengeCode(guildId, challengeCode);

		if (!challengeOpt.isPresent()) {
			BotUtils.sendMessage(channel, "No challenge with code " + challengeCode + " was found");
			return;
		}

		Challenge challenge = challengeOpt.get();
		if (challenge.getWinnerUserId() != null) {
			BotUtils.sendMessage(channel, "Challenge with code " + challengeCode + " was already decided.");
			return;
		}

		Optional<Long> granteeUserId = BotUtils.getIdFromText(args[1]);

		if (!granteeUserId.isPresent()) {
			BotUtils.sendMessage(channel, "No user to grant victory to identified");
			return;
		}

		Long winnerUserId = granteeUserId.get();
		long defenderUserId = challenge.getDefenderUserId();
		long authorId = event.getAuthor().getLongID();
		long challengerUserId = challenge.getChallengerUserId();
		if (authorId == challenge.getRefereeUserId()) {
			log.debug("user calling the grant is referee");

			if (challengerUserId == winnerUserId || defenderUserId == winnerUserId) {
				challenge.setWinnerUserId(winnerUserId);
			}
		} else {
			log.debug("user calling the grant is not the referee");

			if (authorId == winnerUserId) {
				BotUtils.sendMessage(channel, "You cannot grant a victory to yourself");
				return;
			}

			if (authorId != defenderUserId || authorId != challengerUserId) {
				BotUtils.sendMessage(channel, "You cannot grant a victory to a challenge that does not include you as a participant");
				return;
			}

			challenge.setWinnerUserId(winnerUserId);
		}

		challengeRepository.save(challenge);

		log.trace("challenge saved: " + challenge.toString());
	}
}
