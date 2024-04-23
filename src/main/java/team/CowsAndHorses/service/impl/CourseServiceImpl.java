package team.CowsAndHorses.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import team.CowsAndHorses.entity.CourseEntity;
import team.CowsAndHorses.mapper.CourseMapper;
import team.CowsAndHorses.service.CourseService;
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, CourseEntity> implements CourseService {
}
