package arcaym.view.objects;

import java.io.IOException;

public class StaticObstacleView extends GameObjectSwingView {
    private final String ICON_PATH = getResourcesPath() + "static_obstacle.png";
    
    public StaticObstacleView() {
        try {
            loadSprite(ICON_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
