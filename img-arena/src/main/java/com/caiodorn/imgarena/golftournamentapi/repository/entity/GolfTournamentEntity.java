package com.caiodorn.imgarena.golftournamentapi.repository.entity;

import com.caiodorn.imgarena.golftournamentapi.rest.resource.SourceId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "GolfTournamentEntity")
@Table(
        name = "golf_tournament",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"external_id", "source_id"})
        }
)
public class GolfTournamentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(unique = true, updatable = false, insertable = false)
    private Long id;

    @Column(name = "external_id", nullable = false)
    private String externalId;

    @Column(name = "tournament_name", nullable = false)
    private String tournamentName;

    @Column(name = "start_dt", nullable = false)
    private LocalDateTime startDateTime;

    @Column(name = "end_dt", nullable = false)
    private LocalDateTime endDateTime;

    @Column(name = "course_name", nullable = false)
    private String courseName;

    @Column(nullable = false)
    private String country;

    @Column(name = "num_rounds", nullable = false)
    private Integer numberOfRounds;

    @Column(name = "player_count")
    private Integer playerCount;

    @Column
    private String forecast;

    @Column(name = "source_id", nullable = false)
    private SourceId sourceId;

}
