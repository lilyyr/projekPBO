package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import sprites.Branch;
import sprites.Jerrick;
import sprites.Scoreboard;


public class GameScreen extends Screen {
    private static final int BRANCH_SPACING = 150;
    private static final int BRANCH_COUNT = 100;
    private static final int GROUND_Y_OFFSET = -50;
    private Jerrick jerrick;
    private Texture bg;
    private Texture ground;
    private Texture pauseButton;
    private Vector2 groundPos1, groundPos2;
    private Array<Branch> branches;
    private boolean paused;
    private Scoreboard scoreboard;
    private Texture shield;
    private boolean shieldActive;
    private float shieldTimer;
    private static final float SHIELD_DURATION = 3.0f; //waktu durasi shield
    private boolean shieldActivatedOnce; //limit shield 1
    private Texture regularJerrickTexture;

    public GameScreen(GameScreenManager gsm) {
        super(gsm);
        jerrick = new Jerrick(50, 300);
        cam.setToOrtho(false, 960, 540);
        bg = new Texture("bg.png");
        ground = new Texture("ground.png");
        pauseButton = new Texture("pause.png");
        shield = new Texture("shield.png");
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth, GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth) + ground.getWidth(), GROUND_Y_OFFSET);


        branches = new Array<Branch>();

        for(int i = 1; i <= BRANCH_COUNT; i++){
            branches.add(new Branch(i * (BRANCH_SPACING + Branch.BRANCH_WIDTH)));
        }

        scoreboard = new Scoreboard(new Vector2(400,500));
        shieldActive = false;
        shieldActivatedOnce = false;
        regularJerrickTexture = jerrick.getTexture().getTexture();

    }

    @Override
    protected void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            jerrick.jump();
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            paused = true;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            if (!shieldActivatedOnce) { //limit shield
                if (!shieldActive) {
                    jerrick.activateShield();
                    shieldActive = true;
                    shieldActivatedOnce = true;
                    shieldTimer = SHIELD_DURATION;
                } else {
                    jerrick.deactivateShield();
                    shieldActive = false;
                }
            }
        }
    }
    @Override
    public void update(float dt) {
        if (paused) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
                paused = false;
            }
        } else {
            handleInput();
            updateGround();
            jerrick.update(dt);
            cam.position.x = jerrick.getPosition().x + 80;
            updateScore();

            if (shieldActive) {
                shieldTimer -= dt;
                if (shieldTimer <= 0) {
                    shieldActive = false;
                }
            }
            for (int i = 0; i < branches.size; i++) {
                Branch tube = branches.get(i);

                if (cam.position.x - (cam.viewportWidth / 2) > tube.getPosTopBranch().x + tube.getTopBranch().getWidth()) {
                    tube.reposition(tube.getPosTopBranch().x + ((Branch.BRANCH_WIDTH + BRANCH_SPACING) * BRANCH_COUNT));
                }

                if (!shieldActive&&tube.collides(jerrick.getBounds())) {
                    gsm.set(new TitleScreen(gsm));
                }
            }
            if (!shieldActive&&jerrick.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET) {
                gsm.set(new TitleScreen(gsm));
            }
        }

        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0);
        sb.draw(jerrick.getTexture(), jerrick.getPosition().x, jerrick.getPosition().y);
        for(Branch branch : branches) {
            sb.draw(branch.getTopBranch(), branch.getPosTopBranch().x, branch.getPosTopBranch().y);
            sb.draw(branch.getBottomBranch(), branch.getPosBotBranch().x, branch.getPosBotBranch().y);


        }
        if (shieldActive) {
            sb.draw(shield, jerrick.getPosition().x, jerrick.getPosition().y);
            sb.draw(jerrick.getTexture(), jerrick.getPosition().x, jerrick.getPosition().y);
        }

        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);

        scoreboard.render(sb,cam.position.x);


        if (paused) {
            renderPausedImage(sb);
        }

        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        jerrick.dispose();
        ground.dispose();

        for(Branch branch : branches) {
            branch.dispose();

        }
        shield.dispose();
    }

    private void updateGround() {
        if (cam.position.x - (cam.viewportWidth / 2) > groundPos1.x + ground.getWidth()) {
            groundPos1.add(ground.getWidth() * 2, 0);
        }
        if (cam.position.x - (cam.viewportWidth / 2) > groundPos2.x + ground.getWidth()) {
            groundPos2.add(ground.getWidth() * 2, 0);
        }
    }
    private void updateScore() {
        Branch closestBranch = null;
        float closestTubeDistance = Float.MAX_VALUE;

        for (Branch branch : branches) {
            if (jerrick.getPosition().x > branch.getPosTopBranch().x + branch.getTopBranch().getWidth()&& !branch.isScored()) {
                float distance = jerrick.getPosition().x - (branch.getPosTopBranch().x + branch.getTopBranch().getWidth());
                if (distance < closestTubeDistance) {
                    closestBranch = branch;
                    closestTubeDistance = distance;
                }
            }
        }

        if (closestBranch != null) {
            scoreboard.incrementScore();
            closestBranch.setScored(true);
        }
    }

    private void renderPausedImage(SpriteBatch sb) {
        sb.draw(pauseButton, cam.position.x - pauseButton.getWidth() / 2, cam.position.y - pauseButton.getHeight() / 2);
    }

}