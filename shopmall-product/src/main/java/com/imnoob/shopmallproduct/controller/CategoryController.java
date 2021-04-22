package com.imnoob.shopmallproduct.controller;

import com.imnoob.shopmallcommon.utils.R;
import com.imnoob.shopmallproduct.model.Category;
import com.imnoob.shopmallproduct.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("product/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 查出所有分类以及子分类，以树形结构组装起来
     */
    @RequestMapping("/list/tree")
    public R list(){
        System.out.println("---");
        List<Category> entities = categoryService.listWithTree();
        return R.ok().put("data", entities);
    }

    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] catIds){

        categoryService.removeMenuByIds(Arrays.asList(catIds));
        return R.ok();
    }

    @RequestMapping("/save")
    public R save(@RequestBody Category category){
        categoryService.save(category);

        return R.ok();
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:category:update")
    public R update(@RequestBody Category category){
        //categoryService.updateCascade(category);
        return R.ok();
    }


}
