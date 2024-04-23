package team.CowsAndHorses.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Path;
import org.neo4j.driver.types.Relationship;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
public class GraphData {
    private Set<NodeRepresentation> nodes = new HashSet<>();
    private Set<RelationshipRepresentation> relationships = new HashSet<>();

    public void addPath(Path path) {
        for (Path.Segment segment : path) {
            Node startNode = segment.start();
            Node endNode = segment.end();
            Relationship relationship = segment.relationship();
            nodes.add(new NodeRepresentation(startNode));
            nodes.add(new NodeRepresentation(endNode));
            relationships.add(new RelationshipRepresentation(relationship));
        }
    }

    // Getters and Setters
}



