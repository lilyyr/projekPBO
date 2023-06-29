package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TitleScreen extends Screen {
    private Texture background;
    public TitleScreen(GameScreenManager gsm) {
        super(gsm);
        cam.setToOrtho(false, 1280, 720);
        background = new Texture("main_bg.png");
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()) {
            gsm.set(new GameScreen(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0,0);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
    }
}

