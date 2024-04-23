package team.CowsAndHorses.dto;

import lombok.Data;
@Data
public class PasswordDto {
    private String oldPassword;
    private String newPassword;
}
