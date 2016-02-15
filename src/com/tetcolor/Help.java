package com.tetcolor;

//import static android.opengl.GLES20.GL_LINE_LOOP;
//import static android.opengl.GLES20.GL_POINTS;

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
	public class Help extends Screen {
	GLGraphics glGraphics;
	GL10 gl;
	//private byte count_cubic = 7;// êîë-âî êóáèêîâ â ğÿäó
	//private float width_cubic;// = 2/count_cubic; //øèğèíà êóáèêà
	private int width, height;//øèğèíà è âûñîòà ıêğàíà
	//Alfabeto a = new Alfabeto();
	private double touchPosX,touchPosY;
	private static final String TAG = "Tetcolor";
	private FloatBuffer ramka;
	Rect soundBounds, playBounds, highscoresBounds, helpBounds, exitBounds;
	
	public Help(GLGame game) {
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
		
		xx=(int) (((int)((int)(width/tmp)/2)-1)*tmp); yy=(int)((height/3)+5*Settings.wid-(int)(Settings.wid/2));
		playBounds = new Rect(xx, yy, xx+(int)tmp*3, yy+(int)tmp);
		
		
		xx=(int)(((int)((int)(width/tmp)/2)-1.5f)*tmp); yy=(int)((height/3)+7*Settings.wid-((int) Settings.wid/2));
		exitBounds = new Rect (xx, yy, xx+(int)tmp*4, yy+(int)tmp);
		//Settings.load(files);
		}
	
	@Override
	public void update(float deltaTime) {
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
		}

		//finish
		if (inBounds(exitBounds.left, exitBounds.top, exitBounds.right, exitBounds.bottom)) {
			game.setScreen(new MainMenuScreen((GLGame)game));
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

				
		gl.glLoadIdentity();
		gl.glOrthof(0, width, 0, height, 1, -1);
		//font_size=15;
		float tmp=7*Settings.wid/(100/Settings.size_font);
		Settings.draw_number(((int)((int)(width/tmp)/2)-2.5f)*tmp, (height/3)-2.5f*Settings.wid, Settings.size_font-9, "ÑÎÁÅĞÈÒÅ 3 È ÁÎËÅÅ", width, height, gl);
		Settings.draw_number(((int)((int)(width/tmp)/2)-2.8f)*tmp, (height/3)-1f*Settings.wid, Settings.size_font-9, "ÊÓÁÈÊÎÂ ÎÄÍÎÃÎ ÖÂÅÒÀ", width, height, gl);
		Settings.draw_number(((int)((int)(width/tmp)/2)-2f)*tmp, (height/3)+0.5f*Settings.wid, Settings.size_font-9, "ÏÎ ÂÅĞÒÈÊÀËÈ,", width, height, gl);
		Settings.draw_number(((int)((int)(width/tmp)/2)-2f)*tmp, (height/3)+2f*Settings.wid, Settings.size_font-9, "ÏÎ ÃÎĞÈÇÎÍÒÀËÈ" , width, height, gl);
		Settings.draw_number(((int)((int)(width/tmp)/2)-2f)*tmp, (height/3)+3.5f*Settings.wid, Settings.size_font-9, "ÈËÈ ÏÎ ÄÈÀÃÎÍÀËÈ", width, height, gl);

		Settings.draw_number(((int)((int)(width/tmp)/2)-2)*tmp, (height/3)+5*Settings.wid, Settings.size_font, "ÈÃĞÀÒÜ", width, height, gl);
		Settings.draw_number(((int)((int)(width/tmp)/2)-1.5f)*tmp, (height/3)+7*Settings.wid, Settings.size_font, "ÍÀÇÀÄ", width, height, gl);

	}

	@Override
	public void pause() {
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
		// â áóôåğå áóäóò êîîğäèíàòû
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

	}

	@Override
	public void dispose() {
	}
}
//}
