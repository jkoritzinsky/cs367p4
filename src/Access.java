
public class Access {
	
	private User user;
	private char accessType;
	
	public Access(User user, char accessType) {
		//TODO
	}

	public User getUser() {
		//TODO
		return null;
	}

	public char getAccessType() {
		//TODO
		return ' ';
	}

	public void setAccessType(char accessType) {
		//TODO
	}
	
	@Override
	public String toString() {
		return (user.getName() + ":" + accessType);
	}
	
}
