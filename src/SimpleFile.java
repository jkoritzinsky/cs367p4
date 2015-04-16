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
		if(name == null) throw new IllegalArgumentException("name");
		//Check if name is valid based on an alphanumeric regex
		if(!name.matches("[A-Za-z0-9]+")) throw new IllegalArgumentException("name");
		if(extension == null) throw new IllegalArgumentException("extension");
		if(path == null) throw new IllegalArgumentException("path");
		if(content == null) throw new IllegalArgumentException("content");
		if(parent == null) throw new IllegalArgumentException("parent");
		if(owner == null) throw new IllegalArgumentException("owner");
		this.name = name;
		this.extension = extension;
		this.path = path;
		this.content = content;
		this.parent = parent;
		this.owner = owner;
		allowedUsers = new ArrayList<>();
		allowedUsers.add(new Access(owner, 'w'));
		if(!owner.getName().equals("admin")) allowedUsers.add(new Access(new User("admin"), 'w'));
	}
	
	//returns the path variable.
	public String getPath(){
		return path;
	}

	//return the parent folder of this file.
	public SimpleFolder getParent() {
		return parent;
	}

	//returns the name of the file.
	public String getName() {
		return name;
	}

	//returns the extension of the file.
	public Extension getExtension() {
		return extension;
	}

	//returns the content of the file.
	public String getContent() {
		return content;
	}

	//returns the owner user of this file.
	public User getOwner() {
		return owner;
	}

	//returns the list of allowed user of this file.
	public ArrayList<Access> getAllowedUsers() {
		return allowedUsers;
	}

	//adds a new access(user+accesstype pair) to the list of allowed user.
	public void addAllowedUser(Access newAllowedUser) {
		if(newAllowedUser == null) throw new IllegalArgumentException("newAllowedUser");
		allowedUsers.add(newAllowedUser);
	}
	
	//adds a list of the accesses to the list of allowed users.
	public void addAllowedUsers(ArrayList<Access> newAllowedUser) {
		if(newAllowedUser == null) throw new IllegalArgumentException("allowedUser");
		for(Access user : newAllowedUser) {
			addAllowedUser(user);
		}
	}
	
	
	// returns true if the user name is in allowedUsers.
	// Otherwise return false.
	public boolean containsAllowedUser(String name){
		if(name == null) throw new IllegalArgumentException("name");
		for(Access allowedUser : allowedUsers) {
			if(allowedUser.getUser().getName().equals(name)) return true;
		}
		return false;
	}
	
	
	//removes the file for all users.
	//If the user is owner of the file or the admin or the user has 'w' access,
	//then it is removed for everybody.
	public boolean removeFile(User removeUsr){
		if(removeUsr == null) throw new IllegalArgumentException();
		boolean hasWritePrivilege = false;
		for(Access access : allowedUsers) {
			if(access.getUser().equals(removeUsr) && access.getAccessType() == 'w')
				hasWritePrivilege = true;
			if(!hasWritePrivilege) return false;
		}
		parent.getFiles().remove(this);
		owner.getFiles().remove(this);
		return true;
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
