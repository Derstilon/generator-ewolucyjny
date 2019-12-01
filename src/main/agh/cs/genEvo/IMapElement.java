package agh.cs.genEvo;

public interface IMapElement {
    String toString();
    MapDirection getDirection();
    Vector2d getPosition();
    IWorldMap getImap();
    void move(MoveDirection direction);
    void setMap(IWorldMap map);
    void addObserver(IPositionChangeObserver observer);
    void positionChanged(Vector2d oldVector);
}
