package team.CowsAndHorses.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.CowsAndHorses.dto.AjaxResult;
import team.CowsAndHorses.entity.CourseEntity;
import team.CowsAndHorses.entity.StudentCourseTeacherEntity;
import team.CowsAndHorses.mapper.StudentCourseTeacherMapper;
import team.CowsAndHorses.service.CourseService;
import team.CowsAndHorses.util.ParseUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/course")
public class CourseController {

    final CourseService courseService;
    final StudentCourseTeacherMapper studentCourseTeacherMapper;

    @GetMapping("/query/{id}")
    public AjaxResult<Object> getCourseById(@PathVariable Integer id) {
        CourseEntity course = courseService.getById(id);
        return AjaxResult.SUCCESS(course);
//        Map<String, Object> resultMap = l;
//        return resultMap;
    }

    @GetMapping("/query")
    public AjaxResult<Object> getCourseList(HttpServletRequest request) {
        Integer userId = ParseUtil.parseToken(request, "userId");
        Integer role = ParseUtil.parseToken(request, "role");
        Map<String, Object> resultMap = new HashMap<>();
        QueryWrapper<StudentCourseTeacherEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id", userId);
        List<Integer> ids = new ArrayList<>();List<CourseEntity> courseList = new ArrayList<>();
        List<StudentCourseTeacherEntity> studentCourseTeacherEntities =
                studentCourseTeacherMapper.selectList(queryWrapper);
        if (studentCourseTeacherEntities.isEmpty()) {
            resultMap.put("learningCourses", new ArrayList<>());
        } else {
            ids = studentCourseTeacherEntities.stream()
                    .map(StudentCourseTeacherEntity::getCourseId)
                    .collect(Collectors.toList());
            courseList = courseService.listByIds(ids);
            resultMap.put("learningCourses", courseList);
        }
        if (role == 1) {
            return AjaxResult.SUCCESS(resultMap);
        } else if (role == 2) {
            queryWrapper.clear();
            queryWrapper.eq("teacher_id", userId);
            studentCourseTeacherEntities =
                    studentCourseTeacherMapper.selectList(queryWrapper);
            if (studentCourseTeacherEntities.isEmpty()) {
                resultMap.put("teachingCourses", new ArrayList<>());
            } else {
                ids = studentCourseTeacherEntities.stream()
                        .map(StudentCourseTeacherEntity::getCourseId)
                        .collect(Collectors.toList());
                courseList = courseService.listByIds(ids);
                resultMap.put("teachingCourses", courseList);
            }
        }
        return AjaxResult.SUCCESS(resultMap);
    }

    @DeleteMapping("/remove/{id}")
    public AjaxResult<Object> removeCourse(@PathVariable Integer id) {
        return AjaxResult.SUCCESS(courseService.removeById(id));
    }

    @PostMapping("/update")
    public AjaxResult<Object> updateCourse(@RequestBody CourseEntity course) {
        return AjaxResult.SUCCESS(courseService.updateById(course));
    }

    @PostMapping("/add")
    public AjaxResult<Object> addCourse(@RequestBody CourseEntity course) {
        return AjaxResult.SUCCESS(courseService.save(course));
    }
}
