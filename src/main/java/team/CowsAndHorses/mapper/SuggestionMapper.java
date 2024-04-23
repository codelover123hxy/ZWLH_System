package team.CowsAndHorses.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import team.CowsAndHorses.entity.SuggestionEntity;

@Mapper
public interface SuggestionMapper extends BaseMapper<SuggestionEntity> {
}
