package team.CowsAndHorses.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.driver.types.Relationship;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
class RelationshipRepresentation {
    private Long id;
    private String type;
    private Map<String, Object> properties;

    public RelationshipRepresentation(Relationship relationship) {
        this.id = relationship.id();
        this.type = relationship.type();
        this.properties = new HashMap<>();
        relationship.keys().forEach(key -> this.properties.put(key, relationship.get(key).asObject()));
    }
    // Constructor, Getters, and Setters
}