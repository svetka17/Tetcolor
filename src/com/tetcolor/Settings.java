package com.tetcolor;

import static android.opengl.GLES20.GL_POINTS;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class Settings {
	public static boolean soundEnabled = false;//true;
	public static boolean musicEnabled = false;//true;
	public static int[] highscores = new int[] { 100, 80, 50, 30, 10 };
	public static byte count_cubic = 7;// кол-во кубиков в ряду
	public static byte count_color = 5; // кол-во цветов
	public static byte size_font = 15; // размер шрифта
	public static ArrayList<TColor> list_color = new ArrayList<TColor>(count_color);
	public final static String file = ".tet";
	private static Alfabeto a = new Alfabeto();
	public static int wid = 100;
	
	public static void draw_number(float x, float y, float siz, String num, int width, int height, GL10 gl) {
		int num_point;
		gl.glLoadIdentity();
		gl.glOrthof(0, width, 0, height, 1, -1);
		gl.glTranslatef( x, height-y, 0); 
		gl.glScalef(siz, siz, 1);
		//width_cubic=100;//width/(siz-(siz==7?2:5));
		
		for (int ii = 0; ii < num.length(); ii++)
		
		for (int i = 0; i < a.list_lettera.size(); i++) {
			if (a.list_lettera.get(i).lettera==num.charAt(ii)) {
				num_point=a.list_lettera.get(i).modelLettera.limit()/2;
				a.list_lettera.get(i).modelLettera.position(0);// читаем 2 цифры координаты
				gl.glPointSize(wid/(100/siz));
				gl.glColor4f(0, 0, 0, 1);
				//if (ii!=0)
				//gl.glTranslatef( /*siz/2*/list_lettera.get(i).width_lettera+0.2f, 0, 0);
				gl.glVertexPointer(2, GL10.GL_FLOAT,0, a.list_lettera.get(i).modelLettera);
				gl.glDrawArrays(GL_POINTS, 0, num_point);
				gl.glPointSize((wid/(100/siz))-5);
				
				gl.glColor4f(a.list_lettera.get(i).lettera_color.r,
						a.list_lettera.get(i).lettera_color.g,
						a.list_lettera.get(i).lettera_color.b, 1);
		gl.glDrawArrays(GL_POINTS, 0, num_point);
		gl.glTranslatef( /*siz/2*/a.list_lettera.get(i).width_lettera+0.2f, 0, 0);
			}
		}
		
	}
	
	public static void load(FileIO files) {
		BufferedReader in = null;
		
		// цвет 5 штук
				for (int i = 0; i <  count_color; i++) {
					list_color.add(new TColor());
				}
		try {
			in = new BufferedReader(new InputStreamReader(files.readFile(file)));
			soundEnabled = Boolean.parseBoolean(in.readLine());
			musicEnabled = Boolean.parseBoolean(in.readLine());
			count_cubic = Byte.parseByte(in.readLine());
			size_font = Byte.parseByte(in.readLine());
			count_color = Byte.parseByte(in.readLine());
			if (count_color>4) 
			{
				list_color.clear();
				for (int i = 0; i < count_color; i++) {
					float f1=Float.parseFloat(in.readLine());
					float f2=Float.parseFloat(in.readLine());
					float f3=Float.parseFloat(in.readLine());
					list_color.add(new TColor(f1,f2,f3,1));
				}
			}
			
			for (int i = 0; i < 5; i++) {
				highscores[i] = Integer.parseInt(in.readLine());
			}
		} catch (IOException e) {
			// Хорошо. Стандартные значения у нас есть.
		} catch (NumberFormatException e) {
			// Нет, ну как же прекрасны значения по умолчанию.
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
			}
		}
	}

	public static void save(FileIO files) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(files.writeFile(file)));
			out.write(Boolean.toString(soundEnabled));
			out.write(Boolean.toString(musicEnabled));
			out.write(Byte.toString(count_cubic));
			out.write(Byte.toString(size_font));
			out.write(Byte.toString(count_color));
			for (int i = 0; i < count_color; i++) {
				out.write(Float.toString(list_color.get(i).r));
				out.write(Float.toString(list_color.get(i).g));
				out.write(Float.toString(list_color.get(i).b));
			}
			
			for (int i = 0; i < 5; i++) {
				out.write(Integer.toString(highscores[i]));
			}
		} catch (IOException e) {
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
			}
		}
	}

	public static void addScore(int score) {
		for (int i = 0; i < 5; i++) {
			if (highscores[i] < score) {
				for (int j = 4; j > i; j--)
					highscores[j] = highscores[j - 1];
				break;
			}
		}
	}
}