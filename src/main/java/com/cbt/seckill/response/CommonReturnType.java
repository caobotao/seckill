package com.cbt.seckill.response;

import lombok.Data;

@Data
public class CommonReturnType {

    private String status;
    private Object data;

    public static CommonReturnType create(Object data) {
        return create(data, "success");
    }

    public static CommonReturnType create(Object data, String status) {
        CommonReturnType commonReturnType = new CommonReturnType();
        commonReturnType.setData(data);
        commonReturnType.setStatus(status);
        return commonReturnType;
    }
}
