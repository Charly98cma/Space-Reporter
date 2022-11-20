package com.dam.spacereporter.spacereporter.utils;

public class Constants {

    // Spaceflight News API
    public final static String SNAPI_URL = "https://api.spaceflightnewsapi.net/v3/articles?_limit=%d&amp;_start=%d;";

    // SHARED PREFERENCES KEYS
    public final static String PREF_KEY = "SHARED_PREF_LIST";
    public final static String PREF_SAVE_LOGIN = "SAVE_LOGIN";
    public final static String PREF_SEEN_START = "SEEN_START";
    public final static String PREF_USER_FULLNAME = "USER_FULL_NAME";
    public final static String PREF_USER_USERNAME = "USER_USERNAME";
    public final static String PREF_USER_EMAIL = "USER_EMAIL";
    public final static String PREF_ARTICLE_LASTSEEN = "ARTICLE_LAST_ID";

    // NOTIFICATION CHANNEL IDs
    public final static String NOTIF_CHANNELID_NEWARTICLE = "UNREAD_ARTICLES";
}
