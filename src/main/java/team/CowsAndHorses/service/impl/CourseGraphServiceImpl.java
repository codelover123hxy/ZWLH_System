package team.CowsAndHorses.service.impl;

import org.neo4j.driver.internal.value.PathValue;
import org.springframework.stereotype.Service;
import team.CowsAndHorses.mapper.CourseGraphMapper;
import team.CowsAndHorses.service.CourseGraphService;

import java.util.List;

@Service
public class CourseGraphServiceImpl implements CourseGraphService {
    final CourseGraphMapper courseGraphMapper;

    public CourseGraphServiceImpl(CourseGraphMapper courseGraphMapper) {
        this.courseGraphMapper = courseGraphMapper;
    }
    public List<PathValue> getConnectedNode(String name) {
        return courseGraphMapper.findAllConnectedNodes(name);
    }
}
