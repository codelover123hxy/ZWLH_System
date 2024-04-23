package team.CowsAndHorses.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.CowsAndHorses.dto.AjaxResult;
import team.CowsAndHorses.dto.PageQueryDto;
import team.CowsAndHorses.entity.UserEntity;
import team.CowsAndHorses.service.SuggestionService;
import team.CowsAndHorses.service.UserService;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    final UserService userService;
    final SuggestionService suggestionService;

    @GetMapping("/user/query")
    public AjaxResult<Object> getUsers(PageQueryDto pageQueryDto) {
        IPage<UserEntity> page = new Page<>(pageQueryDto.getPageNum(), pageQueryDto.getPageSize());
        IPage<UserEntity> userEntityList = userService.page(page,
                new QueryWrapper<UserEntity>().orderByDesc("role")
        );
        return AjaxResult.SUCCESS(userEntityList);
    }

    @GetMapping("/user/query/{userId}")
    public AjaxResult<Object> getUserById(@PathVariable Integer userId) {
        UserEntity user = userService.getById(userId);
        return AjaxResult.SUCCESS(user);
    }

    @DeleteMapping("/user/remove/{userId}")
    public AjaxResult<Object> removeUserById(@PathVariable Integer userId) {
        return userService.removeById(userId)?
                AjaxResult.SUCCESS(): AjaxResult.FAIL();
    }

    @PostMapping("/user/update")
    public AjaxResult<Object> updateUserById(@RequestBody UserEntity user) {
        return userService.updateById(user)?
                AjaxResult.SUCCESS(): AjaxResult.FAIL();
    }

    @GetMapping("/suggestion/query")
    public AjaxResult<Object> getSuggestions() {
        return AjaxResult.SUCCESS(suggestionService.list());
    }

    @DeleteMapping("/suggestion/remove/{id}")
    public AjaxResult<Object> removeSuggestion(@PathVariable Integer id) {
        suggestionService.removeById(id);
        return AjaxResult.SUCCESS();
    }
}

