package com.imnoob.shopmallgateway.fegin;

import com.imnoob.shopmallcommon.utils.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("shopmall-auth")
@Component
public interface AuthFegin {

    @PostMapping("/auth/checkToken")
    AjaxResult checkToken(@RequestBody String token);

}
