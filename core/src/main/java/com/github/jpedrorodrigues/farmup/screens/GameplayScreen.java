package com.github.jpedrorodrigues.farmup.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.jpedrorodrigues.farmup.Program;
import com.github.jpedrorodrigues.farmup.domain.Meowscle;

public class GameplayScreen implements Screen {
    private final Program game;

    private OrthographicCamera camera;
    private Viewport viewport;
    
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;

    private Meowscle player;

    public GameplayScreen(Program game) {
        this.game = game;
    }

    @Override
    public void show() {
        this.camera = new OrthographicCamera();
        this.camera.zoom = 0.5f;
        this.viewport = new ExtendViewport(480, 270, this.camera);
        
        this.map = new TmxMapLoader().load("map/map.tmx");
        this.mapRenderer = new OrthogonalTiledMapRenderer(this.map);

        this.player = new Meowscle(34 * 16 + 80, (80 - 30) * 16 - 90);
        this.player.setMap(this.map);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.55f, 0.77f, 0.83f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        player.update(delta);
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
            processInteraction();
        }

        camera.position.set(player.getBounds().x + player.getBounds().width / 2, player.getBounds().y + player.getBounds().height / 2, 0);
        this.camera.update();

        this.mapRenderer.setView(this.camera);
        this.mapRenderer.render();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();
    }

    private void processInteraction() {
        // Find the grid pos the player is facing
        float px = player.getBounds().x + player.getBounds().width / 2;
        float py = player.getBounds().y + 4f;
        
        float interactX = px;
        float interactY = py;
        
        switch (player.getDirection()) {
            case UP: interactY += 16; break;
            case DOWN: interactY -= 16; break;
            case LEFT: interactX -= 16; break;
            case RIGHT: interactX += 16; break;
        }
        
        int gridX = (int) (interactX / 16f);
        int gridY = (int) (interactY / 16f);
        
        MapLayer interactiveLayer = map.getLayers().get("Interactive");
        if (interactiveLayer == null) return;
        
        boolean foundCropObject = false;
        
        for (MapObject obj : interactiveLayer.getObjects()) {
            if (obj instanceof RectangleMapObject) {
                RectangleMapObject rectObj = (RectangleMapObject) obj;
                float objX = rectObj.getRectangle().x;
                float objY = rectObj.getRectangle().y;
                
                int objGridX = (int) (objX / 16f);
                int objGridY = (int) (objY / 16f);
                
                if (gridX == objGridX && gridY == objGridY && rectObj.getName() != null && rectObj.getName().startsWith("crop")) {
                    foundCropObject = true;
                    break;
                }
            }
        }
        
        if (foundCropObject) {
            TiledMapTileLayer natureLayer = (TiledMapTileLayer) map.getLayers().get("nature");
            if (natureLayer != null) {
                TiledMapTileLayer.Cell cropCell = natureLayer.getCell(gridX, gridY);
                if (cropCell != null && cropCell.getTile() != null) {
                    int currentTileId = cropCell.getTile().getId();
                    
                    int newTileId = currentTileId;
                    if (currentTileId >= 197 && currentTileId < 202) {
                        newTileId = currentTileId + 1;
                    } else if (currentTileId >= 203 && currentTileId < 208) {
                        newTileId = currentTileId + 1;
                    }
                    
                    if (newTileId != currentTileId) {
                        cropCell.setTile(map.getTileSets().getTile(newTileId));
                    }
                }
            }
        }
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
        if (this.player != null) this.player.dispose();
    }
}