import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;


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
		if(argument == null) throw new IllegalArgumentException("argument");
		boolean absolutePath = argument.charAt(0) == '/';
		String[] pathParts;
		SimpleFolder newLoc;
		if(absolutePath) {
			pathParts = argument.substring(1).split("/");
			if(pathParts[0].equals(root.getName())) {
				newLoc = root;
			}
			else return false;
		}
		else {
			pathParts = argument.split("/");
			newLoc = currLoc;
		}
		boolean skippedFirstPart = false;
		for(String part : Arrays.asList(pathParts)) {
			if(absolutePath && !skippedFirstPart) {
				skippedFirstPart = true;
				continue;
			}
			// If part is in an invalid format
			if(!part.matches("(\\.\\.|[A-Za-z0-9]+)")) {
				throw new IllegalArgumentException("argument");
			}
			if(part.equals("..")) {
				if(newLoc == root) return false;
				newLoc = newLoc.getParent();
			}
			else {
				newLoc = newLoc.getSubFolder(part);
				if(newLoc == null) return false;
				ArrayList<Access> userAccessRules = newLoc.getAllowedUsers();
				boolean canAccess = false;
				for(Access accessRule : userAccessRules) {
					if(accessRule.getUser().equals(currUser)) {
						canAccess = true;
						break;
					}
				}
				if(!canAccess) return false;
			}
		}
		currLoc = newLoc;
		return true;
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
