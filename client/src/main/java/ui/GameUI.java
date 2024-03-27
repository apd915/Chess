package ui;

import server.ServerFacade;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class GameUI {

    PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
    ServerFacade server = new ServerFacade();
}
