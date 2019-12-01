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
	public int addUser(String phone,String password) {
		UserDao userDao = new UserDao();
		int n = userDao.register(phone, password);
		return n;
	}
}
