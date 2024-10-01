package com.ruoyi.biz.api.controller;

import com.ruoyi.biz.domain.Setmeal;
import com.ruoyi.biz.dto.SetmealListDTO;
import com.ruoyi.biz.service.ISetmealService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 套餐接口Controller
 * @date 2021-12-30
 */
@RestController
@RequestMapping("/api/set_meal")
@Api(tags = "套餐接口")
public class SetMealApiController extends BaseController {
    @Autowired
    private ISetmealService setmealService;

    /**
     * 查询套餐列表
     */
    @ApiOperation("查询套餐列表")
    @GetMapping("/list")
    public ApiResult<List<SetmealListDTO>> list() {
        List<SetmealListDTO> bizSetMeals = setmealService.selectSetmeaDTOlList(new Setmeal());
        return ApiResult.success(bizSetMeals);
    }
}