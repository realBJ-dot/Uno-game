package UNOFunctionality;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {
	//to-do: make a game flow
    public static void main(String[] args) throws IOException, InterruptedException {
        UNOModel dataBuffer = new UNOModel();
        StartGameGUI newGame = new StartGameGUI(dataBuffer);
        //int numPlayers = newGame.getUserInput1();
        //int numAIs = newGame.getUserInput2();
    }
}

