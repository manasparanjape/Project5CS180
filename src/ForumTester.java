/**
 * ForumTester.java
 *
 * Contains instructions to test DiscussionForum class.
 *
 * @author Manas Paranjape, Mehul Gajula, Rishabh Pandey, Avinash Mahesh, Kevin Ma
 *
 * @version 11/15/2021
 *
 */

public class ForumTester {

    public static void main(String[] args) throws Exception {
    
    
        //main method in Accounts that tests full functionality of the project
        //Forum test:
        //Tests capabilities of the program create, edit, delete, reply, upvote, and grade discussion forums and posts.
        //[] indicate input
    
        Main.main(args);
    
        //Test: As a student: open course, view discussion forum, post message, reply to message, upvote message 
        /*
        Welcome!
        What would you like to do today?
        1) Login to my account
        2) Create new account
        3) Delete my account
        4) Exit
        [2]
        Set a new username:
        All usernames are case insensitive.
        [joegg]
        Set a new password:
        [curiousgeorge]
        Enter your first name:
        [Joseph]
        Enter your last name:
        [George]
        Are you a teacher or a student?
        1. Teacher
        2. Student
        [2]
        Your account was successfully created
        What would you like to do today?
        1) Login to my account
        2) Create new account
        3) Delete my account
        4) Exit
        [1]
        Please enter your username.
        [joegg]
        Please enter your password
        [curiousgeorge]
        Please enter the option number of what you want to do.
        1) Open course
        2) Log out
        [1]
        CS 180
        Math 261
        Which course would you like to open?
        [CS 180]
        Please enter the option number of what you want to do.
        1) Open discussion forum
        2) View points
        3) Exit course
        [1]
        Threads
        Terminal
        Which discussion forum would you like to open?
        [Threads]
        Please enter the option number of what you want to do.
        1) View discussion forum
        2) Post message
        3) Reply to message
        4) Upvote message
        5) Exit forum
        [1]
        Threads 18:32:13 11-13-2021
        1. First message
           - mm 18:33:27 11-13-2021
        Upvotes: 1
        2. 2nd message
           - mm 18:33:47 11-13-2021
        Upvotes: 4
        3. 3rd message
           - mm 18:33:51 11-13-2021
        Upvotes: 2
        4. 4th message
           - mm 18:34:13 11-13-2021
        Upvotes: 3
        Please enter the option number of what you want to do.
        1) View discussion forum
        2) Post message
        3) Reply to message
        4) Upvote message
        5) Exit forum
        [2]
        How do you want to write the new post?
        1) Write the post via terminal line.
        2) Import text file with post in file.
        3) Cancel.
        [1]
        What do you want to post?
        [Hi]
        Please enter the option number of what you want to do.
        1) View discussion forum
        2) Post message
        3) Reply to message
        4) Upvote message
        5) Exit forum
        [1]
        Threads 18:32:13 11-13-2021
        1. First message
           - mm 18:33:27 11-13-2021
        Upvotes: 1
        2. 2nd message
           - mm 18:33:47 11-13-2021
        Upvotes: 4
        3. 3rd message
           - mm 18:33:51 11-13-2021
        Upvotes: 2
        4. 4th message
           - mm 18:34:13 11-13-2021
        Upvotes: 3
        5. Hi
           - JosephGeorge 23:04:05 11-14-2021
        Please enter the option number of what you want to do.
        1) View discussion forum
        2) Post message
        3) Reply to message
        4) Upvote message
        5) Exit forum
        [3]
        Which message do you want to reply to? Please enter the message number or 
        0 if you do not want to upvote any message.
        [3]
        How do you want to write the new reply?
        1) 1) Write the reply via terminal line.
        2) Import text file with reply in file.
        3) Cancel.
        [2]
        Please enter the file name and path of the file 
        which contains the post                                                                  
        [work/StudentMessage]
        Please enter the option number of what you want to do.
        1) View discussion forum
        2) Post message
        3) Reply to message
        4) Upvote message
        5) Exit forum
        [1]
        Threads 18:32:13 11-13-2021
        1. First message
           - mm 18:33:27 11-13-2021
        Upvotes: 1
        2. 2nd message
           - mm 18:33:47 11-13-2021
        Upvotes: 4
        3. 3rd message
           - mm 18:33:51 11-13-2021
        Upvotes: 2
        4. Nice!
           - JosephGeorge 23:04:24 11-14-2021
        Reply to message no.: 3
        5. 4th message
           - mm 18:34:13 11-13-2021
        Upvotes: 3
        6. Hi
           - JosephGeorge 23:04:05 11-14-2021
        Please enter the option number of what you want to do.
        1) View discussion forum
        2) Post message
        3) Reply to message
        4) Upvote message
        5) Exit forum
        [4]
        Which message do you want to upvote? Please enter the message number or 
        0 if you do not want to upvote any message.
        [5]
        Please enter the option number of what you want to do.
        1) View discussion forum
        2) Post message
        3) Reply to message
        4) Upvote message
        5) Exit forum
        [5]
        Please enter the option number of what you want to do.
        1) Open discussion forum
        2) View points
        3) Exit course
        [2]
        Threads: Points not assigned to this forum yet.
        Terminal: Points not assigned to this forum yet.
        Please enter the option number of what you want to do.
        1) Open discussion forum
        2) View points
        3) Exit course
        [3]
        Please enter the option number of what you want to do.
        1) Open course
        2) Log out
        [2]
        What would you like to do today?
        1) Login to my account
        2) Create new account
        3) Delete my account
        4) Exit
        [4]
        */

        //Error testing:
        /*
        Welcome!
        What would you like to do today?
        1) Login to my account
        2) Create new account
        3) Delete my account
        4) Exit
        [1]
        Please enter your username.
        [joegg]
        Please enter your password
        [curiousgeorge]
        Please enter the option number of what you want to do.
        1) Open course
        2) Log out
        [open course]
        Please enter a valid number!
        Please enter the option number of what you want to do.
        1) Open course
        2) Log out
        [1]
        CS 180
        Math 261
        Which course would you like to open?
        [econ]
        The course you entered does not exist!
        Please enter the option number of what you want to do.
        1) Open course
        2) Log out
        [1]
        CS 180
        Math 261
        Which course would you like to open?
        [CS 180]
        Please enter the option number of what you want to do.
        1) Open discussion forum
        2) View points
        3) Exit course
        [open]
        You did not input an integer. Please input an integer between 1 and 3.
        Please enter the option number of what you want to do.
        1) Open discussion forum
        2) View points
        3) Exit course
        [view]
        You did not input an integer. Please input an integer between 1 and 3.
        Please enter the option number of what you want to do.
        1) Open discussion forum
        2) View points
        3) Exit course
        [1]
        Threads
        Terminal
        Which discussion forum would you like to open?
        [none]
        The discussion forum you entered does not exist!
        Please enter the option number of what you want to do.
        1) Open discussion forum
        2) View points
        3) Exit course
        [1]
        Threads
        Terminal
        Which discussion forum would you like to open?
        [Threads]
        Please enter the option number of what you want to do.
        1) View discussion forum
        2) Post message
        3) Reply to message
        4) Upvote message
        5) Exit forum
        [view]
        Please enter a valid number!
        You entered an invalid number. Please enter a number between 1 and 5.
        Please enter the option number of what you want to do.
        1) View discussion forum
        2) Post message
        3) Reply to message
        4) Upvote message
        5) Exit forum
        [post]
        Please enter a valid number!
        You entered an invalid number. Please enter a number between 1 and 5.
        Please enter the option number of what you want to do.
        1) View discussion forum
        2) Post message
        3) Reply to message
        4) Upvote message
        5) Exit forum
        [reply]
        Please enter a valid number!
        You entered an invalid number. Please enter a number between 1 and 5.
        Please enter the option number of what you want to do.
        1) View discussion forum
        2) Post message
        3) Reply to message
        4) Upvote message
        5) Exit forum
        [upvote]
        Please enter a valid number!
        You entered an invalid number. Please enter a number between 1 and 5.
        Please enter the option number of what you want to do.
        1) View discussion forum
        2) Post message
        3) Reply to message
        4) Upvote message
        5) Exit forum
        [2]
        How do you want to write the new post?
        1) Write the post via terminal line.
        2) Import text file with post in file.
        3) Cancel.
        [5]
        You entered an invalid option. Please enter a number between 1 and 3.
        How do you want to write the new post?
        1) Write the post via terminal line.
        2) Import text file with post in file.
        3) Cancel.
        [1]
        What do you want to post?
        [wassup]
        Please enter the option number of what you want to do.
        1) View discussion forum
        2) Post message
        3) Reply to message
        4) Upvote message
        5) Exit forum
        [4]
        Which message do you want to upvote? Please enter the message number or 
        0 if you do not want to upvote any message.
        [222]
        You entered an invalid option. Please enter a number between 1 and 7.
        Please enter the option number of what you want to do.
        1) View discussion forum
        2) Post message
        3) Reply to message
        4) Upvote message
        5) Exit forum
        [3]
        Which message do you want to reply to? Please enter the message number or 
        0 if you do not want to upvote any message.
        [5343]
        You entered an invalid number. Please enter a valid message number between 1 and 7.
        Please enter the option number of what you want to do.
        1) View discussion forum
        2) Post message
        3) Reply to message
        4) Upvote message
        5) Exit forum
        [5]
        Please enter the option number of what you want to do.
        1) Open discussion forum
        2) View points
        3) Exit course
        [3]
        Please enter the option number of what you want to do.
        1) Open course
        2) Log out
        [2]
        What would you like to do today?
        1) Login to my account
        2) Create new account
        3) Delete my account
        4) Exit
        [4]
        */

        //Test: As a teacher: open/create course, view discussion forum, create forum, delete forum, update forum, 
        //post message, reply to message, grade student messages 
        /*
        Welcome!
        What would you like to do today?
        1) Login to my account
        2) Create new account
        3) Delete my account
        4) Exit
        2
        Set a new username:
        All usernames are case insensitive.
        [Ms.Applebottom]
        Set a new password:
        [appleaday]
        Enter your first name:
        [Jane]
        Enter your last name:
        [Applebottom]
        Are you a teacher or a student?
        1. Teacher
        2. Student
        [1]
        Your account was successfully created
        What would you like to do today?
        1) Login to my account
        2) Create new account
        3) Delete my account
        4) Exit
        [1]
        Please enter your username.
        [Ms.Applebottom]
        Please enter your password
        [appleaday]
        Please enter the option number of what you want to do.
        1) Create course
        2) Open course
        3) Log out
        [1]
        What would you like to name the new course?
        [STAT 350]
        New course has been created!
        Please enter the option number of what you want to do.
        1) Create course
        2) Open course
        3) Log out
        [2]
        CS 180
        Math 261
        STAT 350
        Which course would you like to open?
        [STAT 350]
        Please enter the option number of what you want to do.
        1) Open discussion forum
        2) Create discussion forum
        3) Delete discussion forum
        4) Exit course
        [2]
        How do you want to create the new discussion forum?
        1) Enter the new forum name via terminal.
        2) Import text file with the forum name.
        3) Cancel.
        [1]
        What would you like to name the new discussion forum?
        [Stats]
        New forum has been created!
        Please enter the option number of what you want to do.
        1) Open discussion forum
        2) Create discussion forum
        3) Delete discussion forum
        4) Exit course
        [1]
        STAT 350
        Stats
        Which discussion forum would you like to open?
        [Stats]
        Please enter the option number of what you want to do.
        1) View messages
        2) Post message
        3) Reply to message
        4) Grade student messages
        5) Change topic
        6) View dashboard
        7) Exit forum
        [2]
        How do you want to write the new post?
        1) Write the post via terminal line.
        2) Import text file with post in file.
        3) Cancel.
        [1]
        What do you want to post?
        [Hi class!]
        Please enter the option number of what you want to do.
        1) View messages
        2) Post message
        3) Reply to message
        4) Grade student messages
        5) Change topic
        6) View dashboard
        7) Exit forum
        [1]
        Stats 23:30:52 11-14-2021
        1. Hi class!
           - JaneApplebottom 23:32:05 11-14-2021
        Please enter the option number of what you want to do.
        1) View messages
        2) Post message
        3) Reply to message
        4) Grade student messages
        5) Change topic
        6) View dashboard
        7) Exit forum
        [5]
        What do you want to change the topic to?
        [Probability]
        Please enter the option number of what you want to do.
        1) View messages
        2) Post message
        3) Reply to message
        4) Grade student messages
        5) Change topic
        6) View dashboard
        7) Exit forum
        [1]
        Probability 23:32:27 11-14-2021
        1. Hi class!
           - JaneApplebottom 23:32:05 11-14-2021
        Please enter the option number of what you want to do.
        1) View messages
        2) Post message
        3) Reply to message
        4) Grade student messages
        5) Change topic
        6) View dashboard
        7) Exit forum
        [6]
        Probability 23:32:27 11-14-2021
        1. Hi class!
           - JaneApplebottom 23:32:05 11-14-2021
        Upvotes: 0
        Check
        Please enter the option number of what you want to do.
        1) View messages
        2) Post message
        3) Reply to message
        4) Grade student messages
        5) Change topic
        6) View dashboard
        7) Exit forum
        [7]
        Please enter the option number of what you want to do.
        1) Open discussion forum
        2) Create discussion forum
        3) Delete discussion forum
        4) Exit course
        [3]
        Which forum would you like to delete?
        Stats
        Please enter the option number of what you want to do.
        1) Open discussion forum
        2) Create discussion forum
        3) Delete discussion forum
        4) Exit course
        [1]
        No courses created yet.
        Which discussion forum would you like to open?
        none
        The discussion forum you entered does not exist!
        Please enter the option number of what you want to do.
        1) Open discussion forum
        2) Create discussion forum
        3) Delete discussion forum
        4) Exit course
        [4]
        Please enter the option number of what you want to do.
        1) Create course
        2) Open course
        3) Log out
        [2]
        CS 180
        Math 261
        STAT 350
        Which course would you like to open?
        [CS 180]
        Please enter the option number of what you want to do.
        1) Open discussion forum
        2) Create discussion forum
        3) Delete discussion forum
        4) Exit course
        [1]
        Threads
        Terminal
        Which discussion forum would you like to open?
        [Threads]
        Please enter the option number of what you want to do.
        1) View messages
        2) Post message
        3) Reply to message
        4) Grade student messages
        5) Change topic
        6) View dashboard
        7) Exit forum
        [1]
        Threads 18:32:13 11-13-2021
        1. First message
           - mm 18:33:27 11-13-2021
        Upvotes: 1
        2. 2nd message
           - mm 18:33:47 11-13-2021
        Upvotes: 4
        3. 3rd message
           - mm 18:33:51 11-13-2021
        Upvotes: 2
        4. Nice!
           - JosephGeorge 23:04:24 11-14-2021
        Reply to message no.: 3
        5. 4th message
           - mm 18:34:13 11-13-2021
        Upvotes: 5
        6. Hi
           - JosephGeorge 23:04:05 11-14-2021
        7. wassup
           - JosephGeorge 23:12:39 11-14-2021
        Please enter the option number of what you want to do.
        1) View messages
        2) Post message
        3) Reply to message
        4) Grade student messages
        5) Change topic
        6) View dashboard
        7) Exit forum
        [3]
        Which message do you want to reply to? Please enter the message number or 
        0 if you do not want to upvote any message.
        [6]
        How do you want to write the new reply?
        1) 1) Write the reply via terminal line.
        2) Import text file with reply in file.
        3) Cancel.
        [1]
        What is your reply?
        [Hi Joe!]
        Please enter the option number of what you want to do.
        1) View messages
        2) Post message
        3) Reply to message
        4) Grade student messages
        5) Change topic
        6) View dashboard
        7) Exit forum
        [1]
        Threads 18:32:13 11-13-2021
        1. First message
           - mm 18:33:27 11-13-2021
        Upvotes: 1
        2. 2nd message
           - mm 18:33:47 11-13-2021
        Upvotes: 4
        3. 3rd message
           - mm 18:33:51 11-13-2021
        Upvotes: 2
        4. Nice!
           - JosephGeorge 23:04:24 11-14-2021
        Reply to message no.: 3
        5. 4th message
           - mm 18:34:13 11-13-2021
        Upvotes: 5
        6. Hi
           - JosephGeorge 23:04:05 11-14-2021
        7. Hi Joe!
           - JaneApplebottom 23:35:48 11-14-2021
        Reply to message no.: 6
        8. wassup
           - JosephGeorge 23:12:39 11-14-2021
        Please enter the option number of what you want to do.
        1) View messages
        2) Post message
        3) Reply to message
        4) Grade student messages
        5) Change topic
        6) View dashboard
        7) Exit forum
        [4]
        Please enter username of the student who's posts you want to view.
        [joegg]
        Messages by joegg
        4. Nice!
           - JosephGeorge 23:04:24 11-14-2021
        Reply to message no.: 3
        6. Hi
           - JosephGeorge 23:04:05 11-14-2021
        8. wassup
           - JosephGeorge 23:12:39 11-14-2021
        Please enter the number of point you wish to assign this student.
        [3]
        [[m, 0], [joegg, 3]]
        Please enter the option number of what you want to do.
        1) View messages
        2) Post message
        3) Reply to message
        4) Grade student messages
        5) Change topic
        6) View dashboard
        7) Exit forum
        [6]
        Threads 18:32:13 11-13-2021
        4. Nice!
           - JosephGeorge 23:04:24 11-14-2021
        Upvotes: 0
        Reply to message no.: 3
        6. Hi
           - JosephGeorge 23:04:05 11-14-2021
        Upvotes: 0
        7. Hi Joe!
           - JaneApplebottom 23:35:48 11-14-2021
        Upvotes: 0
        Reply to message no.: 6
        8. wassup
           - JosephGeorge 23:12:39 11-14-2021
        Upvotes: 0
        1. First message
           - mm 18:33:27 11-13-2021
        Upvotes: 1
        3. 3rd message
           - mm 18:33:51 11-13-2021
        Upvotes: 2
        2. 2nd message
           - mm 18:33:47 11-13-2021
        Upvotes: 4
        5. 4th message
           - mm 18:34:13 11-13-2021
        Upvotes: 5
        Check
        Please enter the option number of what you want to do.
        1) View messages
        2) Post message
        4) Grade student messages
        5) Change topic
        6) View dashboard
        7) Exit forum
        [7]
        Please enter the option number of what you want to do.
        1) Open discussion forum
        2) Create discussion forum
        3) Delete discussion forum
        4) Exit course
        [4]
        Please enter the option number of what you want to do.
        1) Create course
        2) Open course
        3) Log out
        [3]
        What would you like to do today?
        1) Login to my account
        2) Create new account
        3) Delete my account
        4) Exit
        [4]
        */
    
    }
}
