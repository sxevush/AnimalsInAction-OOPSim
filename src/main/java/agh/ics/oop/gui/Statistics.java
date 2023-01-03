package agh.ics.oop.gui;

import agh.ics.oop.AbstractWorldMap;
import agh.ics.oop.SimulationEngine;
import javafx.application.Application;
import javafx.stage.Stage;

public class Statistics extends Application {

    private AbstractWorldMap map;
    private App app;
    private SimulationEngine engine;

    public Statistics(AbstractWorldMap map, App app, SimulationEngine engine) {
        this.map = map;
        this.app = app;
        this.engine = engine;
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        
    }
}
