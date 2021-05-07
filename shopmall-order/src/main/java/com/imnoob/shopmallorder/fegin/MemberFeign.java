package com.imnoob.shopmallorder.fegin;

import com.imnoob.shopmallcommon.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient("shopmall-member")
public interface MemberFeign {

    @GetMapping("/memberReceiveAddress/addressInfo")
    public R getAddressInfo(@RequestParam("memberId")Long memberId);
}
