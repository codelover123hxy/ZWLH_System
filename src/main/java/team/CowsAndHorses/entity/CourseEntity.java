package team.CowsAndHorses.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("course")
public class CourseEntity {
    private Integer id;
    private String courseName;
    private String chapterList;
    private String target;
    private String courseReferences;
    private String preKnowledge;
    private String intro;
    private String imgUrl;
}
