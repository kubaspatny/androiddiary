/*
 * Copyright 2014 Jakub Spatny
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kubaspatny.androiddiary;

import java.util.ArrayList;

import com.kubaspatny.androiddiary.StoryReaderContract.StoryEntry;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnSaveListener {

	private static final int DIALOG_ALERT = 10;
	private static StoryCursorAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		ListView listView = (ListView) findViewById(R.id.listView);
		adapter = new StoryCursorAdapter(this, getFetchAllCursor());
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				Log.d("CURSOR DEBUG", "position: " + position + ", id: " + id);
				Cursor cursor = ((StoryCursorAdapter) adapterView.getAdapter())
						.getCursor();
				cursor.moveToPosition(position);
				long story_id = cursor.getLong(cursor
						.getColumnIndex(StoryEntry._ID));				

				Intent i = new Intent(MainActivity.this, StoryView.class);
				i.putExtra(StoryView.STORY_ID, story_id);
				startActivity(i);

			}

		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		ActionBar actionbar = getActionBar();
		actionbar.setTitle("");
		actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#C01414")));
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.add_story:
			new CustomDialog().show(getFragmentManager(), "dialog");
			break;
		default:
			break;
		}

		return true;
	}

	private Cursor getFetchAllCursor() {

		StoryReaderDbHelper mDbHelper = new StoryReaderDbHelper(
				getApplicationContext());
		SQLiteDatabase db = mDbHelper.getReadableDatabase();

		String[] projection = { StoryEntry._ID, StoryEntry.COLUMN_NAME_TITLE,
				StoryEntry.COLUMN_NAME_TEXT, StoryEntry.COLUMN_NAME_STORYDAY,
				StoryEntry.COLUMN_NAME_STORYMONTH,
				StoryEntry.COLUMN_NAME_STORYYEAR };

		String sortOrder = StoryEntry._ID + " DESC";

		Cursor c = db.query(StoryEntry.TABLE_NAME, // The table to query
				projection, // The columns to return
				null, // The columns for the WHERE clause
				null, // The values for the WHERE clause
				null, // don't group the rows
				null, // don't filter by row groups
				sortOrder // The sort order
				);

		c.moveToFirst();
		db.close();
		return c;

	}

	@Override
	public void onSave(long id) {
		Toast.makeText(this, "Saved object with id: " + id, Toast.LENGTH_SHORT)
				.show();		

		adapter.swapCursor(getFetchAllCursor());

	}

}
