/**
 * 
 */
package top.anets.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
 
import top.anets.entity.Business;
import top.anets.entity.BusinessExample;
import top.anets.entity.SysUsersOrgs;
import top.anets.entity.SysUsersOrgsExample;
import top.anets.exception.ServiceException;
import top.anets.entity.BusinessExample.Criteria;
import top.anets.entity.Path;
import top.anets.entity.PathExample;
import top.anets.mapper.BusinessMapper;
import top.anets.mapper.PathMapper;
import top.anets.mapper.SysUsersOrgsMapper;
import top.anets.redis.RedisKey;
import top.anets.redis.RedisService;
import top.anets.service.BusinessService;
import top.anets.vo.BusinessVo;

/**
 * @author Administrator
 *
 */
@Service
@Transactional
public class BusinessServiceImpl implements BusinessService{
    @Autowired
    private BusinessMapper businessMapper;

    @Autowired
    private SysUsersOrgsMapper sysUsersOrgsMapper;
    
    
    @Autowired
    private PathMapper  pathMapper;
	@Override
	public void saveOrUpdate(BusinessVo businessVo) {
         List<Path> paths = businessVo.getPaths();
		Business business2 = new Business(); 
		BeanUtils.copyProperties(businessVo, business2); 
		if(business2.getId()==null) {
//			新增组织
//			删除旧线路
			PathExample example2 = new PathExample();
			example2.createCriteria().andOrgIdEqualTo(business2.getOrgId()).andOrgMachineEqualTo(business2.getOrgMachine());
			pathMapper.deleteByExample(example2);   
//			新增组织
			businessMapper.insertSelective(business2);
//			新增线路
			for (Path path : paths) {
				pathMapper.insert(path);
			}
			
		}else {
//			修改组织
			businessMapper.updateByPrimaryKeySelective(business2);
//			删除旧线路
			PathExample example2 = new PathExample();
			example2.createCriteria().andOrgIdEqualTo(business2.getOrgId()).andOrgMachineEqualTo(business2.getOrgMachine());
			pathMapper.deleteByExample(example2);   
//			新增线路
			for (Path path : paths) {
				pathMapper.insert(path);
			}
		}
		
//		清除缓存
		this.clearPathCache(business2);
		 
	}

	/**
	* 
	*@param business2 
	*/
	@Autowired
	private RedisService redisService;
	private void clearPathCache(Business business) {
		redisService.expire(RedisKey.Path.getKey()+business.getOrgId()+business.getOrgMachine()+1,-1);
		redisService.expire(RedisKey.Path.getKey()+business.getOrgId()+business.getOrgMachine()+2,-1);
		redisService.expire(RedisKey.Path.getKey()+business.getOrgId()+business.getOrgMachine()+3,-1);
		redisService.expire(RedisKey.Path.getKey()+business.getOrgId()+business.getOrgMachine()+4,-1);
		redisService.expire(RedisKey.Path.getKey()+business.getOrgId()+business.getOrgMachine()+5,-1);
	}

	@Override
	public void delete(Integer id) {
		Business business2 = businessMapper.selectByPrimaryKey(id);
//		删除旧线路
		PathExample example2 = new PathExample();
		example2.createCriteria().andOrgIdEqualTo(business2.getOrgId()).andOrgMachineEqualTo(business2.getOrgMachine());
		pathMapper.deleteByExample(example2);  
		businessMapper.deleteByPrimaryKey(id);
//		清除缓存
		this.clearPathCache(business2);
	}

	@Override
	public List<BusinessVo> queryAll() {
		BusinessExample example=new BusinessExample();
		List<Business> list = businessMapper.selectByExample(example);
		ArrayList<BusinessVo> list2 = new ArrayList<>();
		for (Business business : list) {
			BusinessVo vo = new BusinessVo();
			BeanUtils.copyProperties(business, vo);
			//			查询线路
			PathExample example2 =new PathExample();
			example2.createCriteria().andOrgIdEqualTo(vo.getOrgId()).andOrgMachineEqualTo(vo.getOrgMachine());
			List<Path>  pathz = pathMapper.selectByExample(example2);
			vo.setPaths(pathz); 
			list2.add(vo);
		}
		return list2;
	}

	@Override
	public BusinessVo query(String orgId,String orgMachine) {
		BusinessExample example = new BusinessExample();
		Criteria criteria = example.createCriteria();
		criteria.andOrgIdEqualTo(orgId).andOrgMachineEqualTo(orgMachine);
		 List<Business> selectByExample = businessMapper.selectByExample(example);
		 if(selectByExample==null||selectByExample.size()<=0) {
			 return null;
		 }else {
			 BusinessVo businessVo = new BusinessVo();
			 BeanUtils.copyProperties(selectByExample.get(0), businessVo);
			 
//				查询线路
				PathExample example2 =new PathExample();
				example2.createCriteria().andOrgIdEqualTo(businessVo.getOrgId()).andOrgMachineEqualTo(businessVo.getOrgMachine());
				List<Path>  pathz = pathMapper.selectByExample(example2);
				businessVo.setPaths(pathz);  
			 return businessVo;
		 } 
	}

	@Override
	public List<BusinessVo> queryByUserId(Integer id) {
		SysUsersOrgsExample example1 = new SysUsersOrgsExample();
		  top.anets.entity.SysUsersOrgsExample.Criteria createCriteria = example1.createCriteria();
		createCriteria.andUserIdEqualTo((long)id);
		List<String> values = new ArrayList<>(); 
		List<SysUsersOrgs> byExample = sysUsersOrgsMapper.selectByExample(example1);
		for (SysUsersOrgs sysUsersOrgs : byExample) {
			values.add(sysUsersOrgs.getOrgId());
		}
		
		BusinessExample example3 = new BusinessExample();
		top.anets.entity.BusinessExample.Criteria criteria3 = example3.createCriteria(); 
		criteria3.andOrgIdIn(values);
		List<Business> list2 = businessMapper.selectByExample(example3);
		List<BusinessVo> newList = new ArrayList<>();
		for (Business business : list2) {
			BusinessVo vo = new BusinessVo();
			BeanUtils.copyProperties(business, vo);
			
//			查询线路
			PathExample example2 =new PathExample();
			example2.createCriteria().andOrgIdEqualTo(vo.getOrgId()).andOrgMachineEqualTo(vo.getOrgMachine());
			List<Path>  pathz = pathMapper.selectByExample(example2);
			vo.setPaths(pathz); 
			newList.add(vo);
		}
		return newList;
	}
 
	 

}
