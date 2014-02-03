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

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class StoryCursorAdapter extends CursorAdapter {

	private Context context;
	private final LayoutInflater inflater;
	private Cursor cursor;

	public StoryCursorAdapter(Context context, Cursor cursor) {
		super(context, cursor);
		this.context = context;
		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.cursor = cursor;
	}

	@Override
	public void bindView(View view, Context context, Cursor c) {
		
		TextView mTitle = (TextView) view.findViewById(R.id.row_title);		
		TextView mText = (TextView) view.findViewById(R.id.row_text);
		
		TextView mDay = (TextView) view.findViewById(R.id.row_date);
		TextView mMonth = (TextView) view.findViewById(R.id.row_month);
		TextView mYear = (TextView) view.findViewById(R.id.row_year);

		String title = c.getString(c.getColumnIndex(StoryEntry.COLUMN_NAME_TITLE));
		String text = c.getString(c.getColumnIndex(StoryEntry.COLUMN_NAME_TEXT));
		int day = c.getInt(c.getColumnIndex(StoryEntry.COLUMN_NAME_STORYDAY));		
		int month = c.getInt(c.getColumnIndex(StoryEntry.COLUMN_NAME_STORYMONTH));
		int year = c.getInt(c.getColumnIndex(StoryEntry.COLUMN_NAME_STORYYEAR));
		
		mTitle.setText(title);
		mText.setText(text);
		mDay.setText(day+"");
		mMonth.setText(month+"");
		mYear.setText(year+"");
		

	}

	@Override
	public View newView(Context context, Cursor c, ViewGroup root) {
		View view = inflater.inflate(R.layout.story_row_layout, root, false);

		TextView mTitle = (TextView) view.findViewById(R.id.row_title);		
		TextView mText = (TextView) view.findViewById(R.id.row_text);
		
		TextView mDay = (TextView) view.findViewById(R.id.row_date);
		TextView mMonth = (TextView) view.findViewById(R.id.row_month);
		TextView mYear = (TextView) view.findViewById(R.id.row_year);

		String title = c.getString(c.getColumnIndex(StoryEntry.COLUMN_NAME_TITLE));
		String text = c.getString(c.getColumnIndex(StoryEntry.COLUMN_NAME_TEXT));
		int day = c.getInt(c.getColumnIndex(StoryEntry.COLUMN_NAME_STORYDAY));		
		int month = c.getInt(c.getColumnIndex(StoryEntry.COLUMN_NAME_STORYMONTH));
		int year = c.getInt(c.getColumnIndex(StoryEntry.COLUMN_NAME_STORYYEAR));
		
		mTitle.setText(title);
		mText.setText(text);
		mDay.setText(day+"");
		mMonth.setText(month+"");
		mYear.setText(year+"");	

		return view;
	}

	@Override
	public Cursor getCursor() {		
		return cursor;
	}

	@Override
	public Cursor swapCursor(Cursor newCursor) {
		this.cursor = newCursor;
		return super.swapCursor(newCursor);
	}
	
	
	
	

}
