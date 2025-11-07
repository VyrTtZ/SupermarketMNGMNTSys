package com.example.supermarketmngmntsys;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {

    private Stage primaryStage;
    private Supermarket currentSupermarket;

    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        showDashboard(); // start here
        primaryStage.setTitle("Supermarket Management System");
        primaryStage.show();
    }

    public void showDashboard() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/supermarketmngmntsys/dashboard-view.fxml"));
        Scene scene = new Scene(loader.load());
        DashboardController controller = loader.getController();
        controller.setLauncher(this);
        primaryStage.setScene(scene);
    }

    public void showMarket(Supermarket supermarket) throws Exception {
        this.currentSupermarket = supermarket;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/supermarketmngmntsys/market-view.fxml"));
        Scene scene = new Scene(loader.load());
        MarketController controller = loader.getController();
        controller.setLauncher(this);
        controller.setSupermarket(supermarket);
        primaryStage.setScene(scene);
    }
    public Supermarket getCurrentSupermarket() {
        return currentSupermarket;
    }




    public static void main(String[] args) {
        launch(args);
    }
}
