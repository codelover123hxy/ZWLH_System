package team.CowsAndHorses.entity;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;

public class CourseNodeEntity {
    @Id
    @GeneratedValue
    private Long id;
}
