package com.opencharge.opencharge.domain.parsers.impl;

import com.opencharge.opencharge.domain.Entities.Comment;
import com.opencharge.opencharge.domain.parsers.CommentsParser;

import java.util.Map;

/**
 * Created by Oriol on 4/5/2017.
 */

public class FirebaseCommentsParser extends AbstractParser implements CommentsParser {

    public static final String AUTHOR_KEY = "user";
    public static final String TEXT_KEY = "text";
    public static final String DATE_KEY = "date";


    @Override
    public Comment parseFromMap(String key, Map<String, Object> map) {
        Comment comment = new Comment(key);

        comment.setUser(parseStringKeyFromMap(AUTHOR_KEY, map));
        comment.setText(parseStringKeyFromMap(TEXT_KEY, map));
        comment.setDate(parseStringKeyFromMap(DATE_KEY, map));

        return comment;
    }

}
