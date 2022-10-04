package com.xiaozhi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhi.pojo.User;
import com.xiaozhi.service.UserService;
import com.xiaozhi.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * @author 20232
 * @description 针对表【user(用户信息)】的数据库操作Service实现
 * @createDate 2022-07-12 10:33:12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}




