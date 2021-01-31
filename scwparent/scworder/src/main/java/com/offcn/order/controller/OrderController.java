package com.offcn.order.controller;


import com.offcn.dycommon.response.AppResponse;
import com.offcn.order.po.TOrder;
import com.offcn.order.service.OrderFeignService;
import com.offcn.order.service.OrderService;
import com.offcn.order.service.UserFeignService;
import com.offcn.order.vo.req.OrderInfoSubmitVo;

import com.offcn.order.vo.resp.AddressVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "订单模块")
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @ApiOperation("获取用户地址列表")
    @GetMapping("/getAddrs")
    public AppResponse getAddrs(String accessToken){
        //1获取当前登录人的地址列表
        String mid = stringRedisTemplate.opsForValue().get(accessToken);
        if(StringUtils.isEmpty(mid)){
            return AppResponse.fail(null);
        }

        List<AddressVo> addressVos = orderService.getAddress(Integer.parseInt(mid));
        return AppResponse.ok(addressVos);
    }

    @ApiOperation("提交订单")
    @GetMapping("/submitOrder")
    public AppResponse submitOrder(OrderInfoSubmitVo submitVo){

        TOrder order = orderService.submitOrder(submitVo);
        if(order == null){
            return AppResponse.fail("订单提交失败");
        }
        return AppResponse.ok("订单提交成功");

    }


}
