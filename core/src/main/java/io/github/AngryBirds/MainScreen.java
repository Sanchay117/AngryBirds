package io.github.AngryBirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.graphics.Color;

public class MainScreen extends ScreenAdapter {

    private final Main game;
    private int viewHeight = Gdx.graphics.getHeight();
    private int viewWidth = Gdx.graphics.getWidth();

    private Stage stage;
    private Texture backBtnTexture;
    private Texture lvlBtnTexture;
    private Texture lockTexture;
    private ImageButton backBtn;
    private ImageButton lvlBtn1;
    private ImageButton lvlBtn2;
    private ImageButton lvlBtn3;
    private ImageButton lvlBtn4;
    private ImageButton lvlBtn5;

    public MainScreen(Main game) {
        this.game = game;
        game.background = new Texture("MainScreenBG.jpg");
    }

    @Override
    public void show() {
        stage = new Stage();

        backBtnTexture = new Texture("back.png");
        lvlBtnTexture = new Texture("lvlBG.png");
        lockTexture = new Texture("lock.png");

        Skin skin = new Skin();
        skin.add("back", backBtnTexture);
        skin.add("lvl", lvlBtnTexture);

        ImageButton.ImageButtonStyle playStyle = new ImageButton.ImageButtonStyle();
        ImageButton.ImageButtonStyle lvlStyle = new ImageButton.ImageButtonStyle();
        playStyle.imageUp = new TextureRegionDrawable(new TextureRegion(backBtnTexture));
        lvlStyle.imageUp = new TextureRegionDrawable(new TextureRegion(lvlBtnTexture));
        backBtn = new ImageButton(playStyle);
        lvlBtn1 = new ImageButton(lvlStyle);
        lvlBtn2 = new ImageButton(lvlStyle);
        lvlBtn3 = new ImageButton(lvlStyle);
        lvlBtn4 = new ImageButton(lvlStyle);
        lvlBtn5 = new ImageButton(lvlStyle);

        backBtn.setPosition(0.05f, viewHeight - 200);
        backBtn.setSize(200, 200);

        lvlBtn1.setSize(350, 350);
        lvlBtn2.setSize(350, 350);
        lvlBtn3.setSize(350, 350);
        lvlBtn4.setSize(350, 350);
        lvlBtn5.setSize(350, 350);

        float gap = (viewWidth - (3 * lvlBtn1.getWidth())) / 4;
        float rowHeight = viewHeight * 0.4f;
        float secondRowHeight = rowHeight - 250;

        lvlBtn1.setPosition(gap, rowHeight);
        lvlBtn2.setPosition(2 * gap + lvlBtn1.getWidth(), rowHeight);
        lvlBtn3.setPosition(3 * gap + 2 * lvlBtn1.getWidth(), rowHeight);

        lvlBtn4.setPosition(gap + lvlBtn1.getWidth() / 2, secondRowHeight);
        lvlBtn5.setPosition(2 * gap + lvlBtn1.getWidth() + lvlBtn4.getWidth() / 2, secondRowHeight);

        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new HomeScreen(game));
            }
        });

        lvlBtn1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelScreen(game));
            }
        });

        lvlBtn2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelScreen(game));
            }
        });
        ClickListener shakeListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                shakeButton((ImageButton) event.getListenerActor());
            }
        };
        lvlBtn3.addListener(shakeListener);
        lvlBtn4.addListener(shakeListener);
        lvlBtn5.addListener(shakeListener);
        // Add buttons to stage
        stage.addActor(backBtn);
        stage.addActor(lvlBtn1);
        stage.addActor(lvlBtn2);
        stage.addActor(lvlBtn3);
        stage.addActor(lvlBtn4);
        stage.addActor(lvlBtn5);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(game.background, 0, 0, viewWidth, viewHeight);

        game.font.draw(game.batch, "LEVELS", viewWidth * 0.38f, viewHeight * 0.95f);

        drawCenteredText(game.mediumFont, "1", lvlBtn1);
        drawCenteredText(game.mediumFont, "2", lvlBtn2);
        drawCenteredText(game.mediumFont, "3", lvlBtn3);
        drawCenteredText(game.mediumFont, "4", lvlBtn4);
        drawCenteredText(game.mediumFont, "5", lvlBtn5);

        stage.act(delta);
        stage.draw();

        drawLockOverlay(lvlBtn3);
        drawLockOverlay(lvlBtn4);
        drawLockOverlay(lvlBtn5);

        game.batch.end();
    }
    private void shakeButton(ImageButton button) {
        button.addAction(Actions.sequence(
                Actions.moveBy(10, 0, 0.05f),
                Actions.moveBy(-20, 0, 0.05f),
                Actions.moveBy(20, 0, 0.05f),
                Actions.moveBy(-10, 0, 0.05f)
        ));
    }
    private void drawCenteredText(BitmapFont font, String text, ImageButton button) {
        GlyphLayout layout = new GlyphLayout(font, text);

        float textX = button.getX() + (button.getWidth() - layout.width) / 2;
        float textY = button.getY() + (button.getHeight() + layout.height) / 2;

        textY -= layout.height / 2;

        font.draw(game.batch, text, textX, textY);
    }

    private void drawLockOverlay(ImageButton button) {
        float lockSize = 250;
        float lockX = button.getX() + (button.getWidth() - lockSize) / 2;
        float lockY = button.getY() + (button.getHeight() - lockSize) / 2-50;
        game.batch.draw(lockTexture, lockX, lockY, lockSize, lockSize);
    }

    @Override
    public void resize(int width, int height) {
        viewWidth = width;
        viewHeight = height;
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
        backBtnTexture.dispose();
        lockTexture.dispose();
        stage.dispose();
    }
}
