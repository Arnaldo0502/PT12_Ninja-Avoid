package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {

    final Shuriken game;

    private OrthographicCamera camera;
    private Texture background;
    private Texture logo;
    private BitmapFont titleFont;
    private BitmapFont menuFont;
    private GlyphLayout layout;
    private float timeElapsed;

    public MainMenuScreen(final Shuriken game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        background = new Texture(Gdx.files.internal("background.png"));
        logo = new Texture(Gdx.files.internal("shuriken.png"));

        try {
            // Configuració de fonts amb FreeType
            com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator generator =
                    new com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
            com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter parameter =
                    new com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter();

            // Font per al títol
            parameter.size = 56;
            parameter.color = Color.GOLD;
            parameter.borderWidth = 3;
            parameter.borderColor = Color.BLACK;
            titleFont = generator.generateFont(parameter);

            // Font per al menú
            parameter.size = 24;
            parameter.color = Color.WHITE;
            parameter.borderWidth = 1;
            menuFont = generator.generateFont(parameter);

            generator.dispose();
        } catch (Exception e) {
            // Fallback si no hi ha font.ttf
            titleFont = new BitmapFont();
            titleFont.getData().setScale(3.0f);
            titleFont.setColor(Color.GOLD);

            menuFont = new BitmapFont();
            menuFont.getData().setScale(1.5f);
            menuFont.setColor(Color.WHITE);
            Gdx.app.error("MainMenuScreen", "No s'ha pogut carregar font.ttf, usant fonts per defecte.");
        }

        layout = new GlyphLayout();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        timeElapsed += delta;

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        game.batch.draw(background, 0, 0, 800, 480);

        float logoSize = 128;
        game.batch.draw(logo, 400 - logoSize / 2, 300, logoSize, logoSize);

        layout.setText(titleFont, "Ninja Avoid");
        titleFont.draw(game.batch, layout, 400 - layout.width / 2, 280);

        float alpha = (float) Math.abs(Math.sin(timeElapsed * 2));
        menuFont.getColor().a = alpha;
        layout.setText(menuFont, "FES CLIC PER COMENÇAR!");
        menuFont.draw(game.batch, layout, 400 - layout.width / 2, 150);

        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        // Liberamos los recursos para evitar fugas de memoria
        background.dispose();
        logo.dispose();
        titleFont.dispose();
        menuFont.dispose();
    }
}
