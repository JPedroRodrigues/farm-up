package com.github.jpedrorodrigues.farmup.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import com.github.jpedrorodrigues.farmup.Program;

public class MainMenuScreen implements Screen {
    final Program game;

    public MainMenuScreen(final Program game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        this.game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        
        game.batch.begin();

        game.font.draw(game.batch, "Welcome!", 1, 1.5f);
        game.font.draw(game.batch, "Tap anywhere to begin", 1, 1);

        game.batch.end();

        // if (Gdx.input.isTouched()) {
        //     game.setScreen(new GameScreen(game));
        //     dispose();
        // }
    }

    @Override
    public void show() {
        // Called when this screen becomes the current screen for a Game.
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override
    public void pause() {
        // Called when the application is paused.
    }

    @Override
    public void resume() {
        // Called when the application is resumed from a paused state.
    }

    @Override
    public void hide() {
        // Called when this screen is no longer the current screen for a Game.
    }

    @Override
    public void dispose() {
        // Release assets when no longer needed.
    }
}