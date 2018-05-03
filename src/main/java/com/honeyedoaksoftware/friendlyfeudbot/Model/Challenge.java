package com.honeyedoaksoftware.friendlyfeudbot.Model;

import lombok.*;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Challenge challenge1 = (Challenge) o;

        return new EqualsBuilder()
                .append(guildId, challenge1.guildId)
                .append(challengerUserId, challenge1.challengerUserId)
                .append(defenderUserId, challenge1.defenderUserId)
                .append(id, challenge1.id)
                .append(challenge, challenge1.challenge)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(guildId)
                .append(challengerUserId)
                .append(defenderUserId)
                .append(challenge)
                .toHashCode();
    }
}
