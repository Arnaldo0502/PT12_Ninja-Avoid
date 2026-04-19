package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class SplashScreen implements Screen {
    final Shuriken game;
    private OrthographicCamera camera;
    private Texture logo;
    private float displayTime = 0;
    private final float MAX_DISPLAY_TIME = 3.0f; // Segundos que durará la pantalla

    public SplashScreen(final Shuriken game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        logo = new Texture(Gdx.files.internal("icon_game.png"));
    }

    @Override
    public void render(float delta) {
        // Fondo negro para la splash
        ScreenUtils.clear(0, 0, 0, 1);

        displayTime += delta;

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        // Dibujamos el logo centrado (suponiendo tamaño 256x256, ajusta si es necesario)
        float size = 256;
        game.batch.draw(logo, 400 - size / 2, 240 - size / 2, size, size);
        game.batch.end();

        // Si pasa el tiempo o el usuario toca la pantalla, vamos al menú
        if (displayTime >= MAX_DISPLAY_TIME || Gdx.input.isTouched()) {
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        logo.dispose();
    }
}
