package com.xiaozhi.mapper;

import com.xiaozhi.pojo.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
* @author 20232
* @description 针对表【employee(员工信息)】的数据库操作Mapper
* @createDate 2022-07-12 10:33:12
* @Entity com.xiaozhi.pojo.Employee
*/


@Repository
public interface EmployeeMapper extends BaseMapper<Employee> {

}




