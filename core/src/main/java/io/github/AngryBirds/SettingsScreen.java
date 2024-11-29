package io.github.AngryBirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
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
    private Texture creditsTexture;
    private Texture saveTexture;
    private Texture sliderBgTexture;
    private Texture sliderKnobTexture;

    private ImageButton crossBtn;
    private ImageButton soundBtn;
    private ImageButton musicBtn;
    private ImageButton creditsBtn;
    private ImageButton saveBtn;

    private Slider volumeSlider;
    private Label volumeLabel;

    private float boardWidth;
    private float boardHeight;

    private int lvl;

    private final LevelScreen levelScreen;

    public SettingsScreen(Main game, LevelScreen levelScreen, int lvl) {
        this.game = game;
        this.levelScreen = levelScreen;
        this.lvl = lvl;

        boardTexture = new Texture(Gdx.files.internal("settingbg.png"));
        settingsBgTexture = new Texture(Gdx.files.internal("settingsBackground.png"));
    }

    @Override
    public void show() {
        stage = new Stage();

        crossBtnTexture = new Texture("cross.png");
        soundTexture = new Texture("sound.png");
        musicTexture = new Texture("music.png");
        creditsTexture = new Texture("credits.png");
        saveTexture = new Texture("save.png");
        sliderBgTexture = new Texture("sliderBg.png");
        sliderKnobTexture = new Texture("sliderKnob.png");

        Skin skin = new Skin();
        skin.add("cross", crossBtnTexture);
        skin.add("sound", soundTexture);
        skin.add("music", musicTexture);
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
                game.setScreen(new LevelScreen(game, lvl));
            }
        });

        float buttonSize = 120f;
        float leftColumnX = boardX + 0.25f * boardWidth;
        float rightColumnX = boardX + 0.525f * boardWidth;
        float secbuttonSize = 325f;
        soundBtn = createButton(soundTexture, leftColumnX, boardY + 0.475f * boardHeight+15, buttonSize*1.1f);

        soundBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(game.songs[game.currentSongIndex].getVolume()==0) game.songs[game.currentSongIndex].setVolume(0.4f);
                else game.songs[game.currentSongIndex].setVolume(0.0f);
            }
        });

        musicBtn = createButton(musicTexture, rightColumnX, boardY + 0.475f * boardHeight +15, buttonSize*1.1f);
        musicBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.nextSong();
            }
        });

        creditsBtn = createButton(creditsTexture, leftColumnX, boardY + 0.45f * boardHeight - 30, secbuttonSize);
        saveBtn = createButton(saveTexture, leftColumnX, boardY + 0.45f * boardHeight - 60, secbuttonSize);

        Slider.SliderStyle sliderStyle = new Slider.SliderStyle();
        sliderStyle.background = new TextureRegionDrawable(new TextureRegion(sliderBgTexture));
        TextureRegionDrawable knobDrawable = new TextureRegionDrawable(new TextureRegion(sliderKnobTexture));
        knobDrawable.setMinWidth(35);
        knobDrawable.setMinHeight(35);
        sliderStyle.knob = knobDrawable;


        volumeSlider = new Slider(0, 1, 0.01f, false, sliderStyle);
        volumeSlider.setSize(0.45f * boardWidth, 25);
        volumeSlider.setPosition(boardX + 0.3f * boardWidth, boardY - 0.4f * boardHeight);
        volumeSlider.setValue(game.songs[game.currentSongIndex].getVolume());

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = game.font;
        volumeLabel = new Label("Volume: " + (int) (volumeSlider.getValue() * 100) + "%", labelStyle);
        volumeLabel.setPosition(volumeSlider.getX(), volumeSlider.getY() + 60);

        volumeSlider.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                float volume = volumeSlider.getValue();
                volumeLabel.setText("Volume: " + (int) (volume * 100) + "%");
                game.songs[game.currentSongIndex].setVolume(volume);
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
        stage.addActor(creditsBtn);
        stage.addActor(saveBtn);
        stage.addActor(volumeSlider);
        stage.addActor(volumeLabel);

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
        creditsTexture.dispose();
        saveTexture.dispose();
        sliderBgTexture.dispose();
        sliderKnobTexture.dispose();
    }
}
