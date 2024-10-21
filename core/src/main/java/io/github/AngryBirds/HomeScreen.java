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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.graphics.Color;

public class HomeScreen extends ScreenAdapter {

    private final Main game;
    private int viewHeight = Gdx.graphics.getHeight();
    private int viewWidth = Gdx.graphics.getWidth();
    private ShapeRenderer shapeRenderer;
    private GlyphLayout layout;

    private Stage stage;
    private Texture playTexture;
    private ImageButton playButton;
    private Texture quitTexture;
    private ImageButton quitButton;


    public HomeScreen(Main game) {
        this.game = game;
        game.background = new Texture("bg.png");
    }

    @Override
    public void show(){
        stage = new Stage();

        // Load the button textures
        playTexture = new Texture("play.png");
        quitTexture = new Texture("quit.png");

        // Create an ImageButton from the texture
        Skin skin = new Skin();
        skin.add("play", playTexture);
        skin.add("quit", quitTexture);

        // Create ImageButtonStyle and set imageUp
        ImageButton.ImageButtonStyle playStyle = new ImageButton.ImageButtonStyle();
        playStyle.imageUp = new TextureRegionDrawable(new TextureRegion(playTexture)); // Set the up state
        playButton = new ImageButton(playStyle); // Create the button with style

        ImageButton.ImageButtonStyle quitStyle = new ImageButton.ImageButtonStyle();
        quitStyle.imageUp = new TextureRegionDrawable(new TextureRegion(quitTexture)); // Set the up state
        quitButton = new ImageButton(quitStyle); // Create the button with style

        // Set the button's position and size
        playButton.setPosition(viewWidth - 300, 0);
        playButton.setSize(300, 300);

        quitButton.setPosition(0, 0);
        quitButton.setSize(300, 300);

        // Add ClickListener
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainScreen(game));
            }
        });

        // Add the buttons to the stage
        stage.addActor(playButton);
        stage.addActor(quitButton);

        // Set the input processor to the stage
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

        // Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        game.batch.draw(game.background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        game.batch.end();

        // Update and draw the stage
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewWidth = width;
        viewHeight = height;
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
        stage.dispose();
        playTexture.dispose();
        quitTexture.dispose();
    }
}
