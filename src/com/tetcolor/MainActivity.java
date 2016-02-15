package com.tetcolor;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends ListActivity {

	String tests[] = {
			"Klocki",
			/*"LifeCycleTest",
			"SingleTouchTest",
			"MultiTouchTest",
			"KeyTest",*/
			//"AccelerometerTest",
			/*"AssetsTest",
			"ExsternalStorageTest",
			"SoundPoolTest",
			"MediaPlayerTest",
			"FullScreenTest",
			"RGBColor",
			"ActivityGame",*/
			"MainMenu"/*,
			"SurfaceViewTest"*/
			};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String> (this,android.R.layout.simple_list_item_1, tests));
        //setContentView(R.layout.activity_main);
    }

    @Override
    protected void onListItemClick(ListView list, View view, int position, long id) {
        super.onListItemClick(list, view, position, id);
        String testName = tests[position];
        try {
        	Class clazz = Class.forName("com.tetcolor."+testName);
        	Intent intent = new Intent(this, clazz);
        	startActivity(intent);
        }
        catch (ClassNotFoundException e) {
        	e.printStackTrace();
        }
    }

    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        /*int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);*/
    	return true;
    }
}
