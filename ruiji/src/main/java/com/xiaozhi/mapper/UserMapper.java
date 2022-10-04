package com.xiaozhi.mapper;

import com.xiaozhi.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
* @author 20232
* @description 针对表【user(用户信息)】的数据库操作Mapper
* @createDate 2022-07-12 10:33:12
* @Entity com.xiaozhi.pojo.User
*/


@Repository
public interface UserMapper extends BaseMapper<User> {

}




