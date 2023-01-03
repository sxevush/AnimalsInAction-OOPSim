package agh.ics.oop;

import java.util.Random;

public class Hell extends AbstractWorldMap {

    Random random = new Random();
    public Hell(int width, int height) {
        super(width, height);
    }

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
