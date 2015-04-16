import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SimpleFileSystem {

	SimpleFolder root;
	ArrayList<User> users;
	SimpleFolder currLoc;
	User currUser;

	//constructor
	public SimpleFileSystem(SimpleFolder _root, ArrayList<User> _users) {
		root = _root;
		users = _users;
		setCurrentUser("admin");
	}

	// resets current user to admin and current location to root
	public void reset(){
		setCurrentUser("admin");
	}


	//gets currUser.
	public User getCurrUser() {
		return currUser;
	}

	//sets the current user to the user with name passed in the argument.
	public boolean setCurrentUser(String name){
		if(name == null) throw new IllegalArgumentException("name");
		User newUser = containsUser(name);
		if(newUser == null) return false;
		currUser = newUser;
		currLoc = root;
		return true;
	}


	//checks if the user is contained in the existing users list or not.
	public User containsUser(String name){
		if(name == null) throw new IllegalArgumentException("name");
		for(User user : users) {
			if(user.getName().equals(name)) {
				return user;
			}
		}
		return null;
	}
	
	
	//checks whether curr location contains any file/folder with name name = fname
	public boolean containsFileFolder(String fname){
		//TODO
		return false;
	}
	


	//changes the current location. If successful returns true, false otherwise.
	public boolean moveLoc(String argument){
		//TODO
		return false;
	}

	
	//returns the currentlocation.path + currentlocation.name.
	// Implemented by CS instructors/TAs
	public String getPWD(){
		return ((currLoc.getPath().isEmpty()?"":(currLoc.getPath()+"/"))+currLoc.getName());
	}
		


	//deletes the folder/file identified by the 'name'
	public boolean remove(String name){
		if(name == null) throw new IllegalArgumentException("name");
		SimpleFile file = currLoc.getFile(name);
		if(file != null) {
			return file.removeFile(currUser);
		}
		SimpleFolder folder = currLoc.getSubFolder(name);
		if(folder != null) {
			return folder.removeFolder(currUser);
		}
		return false;
	}


	//Gives the access 'permission' of the file/folder fname to the user if the 
	//current user is the owner of the fname. If succeed, return true, otherwise false.
	public boolean addUser(String fname, String username, char permission){
		//TODO
		return false;
	}


	//displays the user info in the specified format.
	public boolean printUsersInfo(){
		//TODO
		return false;
	}



	//makes a new folder under the current folder with owner = current user.
	public void mkdir(String name){
		if(name == null) throw new IllegalArgumentException("name");
		currLoc.addSubFolder(name, currLoc, currUser);
	}


	//makes a new file under the current folder with owner = current user.
	public void addFile(String filename, String fileContent){
		//TODO
	}


	//prints all the folders and files under the current user for which user has access.
	//Implemented by CS instructors/TAs
	public void printAll(){
		for(SimpleFile f : currLoc.getFiles()){
			if(f.containsAllowedUser(currUser.getName()))
			{
				System.out.print(f.getName() + "." + f.getExtension().toString() + " : " + f.getOwner().getName() + " : ");
				for(int i =0; i<f.getAllowedUsers().size(); i++){
					Access a = f.getAllowedUsers().get(i);
					System.out.print("("+a.getUser().getName() + "," + a.getAccessType() + ")");
					if(i<f.getAllowedUsers().size()-1){
						System.out.print(",");
					}
				}
				System.out.println();
			}
		}
		for(SimpleFolder f: currLoc.getSubFolders()){
			if(f.containsAllowedUser(currUser.getName()))
			{
				System.out.print(f.getName() + " : " + f.getOwner().getName() + " : ");
				for(int i =0; i<f.getAllowedUsers().size(); i++){
					Access a = f.getAllowedUsers().get(i);
					System.out.print("("+a.getUser().getName() + "," + a.getAccessType() + ")");
					if(i<f.getAllowedUsers().size()-1){
						System.out.print(",");
					}
				}
				System.out.println();
			}
		}
		

	}

}
