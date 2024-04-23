package team.CowsAndHorses.dto;

import lombok.Data;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Relationship;

import java.util.List;

@Data
public class GraphDto {
    private final List<Node> nodes;
    private final List<Relationship> relationships;

    public GraphDto(List<Node> nodes, List<Relationship> relationships) {
        this.nodes = nodes;
        this.relationships = relationships;
    }
}
