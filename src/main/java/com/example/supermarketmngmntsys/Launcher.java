package com.example.supermarketmngmntsys;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Launcher extends Application {

    private static Stage primaryStage;
    private static Supermarket currentSupermarket;

    public static DashboardController dController = new DashboardController();
    public static MarketController mController = new MarketController();

    private static Scene dashboardScene;
    private static Scene marketScene;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        setCurrentLoader(0);
        primaryStage.setTitle("Supermarket Management System");
        primaryStage.show();
    }

    public static void setCurrentLoader(int loader) {
        if (primaryStage == null) return;

        try {
            switch (loader) {
                case 0 -> {
                    if (dashboardScene == null) {
                        FXMLLoader loaderDash = new FXMLLoader(
                                Launcher.class.getResource("/com/example/supermarketmngmntsys/dashboard-view.fxml"));
                        Parent root = loaderDash.load();
                        dashboardScene = new Scene(root);
                        dController = loaderDash.getController();
                    }
                    currentSupermarket = dController.getSelectedSupermarket();
                    primaryStage.setScene(dashboardScene);

                }
                case 1 -> {
                    if (marketScene == null) {
                        FXMLLoader loaderMarket = new FXMLLoader(
                                Launcher.class.getResource("/com/example/supermarketmngmntsys/market-view.fxml"));
                        Parent root = loaderMarket.load();
                        marketScene = new Scene(root);
                        mController = loaderMarket.getController();
                    }
                    mController.setSupermarket(currentSupermarket);
                    primaryStage.setScene(marketScene);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Supermarket getCurrentSupermarket() {
        return currentSupermarket;
    }

    public static void setCurrentSupermarket(Supermarket currentSupermarket) {
        Launcher.currentSupermarket = currentSupermarket;
    }
}
