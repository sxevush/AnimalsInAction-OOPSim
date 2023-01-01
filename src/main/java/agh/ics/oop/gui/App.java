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
    private AbstractWorldMap map = new Globe(10, 15);; //TODO ustawić menu jakies i setter
    private SimulationEngine engine;
    private int fieldSize = 30;
    private int windowHeight = fieldSize * map.getHeight(); //TODO dostosować rozmiar okna do wymiarów mapy pewnie przez jakas funkcje
    private int windowWidth = fieldSize * map.getWidth();

    private GridPane grid = new GridPane();
    private String title = "Symulacja";
    private int moveDelay  = 1000;


    public void start(Stage primaryStage) {

        newGrid();
        VBox vBox = new VBox(
                grid,
                startButton());

        Scene scene = new Scene(vBox, windowWidth, windowHeight + 63); //TODO ustalic wymiar paska pod symulacja
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public Button startButton() {
        Button startButton = new Button("Start");
        startButton.setOnAction((action) -> {
            try {
                this.engine = new SimulationEngine(map, this);
                engine.setMoveDelay(this.moveDelay);
                engine.setNumberOfPlants( 10 ); // todo wyjatki dla za duzych/ujemnych wartosci
                engine.setWorldAge( 100 );
                engine.setNumberOfAnimals( 10 );
                map.setNewPlants( 5 );
                map.setPlantEnergy( 5 );
                map.setStartingAnimalEnergy( 20 );
                map.setGenotypeSize( 8 );
                map.setNumberOfMutations( 2 );
                Thread thread = new Thread(engine);
                thread.start();
            }
            catch (IllegalArgumentException exception){
                System.out.println(exception);
                System.exit(0);
            }
        });
        return startButton;
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
            Label label = new Label(Integer.toString(i + 1));
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

