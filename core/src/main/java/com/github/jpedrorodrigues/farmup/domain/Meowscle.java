package com.github.jpedrorodrigues.farmup.domain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Meowscle extends GameObject {
    private Texture walkSheet;
    private Texture actionSheet;
    
    private Animation<TextureRegion> walkDown, walkUp, walkLeft, walkRight;
    private Animation<TextureRegion> plowDown, plowUp, plowLeft, plowRight;
    private Animation<TextureRegion> axeDown, axeUp, axeLeft, axeRight;
    private Animation<TextureRegion> waterDown, waterUp, waterLeft, waterRight;

    private float speed = 100f;
    private float stateTime = 0f;
    private String direction = "DOWN";

    private String currentAction = "None";
    private float actionTimer = 0f;

    public Meowscle(float x, float y) {
        super(new Texture("Characters/Basic Charakter Spritesheet.png"), x, y, 48, 48);
        
        walkSheet = super.sprite.getTexture();
        actionSheet = new Texture("Characters/Basic Charakter Actions.png");
        
        TextureRegion[][] walkFrames = TextureRegion.split(walkSheet, 48, 48);
        walkDown = new Animation<>(0.15f, walkFrames[0]);
        walkUp = new Animation<>(0.15f, walkFrames[1]);
        walkLeft = new Animation<>(0.15f, walkFrames[2]);
        walkRight = new Animation<>(0.15f, walkFrames[3]);

        TextureRegion[][] actionFrames = TextureRegion.split(actionSheet, 48, 48);
        if (actionFrames.length >= 12 && actionFrames[0].length >= 2) {
            plowDown = new Animation<>(0.25f, actionFrames[0][0], actionFrames[0][1]);
            plowUp = new Animation<>(0.25f, actionFrames[1][0], actionFrames[1][1]);
            plowLeft = new Animation<>(0.25f, actionFrames[2][0], actionFrames[2][1]);
            plowRight = new Animation<>(0.25f, actionFrames[3][0], actionFrames[3][1]);
            
            axeDown = new Animation<>(0.25f, actionFrames[4][0], actionFrames[4][1]);
            axeUp = new Animation<>(0.25f, actionFrames[5][0], actionFrames[5][1]);
            axeLeft = new Animation<>(0.25f, actionFrames[6][0], actionFrames[6][1]);
            axeRight = new Animation<>(0.25f, actionFrames[7][0], actionFrames[7][1]);
            
            waterDown = new Animation<>(0.25f, actionFrames[8][0], actionFrames[8][1]);
            waterUp = new Animation<>(0.25f, actionFrames[9][0], actionFrames[9][1]);
            waterLeft = new Animation<>(0.25f, actionFrames[10][0], actionFrames[10][1]);
            waterRight = new Animation<>(0.25f, actionFrames[11][0], actionFrames[11][1]);
        }

        super.sprite.setRegion(walkDown.getKeyFrame(0));
    }

    @Override
    public void update(float dt) {
        boolean isMoving = false;
        stateTime += dt;
        
        if (actionTimer > 0) {
            actionTimer -= dt;
            
            TextureRegion currentFrame = null;
            if (currentAction.equals("Watering")) {
                if (waterDown != null) {
                    switch (direction) {
                        case "UP": currentFrame = waterUp.getKeyFrame(stateTime, true); break;
                        case "DOWN": currentFrame = waterDown.getKeyFrame(stateTime, true); break;
                        case "LEFT": currentFrame = waterLeft.getKeyFrame(stateTime, true); break;
                        case "RIGHT": currentFrame = waterRight.getKeyFrame(stateTime, true); break;
                    }
                }
            } else if (currentAction.equals("Plowing")) {
                if (plowDown != null) { 
                    switch (direction) {
                        case "UP": currentFrame = plowUp.getKeyFrame(stateTime, true); break;
                        case "DOWN": currentFrame = plowDown.getKeyFrame(stateTime, true); break;
                        case "LEFT": currentFrame = plowLeft.getKeyFrame(stateTime, true); break;
                        case "RIGHT": currentFrame = plowRight.getKeyFrame(stateTime, true); break;
                    }
                }
            } else if (currentAction.equals("Using Axe")) {
                if (axeDown != null) {
                    switch (direction) {
                        case "UP": currentFrame = axeUp.getKeyFrame(stateTime, true); break;
                        case "DOWN": currentFrame = axeDown.getKeyFrame(stateTime, true); break;
                        case "LEFT": currentFrame = axeLeft.getKeyFrame(stateTime, true); break;
                        case "RIGHT": currentFrame = axeRight.getKeyFrame(stateTime, true); break;
                    }
                }
            }
            
            if (currentFrame != null) {
                super.sprite.setRegion(currentFrame);
            }

            if (actionTimer <= 0) {
                currentAction = "None";
                System.out.println("Action finished");
            }
        } else {
            float dx = 0;
            float dy = 0;

            if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
                dy += speed * dt;
                direction = "UP";
                isMoving = true;
            } else if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                dy -= speed * dt;
                direction = "DOWN";
                isMoving = true;
            } else if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                dx -= speed * dt;
                direction = "LEFT";
                isMoving = true;
            } else if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                dx += speed * dt;
                direction = "RIGHT";
                isMoving = true;
            }
            
            this.setPosition(this.sprite.getX() + dx, this.sprite.getY() + dy);

            TextureRegion currentFrame = walkDown.getKeyFrame(0);
            if (isMoving) {
                switch (direction) {
                    case "UP": currentFrame = walkUp.getKeyFrame(stateTime, true); break;
                    case "DOWN": currentFrame = walkDown.getKeyFrame(stateTime, true); break;
                    case "LEFT": currentFrame = walkLeft.getKeyFrame(stateTime, true); break;
                    case "RIGHT": currentFrame = walkRight.getKeyFrame(stateTime, true); break;
                }
            } else {
                switch (direction) {
                    case "UP": currentFrame = walkUp.getKeyFrame(0); break;
                    case "DOWN": currentFrame = walkDown.getKeyFrame(0); break;
                    case "LEFT": currentFrame = walkLeft.getKeyFrame(0); break;
                    case "RIGHT": currentFrame = walkRight.getKeyFrame(0); break;
                }
            }
            super.sprite.setRegion(currentFrame);

            if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
                currentAction = "Watering";
                actionTimer = 0.5f;
                stateTime = 0f;
                System.out.println("Meowscle is Watering!");
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.N)) {
                currentAction = "Plowing";
                actionTimer = 0.5f;
                stateTime = 0f;
                System.out.println("Meowscle is Plowing the soil!");
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
                currentAction = "Using Axe";
                actionTimer = 0.5f;
                stateTime = 0f;
                System.out.println("Meowscle is using his Axe!");
            }
        }
    }

    public void dispose() {
        if (walkSheet != null) walkSheet.dispose();
        if (actionSheet != null) actionSheet.dispose();
    }
}
