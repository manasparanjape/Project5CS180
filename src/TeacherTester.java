public class TeacherTester {
  
  public static void main(String[] args) {
    //Creating Accounts object representing a teacher account
    Accounts teacherOne = null;
    Accounts teacherOne = new Accounts("Ms.Paul", "skooliskool", "Mary", "Paul", true);
    if(teacherOne != null) {
      System.out.println(true);
    } else {
      System.out.println(false);
    }
    //successfully created if true
    
    //Testing covertToString method in Accounts.java
    String teacherString = teacherOne.convertToString();
    System.out.println(studentString.equals("Ms.Paul---skooliskool---Mary---Paul---teacher"));
    //returns true if method matches expected output
    
    //check if writeToFile method returns a FileNotFoundException
    try {
    	teacherOne.writeToFile();
    } catch(FileNotFoundException e) {
    	System.out.println("File Not Found");
    }
    //passed; does not return file not found
    
    //checks if username is already in use; true if username is used and false otherwise
    System.out.println(studentOne.checkUsernameAvailability("Ms.Paul"));
    
  }
}
