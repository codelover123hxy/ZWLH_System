package team.CowsAndHorses.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.CowsAndHorses.entity.CourseEntity;
import team.CowsAndHorses.mapper.CourseMapper;
@Transactional
public interface CourseService extends IService<CourseEntity> {

}
