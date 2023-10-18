package chess_game_client.client_interface.game_components;

import chess_game_client.client_interface.SingleGameWindow;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class SingleGameMenu extends GameMenu{

    ReturnMovesPanel returnPanel = new ReturnMovesPanel(this);

    public SingleGameMenu(SingleGameWindow gameWindow){
        super(gameWindow);

        this.getChildren().clear();

        this.getChildren().addAll(this.timePanel, this.gameRecord, this.returnPanel, this.defaultActions,
            this.configurations);
    }

    public class ReturnMovesPanel extends HBox{
        
        public SingleGameMenu gameMenu;
        TextField chosenMove = new TextField();
        {
            chosenMove.setPromptText("type move number");
        }

        public ReturnMovesPanel(SingleGameMenu gameMenu){
            this.gameMenu = gameMenu;

            Button cancel = new Button("Cancel move");
            cancel.setOnAction((e)->{
                //
            });

            Button goBack = new Button("Go bakc to move: ");
            goBack.setOnAction((e) -> {
                //
            });
            
            this.getChildren().addAll(cancel, goBack, this.chosenMove);
        }
    }
}
