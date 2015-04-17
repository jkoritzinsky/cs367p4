import java.util.ArrayList;

public class SimpleFolder {

	private String name;
	private String path;//absolute path of the folder.
	private SimpleFolder parent;
	private User owner;
	private ArrayList<SimpleFolder> subFolders;
	private ArrayList<SimpleFile> files;
	private ArrayList<Access> allowedUsers;

	public SimpleFolder(String name, String path, SimpleFolder parent, User owner) {
		if(name == null) throw new IllegalArgumentException("name");
		//Check if name is valid based on an alphanumeric regex
		if(!name.matches("[A-Za-z0-9]+")) throw new IllegalArgumentException("name");
		if(path == null) throw new IllegalArgumentException("path");
		// No null check for parent b/c parent of root is null
		if(owner == null) throw new IllegalArgumentException("owner");
		this.name = name;
		this.path = path;
		this.parent = parent;
		this.owner = owner;
		subFolders = new ArrayList<>();
		files = new ArrayList<>();
		allowedUsers = new ArrayList<>();
		allowedUsers.add(new Access(owner, 'w'));
		if(!owner.equals(new User("admin"))) allowedUsers.add(new Access(new User("admin"), 'w'));
		owner.addFolder(this);
	}
	
	
	//checks if user - "name" is allowed to access this folder or not. 
	//If yes, return true, otherwise false.
	public boolean containsAllowedUser(String name){
		for(Access user : allowedUsers) {
			if(user.getUser().getName().equals(name))
				return true;
		}
		return false;
	}

	//checks if this folder contains the child folder identified by 'fname'.
	// If it does contain then it returns the folder otherwise returns null.
	public SimpleFolder getSubFolder(String fname){
		for(SimpleFolder folder : subFolders) {
			if(folder.getName().equals(fname))
				return folder;
		}
		return null;
	}


	//checks if this folder contains the child file identified by "fname".
	// If it does contain, return File otherwise null.
	public SimpleFile getFile(String fname){
		// Use \\. because split uses regex
		String[] splitName = fname.split("\\.");
		for(SimpleFile file : files) {
			if(file.getName().equals(splitName[0]) && file.getExtension().toString().toLowerCase().equals(splitName[1]))
				return file;
		}
		return null;
	}


	//returns the owner of the folder.
	public User getOwner() {
		return owner;
	}

	//returns the name of the folder.
	public String getName() {
		return name;
	}

	//returns the path of this folder.
	public String getPath() {
		return path;
	}

	//returns the Parent folder of this folder.
	public SimpleFolder getParent() {
		return parent;
	}

	//returns the list of all folders contained in this folder.
	public ArrayList<SimpleFolder> getSubFolders() {
		return subFolders;
	}

	//adds a folder into this folder.
	public void addSubFolder(SimpleFolder subFolder) {
		if(subFolder == null) throw new IllegalArgumentException("subFolder");
		subFolders.add(subFolder);
	}

	//adds a folder into this folder.
	public void addSubFolder(String name, SimpleFolder parent, User owner){
		if(name == null) throw new IllegalArgumentException("name");
		if(parent == null) throw new IllegalArgumentException("parent");
		if(owner == null) throw new IllegalArgumentException("owner");
		addSubFolder(new SimpleFolder(name, parent.getPath() + '/' + parent.getName(), parent, owner));
	}

	//returns the list of files contained in this folder.
	public ArrayList<SimpleFile> getFiles() {
		return files;
	}

	//add the file to the list of files contained in this folder.
	public void addFile(SimpleFile file) {
		if(file == null) throw new IllegalArgumentException("file");
		files.add(file);
	}

	//returns the list of allowed user to this folder.
	public ArrayList<Access> getAllowedUsers() {
		return allowedUsers;
	}

	//adds another user to the list of allowed user of this folder.
	public void  addAllowedUser(Access allowedUser) {
		if(allowedUser == null) throw new IllegalArgumentException("allowedUser");
		allowedUsers.add(allowedUser);
	}

	//adds a list of allowed user to this folder.
	public void addAllowedUser(ArrayList<Access> allowedUser) {
		if(allowedUser == null) throw new IllegalArgumentException("allowedUser");
		for(Access user : allowedUser) {
			addAllowedUser(user);
		}
	}

	//If the user is owner of this folder or the user is admin or the user has 'w' privilege
	//, then delete this folder along with all its content.
	public boolean removeFolder(User removeUsr){
		if(removeUsr == null) throw new IllegalArgumentException();
		boolean hasWritePrivilege = false;
		for(Access access : allowedUsers) {
			if(access.getUser().equals(removeUsr) && access.getAccessType() == 'w')
				hasWritePrivilege = true;
			if(!hasWritePrivilege) return false;
		}
		User pseudoAdmin = new User("admin"); // Create fake admin user for file and folder removal
		// Remove files
		while(!files.isEmpty()) {
			files.get(0).removeFile(pseudoAdmin);
		}
		// Recursively remove folders
		while(!subFolders.isEmpty()) {
			subFolders.get(0).removeFolder(pseudoAdmin);
		}
		// Remove this folder from its parent and the owner's owned folders
		parent.getSubFolders().remove(this);
		owner.getFolders().remove(this);
		return true;
	}


	//returns the string representation of the Folder object.
	@Override
	public String toString() {
		String retString = "";
		retString = path + "/" + name + "\t" + owner.getName() + "\t";
		for(Access preAccess: allowedUsers){
			retString = retString + preAccess + " ";
		}

		retString = retString + "\nFILES:\n";

		for(int i=0;i<files.size();i++){
			retString = retString + files.get(i);
			if(i != files.size()-1)
				retString = retString + "\n";

		}				
		return retString;
	}


}
