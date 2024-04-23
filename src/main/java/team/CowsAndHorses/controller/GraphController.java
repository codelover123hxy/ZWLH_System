package team.CowsAndHorses.controller;

import lombok.RequiredArgsConstructor;
import org.neo4j.driver.internal.value.NodeValue;
import org.neo4j.driver.internal.value.PathValue;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Path;
import org.neo4j.driver.types.Relationship;
import org.springframework.web.bind.annotation.*;
import team.CowsAndHorses.dto.AjaxResult;
import team.CowsAndHorses.dto.CategoryDto;
import team.CowsAndHorses.dto.LinkDto;
import team.CowsAndHorses.dto.NodeDto;
import team.CowsAndHorses.service.CourseGraphService;

import java.util.*;
import java.util.stream.IntStream;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/graph")
public class GraphController {

    final CourseGraphService courseGraphService;

    @GetMapping("/connectedNodes")
    public AjaxResult<Object> getConnectedNodes(@RequestParam String name) {
        List<PathValue> nodes = courseGraphService.getConnectedNode(name);
        Set<NodeDto> nodeDtoSet = new HashSet<>();
        Set<LinkDto> linkDtoSet = new HashSet<>();
        Set<CategoryDto> categoryDtoSet = new HashSet<>();
        for (PathValue node: nodes) {
            Path path = node.asPath();
            Node start = path.start();
            Node end = path.end();
            String startCategory = ((List<String>) start.labels()).get(0);
            String endCategory = ((List<String>) end.labels()).get(0);
            categoryDtoSet.add(new CategoryDto(startCategory));
            categoryDtoSet.add(new CategoryDto(endCategory));
            List<Relationship> relationship = (List<Relationship>) path.relationships();
            relationship.forEach(item -> {
                String type = item.type();
                String endNodeElementId = item.endNodeElementId().split(":")[2];
                String startNodeElementId = item.startNodeElementId().split(":")[2];
                linkDtoSet.add(new LinkDto(startNodeElementId, endNodeElementId, type));
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
            List<CategoryDto> categoryDtoList = new ArrayList<>(categoryDtoSet);

            Long startCategoryIndex = (long) IntStream.range(0, categoryDtoSet.toArray().length).
                    filter(i -> startCategory.equals(categoryDtoList.get(i).getName()))
                    .findFirst().orElse(-1);

            Long endCategoryIndex = (long) IntStream.range(0, categoryDtoSet.toArray().length).
                    filter(i -> endCategory.equals(categoryDtoList.get(i).getName()))
                    .findFirst().orElse(-1);

            nodeDtoSet.add(new NodeDto(startId, startName, startCategoryIndex));
            nodeDtoSet.add(new NodeDto(endId, endName, endCategoryIndex));
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("nodes", nodeDtoSet);
        resultMap.put("links", linkDtoSet);
        resultMap.put("categories", categoryDtoSet);
        return AjaxResult.SUCCESS(resultMap);
    }

//    @GetMapping("/all")
//    public Collection<GraphRelationshipEntity> getAllNodesAndRelationships() {
//        Collection<GraphRelationshipEntity> graphRelationshipEntities = graphService.getAllNodesAndRelationships();
//        System.out.println(graphRelationshipEntities);
//        return graphService.getAllNodesAndRelationships();
//    }

//    @GetMapping("/all/nodes")
//    public Collection<Object> getAllNodes() {
//        List<Object> graphEntities = (List<Object>) graphService.getAllNodes();
//        graphEntities.forEach(item -> {
//            NodeValue node = (NodeValue) item;
//            System.out.println(node.asNode().keys());
//            System.out.println(node.asNode().values());
//        });
//        return null;
//    }
}