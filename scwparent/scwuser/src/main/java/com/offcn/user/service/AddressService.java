package com.offcn.user.service;

import com.offcn.user.po.TMemberAddress;

import java.util.List;

public interface AddressService {

    public List<TMemberAddress> getAddress(Integer mid);

}
