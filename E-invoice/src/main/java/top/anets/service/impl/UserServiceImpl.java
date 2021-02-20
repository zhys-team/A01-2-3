/**
 * 
 */
package top.anets.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import top.anets.entity.Business;
import top.anets.entity.BusinessExample;
import top.anets.entity.SysUsers;
import top.anets.entity.SysUsersExample;
import top.anets.entity.SysUsersOrgs;
import top.anets.entity.SysUsersOrgsExample;
import top.anets.entity.SysUsersOrgsExample.Criteria;
import top.anets.mapper.BusinessMapper;
import top.anets.mapper.SysUsersMapper;
import top.anets.mapper.SysUsersOrgsMapper;
import top.anets.service.UserService;

/**
 * @author Administrator
 *
 */
@Service
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    private SysUsersMapper usersMapper;
    @Autowired
    private SysUsersOrgsMapper sysUsersOrgsMapper;
    @Autowired
    private BusinessMapper businessMapper;
	@Override
	public List<SysUsers> queryAll() { 
		SysUsersExample example=new SysUsersExample();
		List<SysUsers> list= usersMapper.selectByExample(example);
		for (SysUsers sysUsers : list) {
			//			根据用户id查询对应的组织
			SysUsersOrgsExample userorg=new SysUsersOrgsExample();
			Criteria criteria = userorg.createCriteria();
			criteria.andUserIdEqualTo((long)sysUsers.getId());
			List<SysUsersOrgs> sysUsersOrgs = sysUsersOrgsMapper.selectByExample(userorg);
			ArrayList<String>  oList = new ArrayList<>();
			for (SysUsersOrgs sysUsersOrg : sysUsersOrgs) {
				oList.add(sysUsersOrg.getOrgId());
			}
			sysUsers.setBusinesses(oList);
		}
		return list;
	}
	@Override
	public void saveOrUpdate(SysUsers users) {
        if(users.getId()==null) {
        	usersMapper.insert(users);
        	List<String> businesses = users.getBusinesses();
        	for (String string : businesses) {
				SysUsersOrgs record=new SysUsersOrgs();
				record.setOrgId(string);
				record.setUserId((long)users.getId()); 
				sysUsersOrgsMapper.insert(record);
			}
        }else {
        	SysUsersOrgsExample example=new SysUsersOrgsExample();
        	Criteria criteria = example.createCriteria();
        	criteria.andUserIdEqualTo((long)users.getId());
			sysUsersOrgsMapper.deleteByExample(example);
			usersMapper.updateByPrimaryKey(users);
			List<String> businesses = users.getBusinesses();
			for (String string : businesses) {
				SysUsersOrgs record=new SysUsersOrgs();
				record.setOrgId(string);
				record.setUserId((long)users.getId()); 
				sysUsersOrgsMapper.insert(record);
			}
			
        }
	}
	@Override
	public void delete(Integer id) {
		usersMapper.deleteByPrimaryKey(id);
		SysUsersOrgsExample example=new SysUsersOrgsExample();
    	Criteria criteria = example.createCriteria();
    	criteria.andUserIdEqualTo((long)id);
		sysUsersOrgsMapper.deleteByExample(example);
	}
	@Override
	public SysUsers getUser(String no,String password) {
		SysUsersExample example = new SysUsersExample();
		top.anets.entity.SysUsersExample.Criteria criteria = example.createCriteria();
		criteria.andNoEqualTo(no);
		criteria.andPasswordEqualTo(password);
		List<SysUsers> list = usersMapper.selectByExample(example);		
		if(list==null||list.size()<=0) {
			return null;
		}else {
//			根据用户获取组织
			
			SysUsers sysUsers = list.get(0);
			 
			return sysUsers;
		}
	}
		 
    
}
