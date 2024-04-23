package team.CowsAndHorses.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import team.CowsAndHorses.entity.SuggestionEntity;
@Transactional
public interface SuggestionService extends IService<SuggestionEntity> {
}
