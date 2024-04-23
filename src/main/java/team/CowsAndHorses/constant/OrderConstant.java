package team.CowsAndHorses.constant;

import org.springframework.stereotype.Component;

@Component
public class OrderConstant {
    public static final String[] OrderStateList = {
            "全部", "待付款", "待发货", "待收货", "待评价", "已完成", "已取消", "已退款"
    };
}
