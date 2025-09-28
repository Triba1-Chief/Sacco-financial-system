import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.geometry.Insets;

public class RepaymentView {
    public static VBox getRepaymentPane() {
        VBox pane = new VBox(10);
        pane.setPadding(new Insets(20));
        Label title = new Label("Make Loan Repayment");
        TextField memberIdField = new TextField(); memberIdField.setPromptText("Member ID");
        TextField loanIdField = new TextField(); loanIdField.setPromptText("Loan ID");
        TextField amountField = new TextField(); amountField.setPromptText("Repayment Amount");
        Button repayBtn = new Button("Repay");
        Label statusLabel = new Label();
        TextArea allRepayments = new TextArea(); allRepayments.setEditable(false);

        repayBtn.setOnAction(e -> {
            try {
                RepaymentCommands.getInstance().registerRepayment(
                        memberIdField.getText(),
                        loanIdField.getText(),
                        Double.parseDouble(amountField.getText())
                );
                statusLabel.setText("Repayment successful!");
                allRepayments.setText(Repayment.getRepaymentsList().values().toString());
                memberIdField.clear(); loanIdField.clear(); amountField.clear();
            } catch (Exception ex) {
                statusLabel.setText("Error: " + ex.getMessage());
            }
        });

        pane.getChildren().addAll
                (title, memberIdField, loanIdField, amountField, repayBtn, statusLabel, new Label("All Repayments:"),
                        allRepayments);
        return pane;
    }
}
