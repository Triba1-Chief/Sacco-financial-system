import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.geometry.Insets;

public class ContributionView {
    public static VBox getContributionPane() {
        VBox pane = new VBox(10);
        pane.setPadding(new Insets(20));
        Label title = new Label("Make a Contribution");
        TextField memberIdField = new TextField(); memberIdField.setPromptText("Member ID");
        TextField amountField = new TextField(); amountField.setPromptText("Amount");
        Button contributeBtn = new Button("Contribute");
        Label statusLabel = new Label();
        TextArea allContributions = new TextArea(); allContributions.setEditable(false);

        contributeBtn.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                new Contribution(memberIdField.getText(), amount);
                statusLabel.setText("Contribution successful!");
                allContributions.setText(ContributionCommands.getInstance().displayContribution() + "");
                memberIdField.clear(); amountField.clear();
            } catch (Exception ex) {
                statusLabel.setText("Error: " + ex.getMessage());
            }
        });

        pane.getChildren().addAll(title, memberIdField, amountField, contributeBtn, statusLabel, new Label("All Contributions:"), allContributions);
        return pane;
    }
}
