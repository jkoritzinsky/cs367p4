import java.util.ArrayList;

public class SimpleFile {
	private String name;
	private Extension extension;
	private String content;
	private User owner;
	private ArrayList<Access> allowedUsers;
	private String path;
	private SimpleFolder parent;
	
	public SimpleFile(String name, Extension extension, String path, String content, SimpleFolder parent, User owner) {
		//TODO
	}
	
	//returns the path variable.
	public String getPath(){
		//TODO
		return null;
	}

	//return the parent folder of this file.
	public SimpleFolder getParent() {
		//TODO
		return null;
	}

	//returns the name of the file.
	public String getName() {
		//TODO
		return null;
	}

	//returns the extension of the file.
	public Extension getExtension() {
		//TODO
		return null;
	}

	//returns the content of the file.
	public String getContent() {
		//TODO
		return null;
	}

	//returns the owner user of this file.
	public User getOwner() {
		//TODO
		return null;
	}

	//returns the list of allowed user of this file.
	public ArrayList<Access> getAllowedUsers() {
		//TODO
		return null;
	}

	//adds a new access(user+accesstype pair) to the list of allowed user.
	public void addAllowedUser(Access newAllowedUser) {
		//TODO
	}
	
	//adds a list of the accesses to the list of allowed users.
	public void addAllowedUsers(ArrayList<Access> newAllowedUser) {
		//TODO
	}
	
	
	// returns true if the user name is in allowedUsers.
	// Otherwise return false.
	public boolean containsAllowedUser(String name){
		//TODO
		return false;
	}
	
	
	//removes the file for all users.
	//If the user is owner of the file or the admin or the user has 'w' access,
	//then it is removed for everybody.
	public boolean removeFile(User removeUsr){
		//TODO
		return false;
	}
	
	//returns the string representation of the file.
	@Override
	public String toString() {
		String retString = "";
		retString = name + "." + extension.name() + "\t" + owner.getName() + "\t" ;
		for(Access preAccess : allowedUsers){
			retString = retString + preAccess + " ";
		}
		retString = retString + "\t\"" + content + "\"";
		return retString;
	}
	
}
