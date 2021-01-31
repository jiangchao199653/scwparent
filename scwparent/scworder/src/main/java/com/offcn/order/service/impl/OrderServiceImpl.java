package com.offcn.order.service.impl;

import com.offcn.dycommon.enums.OrderStatusEnumes;
import com.offcn.dycommon.response.AppResponse;
import com.offcn.order.mapper.TOrderMapper;
import com.offcn.order.po.TOrder;
import com.offcn.order.po.TReturn;
import com.offcn.order.service.OrderFeignService;
import com.offcn.order.service.OrderService;
import com.offcn.order.service.UserFeignService;
import com.offcn.order.vo.req.OrderInfoSubmitVo;
import com.offcn.order.vo.resp.AddressVo;
import com.offcn.util.AppDateUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private TOrderMapper orderMapper;

    @Autowired
    private OrderFeignService orderFeignService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserFeignService userFeignService;

    @Override
    public TOrder submitOrder(OrderInfoSubmitVo submitVo) {
        TOrder order = new TOrder();
        try {
            //1、用户是否登录过
            String mid = stringRedisTemplate.opsForValue().get(submitVo.getAccessToken());
            if(StringUtils.isEmpty(mid)){
                return null;
            }

            //2、存储订单基本信息
            order.setMemberid(Integer.parseInt(mid));
            BeanUtils.copyProperties(submitVo,order);

            order.setInvoice(submitVo.getInvoice()+"");
            order.setCreatedate(AppDateUtils.getFormatTime());
            order.setOrdernum(UUID.randomUUID().toString().replace("-",""));
            //3、获取回报列表
            AppResponse<List<TReturn>> response = orderFeignService.findReturns(submitVo.getProjectid());
            List<TReturn> returnList = response.getData();
            System.out.println("return List size : " + returnList.toString());
            //获取用户选择的那个回报对象
            TReturn selRetrun = null;
            if(returnList != null){
                for (TReturn tReturn : returnList) {
                    if(submitVo.getReturnid().intValue() == tReturn.getId().intValue()){
                        selRetrun = tReturn;
                        break;
                    }
                }
            }
            System.out.println("用户选择的retrun:"+selRetrun);
            if(selRetrun!=null){
                order.setMoney(selRetrun.getSupportmoney().intValue() * submitVo.getRtncount() + selRetrun.getFreight());
            }

            //添加订单默认状态
            order.setStatus(OrderStatusEnumes.UNPAY.getCode()+"");
            orderMapper.insertSelective(order);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return order;
    }

    @Override
    public List<AddressVo> getAddress(Integer mid) {
        AppResponse<List<AddressVo>> response = userFeignService.getAddress(mid);
        return response.getData();
    }
}
