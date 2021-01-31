package com.offcn;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ScwuserApplication.class})
public class ScwuserApplicationTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void redisTest(){
//        redisTemplate.boundHashOps("brand").put("name","联想");
//        String name = (String)redisTemplate.boundHashOps("brand").get("name");
        stringRedisTemplate.opsForValue().set("name","张三");
//        String name = stringRedisTemplate.opsForValue().get("name");
        String name = (String)redisTemplate.opsForValue().get("name");
        System.out.println("name : " + name);
        logger.debug("操作成功");
    }

}
