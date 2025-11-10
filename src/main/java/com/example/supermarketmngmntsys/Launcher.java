package com.example.supermarketmngmntsys;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
//----------------------------------------------------------------------------------------------------------------------
public class Launcher extends Application {//FIELDS
    private Stage primaryStage;
    //----------------------------------------------------------------------------------------------------------------------
    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        showDashboard();//STARTS WITH DASHBOARD
        primaryStage.setTitle("Supermarket Management System");
        primaryStage.show();
    }
    //----------------------------------------------------------------------------------------------------------------------
    public void showDashboard() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/supermarketmngmntsys/dashboard-view.fxml"));//LOADS DASHBOARD
        Scene scene = new Scene(loader.load());//SETS THE SCENE WITH THE FXML
        DashboardController controller = loader.getController();//SETS THE CONTROLLER FOR THE FXML
        controller.setLauncher(this);
        primaryStage.setScene(scene);//SETS THE PRIMARY STAGE WITH DASHBOARD FXML SCENE
    }
    //----------------------------------------------------------------------------------------------------------------------
    public void showMarket(Supermarket supermarket) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/supermarketmngmntsys/market-view.fxml"));//LOADS MARKET VIEW
        Scene scene = new Scene(loader.load());//SETS THE SCENE WITH THE FXML
        MarketController controller = loader.getController();//SETS UP THE CONTROLLER FOR THE FXML
        controller.setLauncher(this);
        controller.setSupermarket(supermarket);//SETS THE SUPERMARKET
        primaryStage.setScene(scene);//SETS THE PRIMARY STAGE WITH DASHBOARD FXML SCENE
    }
    //----------------------------------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        launch(args);
    }
    //----------------------------------------------------------------------------------------------------------------------
}
