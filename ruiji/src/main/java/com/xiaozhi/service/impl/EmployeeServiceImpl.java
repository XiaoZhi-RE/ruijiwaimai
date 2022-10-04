package com.xiaozhi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhi.pojo.Employee;
import com.xiaozhi.service.EmployeeService;
import com.xiaozhi.mapper.EmployeeMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

/**
 * @author 20232
 * @description 针对表【employee(员工信息)】的数据库操作Service实现
 * @createDate 2022-07-12 10:33:12
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {


    @Override
    public Employee login(Employee employee) {

        //        获取控制层传来的Employee对象的username和password属性
        String username = employee.getUsername();
        String password = employee.getPassword();
        //进行MD5加密
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();

        //        按照username和password查询
        queryWrapper.eq("username", username).eq("password", password);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage selectEmployee(Page<Employee> pages, String name) {
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        //        设置查询条件
        queryWrapper.like(!StringUtils.isEmpty(name), Employee::getName, name);
        //        设置排序规则
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        //        执行查询
        return baseMapper.selectPage(pages, queryWrapper);
    }
}




