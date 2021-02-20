package top.anets.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.anets.entity.SysUsersOrgs;
import top.anets.entity.SysUsersOrgsExample;
@Mapper
public interface SysUsersOrgsMapper {
    int countByExample(SysUsersOrgsExample example);

    int deleteByExample(SysUsersOrgsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysUsersOrgs record);

    int insertSelective(SysUsersOrgs record);

    List<SysUsersOrgs> selectByExample(SysUsersOrgsExample example);

    SysUsersOrgs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysUsersOrgs record, @Param("example") SysUsersOrgsExample example);

    int updateByExample(@Param("record") SysUsersOrgs record, @Param("example") SysUsersOrgsExample example);

    int updateByPrimaryKeySelective(SysUsersOrgs record);

    int updateByPrimaryKey(SysUsersOrgs record);
}