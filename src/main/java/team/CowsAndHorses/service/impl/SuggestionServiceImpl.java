package team.CowsAndHorses.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.CowsAndHorses.entity.SuggestionEntity;
import team.CowsAndHorses.mapper.SuggestionMapper;
import team.CowsAndHorses.service.SuggestionService;

@Service
@RequiredArgsConstructor
public class SuggestionServiceImpl extends ServiceImpl<SuggestionMapper, SuggestionEntity> implements SuggestionService {
}
