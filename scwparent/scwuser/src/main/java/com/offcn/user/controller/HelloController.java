package com.offcn.user.controller;

import com.offcn.user.bean.User;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

@Api(tags = "测试用户")
@RestController
@RequestMapping("/hello")
public class HelloController {

    @ApiOperation("Hello测试")
    @ApiImplicitParams({
        @ApiImplicitParam(name="name",value = "姓名",required = true,dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 200,message = "成功")
    })
    @GetMapping("/hello")
    public String hello(String name){
        return "hello" + name;
    }

    @ApiOperation("保存用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name="name",value = "姓名",required = true),
            @ApiImplicitParam(name="email",value = "邮箱")
    })
    @GetMapping("/save")
    public User save(String name,String email){
        User user  = new User();
        user.setName(name);
        user.setEmail(email);
        return user;
    }

}
