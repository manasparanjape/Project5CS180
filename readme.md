# Project4CS180 Documentation

### Compiling & Running
- Main class contains a main method that is used to run the program.
- Main includes static methods from other classes, so they need to be compiled
### Submissions
- Manas Paranjape submitted the code on Vocareum
- Rishabh Pandey submitted the report on Brightspace
### Descriptions of Classes
- Main
    - Consists of methods that are used in the creation and deletion of accounts. All the accounts, both students and teachers, are written to the Accounts.txt file. Changes to accounts are made to the Accounts.txt file. 
    - Methods:
      - ConvertToString 
        - creates an output string for each user with all their information separated using “§§§”
        - Ex. username§§§password§§§firstN§§§lastN§§§ifTeacher 
      - write + read file 
        - reads text file to an ArrayList inside an ArrayList called “output” 
        - ArrayList<ArrayList<String>> output = new ArrayList<>(); 
        - Sets the output to an array list called accountDetailsArray 
        - uses split() to read values 
      - checkUsernameAvailability 
        - uses a while loop to check the first index of every array (first index is the username value)
        - username is not case sensitive 
      - getNewAccountDetails
        - prompts the user to input a username, password, first name, and last name
        - checks for their availability
        - creates account and writes it to the Accounts.txt file
      - findAccount
        - reads the Accounts.txt file and checks if the first index (username) matches the username parameter
      - securityCheck
        - takes the username and password input from the user and checks whether it matches with what is stored in the Accounts.txt file
        - has a return value of 0, 1, or 2 which equals invalid, student. or teacher, respectively
      - deleteAccount
        - checks if the user is signed in first, using the securityCheck method
        - goes through the array list of accounts and checks if the first index (username) is equal to the username of the logged in user
        - overwrites the Accounts.txt file with all the users except the one that had the matching username as the if-condition.
        - if(!accountDetailsArray.get(i).get(0).equals(username)){...}
      - main
        - Runs as the primary method and allows users to access their account, create their account or delete it.
- Teacher
    - Teachers have four options to choose which action should be performed: open discussion forum, create discussion forum, delete discussion forum, and view dashboard. 
    - Methods
      - readCourseListsFile
        - reads all the courses that are in the “CoursesList.txt” file
        - stores all the course names in the courseList array list
        - often used within other methods.
      - courseExists
        - checks if the course is already present in the courseList array list
        - returns boolean
      - createCourse
        - takes user input for course name and checks whether the value is null or already exists
        - if not, course with given input name is created
        - adds course name to courseList array list
        - writes course name to text file using PrintWriter 
      - openCourse
        - checks if user inputted course is present in the courseList array list
        - if the course exists, it creates a new course object and reads all the discussion forum title names for that specific course
        - openCourseMainMethod is called to allow user to open a specific forum or view how many points they received
      - openCourseMainMethod:
        - gives the user four options: open discussion forum, create forum, delete forum, and show dashboard
- Student
  - general description
  - Methods:
      - printCourseList
        - uses a for loop to iterate through the courseList array and appends every course to a StringBuilder object
        - prints out the cumulative string
      - readCourseListsFile
        - adds all the courses to an array list called courseList
      - courseExists
        - checks if the course is already present in the courseList array list
        - returns boolean
      - openCourse
        - checks if user inputted course is present in the courseList array list
      - openCourseMainMethod
        - gives the user 2 options: open discussion forum and view points
- DiscussionForum
  - Discussion Forum stores all the information about the forums, including the forum name, the file that stores the messages for that forum, the user who created that forum, the points associated with that forum, the number of votes for replies
  - Methods: 
      - readNewPostFile
        - takes input from the user for the file destination
        - reads the file and appends it to a StringBuilder object called output
        - cleans up the file by replacing unnecessary hidden characters (\r\n and \n with spaces and “.” with “. ”)
      - readUpvotesFile
        - reads the file with all the upvotes stored 
        - splits each line by separating by user and points and stores that information in an ArrayList
        - stores that points array list in an another ArrayList, which holds all the arraylists of points for that discussion forum
        - returns the array list that holds all the points for that discussion forum
      - checkAlreadyUpvoted
        - takes messageNumber as a parameter and checks if that message has been upvoted
        - iterates through the array list called upvotesArray 
        - first checks if the username matches the user’s username
      - readMessagesFile
        - reads the file that stores all the messages in the discussion forum
        - splits the information in each line and stores it into an ArrayList
        - stores that array list into another array list that stores all the messages, and assigns that array to the messages Array
      - checkUsernameInUpvotesArray
        - checks whether the username is found in the upvotes array 
        - returns true if username found, false if not found
      - printMessages
        - reads the Messages file, then adds all the information stored in the discussion forum to a string
        - displays messages in the following format 
          - <message_index> <message> <full name> <time> <date> 
          - Upvotes: <upvotes> (only if there are any upvotes)
          - Reply to message no.: <message no.> (only if it is a reply)
      - convertMessagesArrayToFileString
        - adds all the messages stored in the discussion to a file. 
        - information for each message split by “§§§” and different messages on different lines
      - writeToMessagesFile
        - once the messages are converted to a singular string by the convertMessagesArrayToFileString, they are stored in the specified file
      - postMessage
        - checks whether the user wants enter their response through the terminal or upload a file
        - make sure the file is valid, and then adds the information of that message to a new array
        - adds that new array to the messages Array, which holds all the messages 
      - replyToPost
        - asks the user which message they want to reply to
        - checks how they want to provide their reply to the message (through terminal or file upload)
        - adds the all the information for the new reply to an array list, then adds that array list to the messages Array
        - updates all the information in the messages file
      - upvote
        - prompts the user to input the messageNumber of the message that they are trying to upvote
        - default value of messageNumber is 0, which doesn’t make any changes
        - checks if the messageNumber is in the range of the arraylist size 
        - increments the upvotes value by one, and sets the value to the new upvotes value
        - checks if the username of the user who got upvoted exists in the upvotesArray
          - if not, the username is added to the array
      - writeToUpvotesFile
        - writes all the information in the upvotes array to the upvotess file, using “§§§” as the delimiter
      - changeTopic
        - changes the name of the topic and updates the creation time to when it was changed
        - writes these changes to the file
      - readPointsFile
        - reads the points file, and stores information in each line into an array, and stores each array into another array
        - assigns the array to the points array
      - printSpecificStudentMessages
        - goes through the messages array and checks if a specific student has posted any replies to the discussion forum
        - prints all their posts if they have posted at least once
        - if they haven’t posted, then it notifies that they haven’t posted anything
      - checkUsernameNonexistence
        - reads the accounts file and adds all the accounts to a new array of accounts
        - checks if the given username exists already or not
        - if the given username is found, this method returns false, else true
      - responseGrading
        - receives student username from user, and iterates through points array to find the given username
        - once found, method prints the students messages and student receives a grade for that message
        - adds the new information to the points array and updates the points file
        - writeToPointsFile
        - prints the information in the points array to the points file by using a delimiter of  “§§§”
      - showDashboard
        - displays the messages when they are sorted in order by highest upvote to lowest upvote
      - sortUpvotesArray
        - Sorts the arraylists by comparing the number of upvotes each message has received
        - uses .get() method to access the number of upvotes
        - uses a simple sorting algorithm
        - assigning values to a temporary variable and switching the order of the messages if the previous message has more upvotes than the subsequent message
- Course
    - Includes various methods which allow a student and teacher to interact with discussion forums
    - Methods:
      - readNewForumFile
        - takes input from the user for the file destination
        - reads the file and appends it to a StringBuilder object called output
      - readForumListFile
        - reads all the lines in the discussionBoardsListFileName and appends it to an array list called output
        - assigns the output value to an array list called forumList
      - discussionForumExists
        - checks if the name is non-null and then checks if the forumList array list contains the forumName
      - createForum
        - Prompts the user regarding how they want to create the discussion board: enter the name of the forum via terminal or import text file with the forum name
        - checks whether the course is non null and if it already exists in the forumList array list
        - Adds the forum name to the array and also creates text files with the forum and course name for the messages, points, and upvotes .txt files
      - deleteForum
        - checks if the file is non-null and if it exists
        - if it exists, it deletes all the files associated with that forum: messages, points, and upvotes
        - rewrites all the discussion boards file names to the String Builder object called output
      - viewPoints
        - returns the points for a student for each forum
        - opens the points txt file for the corresponding course and searches all the lines for if they contain the username of the given user
        - the points for the user are tallied and printed out 
      - studentDiscussionForumOpened
        - displays a list of discussion forums and asks the user which discussion forum they want to open
        - creates a discussion forum object
        - calls the showDiscussionForumMainMethodStudent() method
      - teacherDiscussionForumOpened
        - displays a list of discussion forums and asks the user which discussion forum they want to open
        - creates a discussion forum object
        - calls the showDiscussionForumMainMethodTeacher() method
      - showDiscussionForumMainMethodStudent
        - Gives the student 5 choices for action: print messages, post messages, reply to a post, upvote a message, or exit
      - showDiscussionForumMainMethodTeacher
        - Gives the teacher 7 choices for action: print messages, post messages, reply to a post, grade a response, change the forum topic, show dashboard, or exit
- Account
  - The account class gives a list of options for the user, depending on whether the user is a teacher or a student. Teacher can create, open a course or exit the program, and students can either open a course or exit the program
  - Methods:
    - teacherMainMethod
      - gives the teacher three options: create course, open course, or exit
      - calls on the createCourse() or openCourse() method accordingly
    - studentMainMethod
      - gives the student two options: open a course or exit
      - calls on the openCourse() method accordingly
    - accountMainMethod
      - decides which method to run in the main method depending on whether the user is a student or a teacher







