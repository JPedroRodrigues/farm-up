package com.github.jpedrorodrigues.farmup.domain;

import com.badlogic.gdx.graphics.Texture;
import com.github.jpedrorodrigues.farmup.domain.Enum.PlayerTool;
import com.github.jpedrorodrigues.farmup.domain.interfaces.Interactable;

public class SoilPatch extends GameObject implements Interactable {
    private boolean isPlowed = false;
    private boolean isWatered = false;
    private Crop currentCrop = null;

    private Texture unplowedTexture;
    private Texture plowedTexture;
    private Texture wateredTexture;

    public SoilPatch(float x, float y, Texture unplowed, Texture plowed, Texture watered) {
        super(unplowed, x, y, 16, 16); 
        this.unplowedTexture = unplowed;
        this.plowedTexture = plowed;
        this.wateredTexture = watered;
    }

    public void plantCrop(Crop crop) {
        this.currentCrop = crop;
    }

    @Override
    public void onInteract(PlayerTool tool) {
        if (tool == PlayerTool.HOE && !this.isPlowed) {
            this.isPlowed = true;
            this.sprite.setTexture(this.plowedTexture);
            System.out.println("Soil has been plowed!");
        } else if (tool == PlayerTool.WATERING_CAN && this.isPlowed) {
            this.isWatered = true;
            this.sprite.setTexture(this.wateredTexture);
            System.out.println("Soil has been watered!");
            
            if (this.currentCrop != null) {
                this.currentCrop.water();
            }
        }
    }

    @Override
    public void update(float dt) {
        if (this.currentCrop != null) {
            this.currentCrop.update(dt);
        }
    }

    @Override
    public void draw(com.badlogic.gdx.graphics.g2d.SpriteBatch batch) {
        super.draw(batch);
        if (this.currentCrop != null) {
            this.currentCrop.draw(batch);
        }
    }
}
