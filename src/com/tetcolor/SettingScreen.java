package com.tetcolor;

import static android.opengl.GLES20.GL_LINE_LOOP;
import static android.opengl.GLES20.GL_POINTS;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.List;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Rect;

import com.tetcolor.Input.TouchEvent;

public class SettingScreen extends Screen {
	GLGraphics glGraphics;
	GL10 gl;
	//private byte count_cubic = 7;// кол-во кубиков в ряду
	//private float width_cubic;// = 2/count_cubic; //ширина кубика
	private int width, height;//ширина и высота экрана
	private float border_width;
	private double touchPosX,touchPosY;
	private static final String TAG = "Tetcolor";
	private FloatBuffer ramka;
	Rect soundBounds, musicBounds, cubUpBounds, cubDownBounds, exitBounds, colUpBounds, colDownBounds;
	static Random rand = new Random();
	private FloatBuffer vertexModel;
	public SettingScreen(GLGame game) {
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
		
		xx=(int) (((int)((int)(width/tmp)/2)-2)*tmp); yy=(int)((height/3)-2.5f*Settings.wid-((int) Settings.wid/2));
		soundBounds = new Rect(xx, yy, xx+(int)tmp*6, yy+(int)tmp);
		
		xx=(int) (((int)((int)(width/tmp)/2)-2.7f)*tmp); yy=(int)((height/3)-0.8f*Settings.wid-((int) Settings.wid/2));
		musicBounds = new Rect(xx, yy, xx+(int)tmp*6, yy+(int)tmp);
		
		xx=(int) (((int)((int)(width/tmp)/2)-1.7f)*tmp); yy=(int) ((height/3)+2f*Settings.wid-((int) Settings.wid/2));
		cubUpBounds = new Rect((int)(xx-Settings.wid), yy, (int) (xx-Settings.wid+(int)tmp*2), yy+(int)tmp);
		cubDownBounds = new Rect((int)(xx+4*Settings.wid), yy, (int) (xx+4*Settings.wid+(int)tmp*2), yy+(int)tmp);
		
		xx=(int) (((int)((int)(width/tmp)/2)-1.7f)*tmp); yy=(int) ((height/3)+5.5f*Settings.wid-((int) Settings.wid/2));
		colUpBounds = new Rect((int)(xx-Settings.wid), yy, (int) (xx-Settings.wid+(int)tmp*2), yy+(int)tmp);
		colDownBounds = new Rect((int)(xx+4*Settings.wid), yy, (int) (xx+4*Settings.wid+(int)tmp*2), yy+(int)tmp);

		xx=(int)(((int)((int)(width/tmp)/2)-1.5f)*tmp); yy=(int)((height/3)+8.3f*Settings.wid-((int) Settings.wid/2));
		exitBounds = new Rect (xx, yy, xx+(int)tmp*4, yy+(int)tmp);
		}
		
	
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
				//sound
				if (inBounds(soundBounds.left, soundBounds.top, soundBounds.right, soundBounds.bottom)) {
					Settings.soundEnabled=!Settings.soundEnabled;
				}
		
				//music
				if (inBounds(musicBounds.left, musicBounds.top, musicBounds.right, musicBounds.bottom)) {
					Settings.musicEnabled=!Settings.musicEnabled;
				}
				
				//exit
				if (inBounds(exitBounds.left, exitBounds.top, exitBounds.right, exitBounds.bottom)) {
					Settings.save(game.getFileIO());
					game.setScreen(new MainMenuScreen((GLGame)game));
				}
				
		//cubUp
		if (inBounds(cubUpBounds.left, cubUpBounds.top, cubUpBounds.right, cubUpBounds.bottom)) {
			if (Settings.count_cubic>6) Settings.count_cubic=(byte)(Settings.count_cubic-1);
			
		}
		
		//cubDown
				if (inBounds(cubDownBounds.left, cubDownBounds.top, cubDownBounds.right, cubDownBounds.bottom)) {
					if (Settings.count_cubic<15) Settings.count_cubic=(byte)(Settings.count_cubic+1);
					
				}
				
				//colUp
				if (inBounds(colUpBounds.left, colUpBounds.top, colUpBounds.right, colUpBounds.bottom)) {
					
					if (Settings.count_color>5) {Settings.list_color.remove(Settings.count_color-1);Settings.count_color=(byte)(Settings.count_color-1);}
					
				}
				
				//colDown
						if (inBounds(colDownBounds.left, colDownBounds.top, colDownBounds.right, colDownBounds.bottom)) {
							if (Settings.count_color<15) {Settings.list_color.add(new TColor());Settings.count_color=(byte)(Settings.count_color+1);}
							
						}

		}

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
		float tmp=7*Settings.wid/(100/Settings.size_font);
		//String st_col="", st_cub="";
		//for (int i = 0; i < Settings.count_cubic; i++) {st_cub=st_cub+"#";};
		//for (int i = 0; i < Settings.count_color; i++) {st_col=st_col+"#";};
		
		Settings.draw_number(((int)((int)(width/tmp)/2)-2)*tmp, (height/3)-2.5f*Settings.wid, Settings.size_font, "ЗВУК "+(Settings.soundEnabled?"()":"("), width, height, gl);
		Settings.draw_number(((int)((int)(width/tmp)/2)-2.7f)*tmp, (height/3)-0.8f*Settings.wid, Settings.size_font-2, "МУЗЫКА "+(Settings.musicEnabled?"()":"("), width, height, gl);
		Settings.draw_number(((int)((int)(width/tmp)/2)-3f)*tmp, (height/3)+0.5f*Settings.wid, Settings.size_font-7, "КОЛИЧЕСТВО КУБИКОВ", width, height, gl);
		Settings.draw_number(((int)((int)(width/tmp)/2)-1.7f)*tmp, (height/3)+2f*Settings.wid, Settings.size_font, "< "+String.valueOf(Settings.count_cubic)+" >" , width, height, gl);
		//Settings.draw_number(((int)((int)(width/tmp)/2)-2.7f)*tmp, (height/3)+3.1f*width_cubic, Settings.size_font-2, st_cub, width, height, width_cubic, gl);
		Settings.draw_number(((int)((int)(width/tmp)/2)-2.7f)*tmp, (height/3)+4f*Settings.wid, Settings.size_font-7, "КОЛИЧЕСТВО ЦВЕТОВ", width, height, gl);
		Settings.draw_number(((int)((int)(width/tmp)/2)-1.7f)*tmp, (height/3)+5.5f*Settings.wid, Settings.size_font, "< "+String.valueOf(Settings.count_color)+" >" , width, height, gl);
		//Settings.draw_number(((int)((int)(width/tmp)/2)-2)*tmp, (height/3)+6.5f*Settings.wid, Settings.size_font-2, st_col, width, height, gl);
		Settings.draw_number(((int)((int)(width/tmp)/2)-1.5f)*tmp, (height/3)+8.3f*Settings.wid, Settings.size_font, "НАЗАД", width, height, gl);
		
		float width_cubic = width / Settings.count_cubic;
		width_cubic = ((int) (width_cubic/10))*10; //Float.toString(width_cubic).length();
		border_width=(width-width_cubic*Settings.count_cubic)/2;
		
		float[] vertices_cub = { border_width,- width_cubic, width_cubic+ border_width,- width_cubic, width_cubic+ border_width,0, border_width,0};
		vertexModel = ByteBuffer.allocateDirect(vertices_cub.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
		vertexModel.put(vertices_cub);
		gl.glLoadIdentity();
		gl.glOrthof(0,  width, 0,  height, 1, -1);
		for (int i = 0; i < Settings.count_cubic; i++) {
			gl.glLoadIdentity();
			gl.glOrthof(0,  width, 0,  height, 1, -1);
			gl.glTranslatef(i* width_cubic, (height/3)+2f*Settings.wid, 0);
			gl.glColor4f(1, 0, 0, 1);
			vertexModel.position(0);// читаем 2 цифры координаты
			gl.glVertexPointer(2, GL10.GL_FLOAT,0, vertexModel);
			gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4);// gl.glDrawElements(GL10.GL_TRIANGLES,6,GL10.GL_UNSIGNED_SHORT,indices);//аналог
			gl.glLineWidth(5);
			gl.glColor4f(0, 0, 0, 1);
			gl.glDrawArrays(GL_LINE_LOOP, 0, 4);
		};
		
		gl.glLoadIdentity();
		gl.glOrthof(0,  width, 0,  height, 1, -1);
		for (int i = 0; i < Settings.count_color; i++) {
			gl.glLoadIdentity();
			gl.glOrthof(0,  width, 0,  height, 1, -1);
			float f=(float)Settings.count_cubic/(float)Settings.count_color;
			gl.glScalef(f, 1, 1);
			gl.glTranslatef(i* width_cubic, (height/3)-1.5f*Settings.wid, 0);
			
			
			gl.glColor4f(Settings.list_color.get(i).r,
					Settings.list_color.get(i).g,
					Settings.list_color.get(i).b, 1);
			vertexModel.position(0);// читаем 2 цифры координаты
			gl.glVertexPointer(2, GL10.GL_FLOAT,0, vertexModel);
			gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4);// gl.glDrawElements(GL10.GL_TRIANGLES,6,GL10.GL_UNSIGNED_SHORT,indices);//аналог
			gl.glLineWidth(5);
			gl.glColor4f(0, 0, 0, 1);
			gl.glDrawArrays(GL_LINE_LOOP, 0, 4);
		};
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
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glOrthof(0, width, 0, height, 1, -1);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);	
	}

	@Override
	public void dispose() {
		//((GLGame)game).finish();
	}
}
