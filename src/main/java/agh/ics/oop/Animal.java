package agh.ics.oop;

public class Animal {
    private MapDirection orientation = MapDirection.NORTH; // TODO poczatkowy kierunek
    private AbstractWorldMap map;
    private Vector2d position;


    private int energy;
    private int age = 0; //licznik tur, które przeżyło zwierzę
    private Genotype genotype;

    public Animal(AbstractWorldMap map, Vector2d startingPosition){
        this.map = map;
        position = startingPosition;
        genotype = new Genotype();
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public void setOrientation(MapDirection orientation) { this.orientation = orientation; }

    public Animal(Animal parent1, Animal parent2){

    }

    public void move() {
        // TODO do testu mapy - bedzie zmienione
        Vector2d oldPosition = position;
        Integer turn = this.genotype.next();

        for (int i = 0 ; i < turn ; i++) {
            orientation = orientation.next();
        }

        position = position.add( orientation.toUnitVector() );
        map.checkBoundaries( this );
        positionChanged( oldPosition, position ); // TODO OBSERVER
    }

    @Override
    public String toString() {
        return this.orientation.toString();//TODO zmienic dla kilku
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        map.animals.remove(oldPosition);
        map.animals.put(newPosition, this);
    }

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }
    public Vector2d getPosition(){
        return this.position;
    }
    public MapDirection getOrientation() {
        return orientation;
    }
}
