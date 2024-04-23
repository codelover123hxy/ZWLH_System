package team.CowsAndHorses.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kotlin.UInt;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.DeleteExchange;
import team.CowsAndHorses.constant.ErrorCode;
import team.CowsAndHorses.dto.AjaxResult;
import team.CowsAndHorses.dto.LoginDto;
import team.CowsAndHorses.dto.PageQueryDto;
import team.CowsAndHorses.dto.PasswordDto;
import team.CowsAndHorses.entity.UserEntity;
import team.CowsAndHorses.exception.AppException;
import team.CowsAndHorses.service.UserService;
import team.CowsAndHorses.util.JwtUtil;
import team.CowsAndHorses.util.ParseUtil;
import team.CowsAndHorses.util.SmsUtil;

import java.util.*;

/**
 * @author LittleHorse
 * @version 1.0
 */

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/api")
public class UserController {
    final UserService userService;

    @GetMapping("/user/query")
    public AjaxResult<Object> getUserById(HttpServletRequest request) {
        Integer userId = ParseUtil.parseToken(request, "userId");
        UserEntity user = userService.getById(userId);
        return AjaxResult.SUCCESS(user);
    }

    @PostMapping("/login")
    public AjaxResult<Object> login(@RequestBody LoginDto loginDto, HttpSession session) {
        UserEntity user = userService.getOne(
                new QueryWrapper<UserEntity>()
                        .eq("username", loginDto.getUsername())
                        .eq("password", loginDto.getPassword())
        );
        if (null != user) {
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("username", user.getUsername());
            userInfo.put("userId", user.getId());
            userInfo.put("role", user.getRole());
//            session.setAttribute("userId", user.getId());
            String id = UUID.randomUUID().toString();
            String token = JwtUtil.sign(id, userInfo);
            user.setToken(token);
            return AjaxResult.SUCCESS("登录成功", user);
        } else {
            throw new AppException(ErrorCode.PASSWORD_OR_STUDENT_ID_ERROR);
        }
    }

    @PostMapping("/register")
    public AjaxResult<Object> register(@RequestBody UserEntity user) {
        UserEntity userEntity = userService.getOne(new QueryWrapper<UserEntity>().eq("username", user.getUsername()));
        if (userEntity != null) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        userService.save(user);
        return AjaxResult.SUCCESS();
    }

    @PostMapping("/register/phoneNum")
    public AjaxResult<Object> registerByPhoneNUm(@RequestBody UserEntity user) {
        UserEntity userEntity = userService.getOne(new QueryWrapper<UserEntity>().eq("username", user.getUsername()));
        if (userEntity != null) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        userService.save(user);
        return AjaxResult.SUCCESS();
    }

    @GetMapping("/captchaCode")
    public AjaxResult<Object> getCaptchaCode(@RequestParam String phoneNum) throws Exception {
        Random random = new Random();
        StringBuilder captchaCode = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            captchaCode.append(random.nextInt(10));
        }
        SmsUtil.sendCaptchaCode(phoneNum, captchaCode.toString());
        UserEntity user = userService.getOne(new QueryWrapper<UserEntity>().eq("phone_num", phoneNum));
        return AjaxResult.SUCCESS("发送验证码成功", null);
    }

    @PostMapping("/changePwd")
    public AjaxResult<Object> changePwd(HttpServletRequest request, @RequestBody PasswordDto passwordDto) {
        Integer userId = ParseUtil.parseToken(request, "userId");
        UserEntity user =  userService.getById(userId);
        if (!passwordDto.getOldPassword().equals(user.getPassword())) {
            throw new AppException(ErrorCode.PASSWORD_OR_STUDENT_ID_ERROR);
        }
        user.setPassword(passwordDto.getNewPassword());
        userService.updateById(user);
        return AjaxResult.SUCCESS();
    }

    @GetMapping("/logout")
    public AjaxResult<Object> logout() {
        return AjaxResult.SUCCESS();
    }

    @GetMapping("/checkLogin")
    public Boolean checkLogin() {
        return true;
    }
}
