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
		//TODO
	}
	
	
	//checks if user - "name" is allowed to access this folder or not. 
	//If yes, return true, otherwise false.
	public boolean containsAllowedUser(String name){
		//TODO
		return false;
	}

	//checks if this folder contains the child folder identified by 'fname'.
	// If it does contain then it returns the folder otherwise returns null.
	public SimpleFolder getSubFolder(String fname){
		//TODO
		return null;
	}


	//checks if this folder contains the child file identified by "fname".
	// If it does contain, return File otherwise null.
	public SimpleFile getFile(String fname){
		//TODO
		return null;
	}


	//returns the owner of the folder.
	public User getOwner() {
		//TODO
		return null;
	}

	//returns the name of the folder.
	public String getName() {
		//TODO
		return null;
	}

	//returns the path of this folder.
	public String getPath() {
		//TODO
		return null;
	}

	//returns the Parent folder of this folder.
	public SimpleFolder getParent() {
		//TODO
		return null;
	}

	//returns the list of all folders contained in this folder.
	public ArrayList<SimpleFolder> getSubFolders() {
		//TODO
		return null;
	}

	//adds a folder into this folder.
	public void addSubFolder(SimpleFolder subFolder) {
		//TODO
	}

	//adds a folder into this folder.
	public void addSubFolder(String name, SimpleFolder parent, User owner){
		//TODO
	}

	//returns the list of files contained in this folder.
	public ArrayList<SimpleFile> getFiles() {
		//TODO
		return null;
	}

	//add the file to the list of files contained in this folder.
	public void addFile(SimpleFile file) {
		//TODO
	}

	//returns the list of allowed user to this folder.
	public ArrayList<Access> getAllowedUsers() {
		//TODO
		return null;
	}

	//adds another user to the list of allowed user of this folder.
	public void  addAllowedUser(Access allowedUser) {
		//TODO
	}

	//adds a list of allowed user to this folder.
	public void addAllowedUser(ArrayList<Access> allowedUser) {
		//TODO
	}

	//If the user is owner of this folder or the user is admin or the user has 'w' privilege
	//, then delete this folder along with all its content.
	public boolean removeFolder(User removeUsr){
		//TODO
		return false;
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
