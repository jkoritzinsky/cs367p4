
import java.util.Scanner;

public class FileSystemMain {

	private static final Scanner scnr = new Scanner(System.in);

	private enum Cmd {
		reset, pwd, ls, u, uinfo, cd, rm, mkdir, mkfile, sh, x
	}

	public static void main(String[] args) {	

		SimpleFileSystem sfs = new SimpleFileSystem(null, null); //change based on input file

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
				sfs.remove(cmds[1]);
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
				sfs.addFile(cmds[1], cmds[2]); 
				System.out.println(cmds[1] + " added");
			}
			break;
		case sh:
			if(validate3(cmds)) {
				sfs.addUser(cmds[1], cmds[2], cmds[3].charAt(0)); //maybe fix
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
		String []words = line.split(" ");
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
		if(cmds.length == 4) {
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
