import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.geometry.Insets;
import java.time.LocalDate;

public class MemberView {
    public static VBox getMemberPane() {
        VBox pane = new VBox(10);
        pane.setPadding(new Insets(20));
        Label titleLabel = new Label("Register a New Member");

        TextField nameField = new TextField();
        nameField.setPromptText("Full Name");

        ComboBox<Gender> genderBox = new ComboBox<>();
        genderBox.getItems().addAll(Gender.values());
        genderBox.setPromptText("Select Gender");

        DatePicker dobPicker = new DatePicker();
        dobPicker.setPromptText("Date of Birth");

        Button registerBtn = new Button("Register Member");
        Label statusLabel = new Label();

        registerBtn.setOnAction(e -> {
            try {
                // Handle null selection
                if (genderBox.getValue() == null) throw new IllegalArgumentException("Please select a gender.");

                MemberCommands.getInstance().registerMember(
                        nameField.getText(),
                        genderBox.getValue(),
                        dobPicker.getValue()
                );
                statusLabel.setText("Member registered successfully!");
                nameField.clear(); dobPicker.setValue(null); genderBox.setValue(null);
            } catch (Exception ex) {
                statusLabel.setText("Error: " + ex.getMessage());
            }
        });

        pane.getChildren().addAll(
                titleLabel, nameField, genderBox, dobPicker, registerBtn, statusLabel
        );
        return pane;
    }
}
