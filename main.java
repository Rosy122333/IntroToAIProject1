import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.net.*;

public class main {



  public static void main(String[] args) {
    try {
        URL path = main.class.getResource("groupname.go");
      File myObj = new File(path.getFile());
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        System.out.println(data);
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("File not there");
      e.printStackTrace();
    }
  }






}
