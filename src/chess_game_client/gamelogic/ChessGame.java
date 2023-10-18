package chess_game_client.gamelogic;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class ChessGame{

    public enum Figure{
        Cell("cell"),
        WhitePawn("white"), BlackPawn("black"), 
        WhiteKnight("white"), BlackKnight("black"), 
        WhiteBishop("white"), BlackBishop("black"),
        WhiteRook("white"), BlackRook("black"),
        WhiteQueen("white"), BlackQueen("black"),
        WhiteKing("white"), BlackKing("black");


        public String color;
        Figure(String color){
            this.color = color;
        }
    }

    public static class Cell{
        public int x, y; //x stands for raw, y stands for column
        public Cell(int x, int y) throws Exception{
            if(x > 7 || x < 0 || y > 7 || y < 0) throw new Exception("Coordinates out of bounds: " + x + " " + y);

            this.x = x;
            this.y = y;
        }

        public Cell(Cell cell){
            this.x = cell.x;     
            this.y = cell.y;    
        }

        public String toString(){
            return this.x + " " + this.y;
        }
    }

    public static class Move{
        public Figure movingFigure;
        public Figure attackedFigure;
        public Cell firstCell;
        public Cell secondCell;
        public Figure transormationFigure;

        public Move(Figure movingFigure, Figure attackedFigure, Cell firstCell, Cell secondCell, Figure transormationFigure){
            this.movingFigure = movingFigure;
            this.attackedFigure = attackedFigure;
            this.firstCell = new Cell(firstCell);
            this.secondCell = new Cell(secondCell);
            this.transormationFigure = transormationFigure;
        }
    }

    public Figure[][] board = new Figure[8][8];
    public String status = "ongoing";   //ongoing, calculating, whitewon, blackwon, draw 
    public String queue = "white";     //white / black
    public ArrayList<Move> moves = new ArrayList<Move>();

    boolean hasFigureMoved(ArrayList<Move> moves, int x, int y){

        for(int i = 0; i < moves.size(); i++){
            Cell moveCell = moves.get(i).firstCell;
            if(moveCell.x == x && moveCell.y == y) return true;
            
        }
        return false;
    }


    //setting up new game
    public ChessGame(){
        setStartingPostition();
    }

    public ChessGame(ArrayList<Move> moves) throws Exception{
        setStartingPostition();
        for(int i = 0; i < moves.size(); i++){
            Move move = moves.get(i);
            boolean moveResult = this.makeMove(move.firstCell, move.secondCell, move.transormationFigure);
            if(!moveResult) throw new Exception();
        }
    }

    //reading record and creating game by it 
    public ChessGame(String record){

    }

    void setStartingPostition(){
        //by default board is full of cells:
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                board[i][j] = Figure.Cell;
            }
        }

        //setting the pawns on the 2 raw and 7 raw
        for(int i = 0; i < 8; i++){
            board[1][i] = Figure.WhitePawn;
            board[6][i] = Figure.BlackPawn;
        }

        //rooks
        board[0][0] = Figure.WhiteRook;
        board[0][7] = Figure.WhiteRook;
        board[7][0] = Figure.BlackRook;
        board[7][7] = Figure.BlackRook;

        //knights
        board[0][1] = Figure.WhiteKnight;
        board[0][6] = Figure.WhiteKnight;
        board[7][1] = Figure.BlackKnight;
        board[7][6] = Figure.BlackKnight;

        //bishops
        board[0][2] = Figure.WhiteBishop;
        board[0][5] = Figure.WhiteBishop;
        board[7][2] = Figure.BlackBishop;
        board[7][5] = Figure.BlackBishop;

        //queens
        board[0][3] = Figure.WhiteQueen;
        board[7][3] = Figure.BlackQueen;

        //kings
        board[0][4] = Figure.WhiteKing;
        board[7][4] = Figure.BlackKing;

    }


    boolean isCellAttacked(Cell cell){

        //is attacked by pawn:
        if(this.queue.equals("black") && cell.x > 0){
            if(cell.y > 0 && this.board[cell.x - 1][cell.y - 1] == Figure.WhitePawn) return true;
            if(cell.y < 7 && this.board[cell.x - 1][cell.y + 1] == Figure.WhitePawn) return true;
        }else if(this.queue.equals("white") && cell.x < 7){
            if(cell.y > 0 && this.board[cell.x + 1][cell.y - 1] == Figure.BlackPawn) return true;
            if(cell.y < 7 && this.board[cell.x + 1][cell.y + 1] == Figure.BlackPawn) return true;
        }

        //is atttacked on raw?
        for(int i = cell.y + 1; i < 8; i++){
            if(this.board[cell.x][i] == Figure.Cell) continue;
            else if(
                this.queue.equals("black") && 
                    (this.board[cell.x][i] == Figure.WhiteRook || this.board[cell.x][i] == Figure.WhiteQueen) ||
                this.queue.equals("white") && 
                    (this.board[cell.x][i] == Figure.BlackRook || this.board[cell.x][i] == Figure.BlackQueen)
            ){
                return true;
            }else break;
        }

        // System.out.println("label 1.1");

        for(int i = cell.y - 1; i >= 0; i--){
            if(this.board[cell.x][i] == Figure.Cell) continue;
            else if(
                this.queue.equals("black") && 
                    (this.board[cell.x][i] == Figure.WhiteRook || this.board[cell.x][i] == Figure.WhiteQueen) ||
                this.queue.equals("white") && 
                    (this.board[cell.x][i] == Figure.BlackRook || this.board[cell.x][i] == Figure.BlackQueen)
            ){
                return true;
            }else break;
        }
        // System.out.println("label 1.2");
        //is attacked on column?
        for(int i = cell.x + 1; i < 8; i++){
            if(this.board[i][cell.y] == Figure.Cell) continue;
            else if(
                this.queue.equals("black") && 
                    (this.board[i][cell.y] == Figure.WhiteRook || this.board[i][cell.y] == Figure.WhiteQueen) ||
                this.queue.equals("white") && 
                    (this.board[i][cell.y] == Figure.BlackRook || this.board[i][cell.y] == Figure.BlackQueen)
            ){
                return true;
            }else break;
        }
        // System.out.println("label 1.3");
        for(int i = cell.x - 1; i >= 0; i--){
            if(this.board[i][cell.y] == Figure.Cell) continue;
            else if(
                this.queue.equals("black") && 
                    (this.board[i][cell.y] == Figure.WhiteRook || this.board[i][cell.y] == Figure.WhiteQueen) ||
                this.queue.equals("white") && 
                    (this.board[i][cell.y] == Figure.BlackRook || this.board[i][cell.y] == Figure.BlackQueen)
            ){
                return true;
            }else break;
        }
        // System.out.println("label 1.4");
        //is attacked on diagonal?
        //top right
        int i = 1;
        while(cell.x + i < 8 && cell.y + i < 8){
            if(this.board[cell.x + i][cell.y + i] == Figure.Cell){
                i++;
                continue;
            }else if(
                this.queue.equals("black") &&
                (this.board[cell.x + i][cell.y + i] == Figure.WhiteBishop || this.board[cell.x + i][cell.y + i] == Figure.WhiteQueen) ||
                this.queue.equals("white") && 
                (this.board[cell.x + i][cell.y + i] == Figure.BlackBishop || this.board[cell.x + i][cell.y + i] == Figure.BlackQueen)
            ){
                return true;
            }else break;
        }
        // System.out.println("label 1.5");

        //top left
        i = 1;
        while(cell.x + i < 8 && cell.y - i >= 0){
            if(this.board[cell.x + i][cell.y - i] == Figure.Cell){ 
                i++;
                continue;
            }else if(
                this.queue.equals("black") && 
                (this.board[cell.x + i][cell.y - i] == Figure.WhiteBishop || this.board[cell.x + i][cell.y - i] == Figure.WhiteQueen) ||
                this.queue.equals("white") && 
                (this.board[cell.x + i][cell.y - i] == Figure.BlackBishop || this.board[cell.x + i][cell.y - i] == Figure.BlackQueen)
            ){
                return true;
            }else break;
        }
        // System.out.println("label 1.6");
        //bottom right
        i = 1;
        while(cell.x - i >= 0 && cell.y + i < 8){
            if(this.board[cell.x - i][cell.y + i] == Figure.Cell){
                i++;
                continue;
            }else if(
                this.queue.equals("black") && 
                (this.board[cell.x - i][cell.y + i] == Figure.WhiteBishop || this.board[cell.x - i][cell.y + i] == Figure.WhiteQueen) ||
                this.queue.equals("white") && 
                (this.board[cell.x - i][cell.y + i] == Figure.BlackBishop || this.board[cell.x - i][cell.y + i] == Figure.BlackQueen)
            ){
                return true;
            }else break;
        }
        // System.out.println("label 1.7");
        //bottom left
        i = 1;
        while(cell.x - i >= 0 && cell.y - i >= 0){
            if(this.board[cell.x - i][cell.y - i] == Figure.Cell){
                i++;
                continue;
            }else if(
                this.queue.equals("black") && 
                (this.board[cell.x - i][cell.y - i] == Figure.WhiteBishop || this.board[cell.x - i][cell.y - i] == Figure.WhiteQueen) ||
                this.queue.equals("white") && 
                (this.board[cell.x - i][cell.y - i] == Figure.BlackBishop || this.board[cell.x - i][cell.y - i] == Figure.BlackQueen)
            ){
                return true;
            }else break;
        }
        // System.out.println("label 1.8");

        //is attacked by knight?
        int delta[][] = {
            {1,2}, {1,-2}, {-1, 2}, {-1,-2},
            {2,1}, {2,-1}, {-2, 1}, {-2,-1}
        };

        for(i = 0; i < 8; i++){
            int x = cell.x + delta[i][0], y = cell.y + delta[i][1];
            if(x < 0 || x > 7 || y < 0 || y > 7) continue;
            // System.out.println(x + " " + y + " " + this.board[x][y]);

            if( this.queue.equals("white") && this.board[x][y] == Figure.BlackKnight ||
                this.queue.equals("black") && this.board[x][y] == Figure.WhiteKnight
            ){
                return true;
            }  
        }

        //if not attacked by anything
        return false;
    }
    
    Cell findKing(String color){

        try{
            if(color.equals("white")){
                for(int i = 0; i < 8; i++){
                    for(int j = 0; j < 8; j++){
                        if(this.board[i][j] == Figure.WhiteKing) return new Cell(i,j);
                    }
                }
            }else if(color.equals("black")){
                for(int i = 7; i >= 0; i--){
                    for(int j = 0; j < 8; j++){
                        if(this.board[i][j] == Figure.BlackKing) return new Cell(i,j);
                    }
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        return null;
    }

    //returns boolean, whether move is impossible or not
    public boolean makeMove(Cell firstCell, Cell secondCell, Figure transformationFigure){
        //checking if move is possible

        //is it by rules of figures movement?   
        // System.out.println("label 1");

        Figure firstFigure = board[firstCell.x][firstCell.y];
        Figure secondFigure = board[secondCell.x][secondCell.y];
        Move lastMove = null;
        if(this.moves.size() > 0) lastMove = this.moves.get(this.moves.size() - 1) ;

        // System.out.println("label2");

        if(firstFigure == Figure.Cell) return false;
        if( secondFigure.color.equals(this.queue)) return false;
        if( !firstFigure.color.equals(this.queue)) return false;

        //if it is castling
        if(firstFigure == Figure.WhiteKing || firstFigure == Figure.BlackKing){
            //short catsling
            if(secondCell.x == 0 && secondCell.y == 6 && firstCell.x == 0 && firstCell.y == 4 ||
                secondCell.x == 7 && secondCell.y == 6 && firstCell.x == 7 && firstCell.y == 4
            ){
                System.out.println("Trying short castling");
                if( firstFigure == Figure.WhiteKing && (this.hasFigureMoved(this.moves, 0, 4) || this.hasFigureMoved(this.moves, 0, 7)) || 
                    secondFigure == Figure.BlackKing && (this.hasFigureMoved(this.moves, 7, 4) || this.hasFigureMoved(this.moves, 7, 7))) return false;

                int x = firstFigure == Figure.WhiteKing ? 0 : 7;
                try{
                    if(this.isCellAttacked(new Cell(x, 4))) return false;
                }catch(Exception e){

                }
                
                for(int i = 1; i < 3; i++){
                    try{
                        if(this.isCellAttacked(new Cell(x, 4 + i))) return false;
                        if(this.board[x][4 + i] != Figure.Cell) return false;
                    }catch(Exception e){
                        
                    }
                }

                //making a move:
                this.board[x][6] = this.queue.equals("white") ? Figure.WhiteKing : Figure.BlackKing;
                this.board[x][5] = this.queue.equals("white") ? Figure.WhiteRook : Figure.BlackRook;
                this.board[x][4] = Figure.Cell;
                this.board[x][7] = Figure.Cell;

                this.queue = this.queue.equals("white") ? "black" : "white";
                this.moves.add(new Move(firstFigure, secondFigure, firstCell, secondCell, null));
                if(this.status.equals("calculating")) return true;

                isFinished();
                return true;


            }else if(secondCell.x == 0 && secondCell.y == 2 && firstCell.x == 0 && firstCell.y == 4 ||
                secondCell.x == 7 && secondCell.y == 2 && firstCell.x == 7 && firstCell.y == 4
            ){ //long catling
                System.out.println("Trying long castling");
                System.out.println(this.hasFigureMoved(moves, 0, 4));
                if( firstFigure == Figure.WhiteKing && (this.hasFigureMoved(this.moves, 0, 0) || this.hasFigureMoved(this.moves, 0, 4)) || 
                    (secondFigure == Figure.BlackKing) && (this.hasFigureMoved(this.moves, 7, 0) || this.hasFigureMoved(this.moves, 7, 4))) return false;
                System.out.println("l1");

                int x = firstFigure == Figure.WhiteKing ? 0 : 7;
                try{
                    if(this.isCellAttacked(new Cell(x, 4))) return false;
                }catch(Exception e){

                }
                System.out.println("l2");

                for(int i = 1; i < 4; i++){
                    try{
                        if(this.isCellAttacked(new Cell(x, 4 - i))) return false;
                        if(this.board[x][4 - i] != Figure.Cell) return false;
                    }catch(Exception e){
                        
                    }
                }

                System.out.println("l3");

                //making a move:
                this.board[x][2] = this.queue.equals("white") ? Figure.WhiteKing : Figure.BlackKing;
                this.board[x][3] = this.queue.equals("white") ? Figure.WhiteRook : Figure.BlackRook;
                this.board[x][4] = Figure.Cell;
                this.board[x][0] = Figure.Cell;

                this.queue = this.queue.equals("white") ? "black" : "white";
                this.moves.add(new Move(firstFigure, secondFigure, firstCell, secondCell, null));
                if(this.status.equals("calculating")) return true;

                isFinished();
                return true;
            }
        }

        //if pawn and enpassant
        if( (firstFigure == Figure.WhitePawn && firstCell.x == 4 && lastMove != null && lastMove.movingFigure == Figure.BlackPawn &&
            lastMove.firstCell.x == 6 && lastMove.secondCell.x == 4 && Math.abs(lastMove.secondCell.y - firstCell.y) == 1 &&
            (secondCell.x - firstCell.x == 1 && secondCell.y == lastMove.secondCell.y)) ||

            (firstFigure == Figure.BlackPawn && firstCell.x == 3 && lastMove != null &&  lastMove.movingFigure == Figure.WhitePawn &&
            lastMove.firstCell.x == 1 && lastMove.secondCell.x == 3 && Math.abs(lastMove.secondCell.y - firstCell.y) == 1 &&
            (secondCell.x - firstCell.x == -1 && secondCell.y == lastMove.secondCell.y)
            )
        ){

            this.board[lastMove.secondCell.x][lastMove.secondCell.y] = Figure.Cell;
            this.board[firstCell.x][firstCell.y] = Figure.Cell;
            this.board[secondCell.x][secondCell.y] = this.queue.equals("white") ? Figure.WhitePawn : Figure.BlackPawn;

            if(this.isCellAttacked(this.findKing(this.queue))){
                this.board[lastMove.secondCell.x][lastMove.secondCell.y] = this.queue.equals("white") ? Figure.BlackPawn : Figure.WhitePawn;
                this.board[firstCell.x][firstCell.y] = this.queue.equals("white") ? Figure.WhitePawn : Figure.BlackPawn;
                this.board[secondCell.x][secondCell.y] = Figure.Cell;
                return false;
            }else{
                this.queue = this.queue.equals("white") ? "black" : "white";
                this.moves.add(new Move(firstFigure, secondFigure, firstCell, secondCell, null));
                if(this.status.equals("calculating")) return true;

                isFinished();
                return true;
            }
        }


        //if pawn and goes to the last level
        if( (firstCell.x == 6 && secondCell.x == 7 && firstFigure == Figure.WhitePawn  ||
            firstCell.x == 1 && secondCell.x == 0 && firstFigure == Figure.BlackPawn ) && 

            (secondCell.y == firstCell.y && secondFigure == Figure.Cell ||
            Math.abs(secondCell.y - firstCell.y) == 1 && 
            secondFigure.color.equals(firstFigure.color.equals("white") ? "black" : "white"))
        ){
            System.out.println("It is actually a promotion");
            if(transformationFigure == null) 
                transformationFigure = this.queue.equals("white") ? Figure.WhiteQueen : Figure.BlackQueen;

            if(!transformationFigure.color.equals(this.queue)) return false;
            if(transformationFigure == Figure.WhiteKing || transformationFigure == Figure.BlackKing ||
                transformationFigure == Figure.WhitePawn || transformationFigure == Figure.BlackPawn) return false;

            this.board[firstCell.x][firstCell.y] = Figure.Cell;
            this.board[secondCell.x][secondCell.y] = transformationFigure;
            
            if(this.isCellAttacked(this.findKing(this.queue))){
                this.board[lastMove.secondCell.x][lastMove.secondCell.y] = this.queue.equals("white") ? Figure.BlackPawn : Figure.WhitePawn;
                this.board[firstCell.x][firstCell.y] = this.queue.equals("white") ? Figure.WhitePawn : Figure.BlackPawn;
                this.board[secondCell.x][secondCell.y] = Figure.Cell;
                return false;
            }else{
                this.queue = this.queue.equals("white") ? "black" : "white";
                this.moves.add(new Move(firstFigure, secondFigure, firstCell, secondCell, transformationFigure));
                if(this.status.equals("calculating")) return true;

                
                isFinished();
                return true;
            }
        }   

        //if pawn and none of the situations above
        if(firstFigure == Figure.WhitePawn && this.queue == "white"){

            if( !(firstCell.y == secondCell.y && secondCell.x - firstCell.x == 1 && this.board[secondCell.x][secondCell.y] == Figure.Cell ||  //move forward on one
                firstCell.x == 1 && secondCell.x == 3 && firstCell.y == secondCell.y && this.board[secondCell.x][secondCell.y] == Figure.Cell && this.board[secondCell.x - 1][secondCell.y] == Figure.Cell || //move forward on 2 from initial position
                secondFigure != Figure.Cell && !secondFigure.color.equals(firstFigure.color) && Math.abs(firstCell.y - secondCell.y) == 1 && secondCell.x - firstCell.x == 1  //beat enemy piece
            )) return false;
        }

        if(firstFigure == Figure.BlackPawn && this.queue == "black"){
            if( ! (firstCell.y == secondCell.y && secondCell.x - firstCell.x == -1 && this.board[secondCell.x][secondCell.y] == Figure.Cell ||  //move forward on one
                firstCell.x == 6 && secondCell.x == 4 && firstCell.y == secondCell.y && this.board[secondCell.x][secondCell.y] == Figure.Cell && this.board[secondCell.x + 1][secondCell.y] == Figure.Cell || //move forward on 2 from initial position
                secondFigure != Figure.Cell && !secondFigure.color.equals(firstFigure.color) && Math.abs(firstCell.y - secondCell.y) == 1 && secondCell.x - firstCell.x == -1  //beat enemy piece
            )) return false;
        }

        //if it is queen
        if(firstFigure == Figure.WhiteQueen || firstFigure == Figure.BlackQueen){

            if(secondCell.x - firstCell.x == 0 && secondCell.y - firstCell.y != 0 || 
                secondCell.y - firstCell.y == 0 && secondCell.x - firstCell.x != 0
            ){

                int i = 1;

                int dx, dy;
                dx = secondCell.x - firstCell.x;
                dy = secondCell.y - firstCell.y;
                if(dx != 0) dx /= Math.abs(dx);
                else dy /= Math.abs(dy);

                while(i < Math.max(Math.abs(secondCell.x - firstCell.x), Math.abs(secondCell.y - firstCell.y))){
                    Cell currentCell;
                    try{
                        currentCell = new Cell(firstCell.x + i * dx, firstCell.y +  i* dy );               
                    }catch(Exception e){
                        System.out.println(e.getMessage());
                        return false;
                    }
                    if(this.board[currentCell.x][currentCell.y] != Figure.Cell) return false;                 
                    i++;
                }
            }else if(Math.abs(secondCell.x - firstCell.x) == Math.abs(secondCell.y - firstCell.y) &&
                (secondCell.x - firstCell.x) != 0
            ){
                if( Math.abs(secondCell.x - firstCell.x) != Math.abs(secondCell.y - firstCell.y) ||
                    secondCell.x - firstCell.x == 0 ) return false; 

                int i = 1;
                int 
                    dx = (secondCell.x - firstCell.x) / Math.abs(secondCell.x - firstCell.x),
                    dy = (secondCell.y - firstCell.y) / Math.abs(secondCell.y - firstCell.y);

                while(i < Math.abs(secondCell.x - firstCell.x)){
                    Cell currentCell;
                    try{
                        currentCell = new Cell(firstCell.x + i * dx, firstCell.y + i * dy );        
                    }catch(Exception e){
                        System.out.println(e.getMessage());
                        return false;
                    }
                    if(this.board[currentCell.x][currentCell.y] != Figure.Cell) return false;
                    i++;
                }
            }else return false;

        }

        //if it is rook
        if(firstFigure == Figure.BlackRook || firstFigure == Figure.WhiteRook){    
           
            if( !(secondCell.x - firstCell.x == 0 && secondCell.y - firstCell.y != 0 ||
            secondCell.y - firstCell.y == 0 && secondCell.x - firstCell.x != 0) )return false;

            int i = 1;

            int dx, dy;
            dx = secondCell.x - firstCell.x;
            dy = secondCell.y - firstCell.y;
            if(dx != 0) dx /= Math.abs(dx);
            else dy /= Math.abs(dy);

            while(i < Math.max(Math.abs(secondCell.x - firstCell.x), Math.abs(secondCell.y - firstCell.y))){
                Cell currentCell;
                try{
                    currentCell = new Cell(firstCell.x + i * dx, firstCell.y +  i* dy );               
                }catch(Exception e){
                    System.out.println(e.getMessage());
                    return false;
                }
                if(this.board[currentCell.x][currentCell.y] != Figure.Cell) return false;                 
                i++;
            }
        }

        //if it is bishop
        if(firstFigure == Figure.BlackBishop || firstFigure == Figure.WhiteBishop){    

            if( Math.abs(secondCell.x - firstCell.x) != Math.abs(secondCell.y - firstCell.y) ||
                secondCell.x - firstCell.x == 0 ) return false; 

            int i = 1;
            int 
                dx = (secondCell.x - firstCell.x) / Math.abs(secondCell.x - firstCell.x),
                dy = (secondCell.y - firstCell.y) / Math.abs(secondCell.y - firstCell.y);

            while(i < Math.abs(secondCell.x - firstCell.x)){
                Cell currentCell;
                try{
                    currentCell = new Cell(firstCell.x + i * dx, firstCell.y + i * dy );        
                }catch(Exception e){
                    System.out.println(e.getMessage());
                    return false;
                }
                if(this.board[currentCell.x][currentCell.y] != Figure.Cell) return false;
                i++;
            }
        }

        //if it is knight
        if(firstFigure == Figure.BlackKnight || firstFigure == Figure.WhiteKnight){


            int[][] delta = {
                {1,2}, {1,-2}, {-1,2}, {-1,-2},
                {2,1}, {2,-1}, {-2, 1}, {-2,-1}
            };

            boolean isKnightMove = false;
            for(int i = 0; i < 8; i++){
                if(secondCell.x - firstCell.x == delta[i][0] &&
                    secondCell.y - firstCell.y == delta[i][1]
                ){ 
                    isKnightMove = true;
                    break;
                }
            }
            if( !isKnightMove ) return false;
        }

        //if it is king and not castling
        if(firstFigure == Figure.BlackKing || firstFigure == Figure.WhiteKing){
            if(Math.abs(secondCell.y - firstCell.y) > 1) return false;
            if(Math.abs(secondCell.x - firstCell.x) > 1) return false;
            // System.out.println("label 3");
        }

        //make a move
        this.board[secondCell.x][secondCell.y] = firstFigure;
        this.board[firstCell.x][firstCell.y] = Figure.Cell;
        // System.out.println("label4");
        //is there is a check?
        if(!this.status.equals("calculating")){
            Cell kingCell = this.findKing(this.queue);
            boolean attack = this.isCellAttacked(kingCell);
            System.out.println("Is " + this.queue + " king on cell " + kingCell + " attacked: " + attack);
        }
        System.out.println("label7");

        if(this.isCellAttacked(this.findKing(this.queue))){

            System.out.println("label5");
            //cancelling
            this.board[secondCell.x][secondCell.y] = secondFigure;
            this.board[firstCell.x][firstCell.y] = firstFigure;
            return false;
        }else{
            System.out.println("label6");
            this.moves.add(new Move(firstFigure, secondFigure, firstCell, secondCell, null));
            if(this.queue.equals("white")){
                this.queue = "black";
            }else this.queue = "white";

            if(this.status.equals("calculating")) return true;

            System.out.println("First cell:" + firstCell.x + " " + firstCell.y + " " + this.board[firstCell.x][firstCell.y]);
            System.out.println("Second cell: " + secondCell.x + " " + secondCell.y + " " + this.board[secondCell.x][secondCell.y]);
            System.out.println(this.queue);

            isFinished();

            return true;
        }

    }

    public void cancelMove(){

        if(this.moves.size() == 0) return;

        Move lastMove = this.moves.get(this.moves.size() - 1);
        this.moves.remove(this.moves.size() - 1);
        this.queue = this.queue.equals("white") ? "black" : "white";


        //cancelling for enpassant:
        if( (lastMove.movingFigure == Figure.WhitePawn || lastMove.movingFigure == Figure.BlackPawn) && lastMove.attackedFigure == Figure.Cell &&
            lastMove.secondCell.y != lastMove.firstCell.y
        ){
            // System.out.println(lastMove.firstCell.x + " " + lastMove.firstCell.y + " : " + lastMove.movingFigure + " " + lastMove.attackedFigure);
            this.board[lastMove.secondCell.x][lastMove.secondCell.y] = Figure.Cell;
            this.board[lastMove.firstCell.x][lastMove.firstCell.y] = lastMove.movingFigure;
            this.board[lastMove.firstCell.x][lastMove.secondCell.y] = lastMove.movingFigure.color.equals("white") ? Figure.BlackPawn : Figure.WhitePawn;
            
            return;
        }else if( (lastMove.movingFigure == Figure.WhiteKing || lastMove.movingFigure == Figure.BlackKing) &&
            Math.abs(lastMove.secondCell.x - lastMove.firstCell.x) > 1
        ){ //cancelling for castling
            int y = lastMove.movingFigure.color.equals("white") ? 0 : 7;

            this.board[4][y] = lastMove.movingFigure;
            this.board[lastMove.secondCell.x][y] = Figure.Cell;
            
            //if short
            if(lastMove.secondCell.x == 6){
                this.board[5][y] = Figure.Cell;
                this.board[7][y] = lastMove.movingFigure.color.equals("white") ? Figure.WhiteRook : Figure.BlackRook;
            }else if(lastMove.secondCell.x == 2){   //if long
                this.board[3][y] = Figure.Cell;
                this.board[0][y] = lastMove.movingFigure.color.equals("white") ? Figure.WhiteRook : Figure.BlackRook;
            }
            return;
        }

        //for all other types of moves
        // System.out.println(lastMove.movingFigure + " " + lastMove.attackedFigure);
        this.board[lastMove.secondCell.x][lastMove.secondCell.y] = lastMove.attackedFigure;
        this.board[lastMove.firstCell.x][lastMove.firstCell.y] = lastMove.movingFigure;
    }

    public boolean isFinished(){

        this.status = "calculating";
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                
                Figure currentFigure = this.board[i][j];
                if(currentFigure == Figure.Cell) continue;
                if(!currentFigure.color.equals(this.queue)) continue;
                // System.out.println(currentFigure);

                for(int x = 0; x < 8; x++){
                    for(int y = 0; y < 8; y++){
                        try{
                            if(makeMove(new Cell(i,j), new Cell(x,y), null)){
                                this.cancelMove();
                                this.status = "ongoing";
                                return false;
                            }
                        }catch(Exception e){};
                        
                    }
                }                
            }
        }


        if(this.isCellAttacked(this.findKing("white"))) this.status = "blackwon";
        else if(this.isCellAttacked(this.findKing("black"))) this.status = "whitewon";
        else this.status = "draw";

        return true;
    }
}