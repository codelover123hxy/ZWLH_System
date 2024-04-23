package team.CowsAndHorses.constant;

import lombok.Getter;

@Getter
public enum TemplateConstant {
    PINGFENG("SMS_463895048"),
    ZHAOHUI("SMS_463880182");
    private final String templateCode;
    TemplateConstant(String templateCode) {
        this.templateCode = templateCode;
    }
}
