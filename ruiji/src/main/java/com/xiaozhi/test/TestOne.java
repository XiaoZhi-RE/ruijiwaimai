package com.xiaozhi.test;

import com.xiaozhi.pojo.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 20232
 */
public class TestOne {
    public static void main(String[] args) {
       List<User> list = new ArrayList<>();
        User user = new User();
        user.setStatus(1);
        list.add(user);

        System.out.println(Arrays.toString(list.toArray()));
    }
}
