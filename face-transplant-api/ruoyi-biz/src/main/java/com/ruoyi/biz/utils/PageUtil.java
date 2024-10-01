package com.ruoyi.biz.utils;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author lian.tian
 */
public class PageUtil {

    private PageUtil() {
    }

    public static <P, D> PageInfo<D> convertPageInfo2PageDTO(List<P> list, Function<P, D> function) {
        PageInfo<P> pageInfoPo = PageInfo.of(list);
        List<D> convertList = pageInfoPo.getList().stream().map(function).collect(Collectors.toList());
        PageInfo<D> pageInfo = new PageInfo<>();
        BeanUtils.copyProperties(pageInfoPo, pageInfo);
        pageInfo.setList(convertList);
        return pageInfo;
    }

    public static <P, D> PageInfo<D> convertPageInfoPo2PageDTO(PageInfo<P> pageInfoPo, Function<P, D> function) {
        List<D> convertList = pageInfoPo.getList().stream().map(function).collect(Collectors.toList());
        PageInfo<D> pageInfo = new PageInfo<>();
        BeanUtils.copyProperties(pageInfoPo, pageInfo);
        pageInfo.setList(convertList);
        return pageInfo;
    }
}