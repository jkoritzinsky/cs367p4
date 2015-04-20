///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            FileSystem
// Files:            FileSystemMain.java, SimpleFile.java, SimpleFolder.java, User.java
//							Access.java, Extension.java, SimpleFileSystem.java
// Semester:         CS367 Spring 2015
//
// Author:           Jeremy Koritzinsky
// Email:            jeremy.koritzinsky@wisc.edu
// CS Login:         koritzinsky
// Lecturer's Name:  Jim Skrentny
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
//                   CHECK ASSIGNMENT PAGE TO see IF PAIR-PROGRAMMING IS ALLOWED
//                   If pair programming is allowed:
//                   1. Read PAIR-PROGRAMMING policy (in cs302 policy) 
//                   2. choose a partner wisely
//                   3. REGISTER THE TEAM BEFORE YOU WORK TOGETHER 
//                      a. one partner creates the team
//                      b. the other partner must join the team
//                   4. complete this section for each program file.
//
// Pair Partner:     Jeff Tucker
// Email:            jetucker@wisc.edu
// CS Login:         jtucker
// Lecturer's Name:  Jim Skrentny
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Main class for filesystem
 * @author Jeff
 * Bugs: none known
 */
public class FileSystemMain {

	private static final Scanner scnr = new Scanner(System.in);

	private enum Cmd {
		reset, pwd, ls, u, uinfo, cd, rm, mkdir, mkfile, sh, x
	}

	public static void main(String[] args) {	
		//Variable declarations for the information from the file
		SimpleFolder rootfolder = null;
		String[] users = null;
		ArrayList<String> files = new ArrayList<String>();

		try(Scanner fileScnr = new Scanner(new File(args[0]))) //A scanner for the file
		{	
			rootfolder = new SimpleFolder(fileScnr.nextLine(), "", null, null); //from first line
			users = fileScnr.nextLine().split(", "); 
			while(fileScnr.hasNextLine()) { //adds all of the files specified in later lines
				files.add(fileScnr.nextLine());
			}
		}
		catch(FileNotFoundException ex) { //if the file doesn't exist
			System.out.println("Error: Cannot access file");
		}
		User admin = new User("admin");
		ArrayList<User> userObjs = new ArrayList<>();
		userObjs.add(admin);
		SimpleFileSystem sfs = new SimpleFileSystem(rootfolder, userObjs); //change based on input files
		for(int i = 0; i < users.length; i++) {
			 userObjs.add(new User(users[i]));
		 }
		 String[] splitFile = null;
		for(int i = 0; i < files.size(); i++) {
			splitFile = files.get(i).split(" ", 2);
			String contents = splitFile.length == 2 ? splitFile[1] : "";
			String[] path = splitFile[0].split("/");
			if(path.length == 2) {
				rootfolder.addSubFolder(path[1], rootfolder, admin);
			}
			else {
				SimpleFolder parent = rootfolder;
				for(int j = 1; j < path.length - 1; ++j) {
					parent = parent.getSubFolder(path[j]);
				}
				boolean isFile = path[path.length - 1].contains(".");
				if(isFile) {
					String[] fileNameParts = path[path.length-1].split("\\.");
					SimpleFile file = new SimpleFile(fileNameParts[0], Extension.valueOf(fileNameParts[1]), parent.getPath() + '/' + parent.getName(), contents, parent, admin);
					parent.addFile(file);
				}
				else {
					parent.addSubFolder(path[path.length - 1], parent, admin);
				}
			}
			for(int j = 0; j < users.length; j++) {
				sfs.addUser( splitFile[0], "admin", 'w');
				sfs.addUser(splitFile[0] , users[j], 'r');
			}
		}
		while(true) {
			String[] cmds = prompt(sfs.getCurrUser() + "@CS367$ ");
			Cmd cmd = stringToCmd(cmds[0]);

			switch (cmd) {
			//resets the user to admin and location to root
			case reset: 
				if(validate1(cmds)) {
					sfs.reset();
					System.out.println("Reset done");
				}
				break;
				//shows the present working directory
			case pwd:
				if(validate1(cmds)) {
					System.out.println(sfs.getPWD());
				}
				break;
				//shows the folders and files available to the current user
			case ls:
				if(validate1(cmds)) {
					sfs.printAll();
				}
				break;
				//changes the current user to the specified username
			case u:
				if(validate2(cmds)) {
					if(sfs.setCurrentUser(cmds[1])) {							
					}
					else {
						System.out.println("user " + cmds[1] + " does not exist");
					}
				}
				break;
				//displays information on the give user if the current user is admin
			case uinfo:
				if(validate1(cmds)) {
					if(!sfs.printUsersInfo()) {
						System.out.println("Insufficient privileges");
					}
				}
				break;
				//changes the location
			case cd:
				if(validate2(cmds)) {
					try { 
						if(sfs.moveLoc(cmds[1])) { 
							System.out.println("Success");
						}
						else {
							System.out.println("Invalid location passed");
						}
					}
					catch (IllegalArgumentException e) {
						System.out.println("Invalid location passed");
					}
				}
				break;
				//removes the specified file or folder
			case rm:
				if(validate2(cmds)) {
					if(sfs.remove(cmds[1])) {
						System.out.println(cmds[1] + " removed");
					}
					else {
						System.out.println("Insufficient privilage");
					}
				}
				break;
				//creates a directory
			case mkdir:
				if(validate2(cmds)) {
					sfs.mkdir(cmds[1]);
					System.out.println(cmds[1] + " added");
				}
				break;
				//creates a file
			case mkfile:
				if(validate2(cmds)) {
					if(true) { 
						sfs.addFile(cmds[1], cmds[2]); 
					}
					System.out.println(cmds[1] + " added");
				}
				break;
				//shares access to a file or folder with a specified user
			case sh:
				String[] words2 = cmds[2].split(" ");
				if(validate3(words2)) {
					char c = words2[1].charAt(0);
					if(c == 'r' || c == 'w') {
						if(sfs.containsUser(words2[0]) != null) {
							if (sfs.containsFileFolder(cmds[1])) { 
								if(sfs.addUser(cmds[1], cmds[2], cmds[3].charAt(0))) {
									System.out.println("Privilage granted");
								}
							}
							else { 
								System.out.println("Invalid file/folder name");
							}
						}
						else { 
							System.out.println("Invalid user");
						}
					}
					else {
						System.out.println("Invalid permission type");
					}
				}
				break;
				//exits the program
			case x:
				System.exit(0);
			default:
				System.out.println("Invalid Command");
			}
		}
	}

	private static String[] prompt(String prompt) {
		System.out.print(prompt);
		String line = scnr.nextLine();
		String []words = line.split(" " , 3);
		return words;
	}

	private static boolean validate1(String[] cmds) { 
		if(cmds.length == 1) {
			return true;
		}
		System.out.println("No Argument Needed");
		return false;
	} 
	private static boolean validate2(String[] cmds) { 
		if(cmds.length == 2) {
			return true;
		}
		System.out.println("One Argument Needed");
		return false;
	} 
	private static boolean validate3(String[] cmds) { 
		if(cmds.length == 2) { //only two because cmds is taken from only the last item of the string[]
			return true;
		}
		System.out.println("Three Arguments Needed");
		return false;
	} 

	private static Cmd stringToCmd(String strCmd) {
		try {
			return Cmd.valueOf(strCmd.toUpperCase().trim());
		}
		catch (IllegalArgumentException e){
			return Cmd.x;
		}
	}

}
