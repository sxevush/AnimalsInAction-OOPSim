package agh.ics.oop;

public class Main {

    public static void main(String[] args) {
        AbstractWorldMap map = new Globe(10, 15);
        Animal Pysiu = new Animal(map, new Vector2d(2, 2));
        map.place(Pysiu);


        for (int i = 0; i < 10; i++) {
            Pysiu.move();
        }
        System.out.print(map);
    }

}
