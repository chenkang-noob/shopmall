package com.imnoob.shopmallmember.controller;


import com.imnoob.shopmallcommon.utils.R;
import com.imnoob.shopmallmember.service.MemberReceiveAddressService;
import com.imnoob.shopmallmember.vo.MemberAdressVo;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * »áÔ±ÊÕ»õµØÖ· 前端控制器
 * </p>
 *
 * @author chenkang
 * @since 2021-05-03
 */
@RestController
@RequestMapping("/memberReceiveAddress")
public class MemberReceiveAddressController {

    @Resource
    MemberReceiveAddressService memberReceiveAddressService;

    @GetMapping("/addressInfo")
    public R getAddressInfo(@RequestParam("memberId") Long memberId){
        List<MemberAdressVo> list = memberReceiveAddressService.getAddressInfo(memberId);
        return R.ok().put("address", list);
    }

}

