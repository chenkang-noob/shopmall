package com.imnoob.shopmallkill.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.imnoob.shopmallkill.model.KillSku;
import com.imnoob.shopmallkill.to.SkuTo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TaskDetail implements Serializable {

    private Long id;
    private Long createtime;
    private Long starttime;
    private Long endtime;
    private String killName;
    private Integer status;

    private List<SkuTo> skus;
}
