package com.xiaozhi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaozhi.pojo.Employee;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 20232
* @description 针对表【employee(员工信息)】的数据库操作Service
* @createDate 2022-07-12 10:33:12
*/
public interface EmployeeService extends IService<Employee> {

    Employee login(Employee employee);

    IPage selectEmployee(Page<Employee> pages, String name);
}
