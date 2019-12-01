package agh.cs.genEvo;

public interface IPositionChangeObserver {
    void positionChanged(Vector2d oldVector, IMapElement updatedSeafood);
}
