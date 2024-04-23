package team.CowsAndHorses.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("ask_teacher")
public class AskTeacherEntity {
    private Integer id;
    private Integer chatId;
    private Integer userId;
    private String doubt;
}