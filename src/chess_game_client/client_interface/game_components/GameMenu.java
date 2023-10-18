package chess_game_client.client_interface.game_components;

import chess_game_client.client_interface.GameWindow;
import chess_game_client.client_interface.game_components.menu.ConfigurationPanel;
import chess_game_client.client_interface.game_components.menu.DefaultActionsPanel;
import chess_game_client.client_interface.game_components.menu.GameRecord;
import chess_game_client.client_interface.game_components.menu.TimePanel;

import javafx.scene.layout.VBox;

public abstract class GameMenu extends VBox{
    
    public GameWindow gameWindow;

    public TimePanel timePanel = new TimePanel(this);
    public GameRecord gameRecord = new GameRecord(this);
    public DefaultActionsPanel defaultActions = new DefaultActionsPanel(this);
    public ConfigurationPanel configurations = new ConfigurationPanel(this);


    public GameMenu(GameWindow gameWindow){
        this.gameWindow = gameWindow;

        this.getChildren().addAll(timePanel, gameRecord, defaultActions);

    }
}
