package com.ruoyi.biz.mapper;

import java.util.List;
import com.ruoyi.biz.domain.Setmeal;
import com.ruoyi.biz.dto.SetmealListDTO;

/**
 * 套餐Mapper接口
 * 
 * @author ruoyi
 * @date 2024-06-11
 */
public interface SetmealMapper 
{
    /**
     * 查询套餐
     * 
     * @param mealId 套餐主键
     * @return 套餐
     */
    public Setmeal selectSetmealByMealId(Long mealId);

    /**
     * 查询套餐列表
     * 
     * @param setmeal 套餐
     * @return 套餐集合
     */
    public List<Setmeal> selectSetmealList(Setmeal setmeal);

    /**
     * 新增套餐
     * 
     * @param setmeal 套餐
     * @return 结果
     */
    public int insertSetmeal(Setmeal setmeal);

    /**
     * 修改套餐
     * 
     * @param setmeal 套餐
     * @return 结果
     */
    public int updateSetmeal(Setmeal setmeal);

    /**
     * 删除套餐
     * 
     * @param mealId 套餐主键
     * @return 结果
     */
    public int deleteSetmealByMealId(Long mealId);

    /**
     * 批量删除套餐
     * 
     * @param mealIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSetmealByMealIds(Long[] mealIds);

    List<SetmealListDTO> selectSetmeaDTOlList(Setmeal setmeal);
}
