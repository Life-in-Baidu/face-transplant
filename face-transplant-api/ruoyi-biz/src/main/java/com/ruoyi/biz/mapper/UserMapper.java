package com.ruoyi.biz.mapper;

import com.ruoyi.biz.domain.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * 用户信息Mapper接口
 * 
 * @author zhouzhou
 * @date 2021-06-12
 */
public interface UserMapper 
{
    /**
     * 查询用户信息
     * 
     * @param id 用户信息ID
     * @return 用户信息
     */
    public User selectUserById(Long id);

    /**
     * 查询用户信息列表
     * 
     * @param user 用户信息
     * @return 用户信息集合
     */
    public List<User> selectUserList(User user);

    /**
     * 新增用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int insertUser(User user);

    /**
     * 修改用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int updateUser(User user);

    /**
     * 删除用户信息
     * 
     * @param id 用户信息ID
     * @return 结果
     */
    public int deleteUserById(Long id);

    /**
     * 批量删除用户信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteUserByIds(Long[] ids);

    @Select("select id from biz_user")
    List<Long> getUserIdList();

    User selectUserByPhone(String phone);
    /** 根据用户查询用户信息
     *
     * @author lian.tian
     * @param openid 用户微信openid
     * @return 用户信息
     */
    User selectUserByOpenid(String openid);
}
