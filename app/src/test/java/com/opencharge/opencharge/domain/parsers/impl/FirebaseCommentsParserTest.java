package com.opencharge.opencharge.domain.parsers.impl;

import com.opencharge.opencharge.domain.Entities.Comment;
import com.opencharge.opencharge.domain.Entities.FirebaseComment;
import com.opencharge.opencharge.domain.Entities.Point;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by Oriol on 4/5/2017.
 */

public class FirebaseCommentsParserTest {
    FirebaseCommentsParser sut;

    //Collaborators
    Map<String, Object> map;
    String key;
    long time;

    @Before
    public void setUp() {
        setUpCollaborators();
        sut = new FirebaseCommentsParser();
    }

    private void setUpCollaborators() {
        key = "comment1";
        map = new HashMap<>();
        map.put(FirebaseCommentsParser.AUTHOR_KEY, "Oriol");
        map.put(FirebaseCommentsParser.TEXT_KEY, "1r comentari");
        time = System.currentTimeMillis();
        map.put(FirebaseCommentsParser.DATE_KEY, new Date(time));
    }

    //<editor-fold desc="Id tests">
    @Test
    public void test_parseFromMap_createCommentWithCorrectId() {
        //When
        Comment c = sut.parseFromMap(key, map);

        //Then
        assertEquals("Comment id not parsed", key, c.getId());
    }
    //</editor-fold>

    //<editor-fold desc="Author tests">
    @Test
    public void testMapWithAuthor_parseFromMap_createCommentWithCorrectId() {
        //When
        Comment c = sut.parseFromMap(key, map);

        //Then
        assertEquals("Wrong parsed author", "Oriol", c.getAutor());
    }
    //</editor-fold>

    //<editor-fold desc="Text tests">
    @Test
    public void testMapWithText_parseFromMap_createCommentWithCorrectId() {
        //When
        Comment c = sut.parseFromMap(key, map);

        //Then
        assertEquals("Wrong parsed author", "1r comentari", c.getText());
    }
    //</editor-fold>

    //<editor-fold desc="Date tests">
    @Test
    public void testMapWithDate_parseFromMap_createCommentWithCorrectId() {
        //When
        Comment c = sut.parseFromMap(key, map);

        //Then
        assertEquals("Wrong parsed date", time, c.getData().getTime());
    }

}
