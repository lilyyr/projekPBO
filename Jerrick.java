package sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Jerrick {
    private static final int GRAVITY = -8;
    private static int MOVEMENT = 200;
    private Vector3 position;
    private Vector3 velocity;
    private static Rectangle bounds;
    private Animation jerrickAnimation;
    private Texture texture;
    private Texture shieldTexture;
    private Animation shieldAnimation;
    private Sound flap;
    private boolean shieldActive;
    private float shieldCooldownTimer;
    private Texture regularJerrickTexture;


    public Jerrick(int x, int y) {
        position = new Vector3(0 - x * 2, y, 0);
        velocity = new Vector3(0, 0, 0);
        texture = new Texture("jerrick_animation.png");
        jerrickAnimation = new Animation(new TextureRegion(texture), 3, 0.5f);
        bounds = new Rectangle(x, y, texture.getWidth() / 3, texture.getHeight());
        flap = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));
        shieldTexture = new Texture("shield_animation.png");
        regularJerrickTexture = new Texture("jerrick.png");
        shieldAnimation = new Animation(new TextureRegion(shieldTexture), 3, 0.5f);
        shieldActive = false;
        shieldCooldownTimer = 0;
    }

    public void update(float dt) {
        if (shieldCooldownTimer > 0) {
            shieldCooldownTimer -= dt;
            shieldAnimation.update(dt);
            if (shieldCooldownTimer < 0)
                shieldCooldownTimer = 0;
        }
        jerrickAnimation.update(dt);
        if (position.y > 0)
            velocity.add(0, GRAVITY, 0);
        velocity.scl(dt);
        position.add(MOVEMENT * dt, velocity.y, 0);
        if (position.y < 0)
            position.y = 0;

        velocity.scl(1 / dt);
        bounds.setPosition(position.x, position.y);

    }

    public void activateShield() {
        if (shieldCooldownTimer <= 0) {
            shieldActive = true;
            shieldCooldownTimer = 8;
        }
    }

    public void deactivateShield() {
        texture = regularJerrickTexture;
    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getTexture() {
        if (!shieldActive) {
            shieldAnimation.getFrame();
        } else if (shieldCooldownTimer <= 0) {
            shieldActive = true;
            shieldCooldownTimer = 8;
        }return jerrickAnimation.getFrame();
    }
    public void jump() {
        velocity.y = 250;
        flap.play(0.5f);
    }

    public static Rectangle getBounds() {
        return bounds;
    }

    public void dispose() {
        texture.dispose();
        flap.dispose();
        shieldTexture.dispose();
    }

}

