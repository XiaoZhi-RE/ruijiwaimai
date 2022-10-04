package com.xiaozhi.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaozhi.common.CustomException;
import com.xiaozhi.pojo.User;
import com.xiaozhi.service.SendMailService;
import com.xiaozhi.service.UserService;
import com.xiaozhi.utils.EmailModelUtils;
import com.xiaozhi.utils.Result;
import com.xiaozhi.utils.VerifyCodeUtils;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 20232
 */
@Slf4j
@Api(value = "SendMessageController", tags = "{Email验证码控制器}")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private SendMailService sendMailService;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * @param user
     * @return
     */
    @PostMapping("/sendMsg")
    public Result<String> sendEmail(@RequestBody User user, HttpSession httpSession) throws MessagingException {

        //生成验证码
        String code = String.valueOf(VerifyCodeUtils.getComboCode(8));
        //获取邮箱（手机号）
        String phone = user.getPhone();
        if (phone != null) {
            try {
                httpSession.setAttribute(phone, code);
                //存到redis,时长5分钟过期
                redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
                String model = EmailModelUtils.getEmailModel(code);
                log.warn("model:{}", model);
                //发送验证码到邮箱
                sendMailService.send(phone, model);
            } catch (IOException e) {
                throw new CustomException("消息发送失败");
            }
            return Result.success("发送成功");
        }
        return Result.error("发送失败");
    }


    /**
     * 登入验证
     *
     * @param map
     * @param httpSession
     * @return
     */
    @PostMapping("/login")
    public Result<User> getCode(@RequestBody Map<String, String> map, HttpSession httpSession, HttpServletResponse response) {

        log.info("map:{}", map);
        //获取手机号
        String phone = map.get("phone");
        //获取验证码
        String code = map.get("code");
        //session中获取验证码
        Object codeHttp = httpSession.getAttribute(phone);
        //从redis中得到验证码
        String redisCode = redisTemplate.opsForValue().get(phone);
        //比对验证码
        if (redisCode != null && redisCode.equals(code)) {
            LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(User::getPhone, phone);
            User one = userService.getOne(lambdaQueryWrapper);
            if (one == null) {
                one = new User();
                one.setPhone(phone);
                one.setStatus(1);
                userService.save(one);
                log.info("用户已注册成功");
            }
            redisTemplate.delete(phone);
            log.warn("验证码删除:{}", code);
            httpSession.setAttribute("user", one.getId());
            return Result.success(one);
        }
        return Result.error("验证码已过期或者验证码错误");
    }


    @PostMapping("/loginout")
    public Result<String> logOut(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return Result.success("");
    }

}
