package com.offcn.user.controller;

import com.mysql.cj.log.LogFactory;
import com.offcn.dycommon.response.AppResponse;
import com.offcn.user.component.SmsTemplate;
import com.offcn.user.po.TMember;
import com.offcn.user.service.UserService;
import com.offcn.user.vo.UserRegistVo;
import com.offcn.user.vo.UserRespVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Api(tags = "用户注册与登录")
@RestController
@RequestMapping("/user")
public class UserLoginController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private SmsTemplate smsTemplate;

    @Autowired
    private UserService userService;

    Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/findMemberById")
    public AppResponse findMemberById(Integer id){
        TMember member = userService.findMemberById(id);
        UserRespVo vo = new UserRespVo();
        BeanUtils.copyProperties(member,vo);
        return AppResponse.ok(vo);
    }

    @ApiOperation("发送短信验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name="mobile",value = "电话号码",required = true)
    })
    @GetMapping("/sendCode")
    public AppResponse sendCode(String mobile){

        //1、生成验证码
        String sysCode = UUID.randomUUID().toString().substring(0,4);
        //2、存入redis
        stringRedisTemplate.opsForValue().set(mobile,sysCode,3600, TimeUnit.MINUTES);
        //3、发送短信
        Map querys = new HashMap<String,String>();
        querys.put("mobile", mobile);
        querys.put("param", "code:" + sysCode);
        querys.put("tpl_id", "TP1711063");//短信模板
        String resp = smsTemplate.sendCode(querys);

        if(resp.length()==0 || "fail".equals(resp)){
            return AppResponse.fail("短信发送失败");
        }
        return AppResponse.ok(resp);
    }

    @PostMapping
    public AppResponse regist(UserRegistVo userRegistVo){

        //1、判断验证码是否正确 通过手机号从redis中取值 进行比对
        String syscode = stringRedisTemplate.opsForValue().get(userRegistVo.getLoginacct());
        if(userRegistVo.getCode().equals(syscode)){

            //2、需要将视图对象 导入 持久化对象
            TMember member = new TMember();
            BeanUtils.copyProperties(userRegistVo,member);

            try {
                //3、删除用户的redis的验证码
                stringRedisTemplate.delete(userRegistVo.getLoginacct());
                //4、记录日志
                log.info("注册成功："+userRegistVo.getLoginacct());

                userService.registe(member);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return AppResponse.ok("注册成功："+userRegistVo.getLoginacct());
        }else{
            return AppResponse.fail("验证码不正确");
        }

    }

    @PostMapping("/login")
    public AppResponse login(String loginName,String pwd){
        TMember member = userService.login(loginName,pwd);
        if(member!=null){
            UserRespVo userRespVo = new UserRespVo();
            BeanUtils.copyProperties(member,userRespVo);

            //生成token令牌
            String token = UUID.randomUUID().toString().replace("-","");
            stringRedisTemplate.opsForValue().set(token,""+member.getId(),2,TimeUnit.HOURS);

            return AppResponse.ok(userRespVo);
        }
        return AppResponse.fail(null);
    }

}
