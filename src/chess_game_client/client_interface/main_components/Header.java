package chess_game_client.client_interface.main_components;
import chess_game_client.client_interface.MainClient;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class Header extends HBox{
    
    MainClient client;

    public Header(MainClient client){
        this.client = client;

        Button account = new Button("Account");
        Button configurations = new Button("Configurations");
        Button help = new Button("Help");

        this.getChildren().addAll(account, configurations, help);

        //functionality is not fully designed
    }
}
