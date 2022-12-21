package agh.ics.oop;

public class Globe extends AbstractWorldMap {

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
            animal.setOrientation(oppositeDirection(orientation));
        } else if (position.y() >= height) {
            animal.setPosition(new Vector2d(position.x(), height - 1));
            animal.setOrientation(oppositeDirection(orientation));
        }
    }

    private static MapDirection oppositeDirection(MapDirection orientation) {
        for (int i = 0; i < 4; i++) {
            orientation = orientation.next();
        }
        return orientation;
    }


}
