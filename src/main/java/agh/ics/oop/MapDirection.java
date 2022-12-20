package agh.ics.oop;

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
        switch (this) {
            case NORTH -> {return "N";}

            case NORTHEAST -> {return "NE";}

            case EAST -> {return "E";}

            case SOUTHEAST -> {return "SE";}

            case SOUTH -> {return "S";}

            case SOUTHWEST -> {return "SW";}

            case WEST -> {return "W";}

            case NORTHWEST -> {return "NW";}
        }
        return null;
    }

    public Vector2d toUnitVector() {
        switch (this) {
            case NORTH -> {return new Vector2d(0, 1);}

            case NORTHEAST -> {return new Vector2d(1,1);}

            case EAST -> {return new Vector2d(1, 0);}

            case SOUTHEAST -> {return new Vector2d(1,-1);}

            case SOUTH -> {return new Vector2d(0, -1);}

            case SOUTHWEST -> {return new Vector2d(-1,-1);}

            case WEST -> {return new Vector2d(-1, 0);}

            case NORTHWEST -> {return new Vector2d(-1,1);}
        }
        return null;
    }

    public MapDirection next() {
        switch (this) {
            case NORTH -> {return MapDirection.NORTHEAST;}

            case NORTHEAST -> {return MapDirection.EAST;}

            case EAST -> {return MapDirection.SOUTHEAST;}

            case SOUTHEAST -> {return MapDirection.SOUTH;}

            case SOUTH -> {return MapDirection.SOUTHWEST;}

            case SOUTHWEST -> {return MapDirection.WEST;}

            case WEST -> {return MapDirection.NORTHWEST;}

            case NORTHWEST -> {return MapDirection.NORTH;}
        }
        return null;
    }
}
