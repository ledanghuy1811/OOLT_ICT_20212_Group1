package graphics;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sowme
 */
public class Panel1Controller implements Initializable {

    public static boolean directed = false, weighted = false;

    @FXML
    public Button panel1Next, panel2Back;
    @FXML
    private RadioButton dButton, udButton, wButton, uwButton;
    @FXML
    private AnchorPane panel1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        dButton.setSelected(directed);
        wButton.setSelected(weighted);
        udButton.setSelected(!directed);
        uwButton.setSelected(!weighted);

        // Thread for button control
        panel1Next.setDisable(true);
        Thread t = new Thread() {
            @Override
            public void run() {
                while (true) {
                    System.out.println(directed + " " + weighted);
                    System.out.println("In thread " + directed);
                    panel1Next.setDisable(false);
                    panel1Next.setStyle("-fx-background-color : #487eb0;");
                    break;
                }
                System.out.println("Exiting thread");
            }
        };
        t.start();

        // Button Action listeners
        dButton.setOnAction(e -> {
            directed = true;
            System.out.println("dButton");
        });
        udButton.setOnAction(e -> {
            directed = false;
            System.out.println("udButton");
        });
        wButton.setOnAction(e -> {
            weighted = true;
            System.out.println("wButton");
        });
        uwButton.setOnAction(e -> {
            weighted = false;
            System.out.println("uwButton");
        });
        panel1Next.setOnAction(e -> {
            FadeOut();
        });

    }

    void FadeOut() {
        FadeTransition ft = new FadeTransition();
        ft.setDuration(Duration.millis(1000));
        ft.setNode(panel1);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.setOnFinished(e -> {
            loadNextScene();
        });
        ft.play();
        System.out.println("Here");
    }

    void loadNextScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Canvas.fxml"));
            Parent root = loader.load();
            Scene newScene = new Scene(root);
            newScene.getStylesheets().add(getClass().getResource("Styling.css").toExternalForm());
            Main.primaryStage.setScene(newScene);
        } catch (IOException ex) {
            Logger.getLogger(Panel1Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
