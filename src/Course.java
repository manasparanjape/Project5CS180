/*
Methods Required:
1) Enter a specific discussion forum
2) Create a new discussion forum
3) Delete a discussion forum
*/

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Course {
    private final String courseName;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String discussionBoardsListFileName;
    private DiscussionForum discussionForum;
    private final static String newForumNamePrompt = "What would you like to name the new discussion forum?";
    private final static String newForumCreated = "New forum has been created!";
    private final static String deleteForumPrompt = "Which forum would you like to delete?";
    private final static String forumDeleted = "The forum you selected has been deleted";
    private final static String forumSelectionPrompt = "Which discussion forum would you like to open?";
    private static final String forumNamePrompt = "Please enter the file name and path of the file which contains the new forum name.";
    private final static String discussionForumEnteredStudentPrompt = """
            Please enter the option number of what you want to do.
            1) View discussion forum
            2) Post message
            3) Reply to message
            4) Upvote message
            5) Exit forum""";
    private final static String discussionForumEnteredTeacherPrompt = """
            Please enter the option number of what you want to do.
            1) View messages
            2) Post message
            3) Reply to message
            4) Grade student messages
            5) Change topic
            6) View dashboard
            7) Exit forum""";
    private static final String methodOfNewForumPrompt = """
            How do you want to create the new discussion forum?
            1) Enter the new forum name via terminal.
            2) Import text file with the forum name.
            3) Cancel.""";

    private ArrayList<String> forumList = new ArrayList<>();

    public void printForumList() {
        StringBuilder output = new StringBuilder();
        for (String s : forumList) {
            output.append(s).append("\n");
        }
        output = new StringBuilder(output.substring(0, output.length() - 1));
        System.out.println(output);
        if (output.isEmpty()) {
            System.out.println("No courses created yet.");
        }
    }

    public Course(String courseName, String username, String firstName, String lastName, DiscussionForum discussionForum, String discussionBoardsListFileName /*String discussionsPointsFileName*/) {
        this.courseName = courseName;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.discussionForum = discussionForum;
        this.discussionBoardsListFileName = discussionBoardsListFileName;
        //this.discussionsPointsFileName = discussionsPointsFileName;
    }

    public String readNewForumFile() {
        Scanner scan = new Scanner(System.in);
        StringBuilder output = new StringBuilder();
        try {
            System.out.println(forumNamePrompt);
            String fileName = scan.nextLine();
            File f = new File(fileName);
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();
            while (line != null) {
                output.append(line);
                line = bfr.readLine();
            }
            output = new StringBuilder(output.toString().replace("\r\n", " ").replace("\n", " "));
            output = new StringBuilder(output.toString().replace(".", ". "));
        } catch(FileNotFoundException e) {
            System.out.println("The file you entered was not found.");
        } catch(IOException e) {
            System.out.println("Error parsing contents of the file.");
        }
        return output.toString();
    }

    public void readForumListFile() throws Exception {
        File f = new File(discussionBoardsListFileName);
        FileReader fr = new FileReader(f);
        BufferedReader bfr = new BufferedReader(fr);
        ArrayList<String> output = new ArrayList<>();
        String line = bfr.readLine();
        while (line != null) {
            output.add(line);
            line = bfr.readLine();
        }
        forumList = output;
    }

    public boolean discussionForumExists(String forumName) {
        if (forumList == null) {
            return false;
        } else {
            return forumList.contains(forumName);
        }
    }

    public void createForum() {
        Scanner scan = new Scanner(System.in);
        boolean cancelled = false;
        int option = 0;
        while (option != 3 && !cancelled) {
        	try {
        		System.out.println(methodOfNewForumPrompt);
        		option = scan.nextInt();
                scan.nextLine();
        		String newForumName = "";
        		if (option == 1) {
        			System.out.println(newForumNamePrompt);
            	   newForumName = scan.nextLine();
        		} else if (option == 2) {
        			newForumName = readNewForumFile();
        		} else if (option != 3) {
        			System.out.println("You entered an invalid option. Please enter a number between 1 and 3.");
        		}
        		if (option == 1 || option == 2) {
        			if (newForumName == null || newForumName.isBlank()) {
        				System.out.println("Please enter a valid discussion forum name(ie. Not all spaces or blank).");
        			} else if (discussionForumExists(newForumName)) {
        				System.out.println("A discussion forum with that name already exists in this course.");
        			} else {
        				DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss MM-dd-yyyy");
        				forumList.add(newForumName);
        				FileOutputStream fos = new FileOutputStream(courseName + "-" + newForumName + "-messages" + ".txt", true);
        				PrintWriter pw = new PrintWriter(fos);
        				pw.println(newForumName + "---" + LocalDateTime.now().format(format));
        				pw.close();
        				fos = new FileOutputStream(courseName + "-" + newForumName + "-points" + ".txt", false);
        				pw = new PrintWriter(fos);
                    	pw.println("");
                    	pw.close();
                    	fos = new FileOutputStream(courseName + "-" + newForumName + "-upvotes" + ".txt", false);
                    	pw = new PrintWriter(fos);
                    	pw.println("");
                    	pw.close();
                    	fos = new FileOutputStream(discussionBoardsListFileName, true);
                    	pw = new PrintWriter(fos);
                    	pw.println(newForumName);
                    	System.out.println(newForumCreated);
                    	pw.close();
                    	cancelled = true;
        			}
        		} 
        	} catch (Exception e) {
        		System.out.println("Please enter a valid number!");
        		option = 0;
        		scan.nextLine();
            }
        }
    }

    public void deleteForum() throws Exception {
        Scanner scan = new Scanner(System.in);
        System.out.println(deleteForumPrompt);
        String toDeleteForum = scan.nextLine();
        if (toDeleteForum == null || !discussionForumExists(toDeleteForum)) {
            if (!discussionForumExists(toDeleteForum)) {
                System.out.println("The discussion forum you entered does not exist.");
            } else {
                System.out.println("Please enter a valid discussion forum name(ie. Not all spaces or blank).");
            }
        } else {
            forumList.remove(toDeleteForum);
            File f = new File(courseName + "-" + toDeleteForum + "-messages" + ".txt");
            if (f.delete()) {
                f = new File(courseName + "-" + toDeleteForum + "-points" + ".txt");
                if (f.delete()) {
                    f = new File(courseName + "-" + toDeleteForum + "-upvotes" + ".txt");
                    if (f.delete()) {
                        FileOutputStream fos = new FileOutputStream(discussionBoardsListFileName, false);
                        PrintWriter pw = new PrintWriter(fos);
                        StringBuilder output = new StringBuilder();
                        for (String s : forumList) {
                            output.append(s).append("\n");
                        }
                        if (output.length() > 0) {
                            output = new StringBuilder(output.substring(0, output.length() - 1));
                        }
                        pw.println(output);
                        pw.close();
                    }
                    System.out.println(forumDeleted);
                }
            }
        }
    }

    public void viewPoints() throws IOException {
        StringBuilder output = new StringBuilder();
        for (String s : forumList) {
            output.append(s).append(": ");
            File f = new File(courseName + "-" + s + "-points" + ".txt");
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();
            boolean pointsFound = false;
            while (line != null) {
                if (line.contains(username)) {
                    String[] splitLine = line.split("---");
                    output.append(splitLine[1]).append("\n");
                    pointsFound = true;
                }
                line = bfr.readLine();
            }
            if (!pointsFound) {
                output.append("Points not assigned to this forum yet.\n");
            }
        }
        System.out.println(output);
    }

    public void studentDiscussionForumOpened() throws Exception {
        Scanner scan = new Scanner(System.in);
        readForumListFile();
        printForumList();
        System.out.println(forumSelectionPrompt);
        String selectedForum = scan.nextLine();
        if (selectedForum == null || !discussionForumExists(selectedForum)) {
            System.out.println("The discussion forum you entered does not exist!");
        } else {
            String discussionForumMessagesFileName = courseName + "-" + selectedForum + "-messages" + ".txt";
            String discussionForumPointsFileName = courseName + "-" + selectedForum + "-points" + ".txt";
            String discussionForumUpvotesFileName = courseName + "-" + selectedForum + "-upvotes" + ".txt";
            discussionForum = new DiscussionForum(selectedForum, discussionForumMessagesFileName, discussionForumPointsFileName, firstName, lastName, username, discussionForumUpvotesFileName);
            discussionForum.readMessagesFile();
            discussionForum.readPointsFile();
            discussionForum.readUpvotesFile();
            showDiscussionForumMainMethodStudent();
        }
    }

    public void teacherDiscussionForumOpened() throws Exception {
        Scanner scan = new Scanner(System.in);
        readForumListFile();
        printForumList();
        System.out.println(forumSelectionPrompt);
        String selectedForum = scan.nextLine();
        if (selectedForum == null || !discussionForumExists(selectedForum)) {
            System.out.println("The discussion forum you entered does not exist!");
        } else {
            String discussionForumMessagesFileName = courseName + "-" + selectedForum + "-messages" + ".txt";
            String discussionForumPointsFileName = courseName + "-" + selectedForum + "-points" + ".txt";
            String discussionForumUpvotesFileName = courseName + "-" + selectedForum + "-upvotes" + ".txt";
            discussionForum = new DiscussionForum(selectedForum, discussionForumMessagesFileName, discussionForumPointsFileName, firstName, lastName, username, discussionForumUpvotesFileName);
            discussionForum.readMessagesFile();
            discussionForum.readPointsFile();
            discussionForum.readUpvotesFile();
            showDiscussionForumMainMethodTeacher();
        }
    }

    public void showDiscussionForumMainMethodStudent() throws Exception {
        Scanner scan = new Scanner(System.in);
        int option = 0;
        while (option != 5) {
        	try {
        		System.out.println(discussionForumEnteredStudentPrompt);
        		option = scan.nextInt();
                scan.nextLine();
        	} catch (Exception e) {
        		System.out.println("Please enter a valid number!");
        		option = 0;
        		scan.nextLine();
        	}
            if (option < 1 || option > 5) {
                System.out.println("You entered an invalid number. Please enter a number between 1 and 5.");
            } else {
                switch (option) {
                    case 1 -> discussionForum.printMessages();
                    case 2 -> discussionForum.postMessage();
                    case 3 -> discussionForum.replyToPost();
                    case 4 -> discussionForum.upvote();
                }
            }
        }
    }

    public void showDiscussionForumMainMethodTeacher() {
        Scanner scan = new Scanner(System.in);
        int option = 0;
        while (option != 7) {
        	try {
        		System.out.println(discussionForumEnteredTeacherPrompt);
        		option = scan.nextInt();
                scan.nextLine();
        		if (option < 1 || option > 7) {
        			System.out.println("You entered an invalid number. Please enter a number between 1 and 6.");
        		} else {
        			switch (option) {
                    	case 1 -> discussionForum.printMessages();
                    	case 2 -> discussionForum.postMessage();
                    	case 3 -> discussionForum.replyToPost();
                    	case 4 -> discussionForum.responseGrading();
                    	case 5 -> discussionForum.changeTopic();
                    	case 6 -> discussionForum.showDashboard();
        			}
        		}
        	} catch (Exception e) {
        		System.out.println("Please enter a valid number");
        		scan.nextLine();
        		option = 0;
        		
        	}
        }
    }
}
