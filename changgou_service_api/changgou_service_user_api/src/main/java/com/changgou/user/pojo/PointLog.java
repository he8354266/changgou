package com.changgou.user.pojo;

import lombok.Data;

import javax.persistence.Table;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2020/10/2011:43
 */
@Table(name = "tb_point_log")
@Data
public class PointLog {
    private String orderId;
    private String userId;
    private Integer point;
}
