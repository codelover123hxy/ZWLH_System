package team.CowsAndHorses.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team.CowsAndHorses.dto.AjaxResult;
import team.CowsAndHorses.dto.PageQueryDto;
import team.CowsAndHorses.entity.AskTeacherEntity;
import team.CowsAndHorses.entity.TeacherResponseEntity;
import team.CowsAndHorses.service.TeacherService;
import team.CowsAndHorses.util.ParseUtil;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/teacher")
public class TeacherController {
    final TeacherService teacherService;

    @PostMapping("/response/submit")
    public AjaxResult<Object> submitResponse(HttpServletRequest request,
            @RequestBody TeacherResponseEntity teacherResponseEntity) {
        Integer role = ParseUtil.parseToken(request, "role");
        if (role == 1) {
            return AjaxResult.FAIL();
        }
        else {
            teacherResponseEntity.setTeacherId(ParseUtil.parseToken(request, "userId"));
            Integer res = teacherService.submitResponse(teacherResponseEntity);
            return res > 0 ? AjaxResult.SUCCESS() : AjaxResult.FAIL();
        }
    }

    @PostMapping("/response/update/{responseId}")
    public AjaxResult<Object> updateResponse(@PathVariable Integer responseId,
                                             @RequestBody Map<String, String> requestBody) {
        Integer res = teacherService.updateResponse(responseId, requestBody.get("response"));
        return res > 0 ? AjaxResult.SUCCESS(): AjaxResult.FAIL();
    }

    @GetMapping("/question/query")
    public AjaxResult<Object> getQuestionList(PageQueryDto pageQueryDto) {
        IPage<AskTeacherEntity> askTeacherEntities = teacherService.getQuestions(pageQueryDto);
        return AjaxResult.SUCCESS(askTeacherEntities);
    }
}
