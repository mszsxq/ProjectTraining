/**
 * 
 */
package user.service;

import user.controller.UserController;
import user.dao.UserDao;

/**
 * @author ZSX
 *
 */
public class UserService {
	public int addUser(int id,String phone,String password,String time) {
		UserDao userDao = new UserDao();
		int n = userDao.register(id,phone, password,time);
		return n;
	}
}
