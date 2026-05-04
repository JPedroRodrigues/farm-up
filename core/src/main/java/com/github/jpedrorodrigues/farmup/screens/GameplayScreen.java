package com.github.jpedrorodrigues.farmup.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.jpedrorodrigues.farmup.Program;

public class GameplayScreen implements Screen {
    private final Program game;

    private OrthographicCamera camera;
    private Viewport viewport;
    
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;

    public GameplayScreen(Program game) {
        this.game = game;
    }

    @Override
    public void show() {
        this.camera = new OrthographicCamera();
        this.viewport = new ExtendViewport(480, 270, this.camera);
        
        this.map = new TmxMapLoader().load("map/map.tmx");
        this.mapRenderer = new OrthogonalTiledMapRenderer(this.map);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.55f, 0.77f, 0.83f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.camera.update();

        this.mapRenderer.setView(this.camera);
        this.mapRenderer.render();
    }

    @Override
    public void resize(int width, int height) {
        this.viewport.update(width, height, true);
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
        if (this.map != null) this.map.dispose();
        if (this.mapRenderer != null) this.mapRenderer.dispose();
    }
}