# Testing for Project 5

### Test 0: Create courses, forums, and messages for testing
1. User launches application
2. User clicks login
3. User enters username: teacher
4. User enters password: teacher
5. User clicks Create course button
6. User enters "CS 180" in the text box
7. User clicks done and then clicks OK on the confirmation message
8. User clicks Open course
9. User clicks the OK button while the dropdown is on CS 180
10. User clicks Create new discussion forum
11. User enters "Threads" into the forum name text box
12. User clicks OK on confirmation message
13. User clicks Create new discussion forum
14. User enters "GUI" into the forum name text box
15. User clicks OK on confirmation message
16. User clicks Create new discussion forum
17. User enters "For statement" into the forum name text box
18. User clicks OK on confirmation message
19. User clicks Create new discussion forum
20. User enters "If statement" into the forum name text box
21. User clicks OK on confirmation message
22. User clicks Open discussion forum
23. User clicks on "Threads"
24. User enters "Test Message" in the first text box
25. User clicks the Send/reply button
26. User repeats steps 24 and 25 another 6 times to create 7 messages in the Threads forum
27. User clicks on "GUI"
28. User repeats steps 24 and 25 another 7 times to create 7 messages in the GUI forum
29. User clicks on "For statement"
30. User repeats steps 24 and 25 another 7 times to create 7 messages in the For statement forum
31. User clicks on "If statement"
32. User repeats steps 24 and 25 another 7 times to create 7 messages in the If statement forum
33. Test passed: CS 180 course and the 4 forums with messages have been created

### Test 1: Student Log in
1. User launches application
2. User clicks login
3. User enters username: student
4. User enters password: student
5. Test passed: Application verifies log in and brings user to homepage

### Test 2: Student log out
1. User launches application
2. User clicks login
3. User enters username: student
4. User enters password: student
5. User is brought to the homepage and user clicks logout
6. Test passed: User is brought back to the Account Page

### Test 3: Teacher log in
1. User launches application
2. User clicks login
3. User enters username: teacher
4. User enters password: teacher
5. Test passed: Application verifies log in and brings user to homepage

### Test 4: Teacher log out
1. User launches application
2. User clicks login
3. User enters username: teacher
4. User enters password: teacher
5. User is brought to the homepage and user clicks logout
6. Test passed: User is brought back to the Account Page

### Test 5: Create student account and log in
1. User launches application
2. User clicks create account
3. User enters the username: billy
4. User enters the password: billnyethescienceguy
5. User enters first name: Bill
6. User enters last name: Nye
7. User enters account type: student
8. User clicks OK after confirmation message pops up
9. User clicks login
10. User enters username: billy
11. User enters password: billnyethescienceguy
12. Test passed: User successfully created an account and was able to log in

### Test 6: Create teacher account and log in
1. User launches application
2. User clicks create account
3. User enters the username: ms.apple
4. User enters the password: keepthedoctoraway
5. User enters first name: Claire
6. User enters last name: Apple
7. User enters account type: teacher
8. User clicks OK after confirmation message pops up
9. User clicks login
10. User enters username: ms.apple
11. User enters password: keepthedoctoraway
12. Test passed: User successfully created an account and was able to log in

### Test 7: Delete Account
1. User launches application
2. User clicks delete account
3. User enters the username: billy
4. User enters the password: billnyethescienceguy
5. User clicks OK on the account deletion confirmation message
6. User clicks login
7. User enters username: billy
8. User enters password: billnyethescienceguy
9. User receives error message "The account details entered were invalid"
10. Test passed: Account successfully deleted 

### Test 8: Change Account Password
1. User launches application
2. User clicks Change Password
3. User enters the username: ms.apple
4. User enters the password: keepthedoctoraway
5. User enters new password: banana
6. User clicks OK on confirmation message
7. User clicks login
8. User enters username: ms.apple
9. User enters password: banana
10. Test passed: Password changed successfully 

### Test 9, 10, 11: Student opens discussion forum and sends/replies to messages
1. User launches application
2. User clicks login
3. User enters username: student
4. User enters password: student
5. User clicks open course button
6. User clicks the OK button while the dropdown is on CS 180
7. User clicks Open Discussion forums
8. User clicks Threads
9. User clicks GUI
10. User clicks For statement
11. User clicks If Statement
12. Test passed: User is successfully brought to the discussion forums and can click through the different forums
13. User clicks GUI
14. User enters "Hi classmates" in the first text box
15. User clicks Send/Reply button
16. Test passed: User is able to send message in forum
17. User enters "Good idea" in the first text box
18. User enters "3" in the second text box
19. User clicks Send/Reply button
20. User clicks back, clicks back, clicks logout
21. Test passed: User is able to reply to other messages

### Test 12: Student opens discussion forum and imports file with message
1. User launches application
2. User clicks login
3. User enters username: student
4. User enters password: student
5. User clicks open course button
6. User clicks the OK button while the dropdown is on CS 180
7. User clicks Open Discussion forums
8. User clicks GUI
9. User clicks Import File with Message
10. File select menu pops up and user selects the file "message.txt" and then clicks open
11. User clicks back, clicks back, clicks logout
12. Test passed: Text from message.txt shows up in GUI discussion forum  

### Test 13 and 14: Student can upvote messages and is limited to 1 upvote per message
1. User launches application
2. User clicks login
3. User enters username: student
4. User enters password: student
5. User clicks open course button
6. User clicks the OK button while the dropdown is on CS 180
7. User clicks on Threads
8. User enters "6" in the second text box
9. User clicks the upvote button
10. Test passed: Student can successfully upvote messages
11. User enters "6" in the second text box and clicks the upvote button
12. Error message shows up saying "You have already upvoted this message. You cannot upvote the same message twice."
13. User clicks back, clicks back, clicks logout
14. Test passed: User cannot upvote the same message twice

### Test 15 and 16: Teacher deletes message and grades students
1. User launches application
2. User clicks login
3. User enters username: teacher
4. User enters password: teacher
5. User clicks open course button
6. User clicks the OK button while the dropdown is on CS 180
7. User clicks Open discussion forums
8. User clicks For statement
9. User enters "3" in the second text box
10. User clicks Delete Message
11. Test passed: Message is deleted
12. User clicks If statement
13. User enters "2" in second text box
14. User clicks Grade
15. User enters "student" in text box and clicks OK
16. User enters "1" in text box and clicks OK
17. Confirmation message shows up and user clicks OK
18. User clicks back, clicks back, clicks logout
19. Test passed: Teacher is able to assign grades to students

### Test 17 and 18: Teacher can create forums and deletes forums
1. User launches application
2. User clicks login
3. User enters username: teacher
4. User enters password: teacher
5. User clicks open course button
6. User clicks the OK button while the dropdown is on CS 180
7. User clicks Create new discussion forum
8. User enters "Hello World" into the forum name text box
9. User clicks OK on confirmation message
10. User clicks Open discussion forums
11. Test passed: The new forum appears in the list of forums
12. User clicks back
13. User clicks Delete discussion forum
14. User selects "Hello World" from dropdown menu and clicks OK
15. User clicks OK on confirmation message
16. User clicks Open Discussion forums
17. User clicks back, clicks back, clicks logout
18. Test passed: Forum was successfully deleted

### Test 19: Teacher can create discussion forum with file import
1. User launches application
2. User clicks login
3. User enters username: teacher
4. User enters password: teacher
5. User clicks open course button
6. User clicks the OK button while the dropdown is on CS 180
7. User clicks Create new discussion forum via file import
8. User selects the file "teacherForum.txt" from the file select menu
9. User clicks OK on confirmation message
10. User clicks Open discussion forums
11. User clicks back, clicks back, clicks logout
12. Test passed: The new forum appears in the list of forums

### Test 20: Teacher can create course
1. User launches application
2. User clicks login
3. User enters username: teacher
4. User enters password: teacher
5. User clicks Create course button
6. User enters "Class info" in the text box
7. User clicks done and then clicks OK on the confirmation message
8. User clicks Open course
9. User selects "Class info" from the dropdown menu and clicks OK
10. User click back, clicks logout
11. Test passed: Teacher can create new courses

### Test 21: Teacher can change topic names
1. User launches application
2. User clicks login
3. User enters username: teacher
4. User enters password: teacher
5. User clicks open course button
6. User clicks the OK button while the dropdown is on CS 180
7. User clicks Open discussion forums
8. User clicks on If statement
9. User clicks Change topic
10. User enters "If else statements" in the text box and clicks OK
11. Test passed: Teacher is able to change the names of discussion forums
