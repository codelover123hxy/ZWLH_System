package team.CowsAndHorses.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@TableName("student_course_teacher")
public class StudentCourseTeacherEntity {
    private Integer id;
    private Integer courseId;
    private Integer studentId;
    private Integer teacherId;
}
