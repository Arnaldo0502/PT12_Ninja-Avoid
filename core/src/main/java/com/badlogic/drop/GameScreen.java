package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class GameScreen implements Screen {
    final Shuriken game;
    final Stage stage;
    final AssetManager assetManager = new AssetManager();

    BitmapFont font;
    Music rainMusic;
    Player player;
    Texture fons;
    DropletHandler raindrops;

    private int score = 0;
    private float scoreTimer = 0;

    public GameScreen(final Shuriken game) {
        this.game = game;

        try {
            // Configuració de la font amb FreeType
            com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator generator =
                    new com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
            com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter parameter =
                    new com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.size = 28;
            parameter.color = com.badlogic.gdx.graphics.Color.WHITE;
            parameter.borderWidth = 2;
            parameter.borderColor = com.badlogic.gdx.graphics.Color.BLACK;
            font = generator.generateFont(parameter);
            generator.dispose();
        } catch (Exception e) {
            // Fallback si no es troba el fitxer ttf o hi ha un error
            font = new BitmapFont();
            font.getData().setScale(2.0f);
            Gdx.app.error("GameScreen", "No s'ha pogut carregar font.ttf, usant font per defecte: " + e.getMessage());
        }

        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        stage = new Stage(new StretchViewport(800, 480, camera));
    }

    @Override
    public void show() {
        loadAssets();
        rainMusic = assetManager.get(AssetDescriptors.rainMusic);
        rainMusic.setLooping(true);

        raindrops = new DropletHandler(assetManager);
        player = new Player(assetManager.get(AssetDescriptors.bucketTexture));

        stage.addActor(raindrops);
        stage.addActor(player);

        Gdx.input.setInputProcessor(new InputHandler(this));
        rainMusic.play();
    }

    @Override
    public void render(float delta) {
        // 1. Limpiamos la pantalla (Fondo azul oscuro por si el asset no carga)
        ScreenUtils.clear(0, 0, 0.2f, 1);

        fons = assetManager.get(AssetDescriptors.background);

        // Actualització de la puntuació cada 2 segons
        scoreTimer += delta;
        if (scoreTimer >= 2f) {
            score++;
            scoreTimer = 0;
        }

        // 2. Lógica de colisión: Si toca un shuriken, aplicar daño
        if (raindrops.collectDroplet(player)) {
            player.aplicarDany();

            // Si se queda sin vidas, Game Over (volvemos al menú)
            if (player.getVidas() <= 0) {
                game.setScreen(new MainMenuScreen(game));
                dispose();
                return;
            }
        }

        // 3. Dibujamos el fondo y el HUD (Interfaz)
        stage.getBatch().begin();
        stage.getBatch().draw(fons, 0, 0, 800, 480);

        // Cambiamos el color a rojo si solo queda 1 vida
        if (player.getVidas() == 1) {
            font.setColor(com.badlogic.gdx.graphics.Color.RED);
        } else {
            font.setColor(com.badlogic.gdx.graphics.Color.WHITE);
        }

        font.draw(stage.getBatch(), "VIDES: " + player.getVidas(), 20, 460);
        font.draw(stage.getBatch(), "PUNTS: " + score, 600, 460);
        stage.getBatch().end();

        // 4. Actualizar y dibujar los actores (Ninja y Shurikens)
        stage.act(delta);
        stage.draw();
    }

    private void loadAssets() {
        assetManager.load(AssetDescriptors.bucketTexture);
        assetManager.load(AssetDescriptors.dropletTexture);
        assetManager.load(AssetDescriptors.background);
        assetManager.load(AssetDescriptors.dropSound);
        assetManager.load(AssetDescriptors.rainMusic);
        assetManager.finishLoading();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {}
    @Override
    public void pause() {}
    @Override
    public void resume() {}

    @Override
    public void dispose() {
        assetManager.dispose();
        font.dispose();
        stage.dispose();
    }
}
