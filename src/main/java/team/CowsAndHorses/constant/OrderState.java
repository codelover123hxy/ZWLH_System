package team.CowsAndHorses.constant;

import lombok.Data;

@Data
public class OrderState {
    public static final Integer ALL = 0;
    public static final Integer WAIT_FOR_PAYMENT = 1;
    public static final Integer WAIT_FOR_DELIVERY = 2;
    public static final Integer WAIT_FOR_RECEIPT = 3;
    public static final Integer WAIT_FOR_EVALUATION = 4;
    public static final Integer COMPLETED = 5;
    public static final Integer CANCELED = 6;
    public static final Integer REFUNDED = 7;
}