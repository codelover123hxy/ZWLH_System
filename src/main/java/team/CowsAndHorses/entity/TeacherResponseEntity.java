package team.CowsAndHorses.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("teacher_response")
public class TeacherResponseEntity {
    private Integer id;
    private Integer evaluateId;
    private Integer teacherId;
    private String response;
}
