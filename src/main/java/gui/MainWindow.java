package gui;

import dar.Dar;
import dar.Ui;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {

    private static final Ui ui = new Ui();

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Dar dar;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/dar.png"));
    private Image darImage = new Image(this.getClass().getResourceAsStream("/images/boy.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Duke instance */
    public void setDar(Dar d) {
        dar = d;

        // Show the greeting message when the chatbot starts
        String greeting = ui.showGreetingMessage();
        dialogContainer.getChildren().add(
                DialogBox.getDukeDialog(greeting, darImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = dar.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, darImage)
        );
        userInput.clear();
    }
}
