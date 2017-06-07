package com.opencharge.opencharge.domain.Factories;

import com.opencharge.opencharge.domain.Entities.Comment;

/**
 * Created by DmnT on 26/04/2017.
 */

public class CommentFactory {

    private static CommentFactory instance;

    private CommentFactory() {}

    public static CommentFactory getInstance() {
        if(instance == null) {
            instance = new CommentFactory();
        }
        return instance;
    }

    public Comment createNewComment(String user, String text, String date) {
        return new Comment(user, text, date);
    }

}