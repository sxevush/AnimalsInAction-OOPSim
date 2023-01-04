package agh.ics.oop;

// Vector2d to klasa reprezentująca wektor na dwuwymiarowej płaszczyźnie.
// Posiada pola x i y odpowiadające współrzędnym wektora.
// Wektor może być dodawany, odejmowany, porównywany z innymi wektorami
// oraz przekształcany na wektor przeciwny. Możliwe jest także znajdowanie
// wektora o maksymalnych lub minimalnych współrzędnych pomiędzy dwoma wektorami.

public record Vector2d(int x, int y) {

    public String toString() {
        return "(%d, %d)".formatted(x, y);
    }
    public boolean precedes(Vector2d other) {
        return other.x >= this.x && other.y >= this.y;
    }
    public boolean follows(Vector2d other) {
        return other.x <= this.x && other.y <= this.y;
    }
    public Vector2d add(Vector2d other) {
        return new Vector2d(this.x + other.x, this.y + other.y);
    }
    public Vector2d subtract(Vector2d other) {
        return new Vector2d(this.x - other.x, this.y - other.y);
    }
    public Vector2d upperRight(Vector2d other) { return new Vector2d(Math.max(this.x, other.x), Math.max(this.y, other.y)); }
    public Vector2d lowerLeft(Vector2d other) { return new Vector2d(Math.min(this.x, other.x), Math.min(this.y, other.y)); }
    public Vector2d opposite() {
        return new Vector2d(-this.x, -this.y);
    }
}

