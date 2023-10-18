package chess_game_client.client_interface.game_components.menu;

import chess_game_client.client_interface.game_components.GameMenu;
import javafx.scene.layout.HBox;

public class ConfigurationPanel extends HBox{

    public GameMenu menu;

    public ConfigurationPanel(GameMenu menu){
        this.menu = menu;
    }
}
