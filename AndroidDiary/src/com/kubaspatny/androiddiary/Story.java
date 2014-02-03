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

public class Story {
	
	private long id;
	private String title;
	private String text;
	private int day;
	private int month;
	private int year;
	
	public Story(){		
	}
	
	public Story(long id, String title, String text, int day, int month, int year){
		this.id = id;
		this.title = title;
		this.text = text;
		this.day = day;
		this.month = month;
		this.year = year;
	}
	
	public long getID(){
		return id;
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getText(){
		return text;
	}
	
	public int getDay(){
		return day;
	}
	
	public int getMonth(){
		return month;
	}
	
	public int getYear(){
		return year;
	}

}
