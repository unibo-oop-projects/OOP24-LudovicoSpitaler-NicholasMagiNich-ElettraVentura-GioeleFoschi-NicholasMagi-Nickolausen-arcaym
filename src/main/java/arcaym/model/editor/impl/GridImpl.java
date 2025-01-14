package arcaym.model.editor.impl;

import java.util.Collection;

import arcaym.common.point.api.Point;
import arcaym.model.editor.api.Grid;
import arcaym.model.editor.api.Memento;
import arcaym.model.game.core.objects.api.GameObjectCategory;
import arcaym.model.game.objects.api.GameObjectType;

public class GridImpl implements Grid{

    public GridImpl(int i, int j) {
        //TODO Auto-generated constructor stub
    }

    @Override
    public Memento takeSnapshot(Collection<Point> positions, GameObjectCategory changingTo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'takeSnapshot'");
    }

    @Override
    public void restore(Memento state) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'restore'");
    }

    @Override
    public void setObjects(Collection<Point> positions, GameObjectType type) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setObjects'");
    }

    @Override
    public void removeObjects(Collection<Point> positions) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeObjects'");
    }

    @Override
    public Collection<GameObjectType> getObjects(Point pos) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getObjects'");
    }
    
}
