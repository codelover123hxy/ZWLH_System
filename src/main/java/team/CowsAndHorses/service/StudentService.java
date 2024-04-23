package team.CowsAndHorses.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import team.CowsAndHorses.dto.PageQueryDto;
import team.CowsAndHorses.dto.QuestionDto;
import team.CowsAndHorses.entity.AskTeacherEntity;
@Transactional
public interface StudentService {
    void askTeacher(AskTeacherEntity askTeacher);
    IPage<QuestionDto> getQuestions(Integer userId, Integer courseId, PageQueryDto pageQueryDto);
}
