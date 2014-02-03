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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CustomDialog extends DialogFragment {
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		final View view = inflater.inflate(R.layout.dialog_entry, null);
		
		final EditText title = (EditText) view.findViewById(R.id.story_title);
		final EditText text = (EditText) view.findViewById(R.id.username);
		
		builder.setView(view)
				.setPositiveButton("Save",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
															
								StoryReaderDbHelper mDbHelper = new StoryReaderDbHelper(getActivity());
								SQLiteDatabase db = mDbHelper.getWritableDatabase();
								
								ContentValues values = new ContentValues();
								values.put(StoryEntry.COLUMN_NAME_TITLE, title.getText().toString());
								values.put(StoryEntry.COLUMN_NAME_TEXT, text.getText().toString());
								
								Time now = new Time();
								now.setToNow();								
								
								values.put(StoryEntry.COLUMN_NAME_STORYDAY, now.monthDay);
								values.put(StoryEntry.COLUMN_NAME_STORYMONTH, now.month + 1);
								values.put(StoryEntry.COLUMN_NAME_STORYYEAR, now.year);

								// Insert the new row, returning the primary key value of the new row
								long newRowId;
								newRowId = db.insert(
								         StoryEntry.TABLE_NAME,
								         null,
								         values);
								db.close();
								mSaveListener.onSave(newRowId);
								dismiss();
							}
						})
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
					}
				});
		
			
		final Dialog dialog = builder.create();
		dialog.setCanceledOnTouchOutside(false);
		
		text.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
								
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				final Button saveButton = ((AlertDialog)dialog).getButton(AlertDialog.BUTTON_POSITIVE);
		        if(text.getText().length() == 0) {
		            saveButton.setEnabled(false);
		        } else {
		            saveButton.setEnabled(true);
		        }
				
			}
		});
		
		dialog.setOnShowListener(new Dialog.OnShowListener() {
			
			@Override
			public void onShow(DialogInterface dialog) {
				
				((AlertDialog)dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
				
			}
		});
		
		return dialog;
	}
	
	OnSaveListener mSaveListener;
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
        	mSaveListener = (OnSaveListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnSaveListener");
        }
    }


}
