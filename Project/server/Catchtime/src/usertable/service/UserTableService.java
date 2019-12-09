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
		return new UserTableDao().findusertableByuserid(id).getDayrecord_table_name();
	}
}
