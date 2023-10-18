package chess_game_client.client_interface.main_components;
import chess_game_client.client_interface.MainClient;
import javafx.scene.layout.VBox;

/**
 * News feed from chess world
 * 
 * It uses Chat gpt api and other apis in order to generate interesting news and learning materials
 * related to chess and chess world
 */
public class Feed extends VBox{

    public MainClient client;

    public Feed(MainClient client){
        this.client = client;


    }
}
