package com.zhys.fegin;

import com.invoice.pojo.HeadPojo;
import com.invoice.pojo.PersonalTicket;
import com.lycheeframework.core.cmp.kit.Pages;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service("headServiceFegin")
@FeignClient(value="invoice",path="/heads")
public interface HeadServiceFegin {
    /**
     * 查询带分页-手机
     * @param t page
     * @return
     */
    @GetMapping("/pageForMobile")
    Pages<List<HeadPojo>> pageForMobile(@RequestParam("openId")String openId, @RequestParam("pageSize")Integer pageSize, @RequestParam("pageNum")Integer pageNum
            , @RequestParam("bxState")String  bxState);
}
