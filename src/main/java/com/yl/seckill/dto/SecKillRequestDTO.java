package com.yl.seckill.dto;

import com.yl.seckill.model.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author Administrator
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class SecKillRequestDTO extends BaseObject {
    @NotNull
    @Min(1)
    @Max(Long.MAX_VALUE)
    private Long goodsId;

    @NotEmpty
    private String token;
}