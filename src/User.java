///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  FileSystemMain.java
// File:             User.java
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
import java.util.ArrayList;

public class User {
	
	private String name; //name of the user.
	private ArrayList<SimpleFile> files;//list of files owned/created by user
	private ArrayList<SimpleFolder> folders;//list of folder owned by user.

	public User(String name) {
		if(name == null) throw new IllegalArgumentException("name");
		//Check if name is valid based on an alphanumeric regex
		if(!name.matches("[A-Za-z0-9]+")) throw new IllegalArgumentException("name");
		this.name= name;
		files = new ArrayList<>();
		folders = new ArrayList<>();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(!(obj instanceof User)) return false;
		return ((User) obj).getName().equals(this.getName());
	}

	//returns the name of the user.
	public String getName() {
		return name;
	}

	//returns the list of files owned by the user.
	public ArrayList<SimpleFile> getFiles() {
		return files;
	}

	//adds the file to the list of files owned by the user.
	public void addFile(SimpleFile newfile) {
		if(newfile == null) throw new IllegalArgumentException("newfile");
		files.add(newfile);
	} 
	
	//removes the file from the list of owned files of the user.
	public boolean removeFile(SimpleFile rmFile){
		if(rmFile == null) throw new IllegalArgumentException("rmFile");
		return files.remove(rmFile);
	}

	//returns the list of folders owned by the user.
	public ArrayList<SimpleFolder> getFolders() {
		return folders;
	}

	//adds the folder to the list of folders owned by the user.
	public void addFolder(SimpleFolder newFolder) {
		if(newFolder == null) throw new IllegalArgumentException("newFolder");
		folders.add(newFolder);
	}

	//removes the folder from the list of folders owned by the user.
	public boolean removeFolder(SimpleFolder rmFolder){
		if(rmFolder == null) throw new IllegalArgumentException("rmFolder");
		return folders.remove(rmFolder);
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
