package agh.ics.oop;

public class Plant {
    private AbstractWorldMap map;
    private Vector2d position;

    public Plant(Vector2d position){
        this.position = position;
    }

    public Vector2d getPosition(){
        return this.position;
    }

    public void remove() {
        map.plants.remove(this.position);
    }
}
