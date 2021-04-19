package com.imnoob.shopmallproduct;

import com.imnoob.shopmallproduct.mapper.BrandMapper;
import com.imnoob.shopmallproduct.model.Brand;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class ShopmallProductApplicationTests {

    @Autowired
    public BrandMapper brandMapper;

    @Test
    void contextLoads() {
        Brand brand = new Brand();
        brand.setName("小米");
        brand.setDescript("123");
        brandMapper.insert(brand);
    }

}
