package lol.ovr.player_profile.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import java.util.List;
import lombok.*;
import lol.ovr.player_profile.infrastructure.adapter.out.persistence.converter.IntegerListJsonConverter;
import lol.ovr.player_profile.infrastructure.adapter.out.persistence.converter.StringListJsonConverter;
import lol.ovr.player_profile.infrastructure.adapter.out.persistence.converter.MatchParticipantListJsonConverter;
import lol.ovr.player_profile.domain.model.MatchParticipant;

@Entity
@Table(name = "player_cards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerCardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String puuid;

    @Column(nullable = false)
    private String matchId;

    private int overallRating;
    private int mechanicsScore;
    private int farmingScore;
    private int visionScore;

    @Column(nullable = false)
    private long gameCreation;

    @Column(nullable = false)
    private String championName;

    private int kills;
    private int deaths;
    private int assists;
    private int creepScore;
    private boolean win;
    private long gameDuration;

    private int queueId;
    private int summoner1Id;
    private int summoner2Id;

    private int item0;
    private int item1;
    private int item2;
    private int item3;
    private int item4;
    private int item5;
    private int item6;

    @Column(columnDefinition = "TEXT", nullable = false)
    @Convert(converter = IntegerListJsonConverter.class)
    private List<Integer> itemIds;

    @Column(columnDefinition = "TEXT", nullable = false)
    @Convert(converter = IntegerListJsonConverter.class)
    private List<Integer> primaryRuneIds;

    @Column(columnDefinition = "TEXT", nullable = false)
    @Convert(converter = IntegerListJsonConverter.class)
    private List<Integer> secondaryRuneIds;

    @Column(columnDefinition = "TEXT", nullable = false)
    @Convert(converter = StringListJsonConverter.class)
    private List<String> enemyChampions;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = MatchParticipantListJsonConverter.class)
    private List<MatchParticipant> participants;
}