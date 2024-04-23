package team.CowsAndHorses.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NodeDto {
    private String id;
    private String name;
    private Long category;
}
