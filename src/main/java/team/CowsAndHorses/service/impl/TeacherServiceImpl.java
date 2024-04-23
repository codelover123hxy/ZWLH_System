package team.CowsAndHorses.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.CowsAndHorses.dto.PageQueryDto;
import team.CowsAndHorses.entity.AskTeacherEntity;
import team.CowsAndHorses.entity.TeacherResponseEntity;
import team.CowsAndHorses.mapper.AskTeacherMapper;
import team.CowsAndHorses.mapper.TeacherResponseMapper;
import team.CowsAndHorses.service.TeacherService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {
    final TeacherResponseMapper teacherResponseMapper;
    final AskTeacherMapper askTeacherMapper;
    @Override
    public Integer submitResponse(TeacherResponseEntity teacherResponseEntity) {
        return teacherResponseMapper.insert(teacherResponseEntity);
    }
    @Override
    public Integer updateResponse(Integer responseId, String response) {
        TeacherResponseEntity teacherResponseEntity = teacherResponseMapper.selectById(responseId);
        teacherResponseEntity.setResponse(response);
        return teacherResponseMapper.updateById(teacherResponseEntity);
    }
    @Override
    public IPage<AskTeacherEntity> getQuestions(PageQueryDto pageQueryDto) {
        IPage<AskTeacherEntity> page = new Page<>(pageQueryDto.getPageNum(), pageQueryDto.getPageSize());
        return askTeacherMapper.selectPage(page, null);
    }
}
