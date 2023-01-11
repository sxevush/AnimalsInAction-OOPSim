package agh.ics.oop;

// MapDirection to enum zawierający 8 kierunków. Każdy z kierunków ma swoją nazwę
// (NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST) oraz skrót (N, NE, E, SE, S, SW, W, NW).
// MapDirection posiada również kilka metod:
// toString() - zwraca skrót kierunku
// toUnitVector() - zwraca wektor jednostkowy odpowiadający kierunkowi
// next() - zwraca kierunek po prawej stronie od obecnego kierunku
// previous() - zwraca kierunek po lewej stronie od obecnego kierunku

public enum MapDirection {
    NORTH,
    NORTHEAST,
    EAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    WEST,
    NORTHWEST;

    public String toString() {
        return switch (this) {
            case NORTH -> "N";
            case NORTHEAST -> "NE";
            case EAST -> "E";
            case SOUTHEAST -> "SE";
            case SOUTH -> "S";
            case SOUTHWEST -> "SW";
            case WEST -> "W";
            case NORTHWEST -> "NW";
        };
    }

    public Vector2d toUnitVector() {
        return switch (this) {
            case NORTH -> new Vector2d(0, 1);
            case NORTHEAST -> new Vector2d(1,1);
            case EAST -> new Vector2d(1, 0);
            case SOUTHEAST -> new Vector2d(1,-1);
            case SOUTH -> new Vector2d(0, -1);
            case SOUTHWEST -> new Vector2d(-1,-1);
            case WEST -> new Vector2d(-1, 0);
            case NORTHWEST -> new Vector2d(-1,1);
        };
    }

    public MapDirection next() {
        return switch (this) {
            case NORTH -> MapDirection.NORTHEAST;
            case NORTHEAST -> MapDirection.EAST;
            case EAST -> MapDirection.SOUTHEAST;
            case SOUTHEAST -> MapDirection.SOUTH;
            case SOUTH -> MapDirection.SOUTHWEST;
            case SOUTHWEST -> MapDirection.WEST;
            case WEST -> MapDirection.NORTHWEST;
            case NORTHWEST -> MapDirection.NORTH;
        };
    }

    public MapDirection previous() {
        return switch (this) {
            case NORTH -> MapDirection.NORTHWEST;
            case NORTHEAST -> MapDirection.NORTH;
            case EAST -> MapDirection.NORTHEAST;
            case SOUTHEAST -> MapDirection.EAST;
            case SOUTH -> MapDirection.SOUTHEAST;
            case SOUTHWEST -> MapDirection.SOUTH;
            case WEST -> MapDirection.SOUTHWEST;
            case NORTHWEST -> MapDirection.WEST;
        };
    }
}
