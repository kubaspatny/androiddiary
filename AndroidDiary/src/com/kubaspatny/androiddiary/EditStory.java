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

import com.kubaspatny.androiddiary.StoryReaderContract.StoryEntry;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class EditStory extends Activity {

	public static final String EDIT_STORY_ID = "com.kubaspatny.androiddiary.EditStory.STORY_ID";
	private Story story;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		final View customActionBarView = inflater.inflate(
				R.layout.actionbar_custom_view_done_cancel, null);
		setContentView(R.layout.activity_edit_story);
		customActionBarView.findViewById(R.id.actionbar_done)
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {

						EditStory.this.updateStory(story,
								((EditText) EditStory.this
										.findViewById(R.id.editstory_title))
										.getText().toString(),
								((EditText) EditStory.this
										.findViewById(R.id.editstory_text))
										.getText().toString());
						finish();

					}
				});
		customActionBarView.findViewById(R.id.actionbar_cancel)
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// "Cancel"
						finish();
					}
				});

		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM,
				ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME
						| ActionBar.DISPLAY_SHOW_TITLE);
		actionBar.setCustomView(customActionBarView,
				new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
						ViewGroup.LayoutParams.MATCH_PARENT));

		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			return;
		}

		Long story_id = extras.getLong(EDIT_STORY_ID);
		if (story_id != null) {

			EditText title = (EditText) findViewById(R.id.editstory_title);
			EditText text = (EditText) findViewById(R.id.editstory_text);

			story = StoryReaderDbHelper.getStory(this, story_id);
			title.setText(story.getTitle());
			text.setText(story.getText());

		}

	} // onCreate()

	private void updateStory(Story story, String newTitle, String newText) {

		StoryReaderDbHelper mDbHelper = new StoryReaderDbHelper(this);
		SQLiteDatabase db = mDbHelper.getWritableDatabase();

		String strFilter = "_id=" + story.getID();
		ContentValues args = new ContentValues();
		args.put(StoryEntry.COLUMN_NAME_TITLE, newTitle);
		args.put(StoryEntry.COLUMN_NAME_TEXT, newText);

		db.update(StoryEntry.TABLE_NAME, args, strFilter, null);

		db.close();

	}

}
