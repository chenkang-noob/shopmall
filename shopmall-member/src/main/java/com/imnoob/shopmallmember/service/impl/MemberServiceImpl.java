package com.imnoob.shopmallmember.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imnoob.shopmallmember.vo.LoginVo;
import com.imnoob.shopmallmember.vo.RegisterVo;
import com.imnoob.shopmallmember.exception.CustomizeEnum;
import com.imnoob.shopmallmember.exception.CustomizeExcep;
import com.imnoob.shopmallmember.model.Member;
import com.imnoob.shopmallmember.mapper.MemberMapper;
import com.imnoob.shopmallmember.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;

import java.util.UUID;

/**
 * <p>
 * »áÔ± 服务实现类
 * </p>
 *
 * @author chenkang
 * @since 2021-05-03
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Resource
    MemberMapper memberMapper;

    public Member register(RegisterVo registerVo){


        Member email = memberMapper.selectOne(new QueryWrapper<Member>().eq("email", registerVo.getEmail()));
        if (email != null){
            throw new CustomizeExcep(CustomizeEnum.EMAIL_ERROR);
        }
        Member mobile = memberMapper.selectOne(new QueryWrapper<Member>().eq("mobile", registerVo.getPhone()));
        if(mobile != null){
            throw new CustomizeExcep(CustomizeEnum.PHONE_ERROR);
        }

        Member member = new Member();
        member.setEmail(registerVo.getEmail());
        member.setMobile(registerVo.getPhone());
        member.setUsername(registerVo.getPhone());
        member.setCreateTime(new Date(System.currentTimeMillis()));
        member.setNickname("用户"+ UUID.randomUUID().toString().substring(0,10));

        //MD5设置密码

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode(registerVo.getPassword());
        member.setPassword(encode);

        this.baseMapper.insert(member);

        return member;
    }

    public Member login(LoginVo loginVo){
        Member members = memberMapper.selectOne(new QueryWrapper<Member>().eq("mobile", loginVo.getUsername()).or().eq("email", loginVo.getUsername()));
        if (members == null){
            throw new CustomizeExcep(CustomizeEnum.USER_ACCOUNT_ERROR);
        }
        BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
        if (!enc.matches( loginVo.getPassword(),members.getPassword())) {
            throw new CustomizeExcep(CustomizeEnum.USER_PASSWORD_ERROR);
        }
        members.setPassword(null);

        //token 操作
        return members;
    }
}
