package com.github.jpedrorodrigues.farmup.domain.interfaces;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.github.jpedrorodrigues.farmup.domain.Enum.PlayerTool;

public interface Interactable {
    void onInteract(PlayerTool tool);
    void update(float delta);
    void draw(SpriteBatch batch);
    Rectangle getBounds();
}
