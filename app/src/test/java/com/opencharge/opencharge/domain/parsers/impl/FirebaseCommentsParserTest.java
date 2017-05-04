package com.opencharge.opencharge.domain.parsers.impl;

import com.opencharge.opencharge.domain.Entities.Comment;
import com.opencharge.opencharge.domain.Entities.FirebaseComment;
import com.opencharge.opencharge.domain.Entities.Point;

import org.junit.Before;
import org.junit.Test;

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
        map.put(FirebaseCommentsParser.DATE_KEY, "");
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

}
