/**
 * 
 */
package ylss.dao;

import ylss.model.table.User;

/**
 * @author JACK
 *
 */

public interface UserDao extends BaseDao<User, Integer> {

	public User getByPhoneNo(String phoneNo);

	public User setNewToken(User aUser);

	// public boolean push(String phoneNo,String content);
}
