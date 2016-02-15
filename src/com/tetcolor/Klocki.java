package com.tetcolor;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AbsoluteLayout;
import android.widget.ImageButton;
import android.widget.TextView;

public class Klocki extends Activity implements OnTouchListener {
ImageButton L1, L2, L3, L4, L5, Big, S1, S2, S3, S4;
View Abl;
TextView T1, T2;//, TV3;
float x,y, xd,yd,xu,yu, xy,xx,yy, w,h;
int l,r,u,d, display_w, display_h, cani, canj, can, cnt=0;
String s;
float scale = 2;//getResources().getDisplayMetrics().density;
int wh=(int) (80 * scale);

public class B {
	int idb;
	int xb;
	int yb;
	int wb;
	int hb;
	B () {
		this.idb=0;
		this.xb=0;
		this.yb=0;
		this.wb=0;
		this.hb=0;
	};
	B (int idb, int xb, int yb, int wb, int hb) {
		this.idb=idb;
		this.xb=xb;		
		this.yb=yb;
		this.wb=wb;
		this.hb=hb;
	}   
};

/*B [] mb = {
new B(R.id.L1,0,0,80,160), new B(R.id.L2,240,0,80,160), new B(R.id.L3,0,240,80,160), new B(R.id.L4,240,240,80,160), new B(R.id.L5,80,160,160,80), new B(R.id.Big,80,0,160,160),
new B(R.id.S1,80,240,80,80), new B(R.id.S2,160,240,80,80), new B(R.id.S3,80,320,80,80), new B(R.id.S4,160,320,80,80)
};*/

B [] mb = {
new B(R.id.L1,0,0,1,2), new B(R.id.L2,3,0,1,2), new B(R.id.L3,0,3,1,2), new B(R.id.L4,3,3,1,2), new B(R.id.L5,1,2,2,1), new B(R.id.Big,1,0,2,2),
new B(R.id.S1,1,3,1,1), new B(R.id.S2,2,3,1,1), new B(R.id.S3,1,4,1,1), new B(R.id.S4,2,4,1,1)
};

public class canv {
	int point;
	B button;
	canv () {
	this.point=0;
	this.button= new B();
	};
	
	canv (int point, B button) {
		this.point=point;
		this.button=button;
	}
};

canv [][] but  = {
		{new canv(1,mb[0]), new canv(1,mb[5]), new canv(1,mb[5]), new canv(1,mb[1])},
		{new canv(1,mb[0]), new canv(1,mb[5]), new canv(1,mb[5]), new canv(1,mb[1])},
		{new canv(), 		new canv(1,mb[4]), new canv(1,mb[4]), new canv()},
		{new canv(1,mb[2]), new canv(1,mb[6]), new canv(1,mb[7]), new canv(1,mb[3])},
		{new canv(1,mb[2]), new canv(1,mb[8]), new canv(1,mb[9]), new canv(1,mb[3])}
};
int lred = Color.argb(125, 255, 0, 255);
int lgreen = Color.argb(125, 0, 255, 255);
AbsoluteLayout.LayoutParams par;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        cnt=0;
        //GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        //int wi = graphicsDevice.getDisplayMode().getWidth();
        //int he = graphicsDevice.getDisplayMode().getHeight();
     
     DisplayMetrics displaymetrics = getResources().getDisplayMetrics();
     // узнаем размеры экрана из класса Display
     Display display = getWindowManager().getDefaultDisplay();
     DisplayMetrics metricsB = new DisplayMetrics();
     display.getMetrics(metricsB);
     display_h=metricsB.heightPixels; display_w=metricsB.widthPixels;
     /*T1.setText(
     		//"[Используя ресурсы] \n" +
         	//"Ширина: " + displaymetrics.widthPixels + "\n" +
         	//"Высота: " + displaymetrics.heightPixels + "\n"
         	//+ "\n" +
         	//"[Используя Display] \n" +
         	"Ширина: " + metricsB.widthPixels + "\n" +
         	"Высота: " + metricsB.heightPixels //+ "\n"
         );*/
     //Display di = getWindowManager().getDefaultDisplay(); 
     //int width = di.getWidth();  
     //int height = di.getHeight();
     
    /* B [] mb = {
    		 new B(R.id.L1,0,0,80,160), new B(R.id.L2,240,0,80,160), new B(R.id.L3,0,240,80,160), new B(R.id.L4,240,240,80,160), new B(R.id.L5,80,160,160,80), new B(R.id.Big,80,0,160,160),
    		 new B(R.id.S1,80,240,80,80), new B(R.id.S2,160,240,80,80), new B(R.id.S3,80,320,80,80), new B(R.id.S4,160,320,80,80)
    		 };*/

        L1 = (ImageButton) findViewById(R.id.L1);
        L2 = (ImageButton) findViewById(R.id.L2);
        L3 = (ImageButton) findViewById(R.id.L3);
        L4 = (ImageButton) findViewById(R.id.L4);
        L5 = (ImageButton) findViewById(R.id.L5);
        S1 = (ImageButton) findViewById(R.id.S1);
        S2 = (ImageButton) findViewById(R.id.S2);
        S3 = (ImageButton) findViewById(R.id.S3);
        S4 = (ImageButton) findViewById(R.id.S4);
        Big = (ImageButton) findViewById(R.id.Big);
        /*S1.setBackgroundColor(lred);
        S2.setBackgroundColor(lred);
        S3.setBackgroundColor(lred);
        S4.setBackgroundColor(lred);
        Big.setBackgroundColor(lgreen);
        */
        T1 = (TextView) findViewById(R.id.T1);
        T2 = (TextView) findViewById(R.id.T2);
        //TV3 = (TextView) findViewById(R.id.TV3);
        Abl = (View) findViewById(R.id.Abl);
        //IB1.setOnClickListener(this);
        Abl.setOnTouchListener(this);
        //IB1.setOnClickListener(this);
        //IB2.setOnClickListener(this);
        //IB3.setOnClickListener(this);
        //IB4.setOnClickListener(this);
        L1.setOnTouchListener(this);
        L2.setOnTouchListener(this);
        L3.setOnTouchListener(this);
        L4.setOnTouchListener(this);
        L5.setOnTouchListener(this);
        S1.setOnTouchListener(this);
        S2.setOnTouchListener(this);
        S3.setOnTouchListener(this);
        S4.setOnTouchListener(this);
        Big.setOnTouchListener(this);
        scale = getResources().getDisplayMetrics().density;
        wh=(int) (80 * scale);
        if (display_h>((display_w/4)/scale)*5) {
        wh=(int) (((display_w/4)/scale)*scale);
        for (int i = 0; i < mb.length; i++) {
    		//ib = (ImageButton) findViewById(mb[i].idb);
    		//ib.getWidth()
    		//(int) (5 * scale + 0.5f)
        	AbsoluteLayout.LayoutParams p = new AbsoluteLayout.LayoutParams(findViewById(mb[i].idb).getLayoutParams());
            p.x = (int) mb[i].xb*wh;
            p.y = (int) mb[i].yb*wh;
            p.height = (int) mb[i].hb*wh;
            p.width = (int) mb[i].wb*wh;
            findViewById(mb[i].idb).setLayoutParams(p);
    		//findViewById(mb[i].idb).setX(mb[i].xb * scale);
    		//findViewById(mb[i].idb).setY(mb[i].yb * scale);
    	};
        
        };
     
        AbsoluteLayout.LayoutParams p1 = new AbsoluteLayout.LayoutParams(T1.getLayoutParams());
        p1.x = 0;
        p1.y = (int) 5*wh+10;
        T1.setLayoutParams(p1);
        
        AbsoluteLayout.LayoutParams p2 = new AbsoluteLayout.LayoutParams(T2.getLayoutParams());
        p2.x = (int) 2*wh;
        p2.y = (int) 5*wh+10;
        T2.setLayoutParams(p2);
        T2.setText("h="+display_h+"w="+display_w+"s="+(int)((display_w/4)/scale)*5+" "+(int)wh);
     T1.setText("количество шагов = "+cnt);
        
    }

	// создание меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    // TODO Auto-generated method stub
    menu.add(0, 1, 0, "Начать начала");
    menu.add(0, 2, 0, "Выход");
    return super.onCreateOptionsMenu(menu);
    }    

    // обработка нажатий на пункты меню
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
    case 1:
    //заново
    	cnt=0;
    	T1.setText("количество шагов = "+cnt);
    	but[0][0]=new canv(1,mb[0]); but[0][1]=new canv(1,mb[5]); but[0][2]=new canv(1,mb[5]); but[0][3]=new canv(1,mb[1]);
    	but[1][0]=new canv(1,mb[0]); but[1][1]=new canv(1,mb[5]); but[1][2]=new canv(1,mb[5]); but[1][3]=new canv(1,mb[1]);
    	but[2][0]=new canv(); 		 but[2][1]=new canv(1,mb[4]); but[2][2]=new canv(1,mb[4]); but[2][3]=new canv();
    	but[3][0]=new canv(1,mb[2]); but[3][1]=new canv(1,mb[6]); but[3][2]=new canv(1,mb[7]); but[3][3]=new canv(1,mb[3]);
    	but[4][0]=new canv(1,mb[2]); but[4][1]=new canv(1,mb[8]); but[4][2]=new canv(1,mb[9]); but[4][3]=new canv(1,mb[3]);


    	for (int i = 0; i < mb.length; i++) {
    		//ib = (ImageButton) findViewById(mb[i].idb);
    		//ib.getWidth()
    		//(int) (5 * scale + 0.5f)
    		findViewById(mb[i].idb).setX(mb[i].xb*wh );
    		findViewById(mb[i].idb).setY(mb[i].yb*wh );
    		//AbsoluteLayout.LayoutParams p = new AbsoluteLayout.LayoutParams(findViewById(mb[i].idb).getLayoutParams());
            //p.x = (int) mb[i].xb*wh;
            //p.y = (int) mb[i].yb*wh;
            //p.height = (int) mb[i].hb*wh;
            //p.width = (int) mb[i].wb*wh;
            //findViewById(mb[i].idb).setLayoutParams(p);
    	};
    	
    break;
    case 2:
    // выход из приложения
    finish();
    break;
    }
    return super.onOptionsItemSelected(item);
    }    

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		xy=0;
		//float px=getApplicationContext().getResources().getDisplayMetrics().density;
		
		//class dpx {
			//float dp=getResources().getDisplayMetrics().heightPixels/100; //scaledDensity;
			//int to_dp (float d) {
				//return (int) (d * dp + 0.5f);};
			
		//};
		
		
		x = event.getX();    
		y = event.getY();        
		switch (event.getAction()) {    
		case MotionEvent.ACTION_DOWN: // нажатие      
			//TV1.setText("down: x="+x+" y="+y+"\n"+"x="+x/px+" y="+y/px+"\n"+"x="+(int) (x*dp+0.5f)+" y="+(int) (y*dp));     
			/*if (x>10 && x<90 &&
				y>10 && y<170) 
			{xy=1;};*/
			/*if (x>90 && x<170 &&
				y>10 && y<80)
			{xy=2;};*/
			xd=(int)x*scale; yd=(int)y*scale;
			s="";
			//s="xd="+xd+" yd="+yd;
			if (v.getId() != R.id.Abl) 
			//{xd=xd+(int)v.getX()/scale; yd=yd+(int)v.getY()/scale;};
			{//xd=(int)v.getX(); yd=(int)v.getY();
			//s=s+"\nxd="+xd+" yd="+yd;
			xd=xd+(int)v.getX()*scale; yd=yd+(int)v.getY()*scale;
			};
			/*switch (v.getId()) {     
			case R.id.L1:       
				xd=v.getX()+x; yd=v.getY()+y; w=v.getWidth(); h=v.getHeight();
				break;
			case R.id.L2:       
				xd=v.getX()+x; yd=v.getY()+y; w=v.getWidth(); h=v.getHeight();
				break;
			}
			*/
			//T1.setText(s+"\nxd="+xd+" yd="+yd);
		case MotionEvent.ACTION_MOVE: // движение      
			//TV3.setText("move: x="+x+" y="+y+"\n"+"x="+x/px+" y="+y/px+"\n"+"x="+(int) (x*dp+0.5f)+" y="+(int) (y*dp));  
			
			break;    
		case MotionEvent.ACTION_UP: // отпускание    

			r=l=u=d=can=cani=canj=0;
			s="";
			xu=(int)x*scale; yu=(int)y*scale;
			//s="xu="+xu+" yu="+yu;
			if (v.getId() != R.id.Abl)  
			{     
			    s=s+"!";  
			    
				
				xu=xu+(int)v.getX()*scale; yu=yu+(int)v.getY()*scale;
				//xu=xu+(int)v.getX()/scale; yu=yu+(int)v.getY()/scale;
				xx=xd-xu; yy=yd-yu;
				
				if (Math.abs(xx)>60 && xx<0) {r=1;};
				if (Math.abs(xx)>60 && xx>0) {l=1;};
				if (Math.abs(yy)>60 && yy<0) {d=1;};
				if (Math.abs(yy)>60 && yy>0) {u=1;};
				
				if (r!=0) {s="r"+s;};
				if (l!=0) {s="l"+s;};
				if (d!=0) {s="d"+s;};
				if (u!=0) {s="u"+s;};
				
				if (r!=0 /*&& xd<=display_w*/) {
					for (int i = 0; i <= 4; i++) {
						for (int j=0; j<3; j++) {
							
							if (v.getId()==but[i][j].button.idb) {
								int ww=(int)but[i][j].button.wb;
								int hh=(int)but[i][j].button.hb; 
								//if (ww==80) {ww=1;} else {ww=2;};
								//if (hh==80) {hh=1;} else {hh=2;};
								
								if ((i+hh-1)<=4 && (j+ww)<4) {
									can=but[i][j+ww].point+but[i+hh-1][j+ww].point;

									if (can==0) {
										v.setX((int)(v.getX()/scale+wh/scale)*scale);
										cnt=cnt+1;
										T1.setText("количество шагов = "+(int) cnt);
										//T1.setText("1="+but[i][j+ww].point+"whij="+ww+" "+hh+" "+i+" "+j+"\n2="+but[i+hh-1][j+ww].point);
										but[i][j+ww].point=1; but[i+hh-1][j+ww].point=1; 
										but[i][j+ww].button=but[i][j].button; but[i+hh-1][j+ww].button=but[i][j].button;
										//T2.setText(but[i][j].button.idb+"\nwhij="+ww+" "+hh+" "+i+" "+j+"\n"+s);
										but[i][j]=new canv();
										if (hh==2) {but[i+1][j]=new canv();};
										
									};
								} ;
								/*T1.setText("0="+i+" "+j+" "+but[2][0].point+but[2][0].button.idb+
										"\n1="+but[2][1].point+but[2][1].button.idb+
										"\n2="+but[2][2].point+but[2][2].button.idb+
										"\n3="+but[2][3].point+but[2][3].button.idb+
										"\n4="+but[2][4].point+but[2][4].button.idb
										);*/
								return true;
								//break;
								
							};
						}
				};
				}; 
				if (l!=0 /*&& xd>=0*/) {
					for (int i = 0; i <= 4; i++) {
						for (int j=1; j<=3; j++) {
							
							if (v.getId()==but[i][j].button.idb) {
								int ww=(int)but[i][j].button.wb;
								int hh=(int)but[i][j].button.hb; 
								//if (ww==80) {ww=1;} else {ww=2;};
								//if (hh==80) {hh=1;} else {hh=2;};
								
								if ((i+hh-1)<=4 && j>0) {
									can=but[i][j-1].point+but[i+hh-1][j-1].point;

									if (can==0) {
										v.setX((int)(v.getX()/scale-wh/scale)*scale);
										cnt=cnt+1; 
										T1.setText("количество шагов = "+(int) cnt);
										//T1.setText("1="+but[i][j+ww].point+"whij="+ww+" "+hh+" "+i+" "+j+"\n2="+but[i+hh-1][j+ww].point);
										but[i][j-1].point=1; but[i+hh-1][j-1].point=1; 
										but[i][j-1].button=but[i][j].button; but[i+hh-1][j-1].button=but[i][j].button;
										//T2.setText(but[i][j].button.idb+"\nwhij="+ww+" "+hh+" "+i+" "+j+"\n"+s);
										if (ww==1)
										{but[i][j]=new canv();
										if (hh==2) {but[i+1][j]=new canv();}}
										else {but[i][j+1]=new canv();
										if (hh==2) {but[i+1][j+1]=new canv();};}; 
										
									};
								} ;
								/*T2.setText("0="+i+" "+j+" "+but[2][0].point+but[2][0].button.idb+
										"\n1="+but[2][1].point+but[2][1].button.idb+
										"\n2="+but[2][2].point+but[2][2].button.idb+
										"\n3="+but[2][3].point+but[2][3].button.idb);*/
								//break;
								return true;
								
							};
						}
				};
					};
				if (d!=0 /*&& yu<=display_h*/) {
					for (int i = 0; i < 4; i++) {
						for (int j=0; j<=3; j++) {
							
							if (v.getId()==but[i][j].button.idb) {
								int ww=(int)but[i][j].button.wb;
								int hh=(int)but[i][j].button.hb; 
								//if (ww==80) {ww=1;} else {ww=2;};
								//if (hh==80) {hh=1;} else {hh=2;};
								
								if ((i+hh)<=4 && (j+ww-1<=3)) {
									can=but[i+hh][j].point+but[i+hh][j+ww-1].point;
									//s=s+"\n"+ww+" "+hh+" "+i+" "+j+" "+v.getId()+" "+but[i][j].button.idb;
									
									if (can==0) {
										v.setY((int)(v.getY()/scale+wh/scale)*scale);
										cnt=cnt+1; 
										T1.setText("количество шагов = "+(int) cnt);
										//T1.setText("1="+but[i][j+ww].point+"whij="+ww+" "+hh+" "+i+" "+j+"\n2="+but[i+hh-1][j+ww].point);
										but[i+hh][j].point=1; but[i+hh][j+ww-1].point=1; 
										but[i+hh][j].button=but[i][j].button; but[i+hh][j+ww-1].button=but[i][j].button;
										//T2.setText(but[i][j].button.idb+"\nwhij="+ww+" "+hh+" "+i+" "+j+"\n"+s);
										but[i][j]=new canv();
										if (ww==2) {but[i][j+1]=new canv();};
										
									};
								} ;
								/*T1.setText("0="+i+" "+j+" "+but[0][3].point+but[0][3].button.idb+
										"\n1="+but[1][3].point+but[1][3].button.idb+
										"\n2="+but[2][3].point+but[2][3].button.idb+
										"\n3="+but[3][3].point+but[3][3].button.idb);
								*/ //T1.setText(s);
								//break;
								return true;
								
							};
						};
						
				};
					};
				if (u!=0 /*&& yu>=0*/) {
					for (int i = 1; i <= 4; i++)  {
						for (int j=0; j<=3; j++) {
							
							if (v.getId()==but[i][j].button.idb) {
								int ww=(int)but[i][j].button.wb;
								int hh=(int)but[i][j].button.hb; 
								//if (ww==80) {ww=1;} else {ww=2;};
								//if (hh==80) {hh=1;} else {hh=2;};
								
								if ((i-1)<=4 && (j+ww-1)<=3) {
									can=but[i-1][j].point+but[i-1][j+ww-1].point;
									//s=s+"\n"+but[i-1][j].point+" "+but[i-1][j+ww-1].point+i+" "+j+" "+v.getId()+" "+but[i][j].button.idb;

									if (can==0) {
										v.setY((int)(v.getY()/scale-wh/scale)*scale);
										cnt=cnt+1; 
										T1.setText("количество шагов = "+(int) cnt);
										//s=s+" !!!";
										//T1.setText("1="+but[i][j+ww].point+"whij="+ww+" "+hh+" "+i+" "+j+"\n2="+but[i+hh-1][j+ww].point);
										  but[i-1][j].point=1; but[i-1][j+ww-1].point=1; 
										  but[i-1][j].button=but[i][j].button; but[i-1][j+ww-1].button=but[i][j].button;
										//T2.setText(but[i][j].button.idb+"\nwhij="+ww+" "+hh+" "+i+" "+j+"\n"+s);
										if (hh==1)
										{but[i][j]=new canv();
										if (ww==2) {but[i][j+1]=new canv();}}
										else {but[i+1][j]=new canv();
										if (ww==2) {but[i+1][j+1]=new canv();};}; 
										
									};
								} ;
								
								//T1.setText(s);
								/*T1.setText("0="+but[0][0].point+but[0][0].button.idb+
										"\n1="+but[1][0].point+but[1][0].button.idb+
										"\n2="+but[2][0].point+but[2][0].button.idb+
										"\n3="+but[3][0].point+but[3][0].button.idb+
										"\n4="+but[4][0].point+but[4][0].button.idb);*/
								//break;
								return true;
								
							};
						}
				};
					};
				//s=s+"\ncorX="+v.getX()+" corY="+v.getY();
				//v.setX(xu); v.setY(yu);
				
			};
			
			
			//T2.setText(s+"\nxu="+xu+" yu="+yu+"\nxx="+xx+" yy="+yy);
			//T2.setText(s+"\nxu="+xu+" yu="+yu+"\nxi="+(int)(xd-xu)+" yi="+(int)(yd-yu));
			
			break;
		case MotionEvent.ACTION_CANCEL:        
			break;    
			}    

		return true;  
		}
}

