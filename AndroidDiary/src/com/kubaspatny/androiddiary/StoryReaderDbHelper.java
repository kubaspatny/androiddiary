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
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StoryReaderDbHelper extends SQLiteOpenHelper {
	
	// If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "StoryReader.db";
    
    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_STORIES =
        "CREATE TABLE " + StoryEntry.TABLE_NAME + " (" +
        StoryEntry._ID + " INTEGER PRIMARY KEY," +
        StoryEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
        StoryEntry.COLUMN_NAME_TEXT + TEXT_TYPE + COMMA_SEP +
        StoryEntry.COLUMN_NAME_STORYDAY + INT_TYPE + COMMA_SEP +
        StoryEntry.COLUMN_NAME_STORYMONTH + INT_TYPE + COMMA_SEP +
        StoryEntry.COLUMN_NAME_STORYYEAR + INT_TYPE +
        " )";

    private static final String SQL_DELETE_STORIES =
        "DROP TABLE IF EXISTS " + StoryEntry.TABLE_NAME;


    public StoryReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_STORIES);
    }
    
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_STORIES);
        onCreate(db);
    }
    
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    
    public static Story getStory(Context context, long id) {

		StoryReaderDbHelper mDbHelper = new StoryReaderDbHelper(context);
		SQLiteDatabase db = mDbHelper.getReadableDatabase();

		String[] projection = { StoryEntry._ID, StoryEntry.COLUMN_NAME_TITLE,
				StoryEntry.COLUMN_NAME_TEXT,
				StoryEntry.COLUMN_NAME_STORYDAY,
				StoryEntry.COLUMN_NAME_STORYMONTH,
				StoryEntry.COLUMN_NAME_STORYYEAR
				};

		String selection = StoryEntry._ID + " = ? ";
		String[] selectionAttr = { "" + id };

		Cursor c = db.query(StoryEntry.TABLE_NAME, // The table to query
				projection, // The columns to return
				selection, // The columns for the WHERE clause
				selectionAttr, // The values for the WHERE clause
				null, // don't group the rows
				null, // don't filter by row groups
				null // The sort order
				);

		c.moveToFirst();

		String title = c.getString(c.getColumnIndex(StoryEntry.COLUMN_NAME_TITLE));
		String text = c.getString(c.getColumnIndex(StoryEntry.COLUMN_NAME_TEXT));
		int day = c.getInt(c.getColumnIndex(StoryEntry.COLUMN_NAME_STORYDAY));
		int month = c.getInt(c.getColumnIndex(StoryEntry.COLUMN_NAME_STORYMONTH));
		int year = c.getInt(c.getColumnIndex(StoryEntry.COLUMN_NAME_STORYYEAR));
		db.close();
		return new Story(id, title, text, day, month, year);

	}


}
