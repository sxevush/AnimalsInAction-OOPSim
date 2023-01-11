
package agh.ics.oop.gui;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

// Klasa GuiElementBox reprezentuje prostokątny kontener (VBox) z jednym elementem - obrazem (ImageView).
// Tworzony jest na podstawie podanego ścieżki do pliku obrazu i rozmiaru (szerokości i wysokości).
// Kontener jest wyśrodkowany (alignment ustawione na Pos.CENTER).

public class GuiElementBox {
    private VBox vBox;

    // konstruktor GuiElementBox(String path, int size) tworzy obiekt klasy na podstawie podanej ścieżki i rozmiaru
    // wyjątek FileNotFoundException jest rzucany w przypadku braku pliku obrazu pod podaną ścieżką
    public GuiElementBox(String path, int size){
        try {
            Image image = new Image(new FileInputStream(path));
            ImageView image1 = new ImageView(image);
            image1.setFitWidth(size);
            image1.setFitHeight(size);

            this.vBox = new VBox(image1);
            this.vBox.setAlignment(Pos.CENTER);

        }
        catch (FileNotFoundException e){
            throw new RuntimeException("File not found.");
        }
    }

    public VBox getVBox(){
        return this.vBox;
    }
}
