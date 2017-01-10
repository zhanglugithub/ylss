package ylss.test.dao.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import ylss.dao.UserDao;
import ylss.model.table.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml",
		"classpath:springMVC-servlet.xml" })
@Transactional
@TransactionConfiguration(defaultRollback=false)
public class UserDaoImplTest {

	User aUser;

	@Autowired
	@Resource
	UserDao aUserDaoImpl;

	String phoneNo = "15102276525";
	int id = 2;
	int idGet;
	long totalNoNow = 18;

	protected void setUp() throws Exception {

	}

	@Test
	public void testGetById() throws ClassNotFoundException {

		User aUser = aUserDaoImpl.getById(id);
		assertEquals(phoneNo, aUser.getPhoneNo());
	}

	@Test
	public void testGetByPhoneNo() {

		aUser = aUserDaoImpl.getByPhoneNo(phoneNo);
		idGet = aUser.getUserId();
		assertEquals(id, idGet);
	}
	@Ignore
	@Test
	public void testCountAll() {

		assertEquals(totalNoNow, aUserDaoImpl.countAll());

	}
	@Ignore
	@Test
	public void testGetAPage() {
		List<User> aUsers = aUserDaoImpl.getAPage(1, 3);
		idGet = aUsers.get(0).getUserId();
		assertEquals(id, idGet);

		id = 5;
		aUsers = aUserDaoImpl.getAPage(2, 3);
		idGet = aUsers.get(0).getUserId();
		assertEquals(id, idGet);
	}

	@Test
	public void testSaveUser() {
		aUser = new User("15102222225");
		aUserDaoImpl.saveOrUpdate(aUser);
	}
	@Test
	public void testtoken(){
		
	}
}
