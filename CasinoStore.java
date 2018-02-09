package com.katyan.poker;

import java.util.Vector;

import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Filter;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.katyan.poker.games.PlayerRanking;

public class CasinoStore {
	public static String[] avatarNames=new String[]{
			"Alex", "Anne", "Daniel", "Audrey", "Kofi", "Halle", "Glover",
			"Stacey", "Jim", "Kimberly", "Johny", "Marilyn", "Wayne", "Eva",
			"Jon","Gabrielle", "Clark", "Emma", "Tony", "Natelie", "Asa", "Jack",
			"Lana", "Dhruv", "Mia", "Leonidas", "Alexis", "Albert", "Lana", "Abraham", "Sasha", "John", "Jayden",
			"Jacob", "Aletta", "Dan", "Phoenix", "Hiram", "Dylan", "Kendra", "Bree", "Scorpio", "Viper", "August",
			"Dani", "Adam", "Kong", "Crook", "Diana", "Leo", "Killer", "King"
	};
	private static CasinoStore instance;
	private OrthographicCamera camera;
	private SpriteBatch batch;
    public boolean showSpin=true;
    public long crc=-1;
	public PlayerRanking[] rankingArr;
    public boolean toShowWelcomePop=true;
	public Vector<OnlinePlayers> friends;

    public static Obj getIntoSize(Obj obj, float w) {
		float x=obj.width, y=obj.height, exY=w*1.33f;
		float per=y/x;
		x=w;
		y=x*per;
		if(y>exY){
			per=x/y;
			y=exY;
			x=y*per;
		}
		obj.set(x, y);
		return obj;
	}

	public static class Obj{
		public float width;
		public float height;
		public Obj(float w, float h) {
			width=w;
			height=h;
		}
		public void set(float x, float y) {
			width=x;
			height=y;
		}
	}
//	public Vector<Sprite> pictures=new Vector<Sprite>();
	public static final String FONT_CHARSET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ 1234567890,./;'[]\\`~!@#$%^&*()_-+{}|:\"<>?=";
	private Stage stage;
	private ShapeRenderer shapeRenderer;
	private TweenManager tweenManager;
	public Vector<OnlinePlayers> onlinePlayers=new Vector<OnlinePlayers>();
	private Vector<Sprite> allAvatars;
	public boolean online;
	public Sprite onlinePic;
	public boolean setOnlinePlayerImages;
	public int picSet;
	private CasinoStore() {
	}
	public static CasinoStore getInstance() {
		if(instance==null)
			instance=new CasinoStore();
		return instance;
	}
	public static Sprite getIntoSize(Sprite obj, float w) {
		float x=obj.getWidth(), y=obj.getHeight(), exY=w*1.33f;
		float per=y/x;
		x=w;
		y=x*per;
		if(y>exY){
			per=x/y;
			y=exY;
			x=y*per;
		}
		obj.setSize(x, y);
		return obj;
	}
	public Stage getStage() {
		if(stage==null){
			StretchViewport viewport = new StretchViewport(800, 480, getCamera());
//			viewPort=new FitViewport(800, 480, getCamera());
			stage=new Stage(viewport);
		}
		stage.clear();
		return stage;
	}
	public OrthographicCamera getCamera() {
		if(camera==null){
			camera=new OrthographicCamera();
			camera.setToOrtho(false, 800, 480);
		}
		return camera;
	}
	public SpriteBatch getSpriteBatch() {
		if(batch==null){
			batch=new SpriteBatch();
			batch.setProjectionMatrix(getCamera().combined);
		}
		return batch;
	}
	public static BitmapFont getNewFont(int size) {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
				Gdx.files.internal("Calibri.ttf"));
		FreeTypeFontParameter parameter=new FreeTypeFontParameter();
		parameter.size=size;
		parameter.characters=FONT_CHARSET;
		BitmapFont font=null;
		try {
			font = generator.generateFont(parameter);//This Line GdxRuntimeException
			generator.dispose();
		}
		catch(Exception e){
			return getNewFont(size);
		}
		return font;
	}
	
	public static BitmapFont getWinFont(int size) {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
				Gdx.files.internal("RioGrande.ttf"));
		FreeTypeFontParameter parameter=new FreeTypeFontParameter();
		parameter.size=size;
		parameter.characters=FONT_CHARSET;
		BitmapFont font = generator.generateFont(parameter);
		generator.dispose();
		return font;
	}
	public ShapeRenderer getShapeRenderer() {
		if(shapeRenderer==null){
			shapeRenderer=new ShapeRenderer();
			shapeRenderer.setProjectionMatrix(camera.combined);
			shapeRenderer.setColor(Color.BLACK);
		}
		return shapeRenderer;
	}
//	public void bytesToSprite(byte[] bytes, final OnImageConverted imageListener) {
//	    try {
//	        pmap=new Pixmap(bytes, 0, bytes.length);
//	        Gdx.app.postRunnable(new Runnable() {
//	            @Override
//	            public void run() {            
//	                Texture tex = new Texture(pmap);
//	                Sprite sprite = new Sprite(tex);
//	                imageListener.onImageConverted(sprite);
//	            }
//	        });
//	    } catch(Exception e) {
//	        Gdx.app.log("KS", e.toString());
//	        e.printStackTrace();
//	    }
//	}
	public TweenManager getTweenManager() {
		if(tweenManager==null){
			tweenManager=new TweenManager();
		}
		return tweenManager;
	}
	
	public static Sprite clip(Sprite sprite, int radius) {
		TextureData data = sprite.getTexture().getTextureData();
		if (!data.isPrepared())
			data.prepare();
		Pixmap pixmap = data.consumePixmap();

		int row = sprite.getRegionHeight();
		int col = sprite.getRegionWidth();
		if (radius == -1) {
			if (col < row)
				radius = col / 2;
			else
				radius = row / 2;
		}
		int diameter = radius * 2;
		int[] pixels = new int[diameter * diameter];
		int padx = (sprite.getRegionWidth() - diameter) / 2;
		int padY = (sprite.getRegionHeight() - diameter) / 2;
		Vector2 center = new Vector2(col / 2, row / 2);

		int index = 0;
		for (int i = 0; i < diameter; i++) {
			for (int j = 0; j < diameter; j++) {
				if (center.dst(padx + j, padY + i) <= radius) {
					pixels[index++] = pixmap.getPixel(padx + sprite.getRegionX() + j, padY + sprite.getRegionY() + i);
				} else
					pixels[index++] = Color.CLEAR.toIntBits();
			}
		}
		pixmap = new Pixmap(diameter, diameter, Format.RGBA8888);
		pixmap.setFilter(Filter.NearestNeighbour);
		pixmap.getPixels().asIntBuffer().put(pixels);
		Texture texture = new Texture(diameter, diameter, Format.RGBA8888);
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		texture.draw(pixmap, 0, 0);
		pixmap.dispose();
		sprite = new Sprite(texture);
		return sprite;

	}
	public Vector<Sprite> getAllAvatar(TextureAtlas atlas) {
		if(allAvatars==null){
			allAvatars=new Vector<Sprite>();
			for (int i = 1; i <= 52; i++) {
				Sprite sprite=atlas.createSprite("ava"+i);
				sprite.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
				allAvatars.add(sprite);
			}
		}
		return allAvatars;
	}
}
