package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.geometry.HPos;
import javafx.scene.layout.VBox;
import javafx.application.Platform;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

public class App extends Application {
    private AbstractWorldMap map; //TODO ustawić menu jakies i setter
    private SimulationEngine engine;
    private int fieldSize = 30;
    private int windowHeight;
    private int windowWidth;

    private GridPane grid = new GridPane();
    private String title = "Symulacja";


    public App(String mapType,int width, int height, int numAnimals, int numPlants, int startingAnimalEnergy, int breedEnergy, int numPlantsPerYear, int plantEnergy, int timeSleep, int worldAge, int genotypeSize, int numberOfMutations){
        if(mapType=="hell"){
            this.map = new Hell(width, height);
        }
        else if(mapType=="globe"){
            this.map = new Globe(width, height);
        }
        windowHeight = fieldSize * (height+2);
        windowWidth = fieldSize * (width+2);

        this.engine = new SimulationEngine(map, this);
        engine.setMoveDelay(timeSleep);
        engine.setNumberOfPlants( numPlants ); // todo wyjatki dla za duzych/ujemnych wartosci
        engine.setWorldAge( worldAge );
        engine.setNumberOfAnimals( numAnimals );

        map.setNewPlants( numPlantsPerYear );
        map.setPlantEnergy( plantEnergy );
        map.setStartingAnimalEnergy( startingAnimalEnergy );
        map.setGenotypeSize( genotypeSize );
        map.setNumberOfMutations( numberOfMutations );


    }
    public void start(Stage primaryStage) {

        newGrid();
        VBox vBox = new VBox(
                grid, stopButton(primaryStage));

        Scene scene = new Scene(vBox, windowWidth, windowHeight);
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.show();
        Thread thread = new Thread(engine);
        thread.start();
    }

    public Button stopButton(Stage primaryStage){
        Button stopButton = new Button("Stop");
        stopButton.setOnAction((action) -> {
            primaryStage.close();
        });
        return stopButton;
    }
    public void newGrid(){

        int width = fieldSize ;
        int height = fieldSize; //TODO rozmiar pojedynczego pola

        int objectSize = fieldSize;

        grid.setGridLinesVisible(true);
        grid.setMinWidth(width);
        grid.setMinHeight(height);
        grid.getColumnConstraints().add(new ColumnConstraints(width));
        Label startLabel = new Label("y\\x");
        grid.getRowConstraints().add(new RowConstraints(height));
        GridPane.setHalignment(startLabel, HPos.CENTER);
        grid.add(startLabel, 0, 0);


        for (int i = 1; i <= map.getWidth(); i++){
            Label label = new Label(Integer.toString(i -1));
            grid.getColumnConstraints().add(new ColumnConstraints(width));
            grid.add(label, i, 0);
            GridPane.setHalignment(label, HPos.CENTER);

        }
        for (int i = 1 ; i <=  map.getHeight(); i++){
            Label label = new Label(Integer.toString(i - 1));
            grid.getRowConstraints().add(new RowConstraints(height));
            grid.add(label, 0,i);
            GridPane.setHalignment(label, HPos.CENTER);

        }
        for (int x = 0; x < map.getWidth(); x++){
            for (int y = 0; y < map.getHeight(); y++){
                Vector2d position = new Vector2d(x, y);

                String path = map.getImagePath(position);
                VBox box = new GuiElementBox(path, objectSize).getVBox();

                grid.add(box, x+1, y+1);
                GridPane.setHalignment(box, HPos.CENTER);
            }
        }
    }
    public void refresh() {
        Platform.runLater( () -> {
            this.grid.getChildren().clear();
            this.grid.getColumnConstraints().clear();
            this.grid.getRowConstraints().clear();
            grid.setGridLinesVisible(false);
            this.newGrid();
        });
    }
}

