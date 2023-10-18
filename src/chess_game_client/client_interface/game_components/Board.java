package chess_game_client.client_interface.game_components;

import javafx.scene.image.ImageView;
import chess_game_client.client_interface.GameWindow;
import chess_game_client.gamelogic.ChessGame;
import chess_game_client.gamelogic.ChessGame.Figure;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Board extends Pane{

    public GameWindow gameWindow;

    public ChessGame.Figure[] cells;
    public PhysicalCell[][] physicalCells = new PhysicalCell[8][8];

    public Figure promotionFigure = null;

    private double size = 400;
    public void setBoardSize(double size){
        this.size = size;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                //changing each cell sizes and coordinates
            }
        }
    }
    public double getBoardSize(){
        return this.size;
    }


    public class PhysicalCell extends Pane{

        final String pathPattern = "imgs/";
        String path;

        public int x, y;
        private ChessGame.Figure figure = ChessGame.Figure.Cell;
        public void setFigure(ChessGame.Figure figure){
            this.figure = figure;
            this.refresh();
        }
        public ChessGame.Figure getFigure(){
            return this.figure;
        };

        public ImageView imageView;

        public PhysicalCell(int x, int y){
            this.x = x;
            this.y = y;

            this.setPrefSize(50, 50);
            Image image = new Image(pathPattern + ((x+y) % 2 == 0 ? "00" : "01") + ".png");
            this.imageView = new ImageView(image);

            this.getChildren().add(imageView);
        }

        public void refresh(){
            
            this.path = pathPattern;
            boolean isCellBlack = (x + y) % 2 == 0;
            switch(this.figure){
                case Cell:
                    if(isCellBlack) this.path += "00";
                    else this.path += "01";
                    break;
                case WhitePawn:
                    if(isCellBlack) this.path += "02";
                    else this.path += "03";
                    break;
                case BlackPawn:
                    if(isCellBlack) this.path += "04";
                    else this.path += "05";
                    break;
                case WhiteKnight:
                    if(isCellBlack) this.path += "06";
                    else this.path += "07";
                    break;
                case BlackKnight:
                    if(isCellBlack) this.path += "08";
                    else this.path += "09";
                    break;
                case WhiteBishop:
                    if(isCellBlack) this.path += "10";
                    else this.path += "11";
                    break;
                case BlackBishop:
                    if(isCellBlack) this.path += "12";
                    else this.path += "13";
                    break;
                case WhiteRook:
                    if(isCellBlack) this.path += "14";
                    else this.path += "15";
                    break;
                case BlackRook:
                    if(isCellBlack) this.path += "16";
                    else this.path += "17";
                    break;
                case WhiteQueen:
                    if(isCellBlack) this.path += "18";
                    else this.path += "19";
                    break;
                case BlackQueen:
                    if(isCellBlack) this.path += "20";
                    else this.path += "21";
                    break;
                case WhiteKing:
                    if(isCellBlack) this.path += "22";
                    else this.path += "23";
                    break;
                case BlackKing:
                    if(isCellBlack) this.path += "24";
                    else this.path += "25";
                    break;
            }
            this.path += ".png";
        
            imageView.setImage(new Image(this.path));
        }
    }

    PhysicalCell firstChosenCell = null;
    PhysicalCell secondChosenCell = null;

    public Board(GameWindow gameWindow){
        this.gameWindow = gameWindow;

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                PhysicalCell cell = new PhysicalCell(i, j);
                physicalCells[i][j] = cell;

                cell.setLayoutX(j * this.size / 8);
                cell.setLayoutY( (7-i) * this.size / 8);
                cell.setPrefSize(this.size / 8, this.size / 8);

                this.getChildren().add(cell);
            }
        }

        this.setOnMouseClicked((event) -> {

            //only for cells
            PhysicalCell cell = null;
            
            if(event.getTarget() instanceof ImageView){
                cell = (PhysicalCell)(((ImageView)event.getTarget()).getParent());
            }else return;

            //firstly we choose first cell, from the figure will be moving
            if(this.firstChosenCell == null){
                //but if first cell does not have any figure, we skip:
                if(cell.getFigure() == Figure.Cell) return;

                //else we set it chosen
                this.firstChosenCell = cell;
                return;
            }else{
            //then we choose the cell, to which the figure will be moving
                try{
                    System.out.println(firstChosenCell.x + " " + firstChosenCell.y + " " + firstChosenCell.getFigure() + "\n" + 
                        cell.x + " " + cell.y + " " + cell.getFigure());
                    
                    if(this.firstChosenCell.getFigure() == Figure.WhitePawn && cell.x == 7 || 
                        this.firstChosenCell.getFigure() == Figure.BlackPawn && cell.x == 0 
                    ){
                        this.secondChosenCell = cell;
                        this.gameWindow.promotionWindow.show();

                    }else{
                        ChessGame.Figure[][] newBoard = this.gameWindow.tryMove( new ChessGame.Move(
                            firstChosenCell.getFigure(), 
                            cell.getFigure(), 
                            new ChessGame.Cell(firstChosenCell.x, firstChosenCell.y), new ChessGame.Cell(cell.x, cell.y), 
                            this.promotionFigure
                        ));
                        this.firstChosenCell = null;

                        if(newBoard != null){
                            this.refreshBoard(newBoard);
                        }
                    }
                    
                }catch(Exception e){
                    System.out.println(e.toString());
                }
            }
        });
    }

    public void refreshBoard(ChessGame.Figure[][] newBoard){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(this.physicalCells[i][j].getFigure() != newBoard[i][j]){
                    this.physicalCells[i][j].setFigure(newBoard[i][j]);
                }
            }
        }
    }


}
