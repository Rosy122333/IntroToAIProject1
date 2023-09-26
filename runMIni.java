import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class runMIni {
    public static void main(String[] args) {
        String groupName = "human";// Can make this an argument or read from a text document
        Game currentGame = new Game(10, 10, groupName);
        currentGame.setSolver(new miniSolver());
        loop: while (!(new File("end_game").exists())) {
            // URL pathToPass = main.class.getResource("groupname.pass");
            File passFile = new File(groupName + ".pass");
            if (passFile.exists()) {
                currentGame.handlePass();
                continue loop; // if passFile exists then go back to loop
            }

            // URL pathToGo = main.class.getResource("groupname.go");
            File goFile = new File(groupName + ".go");
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

}
