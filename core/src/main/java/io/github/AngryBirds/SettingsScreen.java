package io.github.AngryBirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class SettingsScreen extends ScreenAdapter {

    private final Main game;
    private int viewHeight = Gdx.graphics.getHeight();
    private int viewWidth = Gdx.graphics.getWidth();

    private Stage stage;
    private Texture settingsBgTexture;
    private Texture boardTexture;
    private Texture crossBtnTexture;
    private Texture soundTexture;
    private Texture musicTexture;
    private Texture languageTexture;
    private Texture creditsTexture;
    private Texture termsPrivacyTexture;

    private ImageButton crossBtn;
    private ImageButton soundBtn;
    private ImageButton musicBtn;
    private ImageButton languageBtn;
    private ImageButton creditsBtn;
    private ImageButton termsPrivacyBtn;

    // Declare boardWidth and boardHeight at the class level
    private float boardWidth;
    private float boardHeight;


    public SettingsScreen(Main game) {
        this.game = game;
        settingsBgTexture = new Texture("settingsBackground.png");  // Full background
        boardTexture = new Texture("settingbg.png");  // Board image
    }

    @Override
    public void show() {
        stage = new Stage();

        // Load textures for cross button and other icons
        crossBtnTexture = new Texture("cross.png");
        soundTexture = new Texture("sound.png");
        musicTexture = new Texture("music.png");
        languageTexture = new Texture("lang.png");
        creditsTexture = new Texture("credits.png");
        termsPrivacyTexture = new Texture("TnC.png");

        // Skin setup for assets
        Skin skin = new Skin();
        skin.add("cross", crossBtnTexture);
        skin.add("sound", soundTexture);
        skin.add("music", musicTexture);
        skin.add("language", languageTexture);
        skin.add("credits", creditsTexture);
        skin.add("termsPrivacy", termsPrivacyTexture);

        // Set boardWidth to be wider than boardHeight
        boardHeight = Math.min(viewWidth, viewHeight) * 0.9f;  // Height of the board
        boardWidth = boardHeight * 1.2f;  // Width of the board (20% wider than height)
        float buttonSpacing = 0.15f * boardHeight;
        float boardX = (viewWidth - boardWidth) / 2;
        float boardY = (viewHeight - boardHeight) / 2;

        // Cross button (top-right of the board)
        ImageButton.ImageButtonStyle crossStyle = new ImageButton.ImageButtonStyle();
        crossStyle.imageUp = new TextureRegionDrawable(new TextureRegion(crossBtnTexture));
        crossBtn = new ImageButton(crossStyle);
        crossBtn.setSize(110, 110);
        crossBtn.setPosition(boardX + boardWidth - crossBtn.getWidth() - 10,
            boardY + boardHeight - crossBtn.getHeight() - 10);


        // Click listener to go back to GameScreen
        crossBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelScreen(game));
            }
        });

        // Define button size and spacing for layout consistency
        float buttonSize = 120f;
        float leftColumnX = boardX + 0.2f * boardWidth;
        float rightColumnX = boardX + 0.6f * boardWidth;
        float secbuttonSize = 270f;
        // Buttons on the left (sound and music)
        soundBtn = createButton(soundTexture, leftColumnX, boardY + 0.6f * boardHeight, buttonSize);
        musicBtn = createButton(musicTexture, leftColumnX, boardY + 0.6f * boardHeight - buttonSpacing, buttonSize);

        // Buttons on the right (language, credits, terms & privacy)
        languageBtn = createButton(languageTexture, rightColumnX, boardY + 0.6f * boardHeight, secbuttonSize);
        creditsBtn = createButton(creditsTexture, rightColumnX, boardY + 0.6f * boardHeight - buttonSpacing, secbuttonSize);
        termsPrivacyBtn = createButton(termsPrivacyTexture, rightColumnX, boardY + 0.6f * boardHeight - 2 * buttonSpacing, secbuttonSize);

        // Add actors to the stage
        stage.addActor(crossBtn);
        stage.addActor(soundBtn);
        stage.addActor(musicBtn);
        stage.addActor(languageBtn);
        stage.addActor(creditsBtn);
        stage.addActor(termsPrivacyBtn);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw background and board
        game.batch.begin();
        game.batch.draw(settingsBgTexture, 0, 0, viewWidth, viewHeight);
        game.batch.draw(boardTexture, (viewWidth - boardWidth) / 2, (viewHeight - boardHeight) / 2, boardWidth, boardHeight);
        game.batch.end();

        stage.act(delta);
        stage.draw();
    }

    private ImageButton createButton(Texture texture, float x, float y, float size) {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.imageUp = new TextureRegionDrawable(new TextureRegion(texture));
        ImageButton button = new ImageButton(style);
        button.setSize(size, size);  // Set button size to specified size
        button.setPosition(x, y);
        return button;
    }

    @Override
    public void resize(int width, int height) {
        viewWidth = width;
        viewHeight = height;
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        stage.dispose();
        settingsBgTexture.dispose();
        boardTexture.dispose();
        crossBtnTexture.dispose();
        soundTexture.dispose();
        musicTexture.dispose();
        languageTexture.dispose();
        creditsTexture.dispose();
        termsPrivacyTexture.dispose();
    }
}
