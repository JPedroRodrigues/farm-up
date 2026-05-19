package com.github.jpedrorodrigues.farmup.domain;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.jpedrorodrigues.farmup.domain.Enum.PlayerTool;
import com.github.jpedrorodrigues.farmup.domain.interfaces.Interactable;

public class Tree extends GameObject implements Interactable {
    private int health = 3;
    private TextureRegion stumpTexture;
    private boolean isStump = false;

    public Tree(float x, float y, TextureRegion treeTexture, TextureRegion stumpTexture) {
        super(treeTexture, x, y, 32, 32);
        this.stumpTexture = stumpTexture;
    }

    @Override
    public void onInteract(PlayerTool tool) {
        if (tool == PlayerTool.AXE && !this.isStump) {
            this.health--;
            System.out.println("Chopped tree! Health remaining: " + this.health);

            if (this.health <= 0) {
                this.isStump = true;
                this.sprite.setRegion(this.stumpTexture);
                System.out.println("Tree has been cut down to a stump!");

            }
        }
    }

    @Override
    public void update(float dt) {
    }
}
