package chess_game_client.client_interface;

import chess_game_client.client_interface.game_components.SingleGameMenu;
import chess_game_client.gamelogic.ChessGame;
import chess_game_client.gamelogic.ChessGame.Move;

public class SingleGameWindow extends GameWindow{

    public SingleGameWindow(ChessApp app){
        super(app);
        this.chessGame = new ChessGame();

        this.gameMenu = new SingleGameMenu(this);
        this.menuContainer.getChildren().add(gameMenu);
        
    }

    public ChessGame.Figure[][] tryMove(Move move){
        System.out.println(chessGame.status);
        if(! chessGame.status.equals("ongoing")) return null;
        boolean isMoveDone = this.chessGame.makeMove(move.firstCell, move.secondCell, move.transormationFigure);
        System.out.println(isMoveDone);

        if(!isMoveDone) return null;
        else return chessGame.board;

    }

    public void launch(){
        this.board.refreshBoard(chessGame.board);
        this.show();
    }
}
