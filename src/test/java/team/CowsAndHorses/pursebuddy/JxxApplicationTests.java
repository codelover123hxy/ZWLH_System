package team.CowsAndHorses.pursebuddy;

import org.junit.jupiter.api.Test;
import org.neo4j.driver.internal.value.NodeValue;
import org.neo4j.driver.internal.value.PathValue;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Path;
import org.neo4j.driver.types.Relationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team.CowsAndHorses.dto.CategoryDto;
import team.CowsAndHorses.dto.LinkDto;
import team.CowsAndHorses.dto.NodeDto;
import team.CowsAndHorses.mapper.CourseGraphMapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

@SpringBootTest
class JxxApplicationTests {

    @Autowired
    private CourseGraphMapper courseGraphMapper;

    @Test
    void test2() {
        List<NodeValue> nodeValues = courseGraphMapper.findAllNodes();
        for (NodeValue nodeValue: nodeValues) {
            Node node = nodeValue.asNode();
            node.elementId();
        }
    }

    @Test
    void testList() {

    }

    @Test
    void test() {
        List<PathValue> nodes = courseGraphMapper.findAllConnectedNodes("计算机网络");
        Set<NodeDto> nodeDtos = new HashSet<>();
        Set<LinkDto> linkDtos = new HashSet<>();
        Set<CategoryDto> categoryDtos = new HashSet<>();

        for (PathValue node: nodes) {
            Path path = node.asPath();
            Node start = path.start();
            Node end = path.end();

            String startCategory = ((List<String>) start.labels()).get(0);
            String endCategory = ((List<String>) end.labels()).get(0);
            categoryDtos.add(new CategoryDto(startCategory));
            categoryDtos.add(new CategoryDto(endCategory));

            List<Relationship> relationship = (List<Relationship>) path.relationships();
            relationship.forEach(item -> {
                String type = item.type();
                String endNodeElementId = item.endNodeElementId().split(":")[2];
                String startNodeElementId = item.startNodeElementId().split(":")[2];
                linkDtos.add(new LinkDto(startNodeElementId, endNodeElementId, type));
            });
        }

        for (PathValue node: nodes) {
            Path path = node.asPath();
            Node start = path.start();
            Node end = path.end();
            String startId = start.elementId().split(":")[2];
            String startName = start.get("name").asString();
            String endId = end.elementId().split(":")[2];
            String endName = end.get("name").asString();
            String startCategory = ((List<String>) start.labels()).get(0);
            String endCategory = ((List<String>) end.labels()).get(0);

//            categoryDtos.toArray();

            List<CategoryDto> categoryDtoList = new ArrayList<>(categoryDtos);

            Long startCategoryIndex = (long) IntStream.range(0, categoryDtos.toArray().length).
                    filter(i -> startCategory.equals(categoryDtoList.get(i).getName()))
                    .findFirst().orElse(-1);

            Long endCategoryIndex = (long) IntStream.range(0, categoryDtos.toArray().length).
                    filter(i -> endCategory.equals(categoryDtoList.get(i).getName()))
                    .findFirst().orElse(-1);

            nodeDtos.add(new NodeDto(startId, startName, startCategoryIndex));
            nodeDtos.add(new NodeDto(endId, endName, endCategoryIndex));
        }
    }
}
