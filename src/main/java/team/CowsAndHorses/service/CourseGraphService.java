package team.CowsAndHorses.service;

import org.neo4j.driver.internal.value.PathValue;

import java.util.List;

public interface CourseGraphService {
    List<PathValue> getConnectedNode(String name);
}
