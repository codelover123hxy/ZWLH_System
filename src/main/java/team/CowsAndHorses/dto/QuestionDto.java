package team.CowsAndHorses.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import team.CowsAndHorses.entity.AskTeacherEntity;
import team.CowsAndHorses.entity.TeacherResponseEntity;

import java.util.List;
@Data
@RequiredArgsConstructor
public class QuestionDto {
    private Integer questionId;
    private Integer chatId;
    private Integer userId;
    private String doubt;
    private List<TeacherResponseEntity> teacherAnswers;

    public QuestionDto(AskTeacherEntity askTeacherEntity) {
        this.questionId = askTeacherEntity.getId();
        this.chatId = askTeacherEntity.getChatId();
        this.userId = askTeacherEntity.getUserId();
        this.doubt = askTeacherEntity.getDoubt();
    }
}
