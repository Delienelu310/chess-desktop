package chess_game_client.client_interface;

import javafx.application.Application;
import javafx.stage.Stage;

public class ChessApp extends Application{

    public MainClient mainClient = new MainClient(this);
    
    public void start(Stage primStage){
        mainClient.show();
    }

    public static void main(String[] args){
        launch();
    }
}
