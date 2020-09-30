package com.lms.service.serviceimpl;

import com.lms.bean.User;
import com.lms.dao.BookDao;
import com.lms.dao.RecordDao;
import com.lms.dao.UserDao;
import com.lms.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service(value="userServiceImpl")
@Transactional
public class UserServiceImpl implements UserService {

    @Resource(name="userDao")
    private UserDao userDao;

    @Resource(name="bookDao")
    private BookDao bookDao;

    @Resource(name="recordDao")
    private RecordDao recordDao;

    /**
     * 登录用户检测，用户不存在，返回-1，用户密码匹配，返回1，不匹配，返回0
     * @param userId
     * @param password
     * @return
     */
    @Override
    public int loginVerify(String userId, String password) {
        User user=userDao.selectById(Integer.valueOf(userId));
        if(user==null){
            return -1;
        }
        else if(user.getUser_pwd().equals(password)){
            return 1;
        }
        return 0;
    }
}
