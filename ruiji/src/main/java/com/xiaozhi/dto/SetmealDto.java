package com.xiaozhi.dto;

import com.xiaozhi.pojo.Setmeal;
import com.xiaozhi.pojo.SetmealDish;
import lombok.Data;

import java.util.List;

/**
 * @author 20232
 */
@Data
public class SetmealDto extends Setmeal {
    private List<SetmealDish> setmealDishes;
    private String categoryName;
}
