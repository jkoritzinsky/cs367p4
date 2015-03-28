import java.util.ArrayList;

public class User {
	
	private String name; //name of the user.
	private ArrayList<SimpleFile> files;//list of files owned/created by user
	private ArrayList<SimpleFolder> folders;//list of folder owned by user.

	public User(String name) {
		//TODO
	}
	
	@Override
	public boolean equals(Object obj) {
		//TODO		
		return false;
	}

	//returns the name of the user.
	public String getName() {
		//TODO
		return null;
	}

	//returns the list of files owned by the user.
	public ArrayList<SimpleFile> getFiles() {
		//TODO
		return null;
	}

	//adds the file to the list of files owned by the user.
	public void addFile(SimpleFile newfile) {
		//TODO
	} 
	
	//removes the file from the list of owned files of the user.
	public boolean removeFile(SimpleFile rmFile){
		//TODO
		return false;
	}

	//returns the list of folders owned by the user.
	public ArrayList<SimpleFolder> getFolders() {
		//TODO
		return null;
	}

	//adds the folder to the list of folders owned by the user.
	public void addFolder(SimpleFolder newFolder) {
		//TODO
	}

	//removes the folder from the list of folders owned by the user.
	public boolean removeFolder(SimpleFolder rmFolder){
		//TODO
		return false;
	}
	
	//returns the string representation of the user object.
	@Override
	public String toString() {
		String retType = name + "\n";
		retType = retType + "Folders owned :\n";
		for(SimpleFolder preFolder : folders){
			retType = retType + "\t" + preFolder.getPath() + "/" + preFolder.getName() + "\n";
		}
		retType = retType + "\nFiles owned :\n"; 
		for(SimpleFile preFile : files){
			retType = retType + "\t" + preFile.getPath() + "/" + preFile.getName() + "." + preFile.getExtension().toString() + "\n";
		}
		return retType;
	}
	
	
	
}
