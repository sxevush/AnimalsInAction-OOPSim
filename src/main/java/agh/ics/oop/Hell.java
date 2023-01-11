package agh.ics.oop;

import java.util.Random;

// Klasa Hell dziedziczy po klasie AbstractWorldMap.

public class Hell extends AbstractWorldMap {

    Random random = new Random();
    public Hell(int width, int height) {
        super(width, height);
    }

    // Metoda checkBoundaries jest odpowiedzialna za sprawdzenie czy zwierzę przekroczyło granice mapy.
    // Jeśli tak, to zostaje ono przeniesione na losowe miejsce na mapie oraz ma losowo wybraną orientację.
    // Klasa ta definiuje także obiekt random z klasy Random,
    // który jest używany do losowania pozycji i orientacji dla zwierzęcia, gdy przekroczy ono granice mapy.
    @Override
    public void checkBoundaries(Animal animal) {
        Random rand = new Random();
        int randomX = rand.nextInt(width);
        int randomY = rand.nextInt(height);
        Vector2d position = animal.getPosition();
        if ((position.x() < 0) || (position.x() >= width) || (position.y() < 0) || (position.y() >= height)) {
            animal.setOrientation(MapDirection.values()[random.nextInt(MapDirection.values().length)]);
            animal.setPosition(new Vector2d(randomX, randomY));
        }
    }
}
