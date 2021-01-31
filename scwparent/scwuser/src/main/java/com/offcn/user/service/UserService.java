package com.offcn.user.service;

import com.offcn.user.po.TMember;

public interface UserService {

    public void registe(TMember member);

    public TMember login(String loginName,String pwd);

    public TMember findMemberById(Integer id);

}
