package  team.CowsAndHorses.constant;

import lombok.Getter;

/**
 * @author patrick_star
 * @Desc 错误类型枚举类
 * @version 1.0
 */
@Getter
public enum ErrorCode {
    /** 身份认证失败 */
    NOT_LOGIN(200100, "未登陆"),
    /** 用户已存在 */
    USER_EXISTED(200101, "用户已存在"),
    /** 用户不存在 */
    USER_NOT_EXISTED(200102, "用户不存在"),
    /** 用户名或密码错误 */
    PASSWORD_OR_STUDENT_ID_ERROR(200103, "用户名或密码错误"),

    /** 用户无权限 */
    FORBIDDEN(200104, "用户无权限"),

    /** 已处理 */
    PROCESSED(200200, "已处理"),

    /** 服务器异常 */
    SERVER_ERROR(200300, "服务器异常"),
    /** 参数有误 */
    PARAM_ERROR(200302, "参数有误"),

    /** 非法请求 */
    ILLEGAL_REQUEST(200404, "非法请求"),
    ;

    private final Integer code;
    private final String message;

    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
