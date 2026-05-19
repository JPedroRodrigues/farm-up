package com.github.jpedrorodrigues.farmup.domain;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public abstract class GameSprite {
    protected Sprite sprite;
    protected Rectangle bounds;

    public GameSprite(TextureRegion region, float x, float y, float width, float height) {
        this.sprite = new Sprite(region);
        this.sprite.setPosition(x, y);
        this.sprite.setSize(width, height);

        this.bounds = new Rectangle(x, y, width, height);

    }

    public abstract void update(float dt);

    public void draw(SpriteBatch batch) {
        this.sprite.draw(batch);
    }

    public Rectangle getBounds() {
        bounds.setPosition(sprite.getX(), sprite.getY());
        return this.bounds;
    }

    public void setPosition(float x, float y) {
        this.sprite.setPosition(x, y);
    }
}
