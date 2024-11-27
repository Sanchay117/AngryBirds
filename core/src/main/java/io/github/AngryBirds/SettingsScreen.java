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
    private Texture saveTexture;

    private ImageButton crossBtn;
    private ImageButton soundBtn;
    private ImageButton musicBtn;
    private ImageButton languageBtn;
    private ImageButton creditsBtn;
    private ImageButton saveBtn;

    private float boardWidth;
    private float boardHeight;

    private int lvl;


    private final LevelScreen levelScreen;

    public SettingsScreen(Main game, LevelScreen levelScreen, int lvl) {
        this.game = game;
        this.levelScreen = levelScreen;
        this.lvl = lvl;
    }


    @Override
    public void show() {
        stage = new Stage();

        crossBtnTexture = new Texture("cross.png");
        soundTexture = new Texture("sound.png");
        musicTexture = new Texture("music.png");
        languageTexture = new Texture("lang.png");
        creditsTexture = new Texture("credits.png");
        saveTexture = new Texture("save.png");

        Skin skin = new Skin();
        skin.add("cross", crossBtnTexture);
        skin.add("sound", soundTexture);
        skin.add("music", musicTexture);
        skin.add("language", languageTexture);
        skin.add("credits", creditsTexture);
        skin.add("termsPrivacy", saveTexture);

        boardHeight = Math.min(viewWidth, viewHeight) * 0.9f;
        boardWidth = boardHeight * 1.2f;
        float buttonSpacing = 0.17f * boardHeight;
        float boardX = (viewWidth - boardWidth) / 2;
        float boardY = (viewHeight - boardHeight) / 2;

        ImageButton.ImageButtonStyle crossStyle = new ImageButton.ImageButtonStyle();
        crossStyle.imageUp = new TextureRegionDrawable(new TextureRegion(crossBtnTexture));
        crossBtn = new ImageButton(crossStyle);
        crossBtn.setSize(110, 110);
        crossBtn.setPosition(boardX + boardWidth - crossBtn.getWidth() - 75,
            boardY + boardHeight - crossBtn.getHeight() - 30);


        crossBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelScreen(game,lvl));
            }
        });



        float buttonSize = 120f;
        float leftColumnX = boardX + 0.25f * boardWidth;
        float rightColumnX = boardX + 0.525f * boardWidth;
        float secbuttonSize = 270f;
        soundBtn = createButton(soundTexture, leftColumnX, boardY + 0.475f * boardHeight, buttonSize);
        musicBtn = createButton(musicTexture, leftColumnX, boardY + 0.475f * boardHeight - buttonSpacing, buttonSize);

        languageBtn = createButton(languageTexture, rightColumnX, boardY + 0.45f * boardHeight, secbuttonSize);
        creditsBtn = createButton(creditsTexture, rightColumnX, boardY + 0.45f * boardHeight - buttonSpacing, secbuttonSize);
        saveBtn = createButton(saveTexture, rightColumnX, boardY + 0.45f * boardHeight - 2 * buttonSpacing, secbuttonSize);

        musicBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(game.music.getVolume()==0) game.music.setVolume(0.4f);
                else game.music.setVolume(0.0f);
            }
        });
        saveBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                levelScreen.saveGame();
            }
        });

        stage.addActor(crossBtn);
        stage.addActor(soundBtn);
        stage.addActor(musicBtn);
        stage.addActor(languageBtn);
        stage.addActor(creditsBtn);
        stage.addActor(saveBtn);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
        button.setSize(size, size);
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
        saveTexture.dispose();
    }
}
