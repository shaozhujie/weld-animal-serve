package com.project.wild.controller.login;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.project.wild.common.utils.JwtUtil;
import com.project.wild.common.utils.PasswordUtils;
import com.project.wild.config.utils.RedisUtils;
import com.project.wild.domain.DataAnimal;
import com.project.wild.domain.DataOrder;
import com.project.wild.domain.Result;
import com.project.wild.domain.User;
import com.project.wild.service.DataAnimalService;
import com.project.wild.service.DataAnimalTypeService;
import com.project.wild.service.DataOrderService;
import com.project.wild.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @version 1.0
 * @description: 登陆
 * @date 2024/2/26 21:20
 */
@Controller
@ResponseBody
@RequestMapping("login")
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private DataAnimalService dataAnimalService;
    @Autowired
    private DataOrderService dataOrderService;
    @Autowired
    private DataAnimalTypeService dataAnimalTypeService;

    @Autowired
    private RedisUtils redisUtils;

    @PostMapping()
    public Result login(HttpServletRequest request, @RequestBody JSONObject jsonObject) {
        String username = jsonObject.getString("loginAccount");
        String password = jsonObject.getString("password");
        Integer type = jsonObject.getInteger("type");
        QueryWrapper<User> query = new QueryWrapper<>();
        query.lambda().eq(User::getLoginAccount,username);
        if (type == 0) {
            query.lambda().eq(User::getUserType,1);
        } else {
            query.lambda().ne(User::getUserType,1);
        }
        User user = userService.getOne(query);
        if (user == null) {
            return Result.fail("用户名不存在！");
        }
        //比较加密后得密码
        boolean decrypt = PasswordUtils.decrypt(password, user.getPassword() + "$" + user.getSalt());
        if (!decrypt) {
            return Result.fail("用户名或密码错误！");
        }
        if (user.getStatus() == 1) {
            return Result.fail("用户被禁用！");
        }
        //密码正确生成token返回
        String token = JwtUtil.sign(user.getId(), user.getPassword());
        JSONObject json = new JSONObject();
        json.put("token", token);
        return Result.success(json);
    }

    @GetMapping("logout")
    public Result logout() {
        return Result.success();
    }

    @GetMapping("getManageData")
    public Result getManageData() {
        JSONObject json = new JSONObject();
        int animal = dataAnimalService.count();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().ne(User::getUserType,1);
        int user = userService.count(queryWrapper);
        QueryWrapper<User> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.lambda().eq(User::getUserType,1);
        int ticket = userService.count(queryWrapper1);
        int order = dataOrderService.count();
        json.put("animal",animal);
        json.put("user",user);
        json.put("ticket",ticket);
        json.put("order",order);

        LocalDate today = LocalDate.now();
        List<String> dates = new ArrayList<>();
        List<Object> nums = new ArrayList<>();
        IntStream.rangeClosed(1, 30)
                .mapToObj(days -> today.minusDays(days))
                .forEach(date -> dates.add(date.format(DateTimeFormatter.ISO_DATE)));
        for (String s : dates) {
            float num = 0;
            QueryWrapper<DataOrder> wrapper = new QueryWrapper<>();
            wrapper.lambda().like(DataOrder::getCreateTime,s);
            List<DataOrder> orderList = dataOrderService.list(wrapper);
            for (DataOrder dataOrder : orderList) {
                num += dataOrder.getPrice();
            }
            nums.add(num);
        }
        json.put("dates",dates);
        json.put("nums",nums);
        return Result.success(json);
    }

    @GetMapping("getIndexData")
    public Result getIndexData() {
        JSONObject jsonObject = new JSONObject();
        int type = dataAnimalTypeService.count();
        int animal = dataAnimalService.count();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getUserType,1);
        int user = userService.count(queryWrapper);
        QueryWrapper<User> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.lambda().ne(User::getUserType,1);
        int team = userService.count(queryWrapper1);

        jsonObject.put("type",type);
        jsonObject.put("animal",animal);
        jsonObject.put("user",user);
        jsonObject.put("team",team);

        return Result.success(jsonObject);
    }

}
