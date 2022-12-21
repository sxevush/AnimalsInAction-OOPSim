package agh.ics.oop;

public class Main {

    public static void main(String[] args) {
        AbstractWorldMap map = new Hell(10, 15);
        Animal Pysiu = new Animal(map, new Vector2d(2, 2));
        map.place(Pysiu);
        for (int i = 0; i < 13; i++) {
            Pysiu.move(0);
        }
        System.out.print(map);
    }

}
