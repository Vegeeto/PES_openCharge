package com.opencharge.opencharge.domain.parsers.impl;

import com.opencharge.opencharge.domain.Entities.Comment;
import com.opencharge.opencharge.domain.parsers.CommentsParser;

import java.util.Map;

/**
 * Created by Oriol on 4/5/2017.
 */

public class FirebaseCommentsParser implements CommentsParser {

    public static final String AUTHOR_KEY = "author";
    public static final String TEXT_KEY = "text";
    public static final String DATE_KEY = "date";


    @Override
    public Comment parseFromMap(String key, Map<String, Object> map) {
        
        return null;
    }
}
