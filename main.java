import java.io.*;
import java.util.ArrayList;
import java.util.Scanner; // Import the Scanner class to read text files
import java.net.*;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    if (endGameFile()==false) {
      move = oppMove();
      if (oppMove.equals("")) {
        //meaning start of game it's our turn
        
      }
    }






  }

public static boolean endGameFile() throws IOException {
  try {
  File myObject = new File("end_game");
  Scanner myReader2 = new Scanner(myObject);
  return true;
  } catch(FileNotFoundException e) {
    return false;
  }
}





  public static String oppMove() throws IOException {
    File file = new File("move_file");
    InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
    BufferedReader br = new BufferedReader(reader);
    String line = "";
    while (br.ready()) {
      line = br.readLine();
    }
    return line;
  }
}
