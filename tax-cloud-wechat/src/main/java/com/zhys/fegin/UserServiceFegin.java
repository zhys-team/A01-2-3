package com.zhys.fegin;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.zhys.user.po.SysUsers;

//@Component("invoiceHeadServiceFegin")
@Service("userServiceFegin")
@FeignClient(value="user",path="/user")
public interface UserServiceFegin  {	
     @PostMapping("save")
	 Integer save(@RequestBody SysUsers user);
     
     @PostMapping("/queryList")
     List<SysUsers> queryList(@RequestBody SysUsers t);

}
