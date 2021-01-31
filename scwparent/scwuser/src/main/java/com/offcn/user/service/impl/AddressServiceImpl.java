package com.offcn.user.service.impl;

import com.offcn.user.mapper.TMemberAddressMapper;
import com.offcn.user.po.TMemberAddress;
import com.offcn.user.po.TMemberAddressExample;
import com.offcn.user.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private TMemberAddressMapper addressMapper;

    @Override
    public List<TMemberAddress> getAddress(Integer mid) {
        TMemberAddressExample example = new TMemberAddressExample();
        TMemberAddressExample.Criteria criteria = example.createCriteria();
        criteria.andMemberidEqualTo(mid);
        return addressMapper.selectByExample(example);
    }
}
