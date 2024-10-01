package com.ruoyi.common.enums;

import lombok.Getter;

/**
 * @author lian.tian
 */
@Getter
public enum ProductStatus {

    ONLINE(1), OFF_LINE(2);

    private int code;

    ProductStatus(int code) {
        this.code = code;
    }


}