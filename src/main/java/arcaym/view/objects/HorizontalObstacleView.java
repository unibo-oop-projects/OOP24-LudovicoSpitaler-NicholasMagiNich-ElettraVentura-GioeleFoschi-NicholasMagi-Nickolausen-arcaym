package arcaym.view.objects;

import java.io.IOException;

public class HorizontalObstacleView extends GameObjectSwingView {
    private final String ICON_PATH = getResourcesPath() + "h_obstacle.png";
    
    public HorizontalObstacleView() {
        try {
            loadSprite(ICON_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
