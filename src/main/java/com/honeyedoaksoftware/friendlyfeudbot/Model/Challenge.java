package com.honeyedoaksoftware.friendlyfeudbot.Model;

import com.honeyedoaksoftware.friendlyfeudbot.util.BotUtils;
import lombok.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames = {"guildId", "challengeCode"})
})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Challenge implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, updatable = false)
	private long guildId;

	@Column(nullable = false, updatable = false)
	private long challengerUserId;

	@Column(nullable = false, updatable = false)
	private long defenderUserId;

	private Long refereeUserId;

	private String challenge;

	@Column(nullable = false, length = 12, updatable = false)
	@Setter(AccessLevel.NONE)
	private String challengeCode;

	@Column(updatable = false)
	private Long winnerUserId;

	@PrePersist
	public void generateChallengeCode() {
		if (Objects.isNull(challengeCode)) {
			challengeCode = RandomStringUtils.random(6, true, true).toUpperCase();
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		Challenge challenge1 = (Challenge) o;

		return new EqualsBuilder()
				.append(guildId, challenge1.guildId)
				.append(challengeCode, challenge1.challengeCode)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(guildId)
				.append(challengeCode)
				.toHashCode();
	}

	@Override
	public String toString() {
		StringBuilder challengeStringBuilder = new StringBuilder("Challenge[").append(challengeCode).append("]").append("\n")
				.append(BotUtils.userLongIdToMention(challengerUserId))
				.append(" Challenged ")
				.append(BotUtils.userLongIdToMention(defenderUserId))
				.append(" To a duel of \"").append(challenge).append("\".");

		if (Objects.nonNull(refereeUserId)) {
			challengeStringBuilder.append(BotUtils.userLongIdToMention(refereeUserId)).append(" has been chosen as witness and arbiter");
		}

		return challengeStringBuilder.toString();
	}
}
