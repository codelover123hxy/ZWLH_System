package team.CowsAndHorses.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import team.CowsAndHorses.entity.UserEntity;

@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {
}
