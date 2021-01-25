package com.yl.seckill.exception;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.yl.seckill.vo.CodeMsg;
import com.yl.seckill.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Administrator
 */
public class BlockedException extends Exception {
    private static final Logger LOGGER = LoggerFactory.getLogger(BlockedException.class);

    public static Result handleException(BlockException ex) {
        LOGGER.error("{}", ex.getMessage());
        return Result.error(CodeMsg.FLOW_LIMITED);
    }
}