package chess_game_client.client_interface.game_components.menu;

import chess_game_client.client_interface.game_components.GameMenu;
import javafx.scene.layout.VBox;

public class TimePanel extends VBox{

    public GameMenu menu;

    public TimePanel(GameMenu menu){
        this.menu = menu;
    }
}
