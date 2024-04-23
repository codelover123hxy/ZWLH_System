package team.CowsAndHorses.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.CowsAndHorses.dto.AjaxResult;
import team.CowsAndHorses.dto.PageQueryDto;
import team.CowsAndHorses.dto.QuestionDto;
import team.CowsAndHorses.entity.AskTeacherEntity;
import team.CowsAndHorses.service.StudentService;
import team.CowsAndHorses.util.PageUtil;
import team.CowsAndHorses.util.ParseUtil;

import java.util.Map;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/student")
public class StudentController {
    final StudentService studentService;

    @PostMapping("/question/submit")
    public AjaxResult<Object> submitQuestion(HttpServletRequest request, AskTeacherEntity askTeacher) {
        Integer userId = ParseUtil.parseToken(request, "userId");
        askTeacher.setUserId(userId);
        studentService.askTeacher(askTeacher);
        return AjaxResult.SUCCESS();
    }

    @GetMapping("/question/query/{courseId}")
    public AjaxResult<Object> getQuestions(HttpServletRequest request,
                                           @PathVariable Integer courseId,
                                           PageQueryDto pageQueryDto) {
        Integer userId = ParseUtil.parseToken(request, "userId");
//        IPage<AskTeacherEntity> pages = studentService.get
        IPage<QuestionDto> questions = studentService.getQuestions(userId, courseId, pageQueryDto);
        return AjaxResult.SUCCESS(questions);
    }
}
