package com.example.administrator.onedaytwoday;

/**
 * Created by Administrator on 2016-11-29.
 */

public class UrlContainer {
    public final static String MAIN_URL ="http://onedaytwoday.cafe24app.com";
    public final static String REST_LOGIN = "/users/login";
    public final static String REST_REGISTER = "/users/register";
    public final static String REST_NEW_JOB = "/job/new";
    public final static String REST_JOBBY_TYPE = "/job/:type";
    public final static String REST_JTITLE_EDIT = "/job/tupdate";
    public final static String REST_JCATEGORY_EDIT = "/job/cupdate";
    public final static String REST_REMOVE_JOB = "/job/delete";
    public final static String REST_REMOVE_EXPIRED = "/job/expire";
    public final static String REST_ALL_REVIEW = "/review/:Uid";
    public final static String REST_REVIEW_EDIT = "/review/update";
    public final static String REST_NEW_REVIEW = "/review/new";
    public final static String REST_DELETE_USER = "/users/delete";
    public final static String REST_TARGET_USER = "/users/:Uid";
    public final static String REST_NEW_KEYWORD = "/keyword/new";
    public final static String REST_KEYWORD_OF_USER = "/keyword/:Uid";
    public final static String REST_EXPIRE_KEYWORD = "/keyword/expire";
}