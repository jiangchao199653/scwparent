package com.offcn.order.service;

import com.offcn.order.po.TOrder;
import com.offcn.order.vo.req.OrderInfoSubmitVo;
import com.offcn.order.vo.resp.AddressVo;
import java.util.List;

public interface OrderService {

    public TOrder submitOrder(OrderInfoSubmitVo vo);

    public List<AddressVo> getAddress(Integer mid);

}
