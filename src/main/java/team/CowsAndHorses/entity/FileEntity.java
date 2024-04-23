package team.CowsAndHorses.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@TableName("file")
@Data
@RequiredArgsConstructor
public class FileEntity {
    private Integer id;
    private String name;
    private Integer courseId;
    private Integer teacherId;
    private Double size;
    private String createDate;
    private String fileUrl;
}
