package team.CowsAndHorses.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.transaction.annotation.Transactional;
import team.CowsAndHorses.dto.PageQueryDto;
import team.CowsAndHorses.entity.AskTeacherEntity;
import team.CowsAndHorses.entity.TeacherResponseEntity;

import java.util.List;
@Transactional
public interface TeacherService {
    Integer submitResponse(TeacherResponseEntity teacherResponseEntity);
    Integer updateResponse(Integer responseId, String response);

    IPage<AskTeacherEntity> getQuestions(PageQueryDto pageQueryDto);
}
