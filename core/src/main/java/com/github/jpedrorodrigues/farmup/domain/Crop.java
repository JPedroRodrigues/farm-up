package com.github.jpedrorodrigues.farmup.domain;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Crop extends GameSprite {
    private int maxStages;
    private int growthStage = 0;
    private float x, y, width, height;
    
    private Sprite sprite;
    private TextureRegion[] growthFrames;

    public Crop(TextureRegion[] growthFrames, float x, float y, float width, float height) {
        super(growthFrames[0], x, y, width, height);

        this.growthFrames = growthFrames;
        this.maxStages = growthFrames.length;

        this.x = x;
        this.y = y;
        
        this.width = width;
        this.height = height;
        
        this.sprite = new Sprite(this.growthFrames[0]);
        this.sprite.setPosition(this.x, this.y);
        this.sprite.setSize(this.width, this.height);
    }

    public void water() {
        this.grow();
    }

    public void grow() {
        if (this.growthStage < this.maxStages - 1) {
            this.growthStage++;
            this.sprite.setRegion(this.growthFrames[this.growthStage]);
        }
    }

    public boolean isHarvestable() {
        return this.growthStage == this.maxStages - 1;
    }

    public void update(float dt) {
    }

    public void draw(SpriteBatch batch) {
        this.sprite.draw(batch);
    }
}
