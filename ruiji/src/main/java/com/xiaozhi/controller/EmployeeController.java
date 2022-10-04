package com.xiaozhi.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaozhi.utils.Result;
import com.xiaozhi.pojo.Employee;
import com.xiaozhi.service.EmployeeService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @author 20232
 */


@Slf4j
@RestController
@RequestMapping("/employee")
@Api(value = "EmployeeController", tags = {"员工控制器"})
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    /**
     * 根据id获取数据，回显到修改员工信息页面
     * <p>
     * 请求路径：http://localhost:8088/employee/1547531683161468930
     *
     * @return Result
     */
    @GetMapping("/{id}")
    public Result<Employee> getEmployeeById(@PathVariable("id") Long id) {
        //        查询数据库
        Employee employee = employeeService.getById(id);
        if (employee != null) {
            return Result.success(employee);
        } else {
            return Result.error("没有该员工信息");
        }
    }


    /**
     * 员工分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Result<IPage> getEmployeeList(@RequestParam Integer page, @RequestParam Integer pageSize, String name) {
        //        分页构造器
        Page<Employee> pages = new Page<>(page, pageSize);
        log.info("page{} pageSize{} name{}", page, pageSize, name);
        IPage selectResult = employeeService.selectEmployee(pages, name);


        return Result.success(selectResult);
    }

    /**
     * 方法用于登入页面登入使用
     *
     * @param employee 前端页面post提价的Employee对象
     * @param request  请求
     * @return Result封装结果，封装employee对象
     */
    @PostMapping("/login")
    public Result<Employee> login(@RequestBody Employee employee, HttpServletRequest request) {


        //        通过service层进行查询
        Employee emp = employeeService.login(employee);
        log.info("emp:" + emp);
        //        判断数据库查询的数据是否为null，数据为空，可能是username和password错误
        if (emp == null) {
            return Result.error("用户名或者密码错误");
        } else {
            //            判断账号的使用状态
            if (emp.getStatus() == 0) {
                return Result.error("用户已被禁用");
            } else {
                request.getSession().setAttribute("employee", emp.getId());
                System.out.println(request.getSession().getAttribute("employee"));
            }
        }
        return Result.success(emp);
    }


    /**
     * 退出系统
     *
     * @param request 获取请求
     * @return Result请求结果
     */
    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest request) {
        //        移除浏览器session域中的employee对象
        request.getSession().removeAttribute("employee");
        return Result.success("退出成功");
    }


    /**
     * 修改员工状态
     *
     * @param employee
     * @param request
     * @return
     */
    @PutMapping
    public Result update(@RequestBody
                         Employee employee, HttpServletRequest request) {
        log.info("update方法已经启动");
        employee.setUpdateTime(LocalDateTime.now());
        employee.setCreateUser((Long) request.getSession().getAttribute("employee"));
        employeeService.updateById(employee);
        return Result.success("员工消息修改成功");
    }


    /**
     * 添加员工信息
     *
     * @param employee
     * @param request
     * @return
     */
    @PostMapping
    public Result insertEmployee(@RequestBody Employee employee, HttpServletRequest request) {

        //        从session域中取得employee的id，在登入时已经将id存入了session
        Long empId = (Long) request.getSession().getAttribute("employee");
        //        设置初始密码123456，密码进行md5加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        //        //        设置更新时间和账号创建时间
        //        employee.setCreateTime(LocalDateTime.now());
        //        employee.setUpdateTime(LocalDateTime.now());
        //
        //        //        设置默认值 设置操人作员工信息
        //        employee.setCreateUser(empId);
        //        employee.setUpdateUser((empId));

        //      执行insert语句，将employee对象保存到数据库
        employeeService.save(employee);

        log.info("添加用户成功");
        return Result.success("更新成功");
    }


}
