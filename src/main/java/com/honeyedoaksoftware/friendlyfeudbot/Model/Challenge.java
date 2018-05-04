package com.honeyedoaksoftware.friendlyfeudbot.Model;

import lombok.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Challenge implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private long guildId;

	@Column(nullable = false)
	private long challengerUserId;

	@Column(nullable = false)
	private long defenderUserId;

	private Long refereeUserId;

	private String challenge;

	@Column(nullable = false)
	@Setter(AccessLevel.NONE)
	private String challengeCode;

	@PrePersist
	public void generateChallengeCode() {
		challengeCode = RandomStringUtils.random(6, true, true).toUpperCase();
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
}
