package com.invoice.fegin;

import java.util.List;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.lycheeframework.core.cmp.kit.Pages;
import com.zhys.user.po.Goods;
import com.zhys.user.pojo.GoodsPojo;

@Component

@FeignClient(value="user",path="/goods")
public interface GoodsServiceFegin  {	

    /**
     * 新增
     * @param t
     * @return
     */
	@PostMapping("/save")
    Integer save(Goods t);


    
    /**
     * 查询单个实体
     * @return
     */
	@PostMapping("/queryByEntity")
    Goods queryByEntity(Goods t);

    /**
     * 查询带分页
     * @param t page
     * @return
     */
	@PostMapping("/pagesByPojo")
    Pages<List<Goods>> pages(@RequestBody GoodsPojo t,@RequestParam("pageSize") Integer pageSize,@RequestParam("pageNum") Integer pageNum);
	
	
	/**
     * 查询
     * @param t
     * @return
     */
	@PostMapping("/queryList")
    List<Goods> queryList(Goods t);
	
	@PostMapping("/changeDelStateById")
	public Integer changeDelStateById(@RequestBody Goods t) ;

	@PostMapping("/changeDelStateByIds")
	public Integer changeDelStateByIds(@RequestBody GoodsPojo t) ;
	

}
