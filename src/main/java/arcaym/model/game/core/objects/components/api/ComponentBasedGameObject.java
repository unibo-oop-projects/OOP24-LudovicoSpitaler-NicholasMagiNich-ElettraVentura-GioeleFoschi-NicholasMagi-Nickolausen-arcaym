package arcaym.model.game.core.objects.components.api;

import java.util.stream.Stream;

import arcaym.model.game.core.objects.api.GameObject;

public interface ComponentBasedGameObject extends GameObject {

    void addComponent(GameObjectComponent component);

    void removeComponent(GameObjectComponent component);

    Stream<GameObjectComponent> componentsStream();
    
}