public class AccountsTesting {
  //Tester class used to test each method included in the Accounts class
  public static void main(String[] args) {
    
    //Creating Accounts object representing a student account
    Accounts studentOne = new Accounts("Billy23", "B1L@#", "Billy", "Smith", false);
    
    //Testing covertToString method in Accounts.java
    String studentString = studentOne.convertToString();
    System.out.println(studentString);
    //output:
    //Billy23---B1L@#---Billy---Smith---student
  }
}
 
