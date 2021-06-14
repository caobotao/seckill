package com.cbt.seckill.validator;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Data
public class ValidatorResult {
    private boolean hasError = false;
    private Map<String, String> errorMsgMap = new HashMap<>();

    public String getErrMsg() {
        return StringUtils.join(errorMsgMap.values().toArray(), "");
    }
}
