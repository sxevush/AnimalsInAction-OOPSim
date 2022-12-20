package agh.ics.oop;
import java.util.Objects;

public class Vector2d {
    public final int x;
    public final int y;

    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }

    public String toString(){
        return "(%d, %d)".formatted(x,y);

    }
    public boolean precedes(Vector2d other){
        if(other.x>=this.x && other.y>=this.y){
            return true;

        }
        return false;
    }
    public boolean follows(Vector2d other){
        if(other.x<=this.x && other.y<=this.y){
            return true;

        }
        return false;
    }
    public Vector2d add(Vector2d other){
        Vector2d v2 = new Vector2d(this.x + other.x, this.y + other.y);
        return v2;
    }
    public Vector2d subtract(Vector2d other){
        Vector2d v2 = new Vector2d(this.x - other.x, this.y - other.y);
        return v2;
    }
    public Vector2d upperRight(Vector2d other){
        return new Vector2d(Math.max(this.x, other.x), Math.max(this.y, other.y));
    }

    public Vector2d lowerLeft(Vector2d other){
        return new Vector2d(Math.min(this.x, other.x), Math.min(this.y, other.y));
    }
    public Vector2d opposite(){
        return new Vector2d(-this.x,-this.y);
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2d vector2d = (Vector2d) o;
        return x == vector2d.x && y == vector2d.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public static void main(String[] args) {
        Vector2d position1 = new Vector2d(1,2);
        System.out.println(position1);
        Vector2d position2 = new Vector2d(-2,1);
        System.out.println(position2);
        System.out.println(position1.add(position2));

    }



}

