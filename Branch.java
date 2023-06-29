package sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import java.util.Random;

public class Branch {
    public static final int BRANCH_WIDTH = 52;

    private static final int FLUCTUATION = 130;
    private static final int BRANCH_GAP = 125;
    private static final int LOWEST_OPENING = 120;
    private Texture topBranch, bottomBranch;
    private Vector2 posTopBranch, posBotBranch;
    private Rectangle boundsTop, boundsBot;
    private Random rand;
    private boolean passed;
    private boolean scored;

    public Branch(float x){
        topBranch = new Texture("top_branch.png");
        bottomBranch = new Texture("bottom_branch.png");
        rand = new Random();


        posTopBranch = new Vector2(x, rand.nextInt(FLUCTUATION) + BRANCH_GAP + LOWEST_OPENING);
        posBotBranch = new Vector2(x, posTopBranch.y - BRANCH_GAP - bottomBranch.getHeight());

        boundsTop = new Rectangle(posTopBranch.x, posTopBranch.y, topBranch.getWidth(), topBranch.getHeight());
        boundsBot = new Rectangle(posBotBranch.x, posBotBranch.y, bottomBranch.getWidth(), bottomBranch.getHeight());

    }

    public boolean isScored(){
        return scored;
    }

    public void setScored(boolean scored){
        this.scored = scored;
    }

    public Texture getBottomBranch() {
        return bottomBranch;
    }
    public Texture getTopBranch() {
        return topBranch;
    }
    public Vector2 getPosTopBranch() {
        return posTopBranch;
    }
    public Vector2 getPosBotBranch() {
        return posBotBranch;
    }

    public void reposition(float x){
        posTopBranch.set(x, rand.nextInt(FLUCTUATION) + BRANCH_GAP + LOWEST_OPENING);
        posBotBranch.set(x, posTopBranch.y - BRANCH_GAP - bottomBranch.getHeight());
        boundsTop.setPosition(posTopBranch.x, posTopBranch.y);
        boundsBot.setPosition(posBotBranch.x, posBotBranch.y);
    }

    public boolean collides(Rectangle player){
        return player.overlaps(boundsTop) || player.overlaps(boundsBot);
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }


    public void dispose(){
        topBranch.dispose();
        bottomBranch.dispose();
    }
}