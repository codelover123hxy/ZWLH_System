package team.CowsAndHorses.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class UserEntity {
    private Integer id;
    private String username;
    private String name;
    private String phoneNum;
    private String password;
    private Integer role;
    private Integer credit;
    private String nickname;
    private String avatarUrl;
    private Double learningHour;
    @TableField(exist = false)
    private String token;
}