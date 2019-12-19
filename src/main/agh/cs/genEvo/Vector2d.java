package agh.cs.genEvo;

import java.util.Objects;

public class Vector2d {
    public final int x;
    public final int y;

    public Vector2d() {
        this.x = 1;
        this.y = 1;
    }
    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }
    public Vector2d(Vector2d v){
        this.x = v.x;
        this.y = v.y;
    }
    public String toString(){
        return "("+this.x+","+this.y+")";
    }
    //
    public boolean equals(Object other){
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d that = (Vector2d) other;
        return (that.x == this.x && that.y == this.y);
    }
    public boolean precedes(Vector2d v){
        if(!this.equals(v)) {
            if (v.x < this.x) return false;
            if (v.y < this.y) return false;
        }
        return  true;
    }
    public boolean follows(Vector2d v){
        if(!this.equals(v)) {
            if (v.x > this.x) return false;
            if (v.y > this.y) return false;
        }
        return  true;
    }
    public Vector2d upperRight(Vector2d v){
        return new Vector2d(Math.max(this.x, v.x), Math.max(this.y, v.y));
    }
    public Vector2d lowerLeft(Vector2d v){
        return new Vector2d(Math.min(this.x, v.x), Math.min(this.y, v.y));
    }
    public Vector2d add(Vector2d v){
        return new Vector2d(this.x + v.x, this.y + v.y);
    }
    public Vector2d subtract(Vector2d v){
        return new Vector2d(this.x - v.x, this.y - v.y);
    }public Vector2d opposite(){
        return new Vector2d(-this.x, -this.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
