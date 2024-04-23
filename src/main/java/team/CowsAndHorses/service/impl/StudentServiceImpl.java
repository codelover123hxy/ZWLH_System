package team.CowsAndHorses.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.CowsAndHorses.dto.PageQueryDto;
import team.CowsAndHorses.dto.QuestionDto;
import team.CowsAndHorses.entity.AskTeacherEntity;
import team.CowsAndHorses.entity.TeacherResponseEntity;
import team.CowsAndHorses.mapper.AskTeacherMapper;
import team.CowsAndHorses.mapper.TeacherResponseMapper;
import team.CowsAndHorses.service.StudentService;
import team.CowsAndHorses.util.PageUtil;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    final AskTeacherMapper askTeacherMapper;
    final TeacherResponseMapper teacherResponseMapper;


    @Override
    public void askTeacher(AskTeacherEntity askTeacher) {
        askTeacherMapper.insert(askTeacher);
    }
    @Override
    public IPage<QuestionDto> getQuestions(Integer userId, Integer courseId, PageQueryDto pageQueryDto) {
        IPage<AskTeacherEntity> askTeacherEntityIPage = askTeacherMapper.selectPage(PageUtil.toIPage(pageQueryDto),
                new QueryWrapper<AskTeacherEntity>().eq("user_id", userId)
                        .eq("course_id", courseId));
        IPage<QuestionDto> questionDtoIPage = PageUtil.pageFormatTransform(askTeacherEntityIPage, QuestionDto.class);
        List<QuestionDto> questionDtos = new ArrayList<>();
        for (AskTeacherEntity askTeacher: askTeacherEntityIPage.getRecords()) {
            QuestionDto questionDto = new QuestionDto(askTeacher);
            questionDto.setTeacherAnswers(
                    teacherResponseMapper.selectList(
                        new QueryWrapper<TeacherResponseEntity>().eq("evaluate_id", askTeacher.getId())
                    )
            );
            questionDtos.add(questionDto);
        }
        questionDtoIPage.setRecords(questionDtos);
        return questionDtoIPage;
    }
}
