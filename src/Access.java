///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  FileSystemMain.java
// File:             Access.java
// Semester:         CS367 Spring 2015
//
//
// Author:           Jeremy Koritzinsky
// Email:            jeremy.koritzinsky@wisc.edu
// CS Login:         koritzinsky
// Lecturer's Name:  Jim Skrentny
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
// Pair Partner:     Jeff Tucker
// Email:            jetucker@wisc.edu
// CS Login:         jtucker
// Lecturer's Name:  Jim Skrentny
public class Access {
	
	private User user;
	private char accessType;
	
	public Access(User user, char accessType) {
		if(user == null) throw new IllegalArgumentException("user");
		if(accessType != 'r' && accessType !='w') throw new IllegalArgumentException("accessType");
		this.user = user;
		this.accessType = accessType;
	}

	public User getUser() {
		return user;
	}

	public char getAccessType() {
		return accessType;
	}

	public void setAccessType(char accessType) {
		if(accessType != 'r' && accessType !='w') throw new IllegalArgumentException("accessType");
		this.accessType = accessType;
	}
	
	@Override
	public String toString() {
		return (user.getName() + ":" + accessType);
	}
	
}
