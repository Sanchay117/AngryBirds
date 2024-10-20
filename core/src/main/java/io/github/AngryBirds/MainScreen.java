package io.github.AngryBirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.graphics.Color;

public class MainScreen extends ScreenAdapter {

    private final Main game;
    private int viewHeight = Gdx.graphics.getHeight();
    private int viewWidth = Gdx.graphics.getWidth();
    private final Texture backArrow = new Texture(Gdx.files.internal("LeftArrow.png"));
    private ShapeRenderer shapeRenderer;
    private GlyphLayout layout;


    public MainScreen(Main game) {
        this.game = game;
        game.background = new Texture("MainScreenBG.jpg");
    }

    @Override
    public void show(){

        // font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("angrybirds-regular.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 130;  // Set the font size
        game.font = generator.generateFont(parameter); // Generate the BitmapFont
        generator.dispose(); // Don't forget to dispose of the generator after use
        shapeRenderer = new ShapeRenderer();
        layout = new GlyphLayout();

    }

    @Override
    public void render(float delta) {

        game.batch.begin();

        game.batch.draw(game.background, 0, 0,viewWidth,viewHeight);
        game.font.draw(game.batch, "LEVELS", viewHeight*0.58f, viewHeight*0.95f);
        game.batch.draw(backArrow,viewWidth*0.03f,viewHeight*0.95f - 130,120,150);

        //Level Nums
        int row = 0;

//        for (int i = 1; i <= 5; i++) {
//            String text = String.valueOf(i);
//
//            float x = 250 + ((i - 1) % 5) * 100;
//            float y = viewHeight * (0.8f - row * 0.05f);
//
//            // Use GlyphLayout to calculate the size of the text
//            layout.setText(game.font, text);
//
//            // Get the width and height of the text
//            float textWidth = layout.width;
//            float textHeight = layout.height;
//
//            // Draw background rectangle with ShapeRenderer
//            shapeRenderer.begin(ShapeType.Filled);
//            shapeRenderer.setColor(Color.BLUE); // Set background color
//            shapeRenderer.rect(x, y - textHeight, textWidth, textHeight); // Draw the background
//            shapeRenderer.end();
//
//            // Draw the text on top of the background
//            game.batch.draw(game.font.getRegion(), x, y, textWidth, textHeight);
//            game.font.draw(game.batch, text, x, y);
//
//            // Move to next row every 5 items
//            if (i % 5 == 0) row++;
//        }

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewWidth = width;
        viewHeight = height;
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
        shapeRenderer.dispose();
    }
}
