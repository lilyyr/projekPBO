package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import screens.GameScreen;
import screens.GameScreenManager;
import screens.TitleScreen;

public class JerrickDemo extends ApplicationAdapter {
    public static final int WIDTH = 1820;
    public static final int HEIGHT = 1080;

    public static final String TITLE = "Jerrick Go!";
    private GameScreenManager gsm;
    private SpriteBatch batch;

    private Music music;


    @Override
    public void create () {
        batch = new SpriteBatch();
        gsm = new GameScreenManager();
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.setLooping(true);
        music.setVolume(0.1f);
        music.play();
        Gdx.gl.glClearColor(1, 0, 0, 1);
        gsm.push(new TitleScreen(gsm));
    }

    @Override
    public void render () {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(batch);
    }

    @Override
    public void dispose() {
        super.dispose();
        music.dispose();
    }

}