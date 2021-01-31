package com.offcn.order.service;

import com.offcn.dycommon.response.AppResponse;
import com.offcn.order.po.TReturn;
import com.offcn.order.service.impl.OrderFeignServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "SCWPROJECT",fallback = OrderFeignServiceImpl.class)
public interface OrderFeignService {

    @GetMapping("/project/findReturns")
    public AppResponse<List<TReturn>> findReturns(@RequestParam Integer pid);


}
