package com.offcn.order.service.impl;

import com.offcn.dycommon.response.AppResponse;
import com.offcn.order.service.UserFeignService;
import org.springframework.stereotype.Component;

@Component
public class UserFeignServiceImpl implements UserFeignService {
    @Override
    public AppResponse getAddress(Integer mid) {
        AppResponse response = new AppResponse();
        response.setMessage("【用户地址列表获取失败】");
        return response;
    }
}
