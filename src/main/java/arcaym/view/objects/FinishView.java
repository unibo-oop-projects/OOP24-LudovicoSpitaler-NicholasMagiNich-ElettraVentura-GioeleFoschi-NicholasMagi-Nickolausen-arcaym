package arcaym.view.objects;

import java.io.IOException;

public class FinishView extends GameObjectSwingView {
    private final String ICON_PATH = getResourcesPath() + "finish_tile.png";
    
    public FinishView() {
        try {
            loadSprite(ICON_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
