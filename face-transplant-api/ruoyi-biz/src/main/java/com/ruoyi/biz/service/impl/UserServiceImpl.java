package com.ruoyi.biz.service.impl;

import com.ruoyi.biz.domain.User;
import com.ruoyi.biz.mapper.UserMapper;
import com.ruoyi.biz.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * 用户信息Service业务层处理
 * 
 * @author zhouzhou
 * @date 2021-06-12
 */
@Service
public class UserServiceImpl implements IUserService
{
    @Resource
    private UserMapper userMapper;

    /**
     * 查询用户信息
     * 
     * @param id 用户信息ID
     * @return 用户信息
     */
    @Override
    public User selectUserById(Long id)
    {
        return userMapper.selectUserById(id);
    }

    /**
     * 查询用户信息列表
     * 
     * @param user 用户信息
     * @return 用户信息
     */
    @Override
    public List<User> selectUserList(User user)
    {
        return userMapper.selectUserList(user);
    }

    /**
     * 新增用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int insertUser(User user)
    {
        return userMapper.insertUser(user);
    }

    /**
     * 修改用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUser(User user)
    {
        return userMapper.updateUser(user);
    }

    /**
     * 批量删除用户信息
     * 
     * @param ids 需要删除的用户信息ID
     * @return 结果
     */
    @Override
    public int deleteUserByIds(Long[] ids)
    {
        return userMapper.deleteUserByIds(ids);
    }

    /**
     * 删除用户信息信息
     * 
     * @param id 用户信息ID
     * @return 结果
     */
    @Override
    public int deleteUserById(Long id)
    {
        return userMapper.deleteUserById(id);
    }

    /**
     * 获取用户ID集合
     * @return
     */
    @Override
    public List<Long> getIdList() {
        return userMapper.getUserIdList();
    }

    @Override
    public User selectUserByPhone(String phone) {
        return userMapper.selectUserByPhone(phone);
    }

    @Override
    public User selectUserByOpenid(String openid) {
        return userMapper.selectUserByOpenid(openid);
    }

}
