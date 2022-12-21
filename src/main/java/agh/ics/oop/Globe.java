package agh.ics.oop;

public class Globe extends AbstractWorldMap {

    private int width;
    private int height;

    protected Globe(int width, int height) {
        super(width, height);
    }

    @Override
    public void checkBoundaries(Animal animal) {
        Vector2d position = animal.getPosition();
        MapDirection orientation = animal.getOrientation();

        if (position.x() < 0) {
            animal.setPosition(new Vector2d(0, position.y()));
        } else if (position.x() >= width) {
            animal.setPosition(new Vector2d(width - 1, position.y()));
        } else if (position.y() < 0) {
            animal.setPosition(new Vector2d(position.x(), 0));
            setOppositeDirection(orientation);
        } else if (position.y() >= height) {
            animal.setPosition(new Vector2d(position.x(), height - 1));
            setOppositeDirection(orientation);
        }
    }

    private static void setOppositeDirection(MapDirection orientation) {
        for (int i = 0; i < 4; i++) {
            orientation = orientation.next();
            // sprawdzic czy dziala
        }
    }


}
