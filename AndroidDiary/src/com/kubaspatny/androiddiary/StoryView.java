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

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.kubaspatny.androiddiary.StoryReaderContract.StoryEntry;
import com.kubaspatny.androiddiary.view.NotifyingScrollView;

public class StoryView extends Activity {

	public static final String STORY_ID = "com.kubaspatny.androiddiary.StroyView.STORY_ID";
	private Story story;
	private Long story_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_story_view);

		((NotifyingScrollView) findViewById(R.id.scroll_view))
				.setOnScrollChangedListener(mOnScrollChangedListener);

		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			return;
		}
		story_id = extras.getLong(STORY_ID);

	} // onCreate

	@Override
	protected void onStart() {
		super.onStart();

		if (story_id != null) {

			TextView title = (TextView) findViewById(R.id.storyview_title);
			TextView text = (TextView) findViewById(R.id.storyview_text);

			TextView day = (TextView) findViewById(R.id.storyview_date);
			TextView month = (TextView) findViewById(R.id.storyview_month);
			TextView year = (TextView) findViewById(R.id.storyview_year);

			story = StoryReaderDbHelper.getStory(this, story_id);

			title.setText(story.getTitle());
			text.setText(story.getText());

			day.setText(story.getDay() + "");
			month.setText(story.getMonth() + "");
			year.setText(story.getYear() + "");

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_storyview, menu);
		ActionBar actionbar = getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		actionbar.setTitle("");
		actionbar.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#C01414")));
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.delete_story:
			deleteStory(story);
			Toast.makeText(this, "Story \"deleted\"", Toast.LENGTH_SHORT)
					.show();
			break;
		case R.id.edit_story:
			Toast.makeText(this, "Launch Activity Edit", Toast.LENGTH_SHORT)
					.show();
			Intent i = new Intent(this, EditStory.class);
			i.putExtra(EditStory.EDIT_STORY_ID, story.getID());
			startActivity(i);

			break;

		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			break;
		default:
			super.onOptionsItemSelected(item);
			break;
		}

		return true;
	}

	private void deleteStory(Story story) {

		StoryReaderDbHelper mDbHelper = new StoryReaderDbHelper(this);
		SQLiteDatabase db = mDbHelper.getWritableDatabase();

		long id = story.getID();

		db.delete(StoryEntry.TABLE_NAME, StoryEntry._ID + " = " + id, null);
		db.close();
		Intent intent = new Intent(this, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		this.finish();

	}

	private NotifyingScrollView.OnScrollChangedListener mOnScrollChangedListener = new NotifyingScrollView.OnScrollChangedListener() {

		@Override
		public void onScrollChanged(ScrollView who, int l, int t, int oldl,
				int oldt) {

			int scrollview_height = who.getHeight();

			int diff = oldt - t;
			int ab_height = StoryView.this.getActionBar().getHeight();

			Log.d("onScrollChanged2", "oldt: " + oldt + ", t: " + t);

			View view = (View) who.getChildAt(who.getChildCount() - 1);
			Log.d("onScrollChanged1", "Bottom: " + view.getBottom());
			if (diff < -20
					|| (t > ab_height + 20 && t < ab_height * 2 && diff < 0)) {

				if (t + scrollview_height > view.getBottom() - ab_height - 20)
					return;
				StoryView.this.getActionBar().hide();

				return;
			}

			if (diff > 20 || t < 10) {
				StoryView.this.getActionBar().show();
			}

		}
	};

}
