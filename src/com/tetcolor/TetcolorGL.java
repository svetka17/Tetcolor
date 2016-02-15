package com.tetcolor;

import static android.opengl.GLES20.GL_LINE_LOOP;
import static android.opengl.GLES20.GL_POINTS;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

import com.tetcolor.GLGame.GLGameState;
import com.tetcolor.Input.TouchEvent;
//import com.tetcolor.GLGameState;
//import com.tetcolor.TetcolorGLTest.TetcolorGLTestScreen.Set;
//import com.tetcolor.TetcolorGLTest.TetcolorGLTestScreen.Set.ii;
//import com.tetcolor.TetcolorGLTest.TetcolorGLTestScreen.Set.set_rec;

public class TetcolorGL extends Screen {
	GLGraphics glGraphics;
	GL10 gl;
	//private float ratio;//width/heigth 
	private int maxline;// = (int)height/(int)width_cubic;
	//private byte count_cubic = 7;// кол-во кубиков в ряду
	private float width_cubic;// = 2/count_cubic; //ширина кубика
	//private byte count_color = 5;// кол-во цветов
	private int width, height;//ширина и высота экрана
	private float border_width;//ширина бордюра
	float x,y,ySpeed=1;//скорости по y
	private static final String TAG = "Tetcolor";
	private FloatBuffer vertexModel /*массив вершин кубика*/, vertexModelDown /*массив вершин падающего кубика*/, vertexModelBordur /*массив вершин бордюра*/;
	//private FloatBuffer uno, due, tre, quattro, cinque, sei, sette, otto, nove, zero, minus, ramka;
	//private FloatBuffer modelLettera;
	Random rand = new Random();
	//Vector2 touchPos = new Vector2();
	private double touchPosX,touchPosY;
	Set set;
	private byte flag_del=0;
	private int chet=0;//текущий счет
	GLGame glgame;
	private int dd;//текущая высота в кубиках
	//private Alfabeto a = new Alfabeto();

	
	/*void draw_number(float x, float y, float siz, String num) {
		int num_point;
		gl.glLoadIdentity();
		gl.glOrthof(0,  width, 0,  height, 1, -1);
		gl.glTranslatef( x,  height-y, 0); 
		gl.glScalef(siz, siz, 1);
		
		
		for (int ii = 0; ii < num.length(); ii++)
		
		for (int i = 0; i < Settings.a.list_lettera.size(); i++) {
			if (Settings.a.list_lettera.get(i).lettera==num.charAt(ii)) {
				num_point=Settings.a.list_lettera.get(i).modelLettera.limit()/2;
				Settings.a.list_lettera.get(i).modelLettera.position(0);// читаем 2 цифры координаты
				gl.glPointSize( width_cubic/(100/siz));
				gl.glColor4f(0, 0, 0, 1);
				//if (ii!=0)
				//gl.glTranslatef( list_lettera.get(i).width_lettera+0.2f, 0, 0);
				gl.glVertexPointer(2, GL10.GL_FLOAT,0, Settings.a.list_lettera.get(i).modelLettera);
				gl.glDrawArrays(GL_POINTS, 0, num_point);
				gl.glPointSize(( width_cubic/(100/siz))-7);
				
				gl.glColor4f(Settings.a.list_lettera.get(i).lettera_color.r,
						Settings.a.list_lettera.get(i).lettera_color.g,
						Settings.a.list_lettera.get(i).lettera_color.b, 1);
		gl.glDrawArrays(GL_POINTS, 0, num_point);
		gl.glTranslatef( Settings.a.list_lettera.get(i).width_lettera+0.2f, 0, 0);
			}
		}
		
	}*/
	
	class Set  {
		private byte set_w=1, set_h=1;//ширина и высота сета
		private byte spostamentoX=0, spostamentoY=0;
		
		//private int num_point;
		private byte [][][][] lrb = 
			{
			 {//0
			 {{1,0,1},{0,1,1},{0,0,0},{0,0,0}},//0
			 {{1,0,1},{0,1,0},{1,1,1},{0,0,0}},//1
			 {{1,0,0},{0,1,1},{1,1,1},{0,0,0}},//2
			 {{1,0,1},{0,0,1},{0,1,1},{0,0,0}},//3
			 {{1,0,1},{0,0,1},{0,1,0},{1,1,1}},//4
			 {{1,0,0},{0,0,1},{0,1,1},{1,1,1}},//5
			 {{1,0,1},{0,0,1},{0,0,1},{0,1,1}},//6
			 {{1,0,1},{0,0,0},{0,1,1},{1,1,1}},//7
			 {{1,0,1},{0,1,0},{1,0,1},{0,1,1}},//8
			 {{1,0,0},{0,1,1},{1,0,1},{0,1,1}},//9
			 {{1,0,0},{0,1,0},{1,0,1},{0,1,1}}//10
			 },
			 
			 {//1
			 {{1,1,0},{1,1,1},{0,0,0},{0,0,0}},//0->90
			 {{1,1,0},{0,1,1},{1,0,1},{0,0,0}},//1
			 {{0,1,0},{1,1,1},{1,0,1},{0,0,0}},//2
			 {{1,1,0},{1,1,0},{1,1,1},{0,0,0}},//3
			 {{1,1,0},{1,1,0},{0,1,1},{1,0,1}},//4
			 {{0,1,0},{1,1,0},{1,1,1},{1,0,1}},//5
			 {{1,1,0},{1,1,0},{1,1,0},{1,1,1}},//6
			 {{1,1,0},{0,1,0},{1,1,1},{1,0,1}},//7
			 {{1,1,0},{0,1,1},{1,0,0},{1,1,1}},//8
			 {{0,1,0},{1,1,1},{1,1,0},{1,0,1}},//9
			 {{0,1,0},{0,1,1},{1,0,0},{1,0,1}}//10
			 },
			 
			 {//2
			 {{0,1,1},{1,0,1},{0,0,0},{0,0,0}},//0->180
			 {{0,1,1},{1,0,1},{1,1,0},{0,0,0}},//1
			 {{0,1,1},{1,0,1},{1,1,0},{0,0,0}},//2
			 {{0,1,1},{0,0,1},{1,0,1},{0,0,0}},//3
			 {{0,1,1},{0,0,1},{1,0,1},{1,1,0}},//4
			 {{0,1,1},{0,0,1},{1,0,1},{1,1,0}},//5
			 {{0,1,1},{0,0,1},{0,0,1},{1,0,1}},//6
			 {{0,1,0},{0,0,1},{1,0,1},{1,1,0}},//7
			 {{0,1,1},{1,0,1},{0,1,0},{1,0,1}},//8
			 {{0,1,1},{1,0,1},{0,1,1},{1,0,0}},//9
			 {{0,1,1},{1,0,1},{0,1,0},{1,0,0}}//10
			 },
			 
			 {//3
			 {{1,1,1},{1,1,0},{0,0,0},{0,0,0}},//0->270
			 {{1,1,1},{1,0,0},{0,1,1},{0,0,0}},//1
			 {{1,0,1},{1,1,0},{0,1,1},{0,0,0}},//2
			 {{1,1,1},{1,1,0},{1,1,0},{0,0,0}},//3
			 {{1,1,1},{1,1,0},{1,0,0},{0,1,1}},//4
			 {{1,0,1},{1,1,0},{1,1,0},{0,1,1}},//5
			 {{1,1,1},{1,1,0},{1,1,0},{1,1,0}},//6
			 {{1,1,1},{1,0,0},{1,1,0},{0,1,1}},//7
			 {{1,1,1},{1,0,0},{0,1,1},{1,1,0}},//8
			 {{1,0,1},{1,1,0},{1,1,1},{0,1,0}},//9
			 {{1,0,1},{1,0,0},{0,1,1},{0,1,0}}//10
			 }	 
			};
		//private byte [] r0 = {1,1};
		//private byte [] b0 = new byte [12];
		
		class set_rec {
			byte r_x;
			byte r_y;
			byte r_color;
			byte r_left;//left 
			byte r_rigth;//rigth 
			byte r_bottom;//bottom
			byte r_count;
			byte r_number;
			set_rec () {
			this.r_x=0;
			this.r_y=0;
			this.r_color=0;
			this.r_left = 0;
			this.r_rigth=0;
			this.r_bottom=0;
			this.r_count=0;
			this.r_number=0;		
			};
			
			set_rec (byte r_x, byte r_y, byte r_color, byte r_left, byte r_rigth, byte r_bottom, byte r_count, byte r_number) {
				this.r_x=r_x;
				this.r_y=r_y;
				this.r_color=r_color;
				this.r_left=r_left;
				this.r_rigth=r_rigth;
				this.r_bottom=r_bottom;
				this.r_count=r_count;
				this.r_number=r_number;
			}
		}; 
		ArrayList<set_rec> rec = new ArrayList<set_rec>(4);	
		
		class ii {
			int irc;//номер направления 
			int i_i;//index_rects
			ii () {
			this.irc=0;
			this.i_i=0;
			};
			
			ii (int irc, int i_i) {
				this.irc=irc;
				this.i_i=i_i;
			}
		}; 
		
		Set () {
		do_set();	
		}
		
		//индекс при врещении влево увеличиваем
		byte r_left(byte pos) {
		if (pos==3)	{return (byte)0;} else {return (byte) (pos+1);}
		}
		
		//индекс при врещении влево уменьшаем
		byte r_rigth(byte pos) {
		if (pos==0)	{return (byte)3;} else {return (byte) (pos-1);}
		}
				
		//создаем следующую случайную фигуру из кубиков
		void do_set() {
			spostamentoY=0;spostamentoX=0;
			
			rec.clear();
			byte select_var=(byte)rand.nextInt(12);
			switch (select_var) {
		    case 11: rec.add(new set_rec((byte)0, (byte)0, (byte) rand.nextInt(Settings.count_color), (byte)1, (byte)1, (byte)1, (byte)0, select_var ));
		    		set_w=1;set_h=1;
		    		//*
		            break;
		    case 0: rec.add(new set_rec((byte)0, (byte)0, (byte) rand.nextInt(Settings.count_color), lrb[0][0][0][0], lrb[0][0][0][1], lrb[0][0][0][2], (byte)0, select_var ));
		    		rec.add(new set_rec((byte)1, (byte)0, (byte) rand.nextInt(Settings.count_color), lrb[0][0][1][0], lrb[0][0][1][1], lrb[0][0][1][2], (byte)0, select_var ));
		    		set_w=2;set_h=1;
		    		//**	
		    		break;
		    case 1: rec.add(new set_rec((byte)0, (byte)0, (byte) rand.nextInt(Settings.count_color),  lrb[0][1][0][0], lrb[0][1][0][1], lrb[0][1][0][2], (byte)0, select_var ));
					rec.add(new set_rec((byte)1, (byte)0, (byte) rand.nextInt(Settings.count_color),  lrb[0][1][1][0], lrb[0][1][1][1], lrb[0][1][1][2], (byte)0, select_var ));
					rec.add(new set_rec((byte)1, (byte)-1, (byte) rand.nextInt(Settings.count_color), lrb[0][1][2][0], lrb[0][1][2][1], lrb[0][1][2][2], (byte)0, select_var ));
					set_w=2;set_h=2;
					//**
					// *
					break;
		    case 2: rec.add(new set_rec((byte)0, (byte)0, (byte) rand.nextInt(Settings.count_color), lrb[0][2][0][0], lrb[0][2][0][1], lrb[0][2][0][2], (byte)0, select_var ));
					rec.add(new set_rec((byte)1, (byte)0, (byte) rand.nextInt(Settings.count_color), lrb[0][2][1][0], lrb[0][2][1][1], lrb[0][2][1][2], (byte)0, select_var ));
					rec.add(new set_rec((byte)0, (byte)-1, (byte) rand.nextInt(Settings.count_color), lrb[0][2][2][0], lrb[0][2][2][1], lrb[0][2][2][2], (byte)0, select_var ));
					set_w=2;set_h=2;
					//**
					//*
					break;
		    case 3: rec.add(new set_rec((byte)0, (byte)0, (byte) rand.nextInt(Settings.count_color), lrb[0][3][0][0], lrb[0][3][0][1], lrb[0][3][0][2], (byte)0, select_var ));
					rec.add(new set_rec((byte)1, (byte)0, (byte) rand.nextInt(Settings.count_color), lrb[0][3][1][0], lrb[0][3][1][1], lrb[0][3][1][2], (byte)0, select_var ));
					rec.add(new set_rec((byte)2, (byte)0, (byte) rand.nextInt(Settings.count_color), lrb[0][3][2][0], lrb[0][3][2][1], lrb[0][3][2][2], (byte)0, select_var ));
					set_w=3;set_h=1;
					//***
					break;
		    case 4: rec.add(new set_rec((byte)0, (byte)0, (byte) rand.nextInt(Settings.count_color), lrb[0][4][0][0], lrb[0][4][0][1], lrb[0][4][0][2], (byte)0, select_var ));
					rec.add(new set_rec((byte)1, (byte)0, (byte) rand.nextInt(Settings.count_color), lrb[0][4][1][0], lrb[0][4][1][1], lrb[0][4][1][2], (byte)0, select_var ));
					rec.add(new set_rec((byte)2, (byte)0, (byte) rand.nextInt(Settings.count_color), lrb[0][4][2][0], lrb[0][4][2][1], lrb[0][4][2][2], (byte)0, select_var ));
					rec.add(new set_rec((byte)2, (byte)-1, (byte) rand.nextInt(Settings.count_color), lrb[0][4][3][0], lrb[0][4][3][1], lrb[0][4][3][2], (byte)0, select_var ));
					set_w=3;set_h=2;
					//***
					//  *
					break;
		    case 5: rec.add(new set_rec((byte)0, (byte)0, (byte) rand.nextInt(Settings.count_color), lrb[0][5][0][0], lrb[0][5][0][1], lrb[0][5][0][2], (byte)0, select_var ));
					rec.add(new set_rec((byte)1, (byte)0, (byte) rand.nextInt(Settings.count_color), lrb[0][5][1][0], lrb[0][5][1][1], lrb[0][5][1][2], (byte)0, select_var ));
					rec.add(new set_rec((byte)2, (byte)0, (byte) rand.nextInt(Settings.count_color), lrb[0][5][2][0], lrb[0][5][2][1], lrb[0][5][2][2], (byte)0, select_var ));
					rec.add(new set_rec((byte)0, (byte)-1, (byte) rand.nextInt(Settings.count_color), lrb[0][5][3][0], lrb[0][5][3][1], lrb[0][5][3][2], (byte)0, select_var ));
					set_w=3;set_h=2;
					//***
					//*
					break;
		    case 6: rec.add(new set_rec((byte)0, (byte)0, (byte) rand.nextInt(Settings.count_color), lrb[0][6][0][0], lrb[0][6][0][1], lrb[0][6][0][2], (byte)0, select_var ));
					rec.add(new set_rec((byte)1, (byte)0, (byte) rand.nextInt(Settings.count_color), lrb[0][6][1][0], lrb[0][6][1][1], lrb[0][6][1][2], (byte)0, select_var ));
					rec.add(new set_rec((byte)2, (byte)0, (byte) rand.nextInt(Settings.count_color), lrb[0][6][2][0], lrb[0][6][2][1], lrb[0][6][2][2], (byte)0, select_var ));
					rec.add(new set_rec((byte)3, (byte)0, (byte) rand.nextInt(Settings.count_color), lrb[0][6][3][0], lrb[0][6][3][1], lrb[0][6][3][2], (byte)0, select_var ));
					set_w=4;set_h=1;
					//****
					break;
		    case 7: rec.add(new set_rec((byte)0, (byte)0, (byte) rand.nextInt(Settings.count_color), lrb[0][7][0][0], lrb[0][7][0][1], lrb[0][7][0][2], (byte)0, select_var ));
					rec.add(new set_rec((byte)1, (byte)0, (byte) rand.nextInt(Settings.count_color), lrb[0][7][1][0], lrb[0][7][1][1], lrb[0][7][1][2], (byte)0, select_var ));
					rec.add(new set_rec((byte)2, (byte)0, (byte) rand.nextInt(Settings.count_color), lrb[0][7][2][0], lrb[0][7][2][1], lrb[0][7][2][2], (byte)0, select_var ));
					rec.add(new set_rec((byte)1, (byte)-1, (byte) rand.nextInt(Settings.count_color), lrb[0][7][3][0], lrb[0][7][3][1], lrb[0][7][3][2], (byte)0, select_var ));
					set_w=3;set_h=2;
					//***
					// *
					break;
		    case 8: rec.add(new set_rec((byte)0, (byte)0, (byte) rand.nextInt(Settings.count_color), lrb[0][8][0][0], lrb[0][8][0][1], lrb[0][8][0][2], (byte)0, select_var ));
					rec.add(new set_rec((byte)1, (byte)0, (byte) rand.nextInt(Settings.count_color), lrb[0][8][1][0], lrb[0][8][1][1], lrb[0][8][1][2], (byte)0, select_var ));
					rec.add(new set_rec((byte)1, (byte)-1, (byte) rand.nextInt(Settings.count_color), lrb[0][8][2][0], lrb[0][8][2][1], lrb[0][8][2][2], (byte)0, select_var ));
					rec.add(new set_rec((byte)2, (byte)-1, (byte) rand.nextInt(Settings.count_color), lrb[0][8][3][0], lrb[0][8][3][1], lrb[0][8][3][2], (byte)0, select_var ));
					set_w=3;set_h=2;
					//**
					// **
					break;
		    case 9: rec.add(new set_rec((byte)1, (byte)0, (byte) rand.nextInt(Settings.count_color), lrb[0][9][0][0], lrb[0][9][0][1], lrb[0][9][0][2], (byte)0, select_var ));
					rec.add(new set_rec((byte)2, (byte)0, (byte) rand.nextInt(Settings.count_color), lrb[0][9][1][0], lrb[0][9][1][1], lrb[0][9][1][2], (byte)0, select_var ));
					rec.add(new set_rec((byte)0, (byte)-1, (byte) rand.nextInt(Settings.count_color), lrb[0][9][2][0], lrb[0][9][2][1], lrb[0][9][2][2], (byte)0, select_var ));
					rec.add(new set_rec((byte)1, (byte)-1, (byte) rand.nextInt(Settings.count_color), lrb[0][9][3][0], lrb[0][9][3][1], lrb[0][9][3][2], (byte)0, select_var ));
					set_w=3;set_h=2;
					// **
					//**
					break;
		    case 10:rec.add(new set_rec((byte)0, (byte)0, (byte) rand.nextInt(Settings.count_color), lrb[0][10][0][0], lrb[0][10][0][1], lrb[0][10][0][2], (byte)0, select_var ));
					rec.add(new set_rec((byte)1, (byte)0, (byte) rand.nextInt(Settings.count_color), lrb[0][10][1][0], lrb[0][10][1][1], lrb[0][10][1][2], (byte)0, select_var ));
					rec.add(new set_rec((byte)0, (byte)-1, (byte) rand.nextInt(Settings.count_color), lrb[0][10][2][0], lrb[0][10][2][1], lrb[0][10][2][2], (byte)0, select_var ));
					rec.add(new set_rec((byte)1, (byte)-1, (byte) rand.nextInt(Settings.count_color), lrb[0][10][3][0], lrb[0][10][3][1], lrb[0][10][3][2], (byte)0, select_var ));
					set_w=2;set_h=2;
					//**
					//**
					break;
		    default: 
		            break;
		}
					 
		}
				
		//рисуем сет
		void draw (float x, float y){
		
		for (int i = 0; i < rec.size(); i++) {
			gl.glLoadIdentity();
			gl.glOrthof(0,  width, 0,  height, 1, -1);
			gl.glTranslatef(rec.get(i).r_x* width_cubic, rec.get(i).r_y* width_cubic, 0);
			gl.glTranslatef( x, -y, 0); 
			
			gl.glColor4f(Settings.list_color.get(rec.get(i).r_color).r,
					Settings.list_color.get(rec.get(i).r_color).g,
					Settings.list_color.get(rec.get(i).r_color).b, 1);
			vertexModelDown.position(0);// читаем 2 цифры координаты
			gl.glVertexPointer(2, GL10.GL_FLOAT,0, vertexModelDown);
			gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4);
			gl.glLineWidth(5);
			gl.glColor4f(0, 0, 0, 1);
			gl.glDrawArrays(GL_LINE_LOOP, 0, 4);
			
			
			//Log.d(TAG, "zero "+String.valueOf((int) zero.array().length/2)+" minus "+String.valueOf((int)minus.array().length/2)+" uno "+String.valueOf((int)uno.array().length/2));
			/*draw_num(x+rec.get(i).r_x*width_cubic, y+(width_cubic/2)-rec.get(i).r_y*width_cubic, rec.get(i).r_left);
			draw_num(x+(width_cubic/5)+rec.get(i).r_x*width_cubic, y+(width_cubic/2)-rec.get(i).r_y*width_cubic,rec.get(i).r_rigth);// draw_l(x,y,rec.get(i).r_left); draw_b(x,y,rec.get(i).r_bottom);
			draw_num(x+(2*width_cubic/5)+rec.get(i).r_x*width_cubic, y+(width_cubic/2)-rec.get(i).r_y*width_cubic,rec.get(i).r_bottom);
			draw_num(x+(3*width_cubic/5)+rec.get(i).r_x*width_cubic, y+(width_cubic/2)-rec.get(i).r_y*width_cubic,rec.get(i).r_x);
			draw_num(x+(4*width_cubic/5)+rec.get(i).r_x*width_cubic, y+(width_cubic/2)-rec.get(i).r_y*width_cubic,rec.get(i).r_y);
			int recline=(int) ((height-y)/width_cubic)+rec.get(i).r_y;
			int reccol=(int) (x/width_cubic)+rec.get(i).r_x;
			if (recline>9) {recline=recline%10;}
			draw_num(x+(width_cubic/5)+rec.get(i).r_x*width_cubic,y+(width_cubic/1)-rec.get(i).r_y*width_cubic,(byte)recline);
			draw_num(x+(2*width_cubic/5)+rec.get(i).r_x*width_cubic,y+(width_cubic/1)-rec.get(i).r_y*width_cubic,(byte)reccol);*/
			//draw_num(x+(3*width_cubic/5)+rec.get(i).r_x*width_cubic,y+(width_cubic/1)-rec.get(i).r_y*width_cubic,(byte)rec.get(i).r_count);
			//draw_num(x+(4*width_cubic/5)+rec.get(i).r_x*width_cubic,y+(width_cubic/1)-rec.get(i).r_y*width_cubic,(byte)rec.get(i).r_number);
			
		};
		//рисуем рамку
		/*Log.d(TAG, "spostamentoY="+String.valueOf(spostamentoY));
		float[] vertices_ramka = {x+border_width,y,
				x+border_width+set.set_w*width_cubic,y, 
				x+border_width+set.set_w*width_cubic,y+set.set_h*width_cubic,
				x+border_width,y+set.set_h*width_cubic};
		ramka = ByteBuffer.allocateDirect(vertices_ramka.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
		ramka.put(vertices_ramka);
		ramka.position(0);
		gl.glLoadIdentity();
		gl.glOrthof(0, width, 0, height, 1, -1);
		//gl.glTranslatef(x, 0, 0);
		gl.glTranslatef(spostamentoX*width_cubic, height-y, 0);
		gl.glVertexPointer(2, GL10.GL_FLOAT,0, ramka);
		gl.glLineWidth(10);
		gl.glColor4f(0, 0, 0, 1);
		gl.glDrawArrays(GL_LINE_LOOP, 0, 4);*/	
		}	
		
		//проверка падающего сета снизу 0-нельзя 1-можно
		byte check_bottom() {			
			for (int ii = 0; ii < rec.size(); ii++) {
				if (rec.get(ii).r_bottom==1)
				{//нижние кубики проверяем
					int recline=(int) (( height-y)/ width_cubic)+rec.get(ii).r_y;
					int reccol=(int) (x/ width_cubic)+rec.get(ii).r_x;
					//Log.d(TAG, "count "+String.valueOf(list_cub.size())+"recline "+String.valueOf(recline)+" reccol "+String.valueOf(reccol));
					for (int iii = 0; iii <list_cub.size(); iii++) {
						if ((list_cub.get(iii).line==recline-1 && list_cub.get(iii).column==reccol) || recline<=0) 
							return 0;
						
					};
				}
				};
			return 1;
			}
		
		//проверка падающего сета справа 0-нельзя 1-можно
		byte check_rigth(){
			for (int ii = 0; ii < rec.size(); ii++) {
				if (rec.get(ii).r_rigth==1)
				{//правые кубики проверяем
					int recline=(int) (( height-y- width_cubic-ySpeed)/ width_cubic)+rec.get(ii).r_y;
					int reccol=(int) (x/ width_cubic)+rec.get(ii).r_x;
					//Log.d(TAG, "count "+String.valueOf(list_cub.size())+"recline "+String.valueOf(recline)+" reccol "+String.valueOf(reccol));
					if (reccol+1>= Settings.count_cubic) {return 0;} else
					for (int iii = 0; iii <list_cub.size(); iii++) {
						if ((list_cub.get(iii).line==recline || list_cub.get(iii).line==recline) && list_cub.get(iii).column==reccol+1) 
							return 0;	
					};
				}
				};
			return 1;			
		}
		
		//удаляем одинаковые
		void do_delete_math() {
		//delete

								for (int iii=0; iii<list_cub.size(); iii++) 
								{
									if (list_cub.get(iii).tmp==1)
										{
										//перед удалением пометить кубики, которые нужно сдвинуть вниз
										//и сдвинуть то что нужно
										int ts=0;
										int tc=0;
										 ts=list_cub.get(iii).line;
										 tc=list_cub.get(iii).column;
										//paint_rect.remove(iii);
										//iii--;
										chet++;
										flag_del=0;
										//s1=" "+ts+" "+tc;
										for (int k=0; k<list_cub.size(); k++) 
										{if (tc==list_cub.get(k).column && ts>list_cub.get(k).line /*&& paint_rect.get(k).tmp!=1 && paint_rect.get(k).tmp!=2*/) 
										{
										
										//paint_rect.set(k, 
											//	new rects(new RectF(paint_rect.get(k).rec.left,paint_rect.get(k).rec.top+w,paint_rect.get(k).rec.right,paint_rect.get(k).rec.top+w+w),//paint_rect.get(k).rec, 
												//paint_rect.get(k).rec_color, paint_rect.get(k).stroka+1 , paint_rect.get(k).colonka,paint_rect.get(k).tmp ) );
											list_cub.set(k, new Cub((byte)(list_cub.get(k).line+1), 
													list_cub.get(k).column, 
													list_cub.get(k).color, 
													list_cub.get(k).number, 
													list_cub.get(k).tmp ) );
													//paint_rect.get(k).rec_color, paint_rect.get(k).stroka+1 , paint_rect.get(k).colonka,paint_rect.get(k).tmp ) );

										}; 
										};
										//paint_rect.set(iii, new rects(paint_rect.get(iii).rec,  
											//	paint_rect.get(iii).rec_color, paint_rect.get(iii).stroka , paint_rect.get(iii).colonka,/*paint_rect.get(k).tmp*/2 ) );
										list_cub.set(iii, new Cub (list_cub.get(iii).line,list_cub.get(iii).column,list_cub.get(iii).color,list_cub.get(iii).number,(byte)2));
										}
									};	
				    			
				}; 

		//удаление
		void do_delete12() {
					for (int iii=0; iii<list_cub.size(); iii++) 
					{
						if (list_cub.get(iii).tmp==1)
							{
							//перед удалением пометить кубики, которые нужно сдвинуть вниз
							//и сдвинуть то что нужно
							int ts=list_cub.get(iii).line;
							int tc=list_cub.get(iii).column;
							 //ts=list_cub.get(iii).line;
							 //tc=list_cub.get(iii).column;
							//paint_rect.remove(iii);
							//iii--;
							//chet++;
							//flag_del=0;
							
							for (int k=0; k<list_cub.size(); k++) 
							{
								if (tc==list_cub.get(k).column && ts<list_cub.get(k).line //&& paint_rect.get(k).tmp!=1 && paint_rect.get(k).tmp!=2
									) 
								{
								list_cub.set(k, new Cub((byte)(list_cub.get(k).line-1), list_cub.get(k).column, list_cub.get(k).color, list_cub.get(k).number, list_cub.get(k).tmp ) );
								}; 
							};
							//list_cub.remove(iii);
							//list_cub.set(iii, new Cub (list_cub.get(iii).line,list_cub.get(iii).column,list_cub.get(iii).color,list_cub.get(iii).number,2));
							}
						
						};	
					try { 
							for (int iii=0; iii<list_cub.size(); iii++)
								if (list_cub.get(iii).tmp==1 || list_cub.get(iii).tmp==2) {list_cub.remove(iii);chet++;};
							} catch (Exception e) {}
				}
					
		//вибираем массимы из 3х и более кубиков, ставим им tmp=1 и удаляем их	
byte check_all1(){
			
			int[] arr = {-1,-1,-1,-1,-1,-1,-1,-1};//new int[8];//8 направлений
			int[] line = {0,0,0,0,0,0,0,0};//new int[8];
			ArrayList<ii> ii_i = new ArrayList<ii>(Settings.count_cubic);
			flag_del=0;			
			
			/////
			//while (flag_del==0) {			
			int tmp_str=125, tmp_col=125, tmp_color=125, flag=0, count=0;
    			//flag_del=1; 
    			
    			for (int i=0; i<list_cub.size(); i++) 
				{
    				tmp_color=list_cub.get(i).color;
    				tmp_str=list_cub.get(i).line;
    				tmp_col=list_cub.get(i).column;
    				flag=0; count=0;
    				ii_i.clear();
    				for (int a=0; a<arr.length; a++) arr[a]=-1;
    				for (int a=0; a<line.length; a++) line[a]=0;
    				
    				//start while
    				while (flag==0) {
    				for (int j=0; j<list_cub.size(); j++) 
    				{
    					if (list_cub.get(j).color==tmp_color && list_cub.get(j).tmp!=2 /*&& list_cub.get(j).tmp!=1*/) {
    					//1
    					if (list_cub.get(j).line==tmp_str-1-count && list_cub.get(j).column==tmp_col-1-count && (count-1)==arr[0]) { line[0]++; arr[0]=count; flag=1; ii_i.add(new ii(0,j) ); };
    					//2
    					if (list_cub.get(j).line==tmp_str-1-count && list_cub.get(j).column==tmp_col && (count-1)==arr[1]) { line[1]++; arr[1]=count; flag=1; ii_i.add(new ii(1,j) ); };
    					//3
    					if (list_cub.get(j).line==tmp_str-1-count && list_cub.get(j).column==tmp_col+1+count && (count-1)==arr[2]) { line[2]++; arr[2]=count; flag=1; ii_i.add(new ii(2,j) ); };
    					//4
    					if (list_cub.get(j).line==tmp_str && list_cub.get(j).column==tmp_col-1-count && (count-1)==arr[3]) { line[3]++; arr[3]=count; flag=1; ii_i.add(new ii(3,j) ); };
    					//5
    					if (list_cub.get(j).line==tmp_str && list_cub.get(j).column==tmp_col+1+count && (count-1)==arr[4]) { line[4]++; arr[4]=count; flag=1; ii_i.add(new ii(4,j) ); };
    					//6
    					if (list_cub.get(j).line==tmp_str+1+count && list_cub.get(j).column==tmp_col-1-count && (count-1)==arr[5]) { line[5]++; arr[5]=count; flag=1; ii_i.add(new ii(5,j) ); };
    					//7
    					if (list_cub.get(j).line==tmp_str+1+count && list_cub.get(j).column==tmp_col && (count-1)==arr[6]) { line[6]++; arr[6]=count; flag=1; ii_i.add(new ii(6,j) ); };
    					//8
    					if (list_cub.get(j).line==tmp_str+1+count && list_cub.get(j).column==tmp_col+1+count && (count-1)==arr[7]) { line[7]++; arr[7]=count; flag=1; ii_i.add(new ii(7,j) ); };
    					};
    				}
    				if (flag==1) {count++; flag=0;} else {flag=1;};
    				};//end wile
    				
    				
    				for (int k=0; k<8; k++) 
    				{
    					if (line[k]>=2) 
    					{
	    					//текущий кубик - на удаление
    						//list_cub.set(i, new rects(tmp_rec,tmp_color,tmp_str,tmp_col,1) );
    						list_cub.set(i, new Cub(tmp_str,tmp_col,tmp_color,(byte)0,(byte)1));
    						flag_del=1;
    						for (int kk=0; kk<ii_i.size(); kk++) 
	    					{
    							if (ii_i.get(kk).irc==k) 
    							{ list_cub.set(ii_i.get(kk).i_i, 
    									//new rects(list_cub.get(ii_i.get(kk).i_i).rec, list_cub.get(ii_i.get(kk).i_i).rec_color, list_cub.get(ii_i.get(kk).i_i).stroka , list_cub.get(ii_i.get(kk).i_i).colonka,1 ) 
    									new Cub (list_cub.get(ii_i.get(kk).i_i).line,list_cub.get(ii_i.get(kk).i_i).column,list_cub.get(ii_i.get(kk).i_i).color,list_cub.get(ii_i.get(kk).i_i).number,1)
    									); };
	    					};
 
    					};
    				};

				};
				if (flag_del==1) {glgame.state=GLGameState.Pausa; draw_cubs();  glgame.state=GLGameState.Pausa;}
    			return flag_del;
			 			
    			//};//while flag_del	
							
		}
		
		byte check_left() {			
			for (int ii = 0; ii < rec.size(); ii++) {
				if (rec.get(ii).r_left==1)
				{//левые кубики проверяем
					int recline=(int) (( height-y- width_cubic-ySpeed)/ width_cubic)+rec.get(ii).r_y;
					int reccol=(int) (x/ width_cubic)+rec.get(ii).r_x;
					//Log.d(TAG, "count "+String.valueOf(list_cub.size())+"recline "+String.valueOf(recline)+" reccol "+String.valueOf(reccol));
					if (reccol<=0) {return 0;} else
					for (int iii = 0; iii <list_cub.size(); iii++) {
						if ((list_cub.get(iii).line==recline || list_cub.get(iii).line==recline) && list_cub.get(iii).column==reccol-1) 
							return 0;
						
					};
				}
				};
			return 1;
			}
		
		//проверка падающего сета снизу с добавлением Cub
		void check_bottom_add(){
			for (int ii = 0; ii < rec.size(); ii++) {
				if (rec.get(ii).r_bottom==1)
				{//нижние кубики проверяем
					int recline=(int) (( height-y/*-width_cubic*/)/ width_cubic)+rec.get(ii).r_y;
					int reccol=(int) (x/ width_cubic)+rec.get(ii).r_x;
					//Log.d(TAG, "count_add "+String.valueOf(list_cub.size())+"recline "+String.valueOf(recline)+" reccol "+String.valueOf(reccol));
					if (recline<=0/*set.set_h-1*/) {
						for (int i = 0; i < rec.size(); i++) 
							list_cub.add(new Cub (((int) (( height-y/*-width_cubic*/)/ width_cubic)+rec.get(i).r_y),((int) (x/ width_cubic)+rec.get(i).r_x), 
									rec.get(i).r_color, 0, 0));
							y=0;x=Math.round(Settings.count_cubic/2)* width_cubic-((int)set_w/2)* width_cubic;
							do_set();
							return;
					} else 
					{
					for (int iii = 0; iii <list_cub.size(); iii++) {
						if (list_cub.get(iii).line==recline-1 && list_cub.get(iii).column==reccol) {
							for (int i = 0; i < rec.size(); i++)
								list_cub.add(new Cub (((int) (( height-y)/ width_cubic)+rec.get(i).r_y),((int) (x/ width_cubic)+rec.get(i).r_x), 
										rec.get(i).r_color,0, 0));
								y=0;x=Math.round(Settings.count_cubic/2)* width_cubic-((int)set_w/2)* width_cubic;
								do_set();
								return;
						}
					};}

				}
				}
				;			
		}
		
		//вращение падающего сета пpотив часовой на 90
		byte rotate_rigth(){
			byte rl=0;
			if (set.set_h+set.set_w!=2)
			{
				for (int ii = 0; ii < rec.size(); ii++) {

					int recline=(int) (( height-y)/ width_cubic)/*+rec.get(ii).r_y*/+rec.get(ii).r_x;
					int reccol=(int) (x/ width_cubic)/*+rec.get(ii).r_x*/-rec.get(ii).r_y;
					Log.d(TAG, "rigth ii="+String.valueOf(ii)+" recline "+String.valueOf(recline)+" reccol "+String.valueOf(reccol));
					if (reccol<0 || reccol>=Settings.count_cubic || recline<=0) {rl=1;} else
						for (int iii = 0; iii <list_cub.size(); iii++) {
								if ( list_cub.get(iii).line==recline && list_cub.get(iii).column==reccol) 
									rl=1;					
							};
						
					};
				if (rl!=1)	{
				for (int i = 0; i < rec.size(); i++)
					rec.set(i, new set_rec((byte)-rec.get(i).r_y, //(byte)(-rec.get(i).r_y+(set.set_h-1)), 
							rec.get(i).r_x, 
							rec.get(i).r_color, 
							lrb[set.r_rigth(rec.get(i).r_count)][rec.get(i).r_number][i][0],
							lrb[set.r_rigth(rec.get(i).r_count)][rec.get(i).r_number][i][1],
							lrb[set.r_rigth(rec.get(i).r_count)][rec.get(i).r_number][i][2],
							set.r_rigth(rec.get(i).r_count),
							rec.get(i).r_number
							));
				if (set_h!=set_w) {byte t; t=set_h; set_h=set_w; set_w=t;};
				}
			
		}	return rl;	
					
		}
		
		//падение падающего сета вниз
		int down() {
			int maxline=0,maxrecline=(int)( height/ width_cubic);
			for (int ii = 0; ii < rec.size(); ii++) {
				int recline=(int) (( height-y/*-width_cubic*/)/ width_cubic)+rec.get(ii).r_y;
				int reccol=(int) (x/ width_cubic)+rec.get(ii).r_x;
				
				if (rec.get(ii).r_bottom==1) {
					if (recline<maxrecline) maxrecline=recline;
					for (int iii = 0; iii <list_cub.size(); iii++) {
						if (list_cub.get(iii).column==reccol) 
							{
							if (list_cub.get(iii).line>maxline) maxline=list_cub.get(iii).line;
							//Log.d(TAG, "cub line="+String.valueOf(list_cub.get(iii).line)+" cub col "+String.valueOf(list_cub.get(iii).column));
							}
					};	
				}
				//Log.d(TAG, "y="+String.valueOf(y));
				//y=y+(maxrecline-maxline+1)*width_cubic-2*ySpeed;
				//Log.d(TAG, "y="+String.valueOf(y)+" maxrecline "+String.valueOf(maxrecline)+" maxline "+String.valueOf(maxline));
								
				};	
				return maxrecline-maxline+1;
		}
		
		//вращение падающего сета пo часовой на 90
		byte rotate_left() {
			byte rl=0;
			spostamentoY=0;spostamentoX=0;
			if (set.set_h+set.set_w!=2)
			{
				for (int ii = 0; ii < rec.size(); ii++) {

					int recline=(int) (( height-y)/ width_cubic)/*+rec.get(ii).r_y*/-rec.get(ii).r_x;
					int reccol=(int) (x/ width_cubic)/*+rec.get(ii).r_x*/+rec.get(ii).r_y+(set.set_h-1)/*+(set.set_w-2)*/;
					//Log.d(TAG, "left ii="+String.valueOf(ii)+" recline "+String.valueOf(recline)+" reccol "+String.valueOf(reccol));
					
					if (reccol<0 || reccol>=Settings.count_cubic || recline<=0) {rl=1;} else
					for (int iii = 0; iii <list_cub.size(); iii++) {
							if ( list_cub.get(iii).line==recline && list_cub.get(iii).column==reccol) 
								{rl=1;
								
								}
						};
						//Log.d(TAG, "rl="+String.valueOf(rl));
						//Log.d(TAG, "i="+String.valueOf(ii)+"recline="+String.valueOf(recline)+" reccol "+String.valueOf(reccol));
				};
					//Log.d(TAG, "spostamentoX="+String.valueOf(spostamentoX));	
					
					if (rl!=1) {
					
					for (int i = 0; i < rec.size(); i++)
					rec.set(i, new set_rec(
							(byte) (rec.get(i).r_y+(set.set_h-1)/*+(set.set_w-2)*/), //(byte)(-rec.get(i).r_y+(set.set_h-1)), 
							(byte) -rec.get(i).r_x, 
							rec.get(i).r_color, 
							lrb[set.r_left(rec.get(i).r_count)][rec.get(i).r_number][i][0],
							lrb[set.r_left(rec.get(i).r_count)][rec.get(i).r_number][i][1],
							lrb[set.r_left(rec.get(i).r_count)][rec.get(i).r_number][i][2],
							set.r_left(rec.get(i).r_count),
							rec.get(i).r_number
							));

					if (set_h!=set_w) {byte t; t=set_h; set_h=set_w; set_w=t;
					byte minX=(byte) Settings.count_cubic, maxY=(byte) -Settings.count_cubic;
					for (int i = 0; i < rec.size(); i++) { 
						if (rec.get(i).r_y>maxY) maxY=rec.get(i).r_y;
						if (rec.get(i).r_x<minX) minX=rec.get(i).r_x;
						
					}
					if (minX!=0) spostamentoX=minX; 
					if (maxY!=0) spostamentoY=maxY;
					};
				};
			
			}	
			
			return rl;	
		};
	}
	
	//ArrayList<TColor> list_color = new ArrayList<TColor>( count_color);
	ArrayList<Cub> list_cub = new ArrayList<Cub>();
    
public TetcolorGL(GLGame game) {
super(game);
glGraphics = ((GLGame)game).getGLGraphics();
glgame=(GLGame)game;
}

int get_maxline() {
	int d=0;		
	for (int iii=0; iii<list_cub.size(); iii++)
				if (list_cub.get(iii).line>d) {d=list_cub.get(iii).line;};
				return d;
}

@Override
public void update(float deltaTime) {
	List<TouchEvent> touchEvents =  glgame.getInput().getTouchEvents();
	
	 glgame.getInput().getKeyEvents();
	int len = touchEvents.size();
	for (int i = 0; i < len; i++) {
	TouchEvent event = touchEvents.get(i);
		touchPosX=event.getXU();
		touchPosY=event.getYU();
//		touchPos.x = (event.x / (float) glGraphics.getWidth()) * width;
	//	touchPos.y = (1 - event.y / (float) glGraphics.getHeight())	* height;

	//Log.d(TAG, "tpy "+String.valueOf(touchPosY)+" posy> "+String.valueOf(y)+" posy< "+String.valueOf(y+set.set_h*width_cubic));
	//Log.d(TAG, "tpx "+String.valueOf(touchPosX)+" posx> "+String.valueOf(x+set.spostamentoX*width_cubic)+" posx< "+String.valueOf(x+set.spostamentoX*width_cubic+width_cubic*set.set_w));
	
	if (touchPosX>=x+set.spostamentoX* width_cubic && touchPosX<=(x+set.spostamentoX* width_cubic+ width_cubic*set.set_w) 
			 && touchPosY<=(y+set.set_h* width_cubic)) 
	{//Log.d(TAG, "x "+String.valueOf(x)+" posx "+String.valueOf(touchPos.x)+" set_w "+String.valueOf(set.set_w));
	byte rl=set.rotate_left(); //if (rl==1) set.rotate_rigth();
	return;}
	//
	if (touchPosX>(x+set.spostamentoX* width_cubic+ width_cubic*set.set_w) && touchPosX<=( width- border_width) && set.check_rigth()==1
			&& !(touchPosX>=x+set.spostamentoX* width_cubic && touchPosX<=(x+set.spostamentoX* width_cubic+ width_cubic*set.set_w) 
					/*&& touchPos.y>=(y) */ && touchPosY<=(y+set.set_h* width_cubic))) 
		{x=x+ width_cubic;return;}
	//
	if (touchPosX<x+set.spostamentoX* width_cubic && touchPosX>= border_width && set.check_left()==1 
			&& !(touchPosX>=x+set.spostamentoX* width_cubic && touchPosX<=(x+set.spostamentoX* width_cubic+ width_cubic*set.set_w)
					/*&& touchPos.y>=(y) */ && touchPosY<=(y+set.set_h* width_cubic))) 
		{x=x- width_cubic;return;}
	
	if (touchPosX>=x+set.spostamentoX* width_cubic && touchPosX<=(x+set.spostamentoX* width_cubic+ width_cubic*set.set_w) 
			&& touchPosY>=(y+3* width_cubic) /* && touchPos.y<=(y+width_cubic+set.set_h*width_cubic)*/) 
	{//Log.d(TAG, "x "+String.valueOf(x)+" posx "+String.valueOf(touchPos.x)+" set_w "+String.valueOf(set.set_w));
	int dow=set.down(); if (dow>1) y=y+(dow-2)* width_cubic-ySpeed;
	return;}
	 
	}
	
	if (dd>= maxline-2) 
			{
				 glgame.state=GLGameState.Runing;
				//int font_size=15;
				float tmp=7* Settings.wid/(100/Settings.size_font);
				float tmp_x = (int) (((int)((int)( width/tmp)/2)-1.5f)*tmp),
						tmp_y= (int) ((height/2)+4.5f*tmp);
				
				if (inBounds((int)tmp_x, (int)(tmp_y), (int)tmp*4, (int)tmp)) {
					// state=GLGameState.Finished;
					dispose();
				}
				
				tmp_x = (int) (((int)((int)( width/tmp)/2)-2)*tmp);
				tmp_y= (int) ((height/2)+tmp-((int) Settings.wid/2));// (height/2)+1.5f*tmp;
				//Log.d(TAG, "tpy "+String.valueOf(touchPosY)+" tmp_y="+String.valueOf(tmp_y)+" tmp="+String.valueOf(tmp));
				//Log.d(TAG, "tpx "+String.valueOf(touchPosX)+" tmp_x "+String.valueOf(tmp_x)+" tmp="+String.valueOf(4*tmp));
				
				if (inBounds((int)tmp_x, (int)(tmp_y), (int)tmp*5, (int)tmp)) {
					list_cub.clear();
					set = new Set();
					chet=0;
					 glgame.state=GLGameState.Running;
					 glgame.setScreen(new TetcolorGL((GLGame)game));
				}
				
				tmp_x = (int) (((int)((int)( width/tmp)/2)-1));
				tmp_y= (int) ((height/2)+3*tmp);// (height/2)+1.5f*tmp;
				//Log.d(TAG, "tpy "+String.valueOf(touchPosY)+" tmp_y="+String.valueOf(tmp_y)+" tmp="+String.valueOf(tmp));
				//Log.d(TAG, "tpx "+String.valueOf(touchPosX)+" tmp_x "+String.valueOf(tmp_x)+" tmp="+String.valueOf(4*tmp));
				
				if (inBounds((int)tmp_x, (int)(tmp_y), (int)tmp*5, (int)tmp)) {
					list_cub.clear();
					chet=0;
					 glgame.setScreen(new MainMenuScreen((GLGame)game));
				}
				// 
				}
	else {y=y+ySpeed;
	set.check_bottom_add();
	byte chek=set.check_all1();}

}

private boolean inBounds(int x, int y, int width, int height) {
	if(touchPosX > x && touchPosX < x + width - 1 &&
	touchPosY > y && touchPosY < y + height - 1)
	return true;
	else
	return false;
	}

public void draw_cubs () {
	
	gl.glLoadIdentity();
	gl.glOrthof(0,  width, 0,  height, 1, -1);

	for (int i = 0; i < list_cub.size(); i++) {
		gl.glLoadIdentity();
		gl.glOrthof(0,  width, 0,  height, 1, -1);
		gl.glTranslatef(list_cub.get(i).column* width_cubic, list_cub.get(i).line* width_cubic+ width_cubic, 0);
		if (list_cub.get(i).tmp==1 || list_cub.get(i).tmp==2) 
			gl.glColor4f(Settings.list_color.get(list_cub.get(i).color).r+0.25f,
					Settings.list_color.get(list_cub.get(i).color).g+0.25f,
					Settings.list_color.get(list_cub.get(i).color).b+0.25f, 1);
			else
		gl.glColor4f(Settings.list_color.get(list_cub.get(i).color).r,
				Settings.list_color.get(list_cub.get(i).color).g,
				Settings.list_color.get(list_cub.get(i).color).b, 1);
		vertexModel.position(0);// читаем 2 цифры координаты
		gl.glVertexPointer(2, GL10.GL_FLOAT,0, vertexModel);
		gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4);// gl.glDrawElements(GL10.GL_TRIANGLES,6,GL10.GL_UNSIGNED_SHORT,indices);//аналог
		gl.glLineWidth(5);
		gl.glColor4f(0, 0, 0, 1);
		gl.glDrawArrays(GL_LINE_LOOP, 0, 4);
		//draw_number(list_cub.get(i).column*width_cubic+width_cubic/3, height-list_cub.get(i).line*width_cubic,5, String.valueOf(list_cub.get(i).line)/*.charAt(0)*/ );
		//draw_number(list_cub.get(i).column*width_cubic+2*width_cubic/3, height-list_cub.get(i).line*width_cubic,5, String.valueOf(list_cub.get(i).column)/*.charAt(0)*/ );
		if (list_cub.get(i).line>dd) {dd=list_cub.get(i).line;};
	};
	//int font_size=15;
	float tmp=7*Settings.wid/(100/Settings.size_font);
	if (dd>= maxline-2) {
		//int font_size=15; float tmp=7*width_cubic/(100/font_size);
		gl.glLoadIdentity();
		gl.glOrthof(0,  width, 0,  height, 1, -1);
		//draw_number(((int)((int)(width/tmp)/2)-1)*tmp, height/2, font_size, "СЧЕТ");
		Settings.draw_number(((int)((int)( width/tmp)/2)-1)*tmp,  height/3,  Settings.size_font, "СЧЕТ", width, height, gl);
		gl.glLoadIdentity();
		gl.glOrthof(0,  width, 0,  height, 1, -1);
		int font_size=Settings.size_font+10;
		tmp=7* Settings.wid/(100/font_size);
		Settings.draw_number(((int)((int)( width/tmp)/2)-0.5f)*tmp, ( height/3)+10+tmp, font_size, String.valueOf(chet), width, height, gl);
		//font_size=15;
		tmp=7* width_cubic/(100/ Settings.size_font);
		Settings.draw_number(((int)((int)( width/tmp)/2)-2)*tmp, ( height/2)+1.5f*tmp,  Settings.size_font, "СНАЧАЛА", width, height, gl);
		Settings.draw_number(((int)((int)( width/tmp)/2)-1)*tmp, ( height/2)+3*tmp,  Settings.size_font, "МЕНЮ", width, height, gl);
		Settings.draw_number(((int)((int)( width/tmp)/2)-1.5f)*tmp, ( height/2)+4.5f*tmp,  Settings.size_font, "ВЫХОД", width, height, gl);
	} 
	else
	{
		gl.glLoadIdentity();
		gl.glOrthof(0,  width, 0,  height, 1, -1);
		
		Settings.draw_number( width_cubic,  width_cubic, Settings.size_font-3, String.valueOf(chet), width, height, gl);
		//draw_number((width/2)+tmp, height/10, font_size, "^");
		//draw_number(width/2, height/10+0.2f+tmp, font_size, "<&>");
		//draw_number((width/2)+tmp, height/10+0.4f+2*tmp, font_size, "v");	
	}
	
}

@Override
 public void present(float deltaTime) {

	GL10 gl = glGraphics.getGL();
	
	gl.glLoadIdentity();
	gl.glOrthof(0,  width, 0,  height, 1, -1);
	
	vertexModelBordur.position(0);
	gl.glColor4f(0, 0, 1, 1);
	gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertexModelBordur);
	gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4);
	//gl.glColor4f(0, 0, 0, 1);
	//gl.glDrawArrays(GL_LINE_LOOP, 0, 4);
	
	vertexModelBordur.position(0);
	gl.glTranslatef(  border_width+ width_cubic*Settings.count_cubic, 0, 0);
	//gl.glColor4f(0, 0, 1, 1);
	gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertexModelBordur);
	gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4);
	//gl.glColor4f(0, 0, 0, 1);
	//gl.glDrawArrays(GL_LINE_LOOP, 0, 4);
	
	draw_cubs();
	set.do_delete12();

	if (dd<= maxline-2) 
	set.draw(x,y);
}
@Override
public void pause() {
	// TODO Auto-generated method stub
	
}
@Override
public void resume() {
	gl = glGraphics.getGL();
	width = //glGraphics.glView.getWidth();//
	glGraphics.getWidth();
	height = //glGraphics.glView.getHeight();//
	glGraphics.getHeight();
	width_cubic = width / Settings.count_cubic;
	width_cubic= ((int) (width_cubic/10))*10; //Float.toString(width_cubic).length();
	border_width=(width-width_cubic*Settings.count_cubic)/2;
	//border_width;//height-width_cubic;
	gl.glViewport(0, 0,  width,  height);
	gl.glClearColor(0, 0.5f, 1, 1);
	gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	gl.glMatrixMode(GL10.GL_MODELVIEW);//GL_PROJECTION);

	gl.glLoadIdentity();
	 //gl.glOrthof(0, 320, 0, 480, 1, -1);
	gl.glOrthof(0,  width, 0,  height, 1, -1);
	// в буфере будут координаты
	gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	// в буфере будет цвет
	// gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
	// толщина линии
	gl.glLineWidth(5);
		
	// цвет 5 штук
	/*for (int i = 0; i <  count_color; i++) {
		list_color.add(new TColor());
	}*/
	set = new Set();
	y=0;x=Math.round(Settings.count_cubic/2)* width_cubic-((int)set.set_w/2)* width_cubic;
	
	//Log.d(TAG, "r "+String.valueOf(Settings.list_color.get(0).r)+" g "+String.valueOf(Settings.list_color.get(0).g)+" b "+String.valueOf(Settings.list_color.get(0).b));
	maxline = (int)height/(int)width_cubic;
	//ratio=height/width;
	/*list_color.add(new TColor(1, 0, 0, 1));// красный
	list_color.add(new TColor(0, 1, 0, 1));// зеленый
	list_color.add(new TColor(0, 0, 1, 1));// синий
	list_color.add(new TColor(1, 1, 1, 1));// белый
	list_color.add(new TColor());// рандом
	*/
	// list_vertices.ensureCapacity(100);
	
	//public FloatBuffer CubModel (float width) {
		//float[] vertices_cub = {border_width,0,width_cubic+border_width,0,width_cubic+border_width,width_cubic,border_width,width_cubic};
	float[] vertices_cub = { border_width,- width_cubic, width_cubic+ border_width,- width_cubic, width_cubic+ border_width,0, border_width,0};
			vertexModel = ByteBuffer.allocateDirect(vertices_cub.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
			vertexModel.put(vertices_cub);
	float[] vertices_cub_down = { border_width, height- width_cubic, width_cubic+ border_width, height- width_cubic, width_cubic+ border_width, height, border_width, height};
			vertexModelDown = ByteBuffer.allocateDirect(vertices_cub_down.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
			vertexModelDown.put(vertices_cub_down);
	float[] vertices_bordur = {0,0, border_width,0, border_width, height,0, height};
			vertexModelBordur = ByteBuffer.allocateDirect(vertices_bordur.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
			vertexModelBordur.put(vertices_bordur);
	//make_lettera();		
			

}

@Override
public void dispose() {
	//game.setScreen(new MainMenuScreen();
	//Intent intent = new Intent(Class.forName("com.tetcolor.MainMenuScreen"));
	//startActivity(intent);
	list_cub.clear();
	chet=0;
	 glgame.finish();
	
}
}