package com.example.mycameratest;
import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	Uri imageFileUri;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ImageButton ib = (ImageButton) findViewById(R.id.imageButton1);
		ib.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				takeAPhoto();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	public void takeAPhoto(){
		String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyCameraTest";
		File folder = new File(path);
		if (!folder.exists())	
			folder.mkdir();
		String imagePathAndFileName = path + File.separator + String.valueOf(System.currentTimeMillis()) + ".jpg";
		File imageFile = new File(imagePathAndFileName);
		imageFileUri = Uri.fromFile(imageFile);
		
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
		startActivityForResult(intent, 12345);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent intent){
		if (requestCode == 12345){
			TextView tv = (TextView)findViewById(R.id.textView1);
			if (resultCode == RESULT_OK){
				tv.setText("Photo completed!");
				ImageButton ib = (ImageButton)findViewById(R.id.imageButton1);
				ib.setImageDrawable(Drawable.createFromPath(imageFileUri.getPath()));
			}
			else
				if (resultCode == RESULT_CANCELED)
					tv.setText("Photo was canceled!");
				else
					tv.setText("What happened?!!");
		}
	}
}
