package team.CowsAndHorses.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("conversation")
public class ConversationEntity {
    private Integer id;
    private Integer conversationId;
    private Integer chatType;
    private String query;
    private String response;
    private String metaData;
    private Float feedbackScore;
    private String feedbackReason;
    private String createTime;
}
