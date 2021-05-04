package com.imnoob.shopmallcommon.to;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AdminToken implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String mobile;
    private String email;
    private String nickname;

    private Long loginTime;



}
