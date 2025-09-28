import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.geometry.Insets;

public class LoanView {
    public static VBox getLoanPane() {
        VBox pane = new VBox(10);
        pane.setPadding(new Insets(20));
        Label title = new Label("Apply for Loan");
        TextField memberIdField = new TextField(); memberIdField.setPromptText("Member ID");
        ComboBox<LoanTypes> loanTypeBox = new ComboBox<>(); loanTypeBox.getItems().addAll(LoanTypes.values());
        TextField amountField = new TextField(); amountField.setPromptText("Amount");
        TextField g1Field = new TextField(); g1Field.setPromptText("Guarantor1 ID");
        TextField g1AmtField = new TextField(); g1AmtField.setPromptText("Guarantee1 Amount");
        TextField g2Field = new TextField(); g2Field.setPromptText("Guarantor2 ID");
        TextField g2AmtField = new TextField(); g2AmtField.setPromptText("Guarantee2 Amount");
        Button applyBtn = new Button("Apply");
        Label statusLabel = new Label();
        TextArea allLoans = new TextArea(); allLoans.setEditable(false);

        applyBtn.setOnAction(e -> {
            try {
                new Loan(
                        memberIdField.getText(),
                        loanTypeBox.getValue(),
                        Double.parseDouble(amountField.getText()),
                        g1Field.getText(),
                        g2Field.getText(),
                        Double.parseDouble(g1AmtField.getText()),
                        Double.parseDouble(g2AmtField.getText())
                );
                statusLabel.setText("Loan application successful!");
                allLoans.setText(Loan.getLoanList().values().toString());
                memberIdField.clear(); amountField.clear(); g1Field.clear(); g1AmtField.clear(); g2Field.clear(); g2AmtField.clear();
                loanTypeBox.setValue(null);
            } catch (Exception ex) {
                statusLabel.setText("Error: " + ex.getMessage());
            }
        });

        pane.getChildren().addAll(title, memberIdField, loanTypeBox, amountField, g1Field, g1AmtField, g2Field, g2AmtField, applyBtn, statusLabel, new Label("All Loans:"), allLoans);
        return pane;
    }
}
