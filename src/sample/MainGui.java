package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 主函数 程序入口
 * @author wuyiwen
 */
public class MainGui extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setResizable(true);
        Parent root = FXMLLoader.load(getClass().getResource("MainGui.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
