package sprites;

import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
public class Scoreboard {
    private int score = 0;
    private BitmapFont font;
    private Vector2 position;


    public Scoreboard(Vector2 position) {
        font = new BitmapFont();
        font.getData().setScale(2);
        this.position = position;
    }

    public void incrementScore() {
            score++;

    }

    public void render(SpriteBatch sb,float camPositionX) {
        String scoreText = "Score: " + score;
        float textWidth = font.draw(sb, scoreText, 0, 0).width;
        float adjustedX = position.x + camPositionX - (textWidth / 2);
        float adjustedY = position.y;
        font.draw(sb, scoreText, adjustedX, adjustedY);
    }
    public void dispose() {
        font.dispose();
    }
}
