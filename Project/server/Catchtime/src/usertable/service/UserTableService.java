/**
 * 
 */
package usertable.service;

import usertable.dao.UserTableDao;

/**
 * @author ZSX
 *
 */
public class UserTableService {
	public String querryDayTableById(int id) {
		return id+"_data";
	}
}
