package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.geometry.HPos;
import javafx.scene.layout.VBox;
import javafx.application.Platform;
import javafx.scene.control.Button;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

// Klasa App jest klasą reprezentującą okno aplikacji i wyświetlającą grafikę
// oraz interfejs użytkownika. Tworzy ona instancję klasy SimulationEngine
// i uruchamia wątek z jej implementacją metody run().
// Pozwala ona na uruchomienie widoku symulacji, w którym zawarte są m.in. statystyki danej mapy,
// możliwość śledzenia wybranego zwierzęcia oraz sprawdzenie energii zwierząt na danym polu poprzez
// najechanie na nie.

public class App extends Application {
    private AbstractWorldMap map;
    private SimulationEngine engine;
    private int fieldSize = 40;
    private int windowHeight;
    private int windowWidth;
    private GridPane grid = new GridPane();
    Label animalInfoLabel = new Label("");
    Label selectedAnimalInfoLabel = new Label("");
    private Animal selectedAnimal;

    public App(String mapType, int width, int height, int numAnimals, int numPlants,
               int startingAnimalEnergy, int minBreedEnergy, int energyToBreed, int numPlantsPerYear,
               int plantEnergy, int timeSleep, int worldAge, int genotypeSize, int numberOfMutations) {
        if (Objects.equals(mapType, "hell")) {
            this.map = new Hell(width, height);
        } else if (Objects.equals(mapType, "globe")) {
            this.map = new Globe(width, height);
        }
        windowHeight = fieldSize * ( height + 2 );
        windowWidth = fieldSize * ( width + 2 );

        this.engine = new SimulationEngine(map, this);
        engine.setMoveDelay(timeSleep);
        engine.setNumberOfPlants(numPlants);
        engine.setWorldAge(worldAge);
        engine.setNumberOfAnimals(numAnimals);

        map.setNewPlants(numPlantsPerYear);
        map.setPlantEnergy(plantEnergy);
        map.setStartingAnimalEnergy(startingAnimalEnergy);
        map.setGenotypeSize(genotypeSize);
        map.setNumberOfMutations(numberOfMutations);
        map.setMinBreedEnergy(minBreedEnergy);
        map.setEnergyToBreed(energyToBreed);
        map.createMap();


    }

    // Metoda start() definiuje okno aplikacji za pomocą instancji klasy Stage,
    // ustawia na nim widok za pomocą instancji klasy Scene,
    // a następnie wyświetla okno za pomocą metody show().
    public void start(Stage primaryStage) {
        newGrid();
        VBox vBox = new VBox(
                grid, animalInfoLabel, stopButton(primaryStage), pauseButton(), statisticsButton(), selectedAnimalInfoLabel);

        Scene scene = new Scene(vBox, windowWidth, windowHeight);
        String title = "Animals In Action";
        primaryStage.setTitle(title);

        primaryStage.setScene(scene);
        primaryStage.show();

        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter("statistics.csv", false);
            BufferedWriter cleanFile = new BufferedWriter(fileWriter);
            cleanFile.write(""); // wyczyść plik
            cleanFile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        Thread thread = new Thread(engine);
        thread.start();
    }

    // Metoda stopButton() tworzy przycisk, który po kliknięciu zamyka okno aplikacji i kończy działanie wątku.
    public Button stopButton(Stage primaryStage) {
        Button stopButton = new Button("Stop");
        stopButton.setOnAction((action) -> {
            primaryStage.close();
            engine.stop();
        });
        return stopButton;
    }

    // Metoda pauseButton() tworzy przycisk, który po kliknięciu wstrzymuje lub wznawia działanie wątku.
    public Button pauseButton() {
        Button pauseButton = new Button("Pause/Unpause");
        pauseButton.setOnAction((action) -> engine.pause());
        return pauseButton;
    }

    // Metoda statisticsButton() tworzy przycisk, który po kliknięciu wyświetla statystyki dotyczące symulacji.
    public Button statisticsButton() {
        Button statisticsButton = new Button("Show statistics");
        statisticsButton.setOnAction(action -> showStatistics());
        return statisticsButton;
    }

    // Metoda showStatistics() tworzy zawartość okienka statystyk.
    private void showStatistics() {
        Statistics statistics = new Statistics(map, engine);

        Stage window = new Stage();
        window.setTitle(" statistics ");
        GridPane grid = new GridPane();

        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(8);
        grid.setHgap(10);

        Label genotype = new Label("Most common genotype: ");
        GridPane.setConstraints(genotype, 0, 0, 1, 1);

        Label genotypeValue = new Label(statistics.mostPopularGenotype());
        GridPane.setConstraints(genotypeValue, 1, 0, 1, 1);

        Label numberOfAnimals = new Label("Number of animals: ");
        GridPane.setConstraints(numberOfAnimals, 0, 1, 1, 1);

        Label numberOfAnimalsValue = new Label(Integer.toString(statistics.countAllAnimals()));
        GridPane.setConstraints(numberOfAnimalsValue, 1, 1, 1, 1);

        Label numberOfPlants = new Label("Number of plants: ");
        GridPane.setConstraints(numberOfPlants, 0, 2, 1, 1);

        Label numberOfPlantsValue = new Label(Long.toString(statistics.countAllPlants()));
        GridPane.setConstraints(numberOfPlantsValue, 1, 2, 1, 1);

        Label freeFields = new Label("Free fields on the map: ");
        GridPane.setConstraints(freeFields, 0, 3, 1, 1);

        Label freeFieldsValue = new Label(Long.toString(statistics.countFreeFields()));
        GridPane.setConstraints(freeFieldsValue, 1, 3, 1, 1);

        Label energyLevel = new Label("Average energy level of alive animals: ");
        GridPane.setConstraints(energyLevel, 0, 4, 1, 1);

        Label energyLevelValue = new Label(Double.toString(statistics.avgEnergyLevel()));
        GridPane.setConstraints(energyLevelValue, 1, 4, 1, 1);

        Label age = new Label("Average age of dead animals: ");
        GridPane.setConstraints(age, 0, 5, 1, 1);

        Label ageValue = new Label(Double.toString(statistics.avgAnimalAge()));
        GridPane.setConstraints(ageValue, 1, 5, 1, 1);

        grid.getChildren().addAll(genotype, genotypeValue, numberOfPlants, numberOfPlantsValue,
                numberOfAnimals, numberOfAnimalsValue, freeFields, freeFieldsValue,
                energyLevel, energyLevelValue, age, ageValue);
        Scene scene = new Scene(grid, 400, 375);
        window.setScene(scene);
        window.show();
    }

    // Metoda newGrid() tworzy siatkę reprezentującą mapę oraz umieszcza na niej odpowiednie komponenty.
    public void newGrid() {

        int width = fieldSize;
        int height = fieldSize;

        grid.setGridLinesVisible(true);
        grid.setMinWidth(width);
        grid.setMinHeight(height);
        grid.getColumnConstraints().add(new ColumnConstraints(width));
        Label startLabel = new Label("y\\x");
        grid.getRowConstraints().add(new RowConstraints(height));
        GridPane.setHalignment(startLabel, HPos.CENTER);
        grid.add(startLabel, 0, 0);

        for (int i = 1; i <= map.getWidth(); i++) {
            Label label = new Label(Integer.toString(i - 1));
            grid.getColumnConstraints().add(new ColumnConstraints(width));
            grid.add(label, i, 0);
            GridPane.setHalignment(label, HPos.CENTER);
        }

        for (int i = 1; i <= map.getHeight(); i++) {
            Label label = new Label(Integer.toString(i - 1));
            grid.getRowConstraints().add(new RowConstraints(height));
            grid.add(label, 0, i);
            GridPane.setHalignment(label, HPos.CENTER);
        }

        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Vector2d position = new Vector2d(x, y);

                String path = map.getImagePath(position);
                VBox box = new GuiElementBox(path, fieldSize).getVBox();

                grid.add(box, x + 1, y + 1);
                GridPane.setHalignment(box, HPos.CENTER);

                // Kiedy wskaźnik myszy wejdzie na pole zawierające etykietę,
                // zostanie wywołana metoda setOnMouseEntered. W tej metodzie pobierany
                // jest obiekt typu Field z mapy i ustawiana jest informacja o zwierzęciach
                // znajdujących się na tym polu. Kiedy wskaźnik myszy opuści pole zawierające etykietę,
                // zostanie wywołana metoda setOnMouseExited, która ustawia informację o zwierzęciu na pusty
                // ciąg znaków. Na końcu etykieta jest dodawana do siatki (obiektu typu GridPane).
                Label label = new Label();
                label.setMinSize(width, height);
                label.setMaxSize(width, height);
                int finalX = x+1;
                int finalY = y+1;

                label.setOnMouseEntered((MouseEvent event) -> {
                    Field field = map.getField(new Vector2d(finalX-1, finalY-1));
                    if (field != null) {
                        animalInfoLabel.setText(field.getAnimalInfo());
                    }
                });
                label.setOnMouseExited((MouseEvent event) -> animalInfoLabel.setText(""));
                grid.add(label, finalX, finalY);
            }
        }
        windowHeight += 170;

        // Obsługa zdarzenia kliknięcia myszy dla siatki (obiektu typu GridPane).
        // Po kliknięciu myszą, pobierana jest pozycja kliknięcia i na podstawie tej
        // pozycji obliczane są współrzędne pola klikniętego. Następnie pobierany jest
        // obiekt typu Field z mapy i sprawdzane, czy na tym polu znajduje się jakiś zwierzę.
        // Jeśli tak, to pobierany jest dowolny zwierzak z tego pola i ustawiany jako zaznaczony
        // (atrybut selectedAnimal). W końcu ustawiana jest szczegółowa informacja o zaznaczonym
        // zwierzęciu w odpowiedniej etykiecie.
        grid.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            int x = (int) event.getX() / fieldSize;
            int y = (int) event.getY() / fieldSize;
            Vector2d position = new Vector2d(x-1, y-1);
            if (map.getField(position).getAnimals() != null) {
                Optional<Animal> optionalAnimal = map.getField(position).getAnimals().stream().findAny();
                optionalAnimal.ifPresent(animal -> selectedAnimal = animal);
                if (selectedAnimal != null) {
                    selectedAnimalInfoLabel.setText(selectedAnimal.getDetailedAnimalInfo());
                }
            }
        });
    }

    public void refresh() {
        Platform.runLater(() -> {
            this.grid.getChildren().clear();
            this.grid.getColumnConstraints().clear();
            this.grid.getRowConstraints().clear();
            grid.setGridLinesVisible(false);
            this.newGrid();
            Statistics statistics = new Statistics(map, engine);
            try {
                statistics.makeData("statistics" + ".csv");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (selectedAnimal != null) {
                selectedAnimalInfoLabel.setText(selectedAnimal.getDetailedAnimalInfo());
            }
        });
    }
}

