package agh.ics.oop.gui;
import agh.ics.oop.AbstractWorldMap;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Menu extends Application {
    private App app;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        GridPane root = getGridPane();

        Label mapLabel = new Label("Map type:");
        ComboBox<String> mapComboBox = new ComboBox<>();
        mapComboBox.getItems().addAll("hell", "globe");
        mapComboBox.setPromptText("Choose map type");
        mapComboBox.setValue("hell");

        Label widthLabel = new Label("Map width:");
        TextField widthField = new TextField("10");

        Label heightLabel = new Label("Map height:");
        TextField heightField = new TextField("10");

        Label numAnimalsLabel = new Label("Number of starting animals:");
        TextField numAnimalsField = new TextField("5");

        Label numPlantsLabel = new Label("Number of starting plants:");
        TextField numPlantsField = new TextField("5");

        Label startingAnimalEnergyLabel = new Label("Starting animal energy:");
        TextField startingAnimalEnergyField = new TextField("20");

        Label breedEnergyLabel = new Label("Minimum breed energy:");
        TextField breedEnergyField = new TextField("10");

        Label numPlantsPerYearLabel = new Label("Number of plants per year:");
        TextField numPlantsPerYearField = new TextField("5");

        Label plantEnergyLabel = new Label("Plant energy:");
        TextField plantEnergyField = new TextField("1");

        Label timeSleepLabel = new Label("Time sleep:");
        TextField timeSleepField = new TextField("500");

        Label worldAgeLabel = new Label("World Age:");
        TextField worldAgeField = new TextField("240");

        Label genotypeSizeLabel = new Label("Genotype size:");
        TextField genotypeSizeField = new TextField("8");

        Label numberOfMutationsLabel = new Label("Number of mutations:");
        TextField numMutationsField = new TextField("2");

        Button exitButton = new Button("Exit");
        Button startButton = new Button("Start");

        // Dodaj elementy do kontenera
        addElementsToRoot(root, mapLabel, mapComboBox, widthLabel,
                widthField, heightLabel, heightField, numAnimalsLabel,
                numAnimalsField, numPlantsLabel, numPlantsField, startingAnimalEnergyLabel,
                startingAnimalEnergyField, breedEnergyLabel, breedEnergyField, numPlantsPerYearLabel,
                numPlantsPerYearField, plantEnergyLabel, plantEnergyField, timeSleepLabel, timeSleepField,
                worldAgeLabel, worldAgeField, genotypeSizeLabel, genotypeSizeField, numberOfMutationsLabel,
                numMutationsField, exitButton, startButton);

        root.setAlignment(Pos.CENTER);

        setScene(primaryStage, root);

        exitButton.setOnAction(event -> {System.exit(0);});

        setStartButton(mapComboBox, widthField, heightField, numAnimalsField,
                numPlantsField, startingAnimalEnergyField, breedEnergyField,
                numPlantsPerYearField, plantEnergyField, timeSleepField, worldAgeField,
                genotypeSizeField, numMutationsField, startButton);
    }

    private void setStartButton(ComboBox<String> mapComboBox, TextField widthField, TextField heightField,
                                TextField numAnimalsField, TextField numPlantsField, TextField startingAnimalEnergyField,
                                TextField breedEnergyField, TextField numPlantsPerYearField, TextField plantEnergyField,
                                TextField timeSleepField, TextField worldAgeField, TextField genotypeSizeField,
                                TextField numMutationsField, Button startButton) {
        startButton.setOnAction(event -> {
            // Pobierz wartości parametrów z pól tekstowych
            String map = mapComboBox.getValue();
            int width = 0;
            int height = 0;
            int numAnimals = 0;
            int numPlants = 0;
            int startingAnimalEnergy = 0;
            int breedEnergy = 0;
            int numPlantsPerYear = 0;
            int plantEnergy = 0;
            int timeSleep = 0;
            int worldAge = 0;
            int genotypeSize = 0;
            int numberOfMutations = 0;
            try {
                width = Integer.parseInt(widthField.getText());
                if (width < 0 || width > 30) {
                    throw new NumberFormatException();
                }
                height = Integer.parseInt(heightField.getText());
                if (height < 0 || height > 30) {
                    throw new NumberFormatException();
                }
                numAnimals = Integer.parseInt(numAnimalsField.getText());
                if (numAnimals < 0) {
                    throw new NumberFormatException();
                }
                numPlants = Integer.parseInt(numPlantsField.getText());
                if (numPlants < 0) {
                    throw new NumberFormatException();
                }
                startingAnimalEnergy = Integer.parseInt(startingAnimalEnergyField.getText());
                if (startingAnimalEnergy < 0) {
                    throw new NumberFormatException();
                }
                breedEnergy = Integer.parseInt(breedEnergyField.getText());
                if (breedEnergy < 0) {
                    throw new NumberFormatException();
                }
                numPlantsPerYear = Integer.parseInt(numPlantsPerYearField.getText());
                if (numPlantsPerYear < 0) {
                    throw new NumberFormatException();
                }
                plantEnergy = Integer.parseInt(plantEnergyField.getText());
                if (plantEnergy < 0) {
                    throw new NumberFormatException();
                }
                timeSleep = Integer.parseInt(timeSleepField.getText());
                if (timeSleep < 100) {
                    throw new NumberFormatException();
                }
                worldAge = Integer.parseInt(worldAgeField.getText());
                if(worldAge<0){
                    throw new NumberFormatException();
                }
                genotypeSize = Integer.parseInt(genotypeSizeField.getText());
                if(genotypeSize<0){
                    throw new NumberFormatException();
                }
                numberOfMutations = Integer.parseInt(numMutationsField.getText());
                if(numberOfMutations<0){
                    throw new NumberFormatException();
                }
                startSimulation(map, width, height, numAnimals, numPlants, startingAnimalEnergy, breedEnergy, numPlantsPerYear, plantEnergy, timeSleep, worldAge, genotypeSize, numberOfMutations);
            } catch (NumberFormatException e) {
                System.out.println("Invalid parameter");
            }
        });
    }

    private static void setScene(Stage primaryStage, GridPane root) {
        Scene scene = new Scene(root, 400, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Menu");
        primaryStage.show();
    }

    private static void addElementsToRoot(GridPane root, Label mapLabel, ComboBox<String> mapComboBox,
                                          Label widthLabel, TextField widthField, Label heightLabel,
                                          TextField heightField, Label numAnimalsLabel, TextField numAnimalsField,
                                          Label numPlantsLabel, TextField numPlantsField, Label startingAnimalEnergyLabel,
                                          TextField startingAnimalEnergyField, Label breedEnergyLabel,
                                          TextField breedEnergyField, Label numPlantsPerYearLabel,
                                          TextField numPlantsPerYearField, Label plantEnergyLabel,
                                          TextField plantEnergyField, Label timeSleepLabel, TextField timeSleepField,
                                          Label worldAgeLabel, TextField worldAgeField, Label genotypeSizeLabel,
                                          TextField genotypeSizeField, Label numberOfMutationsLabel, TextField numMutationsField,
                                          Button exitButton, Button startButton) {
        root.add(mapLabel, 0, 0);
        root.add(mapComboBox, 1, 0);
        root.add(widthLabel, 0, 1);
        root.add(widthField, 1, 1);
        root.add(heightLabel, 0, 2);
        root.add(heightField, 1, 2);
        root.add(numAnimalsLabel, 0, 3);
        root.add(numAnimalsField, 1, 3);
        root.add(numPlantsLabel, 0, 4);
        root.add(numPlantsField, 1, 4);
        root.add(startingAnimalEnergyLabel, 0, 5);
        root.add(startingAnimalEnergyField, 1, 5);
        root.add(breedEnergyLabel, 0, 6);
        root.add(breedEnergyField, 1, 6);
        root.add(numPlantsPerYearLabel, 0, 7);
        root.add(numPlantsPerYearField, 1, 7);
        root.add(plantEnergyLabel, 0, 8);
        root.add(plantEnergyField, 1, 8);
        root.add(timeSleepLabel, 0, 9);
        root.add(timeSleepField, 1, 9);
        root.add(worldAgeLabel, 0, 10);
        root.add(worldAgeField, 1, 10);
        root.add(genotypeSizeLabel, 0, 11);
        root.add(genotypeSizeField, 1, 11);
        root.add(numberOfMutationsLabel, 0, 12);
        root.add(numMutationsField, 1, 12);
        root.add(exitButton, 1, 13);
        root.add(startButton, 0, 13);
    }

    private static GridPane getGridPane() {
        GridPane root = new GridPane();
        root.setPadding(new Insets(10));
        root.setVgap(8);
        root.setHgap(10);
        return root;
    }

    private void startSimulation(String map, int width, int height, int numAnimals,
                                 int numPlants, int startingAnimalEnergy, int breedEnergy,
                                 int numPlantsPerYear, int plantEnergy, int timeSleep, int worldAge,
                                 int genotypeSize, int numberOfMutations) {
        this.app = new App(map, width, height, numAnimals, numPlants, startingAnimalEnergy, breedEnergy, numPlantsPerYear, plantEnergy, timeSleep, worldAge, genotypeSize, numberOfMutations);
        this.app.start(new Stage());
    }
}

