package agh.ics.oop;

// Klasa Globe dziedziczy po klasie AbstractWorldMap.

public class Globe extends AbstractWorldMap {

    public Globe(int width, int height) {
        super(width, height);
    }

    // Metoda checkBoundaries(Animal animal) służy do sprawdzenia
    // czy zwierzę znajduje się na mapie. Jeśli zwierzę wychodzi poza granice mapy,
    // to zostaje ono przeniesione na odpowiednią granicę mapy.
    // Jeśli zwierzę wychodzi poza górną lub dolną granicę mapy, to jego orientacja zostaje zmieniona na przeciwną.
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

    // Metoda oppositeDirection(MapDirection orientation) zwraca orientację przeciwną do podanej.
    // Jest to osiągane poprzez przekręcenie orientacji o 180 stopni (4 kierunki na mapie).
    private static MapDirection oppositeDirection(MapDirection orientation) {
        for (int i = 0; i < 4; i++) {
            orientation = orientation.next();
        }
        return orientation;
    }


}
