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
		//TODO
	}

	// resets current user to admin and current location to root
	public void reset(){
		//TODO
	}


	//gets currUser.
	public User getCurrUser() {
		//TODO
		return null;
	}

	//sets the current user to the user with name passed in the argument.
	public boolean setCurrentUser(String name){
		//TODO
		return false;
	}


	//checks if the user is contained in the existing users list or not.
	public User containsUser(String name){
		//TODO
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
	public String getPWD(){
		//TODO
		return null;
	}//return of getPWD method


	//deletes the folder/file identified by the 'name'
	public boolean remove(String name){
		//TODO
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
		//TODO
	}


	//makes a new file under the current folder with owner = current user.
	public void addFile(String filename, String fileContent){
		//TODO
	}


	//prints all the folders and files under the current user for which user has access.
	public void printAll(){
		//TODO
	}

}
