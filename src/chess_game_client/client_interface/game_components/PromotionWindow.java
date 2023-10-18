package chess_game_client.client_interface.game_components;

import chess_game_client.client_interface.GameWindow;
import chess_game_client.client_interface.game_components.Board.PhysicalCell;
import chess_game_client.gamelogic.ChessGame;
import chess_game_client.gamelogic.ChessGame.Figure;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PromotionWindow extends Stage{

    public GameWindow gameWindow;
    

    public class FigureButton extends Button{

        PromotionWindow promotionWindow = null;
        Figure figure;
        public String text;

        public FigureButton(String text, PromotionWindow promotionWindow){
            super(text);
            this.text = text;
            this.promotionWindow = promotionWindow;
            
            this.setOnAction(event -> {
                FigureButton clickedButton = (FigureButton)event.getSource();
                this.setFigure();
                clickedButton.promotionWindow.gameWindow.board.promotionFigure = this.figure;
                sendMove();
                clickedButton.promotionWindow.close();
            });
                
        }

        public void setFigure(){
            Board board = this.promotionWindow.gameWindow.board;
            switch(text){
                case "Rook":
                    if(board.firstChosenCell.x == 6){
                        this.figure = Figure.WhiteRook;
                    }else if(board.firstChosenCell.x == 1){
                        this.figure = Figure.BlackRook;
                    }
                    break;
                case "Knight":
                    if(board.firstChosenCell.x == 6){
                        this.figure = Figure.WhiteKnight;
                    }else if(board.firstChosenCell.x == 1){
                        this.figure = Figure.BlackKnight;
                    }
                    break;
                case "Bishop":
                    if(board.firstChosenCell.x == 6){
                        this.figure = Figure.WhiteBishop;
                    }else if(board.firstChosenCell.x == 1){
                        this.figure = Figure.BlackBishop;
                    }
                    break;
                case "Queen":
                    if(board.firstChosenCell.x == 6){
                        this.figure = Figure.WhiteQueen;
                    }else if(board.firstChosenCell.x == 1){
                        this.figure = Figure.BlackQueen;
                    }
                    break;
                default: 
                    this.figure = null;

            }
        }
            
    }

    public void sendMove(){
        try{

            System.out.println("Closing");
            PhysicalCell firstChosenCell = this.gameWindow.board.firstChosenCell;
            PhysicalCell secondChosenCell = this.gameWindow.board.secondChosenCell;

            ChessGame.Figure[][] newBoard = this.gameWindow.tryMove( new ChessGame.Move(
                this.gameWindow.board.firstChosenCell.getFigure(), 
                secondChosenCell.getFigure(), 
                new ChessGame.Cell(firstChosenCell.x, firstChosenCell.y), new ChessGame.Cell(secondChosenCell.x, secondChosenCell.y), 
                this.gameWindow.board.promotionFigure
            ));
            this.gameWindow.board.firstChosenCell = null;
            this.gameWindow.board.secondChosenCell = null;

            if(newBoard != null){
                this.gameWindow.board.refreshBoard(newBoard);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    

    public PromotionWindow(GameWindow gameWindow){
        this.gameWindow = gameWindow; 

        FigureButton rook = new FigureButton("Rook", this);
        FigureButton knight = new FigureButton("Knight", this);
        FigureButton bishop = new FigureButton("Bishop", this);
        FigureButton queen = new FigureButton("Queen", this);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(rook, knight, bishop, queen);

        this.setOnCloseRequest(event -> {
            System.out.println("Closing");
            this.gameWindow.board.promotionFigure = null;
            sendMove();
            
        });
        
        Scene scene = new Scene(vbox, 400, 300);
        this.setScene(scene);
    }
}
