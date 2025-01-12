package arcaym.view.objects;

import java.io.IOException;

public class StartView extends GameObjectSwingView {
    private final String ICON_PATH = getResourcesPath() + "start_tile.png";
    
    public StartView() {
        try {
            loadSprite(ICON_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
