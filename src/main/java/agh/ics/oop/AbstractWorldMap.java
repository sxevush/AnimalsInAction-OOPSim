package agh.ics.oop;

import java.util.*;

// Klasa AbstractWorldMap reprezentuje mapę świata, na której znajdują się zwierzęta i rośliny.
// Mapa jest zorganizowana jako tablica pól (Field) reprezentujących pojedyncze pola mapy.

public abstract class AbstractWorldMap {
    protected int width; // szerokość mapy
    protected int height; // wysokość mapy
    public int minBreedEnergy; // minimalna energia wymagana, aby zwierzę mogło się rozmnożyć
    public int energyToBreed; // energia pobierana od zwierzęcia w trakcie rozmnażania
    private int newPlants; // liczba roślin dodawana do mapy w każdym kroku symulacji
    private int plantEnergy = 5; // energia jaką daje roślina zjedzona przez zwierzę
    public int startingAnimalEnergy = 20; // energia początkowa dla zwierząt
    private int numberOfMutations = 2; // liczba mutacji dokonywana podczas rozmnażania zwierząt
    private int genotypeSize = 10; // rozmiar genotypu zwierzęcia

    // mapa (HashMap) zawierająca pary (Vector2d, Field) oznaczające pozycję i pole mapy
    protected HashMap<Vector2d, Field> fields = new HashMap<>();

    // lista (ArrayList) zawierająca wszystkie pola mapy
    protected ArrayList<Field> fieldArrayList = new ArrayList<>();


    protected AbstractWorldMap(int width, int height) {
        this.width = width;
        this.height = height;
        createMap( width, height );
    }

    // Metoda createMap(int width, int height) tworzy nowe pola dla wszystkich pozycji na mapie.
    // Każde pole jest dodawane do mapy (fields) oraz do listy (fieldArrayList).
    public void createMap (int width, int height) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Field field = new Field(this);
                fields.put( new Vector2d( x, y ), field);
                fieldArrayList.add(field);
            }
        }
    }

    // Metoda addNewPlants() dodaje do mapy określoną liczbę roślin (newPlants).
    // Pola są wybierane losowo z listy (fieldArrayList) i sortowane według
    // liczby zwierząt, które umarły na danym polu (Field::getNumberOfDiedAnimals).
    // Najpierw są wybierane pola, na których nie ma roślin (field -> field.plant == 0)
    // i są one sortowane od największej do najmniejszej liczby zwierząt umarłych.
    // Następnie z tej listy są wybierane pola o największej liczbie zwierząt umarłych
    // i na te pola są dodawane rośliny (field.addPlant(plantEnergy)).
    public void addNewPlants() {
        Collections.shuffle( fieldArrayList );
        fieldArrayList.stream()
                .filter( field -> field.plant == 0 )
                .sorted(Comparator.comparingInt( Field::getNumberOfDiedAnimals )
                        .reversed())
                        .limit( newPlants )
                        .forEach( field -> field.addPlant( plantEnergy ) );
    }

    public int getStartingAnimalEnergy() { return startingAnimalEnergy; }
    public Field getField(Vector2d position) { return fields.get(position); }
    public ArrayList<Field> getFieldArrayList() { return fieldArrayList; }
    public int getPlantEnergy() { return plantEnergy; }
    public int getNumberOfMutations() { return numberOfMutations; }
    public int getGenotypeSize() { return genotypeSize; }
    public int getHeight() { return height; }
    public int getWidth(){ return width; }
    public String getImagePath(Vector2d position){ return fields.get(position).getImagePath(); }
    public void setGenotypeSize(int genotypeSize) { this.genotypeSize = genotypeSize; }
    public void setStartingAnimalEnergy(int startingEnergy) { this.startingAnimalEnergy = startingEnergy; }
    public void setNewPlants(int newPlants) { this.newPlants = newPlants; }
    public void setPlantEnergy(int plantEnergy) { this.plantEnergy = plantEnergy; }
    public void setMinBreedEnergy(int minBreedEnergy){ this.minBreedEnergy = minBreedEnergy; }
    public void setEnergyToBreed(int energyToBreed){ this.energyToBreed = energyToBreed; }
    public void setNumberOfMutations(int numberOfMutations) { this.numberOfMutations = numberOfMutations; }

    // Metoda checkBoundaries(Animal animal) jest metodą abstrakcyjną i musi zostać
    // zaimplementowana w klasie dziedziczącej po AbstractWorldMap. Służy do
    // sprawdzenia czy zwierzę nie wyszło poza granice mapy i ewentualnego
    // przeniesienia go na odpowiednie miejsce na mapie.
    public abstract void checkBoundaries(Animal animal);


    // Metoda place(Animal animal) umieszcza zwierzę na mapie.
    // Jeśli pole na pozycji zwierzęcia byłoby puste, tworzone jest nowe pole i zwierzę jest dodawane na to pole.
    // Jeśli pole na pozycji zwierzęcia jest już zajęte, zwierzę jest dodawane do tego pola.
    public void place(Animal animal) {
        Vector2d position = animal.getPosition();
        if(objectAt(position) == null) {
            Field field = new Field(this);
            field.addAnimal(animal);
            fields.put(position, field);
        }
        else{
            fields.get(position).addAnimal(animal);
        }
    }

    // Metoda placePlant(int energy, Vector2d position) dodaje roślinę o podanej energii na pole o podanej pozycji.
    public void placePlant(int energy, Vector2d position) {
        fields.get(position).addPlant(energy);
    }

    // Metoda remove(Animal animal) usuwa zwierzę z mapy.
    // Zwierzę jest usuwane z pola na którym się znajduje i na tym polu jest zwiększana liczba zwierząt umarłych.
    public void remove(Animal animal){
        Vector2d position = animal.getPosition();
        fields.get(position).addDiedAnimal();
        fields.get(position).removeAnimal(animal);
    }

    public Object objectAt(Vector2d position) {
        return fields.get(position);
    }

    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }


}
