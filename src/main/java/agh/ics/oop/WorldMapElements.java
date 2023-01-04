package agh.ics.oop;

import java.util.*;

// WorldMapElements to klasa przechowująca informacje o obiektach na mapie.
// Zawiera listę obiektów typu Animal, a także instancję mapy, na której te obiekty się znajdują.

public class WorldMapElements {
    private ArrayList<Animal> animals = new ArrayList<>();
    private AbstractWorldMap map;
    protected HashMap<Vector2d, Field> fields;
    private List<Integer> ageOfDiedAnimals = new ArrayList<>();

    public WorldMapElements(AbstractWorldMap map){
        this.map = map;
        this.fields = map.fields;
    }

    public ArrayList<Animal> getAnimals() { return animals; }
    public List<Integer> getAgeOfDiedAnimals() { return ageOfDiedAnimals; }
    public int getSize(){
        return this.animals.size();
    }
    public void addAnimals(ArrayList<Animal> newAnimals) {
        animals.addAll( newAnimals );
    }
    public void addAnimal(Animal animal){
        animals.add(animal);
    }


    // Metoda move wywołuje metodę move dla każdego obiektu typu Animal znajdującego się na liście.
    public void move() {
        for (Animal animal : animals) {
            animal.move();
        }
    }

    // Metoda cleanMap usuwa z listy zwierząt te, których poziom energii jest
    // równy zero lub mniejszy oraz usuwa te obiekty z mapy. Wiek zwierząt, które umarły,
    // jest dodawany do listy ageOfDiedAnimals.
    public void cleanMap() {
        Iterator<Animal> iterator = animals.iterator();
        while (iterator.hasNext()) {
            Animal animal = iterator.next();
            if (animal.getEnergy() <= 0) {
                ageOfDiedAnimals.add(animal.getAge());
                animal.setDied(true);
                map.remove( animal );
                iterator.remove();
            }
        }
    }

}
