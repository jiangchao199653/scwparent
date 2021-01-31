package com.offcn.order.service.impl;

import com.offcn.dycommon.response.AppResponse;
import com.offcn.order.po.TReturn;
import com.offcn.order.service.OrderFeignService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderFeignServiceImpl implements OrderFeignService {
    @Override
    public AppResponse findReturns(Integer pid) {
        AppResponse<List<TReturn>> fail = AppResponse.fail(null);
        fail.setMessage("调用远程服务器失败【订单】");
        return fail;
    }


}
