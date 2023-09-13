import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Random;


import java.lang.*;

public class main {



  public static void main(String[] args) {
      String groupName = "groupname"; // Can make this an argument or read from a text document
      Game currentGame = new Game(9,9, new ArrayList<Edge>(81*4),groupName);
      currentGame.setSolver(new HumanSolver());
       loop:while(!(new File("end_game").exists())) {
         URL pathToPass = main.class.getResource("groupname.pass");
           File passFile = new File(pathToPass.getFile());
         if(passFile.exists()) {
           currentGame.handlePass();
           continue loop; // if passFile exists then go back to loop
         }


        URL pathToGo = main.class.getResource("groupname.go");
        File goFile = new File(pathToGo.getFile());
        if (goFile.exists()) {
          currentGame.handleGo();
        }
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
