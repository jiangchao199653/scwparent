package com.offcn.order.service;

import com.offcn.dycommon.response.AppResponse;
import com.offcn.order.service.impl.UserFeignServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "SCWUSER",fallback = UserFeignServiceImpl.class)
public interface UserFeignService {

    @GetMapping("/address/getAddress/{mid}")
    public AppResponse getAddress(@PathVariable("mid") Integer mid);

}
