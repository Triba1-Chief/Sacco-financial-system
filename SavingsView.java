import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.geometry.Insets;

public class SavingsView {
    public static VBox getSavingsPane() {
        VBox pane = new VBox(10);
        pane.setPadding(new Insets(20));
        Label title = new Label("Group Savings & Dividend Report");
        TextField yearField = new TextField(); yearField.setPromptText("Enter Year");
        Button reportBtn = new Button("View Report");
        TextArea savingsReport = new TextArea(); savingsReport.setEditable(false);

        reportBtn.setOnAction(e -> {
            try {
                int year = Integer.parseInt(yearField.getText());
                savingsReport.clear();
                // Prints to console by default; can redirect here if desired
                SavingsCommands.yearEndReport(year);
                savingsReport.setText("Report for year " + year + ":\nCheck console output for details.");
                yearField.clear();
            } catch (Exception ex) {
                savingsReport.setText("Error: " + ex.getMessage());
            }
        });

        pane.getChildren().addAll(title, yearField, reportBtn, savingsReport);
        return pane;
    }
}
