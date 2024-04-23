package team.CowsAndHorses.constant;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Component
public class ModuleConstant {
    public static final String[] moduleApproved = {"",
            "is_approved_deyu", "is_approved_zhiyu", "is_approved_tiyu", "is_approved_meiyu",
            "is_approved_laoyu", "is_approved_cxcy"};
    public static final String[] moduleReason = {"",
            "deyu_reason", "zhiyu_reason", "tiyu_reason", "meiyu_reason",
            "laoyu_reason", "cxcy_reason"};
}
