package com.imnoob.shopmallproduct.controller;

import com.imnoob.shopmallcommon.utils.R;
import com.imnoob.shopmallproduct.service.AttrGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {

    @Autowired
    AttrGroupService attrGroupService;

    /**
     * 查询某类商品共有的属性
     * @param params
     * @param catelogId
     * @return
     */
    @RequestMapping("/list/{catelogId}")
    public R list(@RequestParam Map<String, Object> params,
                  @PathVariable("catelogId") Long catelogId){

        List res = attrGroupService.queryPage(params,catelogId);
        return R.ok().put("page", res);
    }
}
