package com.badlogic.drop;

import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * La galleda, un actor de tipus image
 */
public class Player extends Image {
    private int vidas = 3;

    /**
     * El constructor carrega la textura i crida al constructor de la superclasse Image
     * Estableix la seva posició centrat a la part inferior de la pantalla
     */
    public Player(Texture texture) {
        super(texture);
        setPosition(800 / 2 - 100 / 2, 0);
        setSize(100, 100);
    }


    public int getVidas() {
        return vidas;
    }

    public void aplicarDany() {
        this.vidas--;
    }
}
