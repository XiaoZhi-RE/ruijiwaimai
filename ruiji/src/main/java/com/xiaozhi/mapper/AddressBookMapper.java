package com.xiaozhi.mapper;

import com.xiaozhi.pojo.AddressBook;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
* @author 20232
* @description 针对表【address_book(地址管理)】的数据库操作Mapper
* @createDate 2022-07-12 10:33:12
* @Entity com.xiaozhi.pojo.AddressBook
*/

@Repository
public interface AddressBookMapper extends BaseMapper<AddressBook> {

}




