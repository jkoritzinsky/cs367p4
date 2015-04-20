
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class FileSystemMain {

	private static final Scanner scnr = new Scanner(System.in);

	private enum Cmd {
		reset, pwd, ls, u, uinfo, cd, rm, mkdir, mkfile, sh, x
	}

	public static void main(String[] args) {	
		
		SimpleFolder rootfolder = null;
		String[] users = null;
		ArrayList<String> files = new ArrayList<String>();
		try(Scanner fileScnr = new Scanner(new File(args[0]))) //A scanner for the file
		{	
		 rootfolder = new SimpleFolder(fileScnr.nextLine(), "", null, null);
		 users = fileScnr.nextLine().split(", ");
		 while(fileScnr.hasNextLine()) {
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
		String[] cmds = prompt("Enter a command");
		Cmd cmd = stringToCmd(cmds[0]);

		switch (cmd) {
		case reset:
			if(validate1(cmds)) {
				sfs.reset();
				System.out.println("Reset done");
			}
			break;
		case pwd:
			if(validate1(cmds)) {
				System.out.println(sfs.getPWD());
			}
			break;
		case ls:
			if(validate1(cmds)) {
				sfs.printAll();
			}
			break;
		case u:
			if(validate2(cmds)) {
				if(sfs.setCurrentUser(cmds[1])) {							
				}
				else {
					System.out.println("user " + cmds[1] + " does not exist");
				}
			}
			break;
		case uinfo:
			if(validate1(cmds)) {
				if(!sfs.printUsersInfo()) {
					System.out.println("Insufficient privileges");
				}
			}
			break;
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
		case mkdir:
			if(validate2(cmds)) {
				sfs.mkdir(cmds[1]);
				System.out.println(cmds[1] + " added");
			}
			break;
		case mkfile:
			if(validate2(cmds)) {
				if(true) { 
					sfs.addFile(cmds[1], cmds[2]); 
				}
				System.out.println(cmds[1] + " added");
			}
			break;
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
		case x:
			System.exit(0);
		default:
			System.out.println("Usage: java FileSystemMain FileName"); //fix with real thing
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
