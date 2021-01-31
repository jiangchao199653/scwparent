package com.offcn.user.service.impl;

import com.offcn.user.enums.UserExceptionEnum;
import com.offcn.user.exception.UserException;
import com.offcn.user.mapper.TMemberMapper;
import com.offcn.user.po.TMember;
import com.offcn.user.po.TMemberExample;
import com.offcn.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TMemberMapper memberMapper;

    @Override
    public void registe(TMember member) {
        //1、判断用户是否存在
        TMemberExample example = new TMemberExample();
        TMemberExample.Criteria criteria = example.createCriteria();
        criteria.andLoginacctEqualTo(member.getLoginacct());
        List<TMember> memberList = memberMapper.selectByExample(example);
        if(memberList.size()>0){
            throw new UserException(UserExceptionEnum.LOGINACCT_EXIST);
        }

        //2、给用户添加默认属性
        member.setAuthstatus("0");
        member.setUsertype("1");
        member.setUsername(member.getLoginacct());

        //3、密码加密
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        member.setUserpswd(encoder.encode(member.getUserpswd()));

        memberMapper.insertSelective(member);
    }

    @Override
    public TMember login(String loginName, String pwd) {

        //1、取当前登录人
        TMemberExample example = new TMemberExample();
        TMemberExample.Criteria criteria = example.createCriteria();
        criteria.andLoginacctEqualTo(loginName);
        List<TMember> memberList = memberMapper.selectByExample(example);
        if(memberList!=null && memberList.size()==1){
            TMember member = memberList.get(0);
            //2、比对密码
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            return encoder.matches(pwd,member.getUserpswd())?member:null;
        }

        return null;
    }

    @Override
    public TMember findMemberById(Integer id) {
        return memberMapper.selectByPrimaryKey(id);
    }
}
