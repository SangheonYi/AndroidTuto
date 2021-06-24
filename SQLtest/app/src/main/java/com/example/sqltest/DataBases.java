package com.example.sqltest;

import android.provider.BaseColumns;

public final class DataBases {

    public  static final class CreateDB implements BaseColumns{
        public static final String TITLE = "title";
        public static final String LAST_PAGE = "last_page";
        public static final String _TABLENAME0 = "booktable";
        public static final String _CREATE0 = "create table if not exists "+_TABLENAME0+"("
                +_ID+" integer primary key autoincrement, "
                +TITLE+" text not null , "
                +LAST_PAGE+" integer not null );";
    }
}
