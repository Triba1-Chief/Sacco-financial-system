import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class SaccoApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("SACCO Management System");

        TabPane tabPane = new TabPane();

        // Example tabs for core modules:
        tabPane.getTabs().add(new Tab("Members", MemberView.getMemberPane()));
        tabPane.getTabs().add(new Tab("Contributions", ContributionView.getContributionPane()));
        tabPane.getTabs().add(new Tab("Loans", LoanView.getLoanPane()));
        tabPane.getTabs().add(new Tab("Repayments", RepaymentView.getRepaymentPane()));
        tabPane.getTabs().add(new Tab("Savings & Dividends", SavingsView.getSavingsPane()));

        Scene scene = new Scene(tabPane, 1000, 600);
        primaryStage.setScene(scene);
        // This line will launch the window in fullscreen mode
        primaryStage.setFullScreen(true);
        primaryStage.show();
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

    }

    public static void main(String[] args) {
        launch(args);
    }
}
