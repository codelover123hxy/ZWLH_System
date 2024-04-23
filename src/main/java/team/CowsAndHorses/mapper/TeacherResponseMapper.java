package team.CowsAndHorses.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import team.CowsAndHorses.entity.TeacherResponseEntity;
import team.CowsAndHorses.service.TeacherService;
@Mapper
public interface TeacherResponseMapper extends BaseMapper<TeacherResponseEntity> {
}
