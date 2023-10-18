package chess_game_client.client_interface.game_components;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import chess_game_client.client_interface.GameWindow;
import javafx.scene.control.Label;

import javafx.scene.layout.HBox;

public class PlayerPanel extends HBox{
    
    GameWindow gameWindow;

    //for guest
    public PlayerPanel(GameWindow gameWindow){

        this.gameWindow = gameWindow;

        ImageView avatarPhoto = new ImageView(new Image("imgs/empty_avatar.jpg"));
        Label nickname = new Label("Guest");


        this.getChildren().addAll(avatarPhoto, nickname);
    }

    // // for a real user
    // public PlayerPanel(Account account){

    // }

    // //for a bot?
    // public PlayerPanel(Bot bot){

    // }

    public void setPlayer(){
        //
    }

    public void setBot(){
        //
    }


}
