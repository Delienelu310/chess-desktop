package chess_game_client.client_interface;
import javafx.stage.Stage;
import chess_game_client.client_interface.main_components.Feed;
import chess_game_client.client_interface.main_components.Header;
import chess_game_client.client_interface.main_components.Sidebar;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainClient extends Stage{

    public ChessApp app;

    MainClient(ChessApp app){
        this.app = app;

        Sidebar sidebar = new Sidebar(this);
        Header header = new Header(this);
        Feed feed = new Feed(this);

        VBox vbox = new VBox(header, feed);
        HBox hbox = new HBox(sidebar, vbox);

        Scene scene = new Scene(hbox, 1000, 500);
        this.setScene(scene);

    }
}
