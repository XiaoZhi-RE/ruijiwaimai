package com.xiaozhi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaozhi.pojo.AddressBook;
import com.xiaozhi.service.AddressBookService;
import com.xiaozhi.mapper.AddressBookMapper;
import org.springframework.stereotype.Service;

/**
* @author 20232
* @description 针对表【address_book(地址管理)】的数据库操作Service实现
* @createDate 2022-07-12 10:33:12
*/
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook>
    implements AddressBookService{

}




