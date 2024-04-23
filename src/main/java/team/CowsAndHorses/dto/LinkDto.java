package team.CowsAndHorses.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LinkDto {
    private String source;
    private String target;
    private String type;
}
