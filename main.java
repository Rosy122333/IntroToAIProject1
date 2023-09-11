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
