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
    Which message do you want to reply to? Please enter the message number or 0 if you do not want to upvote any message.
    [3]
    How do you want to write the new reply?
    1) 1) Write the reply via terminal line.
    2) Import text file with reply in file.
    3) Cancel.
    [1]
    What is your reply?
    [Nice!]
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
    Which message do you want to upvote? Please enter the message number or 0 if you do not want to upvote any message.
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
    Which message do you want to upvote? Please enter the message number or 0 if you do not want to upvote any message.
    [222]
    You entered an invalid option. Please enter a number between 1 and 7.
    Please enter the option number of what you want to do.
    1) View discussion forum
    2) Post message
    3) Reply to message
    4) Upvote message
    5) Exit forum
    [3]
    Which message do you want to reply to? Please enter the message number or 0 if you do not want to upvote any message.
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
    
    Main.main(args);
    
  }
}
