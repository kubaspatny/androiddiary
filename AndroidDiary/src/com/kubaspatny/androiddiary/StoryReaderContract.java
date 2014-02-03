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

import android.provider.BaseColumns;

public final class StoryReaderContract {
	
	public StoryReaderContract() {}
	
	public static abstract class StoryEntry implements BaseColumns {
        public static final String TABLE_NAME = "story";
        public static final String COLUMN_NAME_STORY_ID = "storyid";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_TEXT = "text";
        public static final String COLUMN_NAME_STORYDAY = "story_day";
        public static final String COLUMN_NAME_STORYMONTH = "story_month";
        public static final String COLUMN_NAME_STORYYEAR = "story_year";
    }

}
