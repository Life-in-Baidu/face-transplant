package com.ruoyi.biz.service.impl;

import java.util.List;

import com.ruoyi.biz.dto.SetmealListDTO;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.biz.mapper.SetmealMapper;
import com.ruoyi.biz.domain.Setmeal;
import com.ruoyi.biz.service.ISetmealService;

/**
 * 套餐Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-06-11
 */
@Service
public class SetmealServiceImpl implements ISetmealService 
{
    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * 查询套餐
     * 
     * @param mealId 套餐主键
     * @return 套餐
     */
    @Override
    public Setmeal selectSetmealByMealId(Long mealId)
    {
        return setmealMapper.selectSetmealByMealId(mealId);
    }

    /**
     * 查询套餐列表
     * 
     * @param setmeal 套餐
     * @return 套餐
     */
    @Override
    public List<Setmeal> selectSetmealList(Setmeal setmeal)
    {
        return setmealMapper.selectSetmealList(setmeal);
    }

    /**
     * 新增套餐
     * 
     * @param setmeal 套餐
     * @return 结果
     */
    @Override
    public int insertSetmeal(Setmeal setmeal)
    {
        setmeal.setCreateTime(DateUtils.getNowDate());
        return setmealMapper.insertSetmeal(setmeal);
    }

    /**
     * 修改套餐
     * 
     * @param setmeal 套餐
     * @return 结果
     */
    @Override
    public int updateSetmeal(Setmeal setmeal)
    {
        setmeal.setUpdateTime(DateUtils.getNowDate());
        return setmealMapper.updateSetmeal(setmeal);
    }

    /**
     * 批量删除套餐
     * 
     * @param mealIds 需要删除的套餐主键
     * @return 结果
     */
    @Override
    public int deleteSetmealByMealIds(Long[] mealIds)
    {
        return setmealMapper.deleteSetmealByMealIds(mealIds);
    }

    /**
     * 删除套餐信息
     * 
     * @param mealId 套餐主键
     * @return 结果
     */
    @Override
    public int deleteSetmealByMealId(Long mealId)
    {
        return setmealMapper.deleteSetmealByMealId(mealId);
    }

    @Override
    public List<SetmealListDTO> selectSetmeaDTOlList(Setmeal setmeal) {
        return setmealMapper.selectSetmeaDTOlList(setmeal);
    }
}
