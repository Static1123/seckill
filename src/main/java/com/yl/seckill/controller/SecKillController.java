package com.yl.seckill.controller;

import com.yl.seckill.enums.StatusEnum;
import com.yl.seckill.exception.RepeatKillException;
import com.yl.seckill.exception.SeckillCloseException;
import com.yl.seckill.exception.SeckillException;
import com.yl.seckill.model.Seckill;
import com.yl.seckill.service.SecKillService;
import com.yl.seckill.vo.Exposer;
import com.yl.seckill.vo.Result;
import com.yl.seckill.vo.SeckillExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 */
@Controller
public class SecKillController extends BaseController {
    @Resource
    private SecKillService seckillService;

    private static final Logger LOGGER = LoggerFactory.getLogger(SecKillController.class);

    @RequestMapping("/v1/seckill/list")
    public String findSeckillList(Model model) {
        List<Seckill> list = seckillService.findAll();
        model.addAttribute("list", list);
        return "page/seckill";
    }

    @ResponseBody
    @RequestMapping("/v1/seckill/findById")
    public Seckill findById(@RequestParam("id") Long id) {
        return seckillService.findById(id);
    }

    @RequestMapping("/v1/seckill/{seckillId}/detail")
    public String detail(@PathVariable("seckillId") Long seckillId, Model model) {
        if (seckillId == null) {
            return "page/seckill";
        }
        Seckill seckill = seckillService.findById(seckillId);
        model.addAttribute("seckill", seckill);
        if (seckill == null) {
            return "page/seckill";
        }
        return "page/seckill_detail";
    }

    @ResponseBody
    @RequestMapping(value = "/v1/seckill/{seckillId}/exposer",
            method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public Result<Exposer> exposer(@PathVariable("seckillId") Long seckillId) {
        Result<Exposer> result;
        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result = new Result<Exposer>(true, exposer);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = new Result<Exposer>(false, e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/v1/seckill/{seckillId}/{md5}/execution",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Result<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
                                            @PathVariable("md5") String md5,
                                            @RequestParam("money") BigDecimal money,
                                            @CookieValue(value = "killPhone", required = false) Long userPhone) {
        if (userPhone == null) {
            return new Result<SeckillExecution>(false, "未注册");
        }
        try {
            SeckillExecution execution = seckillService.executeSeckill(seckillId, money, userPhone, md5);
            return new Result<>(true, execution);
        } catch (RepeatKillException e) {
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, StatusEnum.REPEAT_KILL);
            return new Result<>(true, seckillExecution);
        } catch (SeckillCloseException e) {
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, StatusEnum.END);
            return new Result<>(true, seckillExecution);
        } catch (SeckillException e) {
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, StatusEnum.SYSTEM_ERROR);
            return new Result<>(true, seckillExecution);
        }
    }

    @ResponseBody
    @GetMapping(value = "/v1/seckill/time/now")
    public Result<Long> time() {
        Date now = new Date();
        return new Result<>(true, now.getTime());
    }
}