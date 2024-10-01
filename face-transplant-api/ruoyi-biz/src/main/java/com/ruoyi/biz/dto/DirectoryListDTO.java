package com.ruoyi.biz.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hzw
 * @date 2024/6/13
 */
@ApiModel(value = "总目录对象")
@Data
public class DirectoryListDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    List<PicDirectoryListDTO> list;

}
