package arcaym.view.objects;

import java.io.IOException;

public class CoinView extends GameObjectSwingView {
    private final String ICON_PATH = getResourcesPath() + "coin.png";

    public CoinView() {
        try {
            loadSprite(ICON_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
