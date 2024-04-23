package team.CowsAndHorses.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.neo4j.driver.internal.value.NodeValue;
import org.neo4j.driver.internal.value.PathValue;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import team.CowsAndHorses.entity.CourseNodeEntity;

import java.util.List;

@Mapper
public interface CourseGraphMapper extends Neo4jRepository<CourseNodeEntity, Long> {
    @Query("MATCH (n) RETURN n")
    List<NodeValue> findAllNodes();

    @Query("MATCH path = (n {name: $name})-[*]-(connectedNodes) RETURN path")
    List<PathValue> findAllConnectedNodes(@Param("name") String name);
}
