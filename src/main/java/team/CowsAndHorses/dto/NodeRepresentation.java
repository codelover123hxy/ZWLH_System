package team.CowsAndHorses.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.driver.types.Node;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
class NodeRepresentation {
    private Long id;
    private Map<String, Object> properties;

    public NodeRepresentation(Node node) {
        this.id = node.id();
        this.properties = new HashMap<>();
        node.keys().forEach(key -> this.properties.put(key, node.get(key).asObject()));
    }
    // Constructor, Getters, and Setters
}