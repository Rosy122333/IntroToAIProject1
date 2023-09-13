import java.io.*;
import java.util.ArrayList;
import java.util.Scanner; // Import the Scanner class to read text files
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.lang.*;

public class main {



  public static void main(String[] args) {
    //try {
    	
      URL path = main.class.getResource("groupname.go");
      if (FileFoundInUnder2minutes("groupname.go") == false) {
    	  
      } else {
    	  if (FileFound("end_game")) {
    		  //do something game ended
    	  } else {
    		  if (FileFound("move_file")) {
    			  File myObj = new File(path.getFile());
    	      		Scanner myReader = new Scanner(myObj);
    	      		while (myReader.hasNextLine()) {
    	      			String data = myReader.nextLine();
    	      			System.out.println(data);
    	      			myReader.close();
    	      			if (data.equals("")) {
    	      				//meaning it's the start of the game
    	      				// and our turn to pick a move
    	      			} else {
    	      				//save this data somewhere and do stuff
    	      			}
    	      			
    	      		}
    		  
    		  }
      		
      		}
    	  
      }	
   // } catch (FileNotFoundException e) {
   //   System.out.println("File not there");
    //  e.printStackTrace();
    //}

    
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



public static boolean FileFoundInUnder2minutes(String fileName) {
	Path path1 = Paths.get(fileName);
    boolean result = Files.exists(path1);
    long start = System.currentTimeMillis();
    long end = 0;
    boolean TwoMin = false;
    	while (result != true && (TwoMin == false)) {
    		result = Files.exists(path1);
    		end = System.currentTimeMillis();
    		//System.out.println(end-start);
    		if ((end-start) > 10000) {
    			TwoMin = true;
    		}
    	}
    	if (TwoMin) {
    		System.out.print("Could not find the file in under 10 seconds");
    		return false;
    	}
    		return true;
}
public static boolean FileFound(String fileName) {
	Path path1 = Paths.get(fileName);
    boolean result = Files.exists(path1);
    return result;
}

}