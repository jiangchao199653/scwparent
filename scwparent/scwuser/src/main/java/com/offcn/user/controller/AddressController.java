package com.offcn.user.controller;

import com.offcn.dycommon.response.AppResponse;
import com.offcn.user.po.TMemberAddress;
import com.offcn.user.service.AddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "地址模块")
@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    AddressService addressService;

    @ApiOperation("获取地址列表")
    @GetMapping("/getAddress/{mid}")
    public AppResponse getAddress(@PathVariable("mid")Integer mid){
        List<TMemberAddress> memberAddressList = addressService.getAddress(mid);
        return AppResponse.ok(memberAddressList);
    }

}
