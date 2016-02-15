package com.tetcolor;

import static android.opengl.GLES20.GL_LINE_LOOP;
import static android.opengl.GLES20.GL_POINTS;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Rect;
import android.util.Log;

import com.tetcolor.Input.TouchEvent;
//import com.tetcolor.TetcolorGLTest.TetcolorGLTestScreen;

/*public class MainMenuScreen extends GLGame {
	@Override
	public Screen getStartScreen() {
	return new MainMenu(this);
	}
*/	
	public class MainMenuScreen extends Screen {
	GLGraphics glGraphics;
	GL10 gl;
	//private byte count_cubic = 7;// кол-во кубиков в ряду
	//private float width_cubic;// = 2/count_cubic; //ширина кубика
	private int width, height;//ширина и высота экрана
	//Alfabeto a = new Alfabeto();
	private double touchPosX,touchPosY;
	private static final String TAG = "Tetcolor";
	private FloatBuffer ramka;
	Rect soundBounds, playBounds, highscoresBounds, helpBounds, exitBounds;
	
	public MainMenuScreen(GLGame game) {
		super(game);
		glGraphics = ((GLGame)game).getGLGraphics();
		gl = glGraphics.getGL();
		width = glGraphics.getWidth();
		height = glGraphics.getHeight();
		//width_cubic = width / Settings.count_cubic;
		//width_cubic= ((int) (width_cubic/10))*10; 
		//int font_size=15;
		float tmp=7*Settings.wid/(100/Settings.size_font);
		int xx,yy;
		
		xx=(int) (((int)((int)(width/tmp)/2)-2.7f)*tmp); yy=(int)((height/3)+2*Settings.wid-((int) Settings.wid/2));
		soundBounds = new Rect(xx, yy, xx+(int)tmp*6, yy+(int)tmp);
		
		xx=(int) (((int)((int)(width/tmp)/2)-1)*tmp); yy=(int)((height/3)-Settings.wid/2);
		playBounds = new Rect(xx, yy, xx+(int)tmp*3, yy+(int)tmp);
		
		//highscoresBounds = new Rect(160,18, 300, 36);
		
		xx=(int) (((int)((int)(width/tmp)/2)-1)*tmp); yy=(int)((height/3)+6*Settings.wid-((int) Settings.wid/2));
		helpBounds = new Rect(xx, yy, xx+(int)tmp*3, yy+(int)tmp);
		
		xx=(int)(((int)((int)(width/tmp)/2)-1.5f)*tmp); yy=(int)((height/3)+8*Settings.wid-((int) Settings.wid/2));
		exitBounds = new Rect (xx, yy, xx+(int)tmp*4, yy+(int)tmp);
		//Settings.load(files);
		}
		
	/*void draw_number(float x, float y, float siz, String num) {
		int num_point;
		gl.glLoadIdentity();
		gl.glOrthof(0, width, 0, height, 1, -1);
		gl.glTranslatef( x, height-y, 0); 
		gl.glScalef(siz, siz, 1);
		
		
		for (int ii = 0; ii < num.length(); ii++)
		
		for (int i = 0; i < Settings.a.list_lettera.size(); i++) {
			if (Settings.a.list_lettera.get(i).lettera==num.charAt(ii)) {
				num_point=Settings.a.list_lettera.get(i).modelLettera.limit()/2;
				Settings.a.list_lettera.get(i).modelLettera.position(0);// читаем 2 цифры координаты
				gl.glPointSize(width_cubic/(100/siz));
				gl.glColor4f(0, 0, 0, 1);
				//if (ii!=0)
				//gl.glTranslatef( list_lettera.get(i).width_lettera+0.2f, 0, 0);
				gl.glVertexPointer(2, GL10.GL_FLOAT,0, Settings.a.list_lettera.get(i).modelLettera);
				gl.glDrawArrays(GL_POINTS, 0, num_point);
				gl.glPointSize((width_cubic/(100/siz))-5);
				
				gl.glColor4f(Settings.a.list_lettera.get(i).lettera_color.r,
						Settings.a.list_lettera.get(i).lettera_color.g,
						Settings.a.list_lettera.get(i).lettera_color.b, 1);
		gl.glDrawArrays(GL_POINTS, 0, num_point);
		gl.glTranslatef( Settings.a.list_lettera.get(i).width_lettera+0.2f, 0, 0);
			}
		}
		
	}*/
	
	@Override
	public void update(float deltaTime) {
		//Graphics g = game.getGraphics();
		
		//float tmp_x,tmp_y;
		///
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		
		game.getInput().getKeyEvents();
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
		TouchEvent event = touchEvents.get(i);
		touchPosX=event.getXU();// else touchPos.x=event.getXD();
		touchPosY=event.getYU();// else touchPos.y=event.getYD();
			
		//Log.d(TAG, "tpy "+String.valueOf(touchPosY)+" tmp_y="+String.valueOf(exitBounds.top)+" tmp_y="+String.valueOf(exitBounds.bottom));
		//Log.d(TAG, "tpx "+String.valueOf(touchPosX)+" tmp_x "+String.valueOf(exitBounds.left)+" tmp="+String.valueOf(exitBounds.right));
		
		//new game
		if (inBounds(playBounds.left, playBounds.top, playBounds.right, playBounds.bottom)) {
			game.setScreen(new TetcolorGL((GLGame)game));
			/*Assets.kraftwerk_popcorn.setLooping(true);
			Assets.kraftwerk_popcorn.setVolume(0.5f);
			if(Settings.soundEnabled)
				Assets.kraftwerk_popcorn.play();*/
		}
		
		//help
		if (inBounds(helpBounds.left, helpBounds.top, helpBounds.right, helpBounds.bottom)) {
			game.setScreen(new Help((GLGame)game));
		}
		
		//settings
		if (inBounds(soundBounds.left, soundBounds.top, soundBounds.right, soundBounds.bottom)) {
			game.setScreen(new SettingScreen((GLGame)game));
		}
		
		//finish
		if (inBounds(exitBounds.left, exitBounds.top, exitBounds.right, exitBounds.bottom)) {
			
			if(Assets.kraftwerk_popcorn.isPlaying())
				Assets.kraftwerk_popcorn.stop();
			dispose();
			
		}

		 
		}
		///
		/*
				if (inBounds(event, 64, 220 + 84, 192, 42)) {
					game.setScreen(new HelpScreen(game));
					if (Settings.soundEnabled)
						Assets.drop1.play(1);
					return;
				}
			}
		}*/
	}

	private boolean inBounds(int x, int y, int width, int height) {
			if(touchPosX > x && touchPosX < /*x + */width - 1 &&
			touchPosY > y && touchPosY < /*y +*/ height - 1)
			return true;
			else
			return false;
			}

	@Override
	public void present(float deltaTime) {
		int font_size=Settings.size_font+8;
		float tmp=7*Settings.wid/(100/font_size);
		float t=(width/2)-(tmp*1.95f);
		gl.glLoadIdentity();
		gl.glOrthof(0, width, 0, height, 1, -1);
		Settings.draw_number(t, (height/5)-Settings.wid/2, font_size, "Т", width, height, gl);
		Settings.draw_number(2*t+tmp/2, (height/5)+Settings.wid/2, font_size, "Е", width, height, gl);
		Settings.draw_number(t+tmp, (height/5)-Settings.wid/2, font_size, "Т", width, height, gl);
		Settings.draw_number(t+3*tmp/2, (height/5)+Settings.wid/2, font_size, "К", width, height, gl);
		Settings.draw_number(t+2*tmp, (height/5)-Settings.wid/2, font_size, "О", width, height, gl);
		Settings.draw_number(3*t+5*tmp/2, (height/5)+Settings.wid/2, font_size, "L", width, height, gl);
		Settings.draw_number(-t+3*tmp, (height/5)-Settings.wid/2, font_size, "О", width, height, gl);
		Settings.draw_number(t+7*tmp/2, (height/5)+Settings.wid/2, font_size, "R", width, height, gl);
		gl.glLoadIdentity();
		gl.glOrthof(0, width, 0, height, 1, -1);
		//font_size=15;
		tmp=7*Settings.wid/(100/Settings.size_font);
		//t=(width/2)-(tmp*2);
		Settings.draw_number(((int)((int)(width/tmp)/2)-1)*tmp, (height/3)/*+Settings.wid*/, Settings.size_font, "ИГРА", width, height, gl);
		Settings.draw_number(((int)((int)(width/tmp)/2)-2.7f)*tmp, (height/3)+2*Settings.wid, Settings.size_font, "НАСТРОЙКИ", width, height, gl);
		Settings.draw_number(((int)((int)(width/tmp)/2)-2)*tmp, (height/3)+4*Settings.wid, Settings.size_font, "РЕКОРДЫ", width, height, gl);
		Settings.draw_number(((int)((int)(width/tmp)/2)-1)*tmp, (height/3)+6*Settings.wid, Settings.size_font, "ХЕЛП", width, height, gl);
		Settings.draw_number(((int)((int)(width/tmp)/2)-1.5f)*tmp, (height/3)+8*Settings.wid, Settings.size_font, "ВЫХОД", width, height, gl);
		
		//ramka
		//font_size=15;
		//tmp=7*width_cubic/(100/font_size);
		/*float tmp_x = ((int)((int)(width/tmp)/2)-1.5f)*tmp,
				tmp_y= (height/3)+7*width_cubic;		
		float[] vertices_ramka = {tmp_x,height-tmp_y,tmp_x+4*tmp,height-tmp_y,tmp_x+4*tmp,height-tmp_y+tmp,tmp_x,height-tmp_y+tmp};
				ramka = ByteBuffer.allocateDirect(vertices_ramka.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
				ramka.put(vertices_ramka);
				gl.glLoadIdentity();
				gl.glOrthof(0, width, 0, height, 1, -1);
				ramka.position(0);
				gl.glVertexPointer(2, GL10.GL_FLOAT,0, ramka);
				gl.glLineWidth(5);
				gl.glColor4f(0, 0, 0, 1);
				gl.glDrawArrays(GL_LINE_LOOP, 0, 4);*/
		//Assets.drop1 = game.getAudio().newSound("drop1.ogg");
		//Assets.kraftwerk_popcorn = game.getAudio().newMusic("kraftwerk_popcorn.ogg");
		//Settings.load(game.getFileIO());
	}

	@Override
	public void pause() {
		Settings.save(game.getFileIO());
	}

	@Override
	public void resume() {
		
		gl.glViewport(0, 0, width, height);
		gl.glClearColor(0, 0.5f, 1, 1);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glMatrixMode(GL10.GL_MODELVIEW);//GL_PROJECTION);

		gl.glLoadIdentity();
		 //gl.glOrthof(0, 320, 0, 480, 1, -1);
		gl.glOrthof(0, width, 0, height, 1, -1);
		// в буфере будут координаты
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		// в буфере будет цвет
		// gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		// толщина линии
		//gl.glLineWidth(5);
		//list_lettera.clear();
		//make_lettera();
		//Assets.drop1 = game.getAudio().newSound("drop1.ogg");
		//Assets.kraftwerk_popcorn = game.getAudio().newMusic("kraftwerk_popcorn.ogg");
			
	}

	@Override
	public void dispose() {
	//((GLGame)game).finish();
	}
}
//}