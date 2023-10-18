package chess_game_client.client_interface.main_components;


import chess_game_client.client_interface.MainClient;
import chess_game_client.client_interface.SingleGameWindow;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;

public class Sidebar extends VBox{

    MainClient client;
    public Sidebar(MainClient client){
        
        this.client = client;
        
        Button singleGame = new Button("Play single");
        singleGame.setOnAction((e) -> {
            SingleGameWindow gameWindow = new SingleGameWindow(this.client.app);
            gameWindow.launch();
        });
        Button onlineGame = new Button("Play online");
        Button playBot = new Button("Play against bot");
        Button openDesk = new Button("Open desk");
        Button riddles = new Button("Riddles");
        Button games = new Button("Observe games");

        this.getChildren().addAll(singleGame, onlineGame, playBot, openDesk, riddles, games);
    }
}
