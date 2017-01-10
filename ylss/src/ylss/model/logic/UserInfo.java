package ylss.model.logic;

import java.io.Serializable;

import ylss.model.table.User;

public class UserInfo implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8939600762472952359L;
	private String userName;
	private boolean haveIcon;
	
	public UserInfo(User aUser)	{
		
		this.userName=aUser.getUserName();
		this.haveIcon=aUser.getHaveIcon();		
	}


	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean isHaveIcon() {
		return haveIcon;
	}

	public void setHaveIcon(boolean haveIcon) {
		this.haveIcon = haveIcon;
	}


}
