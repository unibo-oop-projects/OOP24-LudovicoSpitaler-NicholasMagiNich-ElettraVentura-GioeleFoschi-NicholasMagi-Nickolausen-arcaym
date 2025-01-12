package arcaym.view.objects;

import java.io.IOException;

public class VerticalObstacleView extends GameObjectSwingView {
    private final String ICON_PATH = getResourcesPath() + "v_obstacle.png";
    
    public VerticalObstacleView() {
        try {
            loadSprite(ICON_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
