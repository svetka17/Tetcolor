package com.tetcolor;


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.tetcolor.GLGame.GLGameState;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Window;
import android.view.WindowManager;

public abstract class GLGame extends Activity implements Game, Renderer {
    enum GLGameState {
        Initialized,
        Running,
        Paused,
        Finished,
        Idle,
        Pausa,
        Runing
    }
    
    GLSurfaceView glView;    
    GLGraphics glGraphics;
    Audio audio;
    Input input;
    FileIO fileIO;
    Screen screen;
    GLGameState state = GLGameState.Initialized;
    Object stateChanged = new Object();
    long startTime = System.nanoTime();
    WakeLock wakeLock;
    /*int size_font = 15;
    byte count_cubic = 7;// кол-во кубиков в ряду
    int width, height;//ширина и высота экрана
	float border_width;//ширина бордюра
	float width_cubic;//ширина кубика
	byte count_color = 5;// кол-во цветов
	int maxline;// = (int)height/(int)width_cubic;
	GL10 gl;
    */
    @Override 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
        glView = new GLSurfaceView(this);
        glView.setRenderer(this);
        setContentView(glView);
        
        glGraphics = new GLGraphics(glView);
        /*size_font = 15;
        count_cubic = 7;
        count_color = 5;
        */
        
    	
        fileIO = new AndroidFileIO(getAssets());
        audio = new AndroidAudio(this);
        input = new AndroidInput(this, glView, 1, 1);
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "TetcolorGLTest");        
    }
    
    public void onResume() {
        super.onResume();
        glView.onResume();
        wakeLock.acquire();
    }
    
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {        
        glGraphics.setGL(gl);
        
        synchronized(stateChanged) {
            if(state == GLGameState.Initialized)
                screen = getStartScreen();
            state = GLGameState.Running;
            screen.resume();
            startTime = System.nanoTime();
        }    
      //gl = glGraphics.getGL();
    	/*width =	glGraphics.getWidth();
    	height = glGraphics.getHeight();
    	width_cubic = width / count_cubic;
    	width_cubic= ((int) (width_cubic/10))*10; //Float.toString(width_cubic).length();
    	border_width=(width-width_cubic*count_cubic)/2;
    	maxline = (int)height/(int)width_cubic;
        */
    }
    
    public void setStatus (GLGameState st) {
    	state=st;
    }
    
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {        
    }

    @Override
    public void onDrawFrame(GL10 gl) {                
        GLGameState state = null;
        
        synchronized(stateChanged) {
            state = this.state;
        }
        
        if(state == GLGameState.Running) {
            float deltaTime = (System.nanoTime()-startTime) / 1000000000.0f;
            startTime = System.nanoTime();
            
            screen.update(deltaTime);
            screen.present(deltaTime);
        }
        
        if(state == GLGameState.Runing) {
            float deltaTime = (System.nanoTime()-startTime) / 1000000000.0f;
            startTime = System.nanoTime();
            
            screen.update(deltaTime);
            //screen.present(deltaTime);
        }
        
        if(state == GLGameState.Paused) {
            screen.pause();            
            synchronized(stateChanged) {
                this.state = GLGameState.Idle;
                stateChanged.notifyAll();
            }
        }
        
        if(state == GLGameState.Pausa) {
            //screen.pause();            
            //synchronized(stateChanged) {
              //  this.state = GLGameState.Idle;
                //stateChanged.notifyAll();
        		float st = System.nanoTime();
        		float dt = (System.nanoTime()-st) / 1000000000.0f;
        		while (dt<1) {
        			dt = (System.nanoTime()-st) / 1000000000.0f;
        		};
        		this.state=GLGameState.Running;
            //}
        }
        
        if(state == GLGameState.Finished) {
            screen.pause();
            screen.dispose();
            synchronized(stateChanged) {
                this.state = GLGameState.Idle;
                stateChanged.notifyAll();
            }            
        }
    }   
    
    @Override 
    public void onPause() {        
        synchronized(stateChanged) {
            if(isFinishing())            
                state = GLGameState.Finished;
            else
                state = GLGameState.Paused;
            while(true) {
                try {
                    stateChanged.wait();
                    break;
                } catch(InterruptedException e) {         
                }
            }
        }
        wakeLock.release();
        glView.onPause();  
        super.onPause();
    }    
    
    public GLGraphics getGLGraphics() {
        return glGraphics;
    }  
    
    @Override
    public Input getInput() {
        return input;
    }

    @Override
    public FileIO getFileIO() {
        return fileIO;
    }

    @Override
    public Graphics getGraphics() {
        throw new IllegalStateException("We are using OpenGL!");
    }

    @Override
    public Audio getAudio() {
        return audio;
    }

    @Override
    public void setScreen(Screen screen) {
        if (screen == null)
            throw new IllegalArgumentException("Screen must not be null");

        this.screen.pause();
        this.screen.dispose();
        screen.resume();
        screen.update(0);
        this.screen = screen;
    }

    @Override
    public Screen getCurrentScreen() {
        return screen;
    }   
}