package vlt.android.textenlarger;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import vlt.android.textenlarger.R;
import vlt.text.textimprover.TextImprover;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.text.ClipboardManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

@SuppressWarnings("deprecation")
public class MainActivity extends Activity {
    private TextImprover textImprover;
    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	try {
	    init();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (ParserConfigurationException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (SAXException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {	
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.main, menu);
	MenuItem shareItem = menu.findItem(R.id.share);
	mShareActionProvider = (ShareActionProvider) MenuItemCompat
		.getActionProvider(shareItem);
	// Set history different from the default before getting the action
	// view since a call to MenuItemCompat.getActionView() calls
	// onCreateActionView() which uses the backing file name. Omit this
	// line if using the default share history file is desired.
	shareItem.setOnMenuItemClickListener(new OnMenuItemClickListener() {
	    
	    @Override
	    public boolean onMenuItemClick(MenuItem item) {
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, ((EditText) findViewById(R.id.editText)).getText());
		sendIntent.setType("text/plain");
		startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.share)));
		return false;
	    }
	});
	
	return true;
    }

    private void init() throws IOException, ParserConfigurationException,
	    SAXException {

	textImprover = new TextImprover(getResources().openRawResource(
		R.raw.enlargerprefs));
	Button buttonEnlarge = (Button) findViewById(R.id.buttonEnlarge);
	buttonEnlarge.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {
		EditText editTextOriginal = (EditText) findViewById(R.id.editText);
		String textEnlarged = editTextOriginal.getText().toString();
		String textImproved = textImprover.improveText(textEnlarged);
		editTextOriginal.setText(textImproved);
	    }
	});
	Button buttonCopy = (Button) findViewById(R.id.buttonCopy);
	buttonCopy.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {
		EditText editTextOriginal = (EditText) findViewById(R.id.editText);
		ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
		clipboard.setText(editTextOriginal.getText());
	    }
	});
    }
    
}
