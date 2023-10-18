package chess_game_client.client_interface.game_components.menu;

import chess_game_client.client_interface.game_components.GameMenu;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class DefaultActionsPanel extends HBox{
    
    GameMenu menu;

    public DefaultActionsPanel(GameMenu menu){
        
        this.menu = menu;
        
        Button back = new Button("Back");
        back.setOnAction((e)->{
            //
        });
        Button forth = new Button("Forth");
        forth.setOnAction((e)->{

        });
        
        Button surrender = new Button("Surrender");
        surrender.setOnAction((e) -> {

        });
        
        Button draw = new Button("Draw");
        draw.setOnAction((e) -> {

        });

        Button turnBoard = new Button("Turn board");
        back.setOnAction((e)->{
            this.menu.gameWindow.turnBoard();
        });
    
    
        this.getChildren().addAll(back, forth, surrender, draw, turnBoard);
    }
}
