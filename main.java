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
  static String pathAddition = "";

  public static void main(String[] args) {
    String groupName = "theWinner";// Can make this an argument or read from a text document
    Game currentGame = new Game(10, 10, groupName);
    currentGame.setSolver(new miniSolver());
    // currentGame.setSolver(new alphaBetaSolver());

    System.out.println("Working Directory = " + System.getProperty("user.dir"));
    if (!new File("move_file").exists())
      System.out.println("Can't find 'move_file'... \nSearching for directory...");

    while ((!new File(pathAddition + "move_file").exists())) {
      pathAddition = findFile(new File(".").getAbsolutePath(), "move_file");
      pathAddition = pathAddition.substring(0, pathAddition.length() - "move_file".length());
    }

    if (!pathAddition.equals(""))
      System.out.println("Directory found!\n Path:" + pathAddition);

    if (pathAddition.equals(null))
      System.out.println("Directory not found");

    System.out.println("pathAddition: " + pathAddition);
    loop: while (!(new File("end_game").exists())) {
      // URL pathToPass = main.class.getResource("groupname.pass");
      File passFile = new File(groupName + ".pass");
      if (passFile.exists()) {
        currentGame.handlePass();
        continue loop; // if passFile exists then go back to loop
      }

      // URL pathToGo = main.class.getResource("groupname.go");
      File goFile = new File(main.pathAddition + groupName + ".go");
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
    br.close();
    return line;
  }

  public static boolean FileFound(String fileName) {
    Path path1 = Paths.get(fileName);
    boolean result = Files.exists(path1);
    return result;
  }

  private static final String findFile(final String rootFilePath, final String fileToBeFound) {

    File rootFile = new File(rootFilePath);
    File[] subFiles = rootFile.listFiles();
    for (File file : subFiles != null ? subFiles : new File[] {}) {
      if (file.getAbsolutePath().endsWith(fileToBeFound)) {
        System.out.println(file.getAbsolutePath());
        return file.getAbsolutePath();
      } else if (file.isDirectory()) {
        String f = findFile(file.getAbsolutePath(), fileToBeFound);
        if (new File(f).getAbsolutePath().endsWith(fileToBeFound)) {
          return f;
        }
      }
    }

    return ""; // null returned in case your file is not found

  }

}
