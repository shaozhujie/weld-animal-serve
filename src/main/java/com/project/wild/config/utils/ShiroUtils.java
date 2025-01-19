package com.project.wild.config.utils;

import com.project.wild.domain.User;
import org.apache.shiro.SecurityUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author shaozhujie
 * @version 1.0
 * @description: shiro工具类
 * @date 2023/9/12 10:52
 */
public class ShiroUtils {

    /**
    * @description: 获取当前登陆用户
    * @param:
    * @return:
    * @author shaozhujie
    * @date: 2023/9/12 10:54
    */
    public static User getUserInfo(){
        return (User) SecurityUtils.getSubject().getPrincipal();
    }

}
