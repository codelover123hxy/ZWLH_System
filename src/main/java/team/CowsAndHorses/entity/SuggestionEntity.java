package team.CowsAndHorses.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SuggestionEntity {
    private Integer id;
    private Integer userId;
    private String content;
    private String answer;
}
