package com.opencharge.opencharge.domain.parsers.impl;

import com.opencharge.opencharge.domain.Entities.Comment;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertNull;
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
        map.put(FirebaseCommentsParser.DATE_KEY, "01/01/2017");
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
    public void testMapWithAuthor_parseFromMap_createCommentWithCorrectAuthor() {
        //When
        Comment c = sut.parseFromMap(key, map);

        //Then
        assertEquals("Wrong parsed author", "Oriol", c.getAutor());
    }

    @Test
    public void testMapWithoutAuthor_parseFromMap_createCommentWithoutAuthor() {
        //Given
        map.remove(FirebaseCommentsParser.AUTHOR_KEY);

        //When
        Comment c = sut.parseFromMap(key, map);

        //Then
        assertNull("Wrong parsed author", c.getAutor());
    }
    //</editor-fold>

    //<editor-fold desc="Text tests">
    @Test
    public void testMapWithText_parseFromMap_createCommentWithCorrectText() {
        //When
        Comment c = sut.parseFromMap(key, map);

        //Then
        assertEquals("Wrong parsed text", "1r comentari", c.getText());
    }

    @Test
    public void testMapWithoutText_parseFromMap_createCommentWithoutText() {
        //Given
        map.remove(FirebaseCommentsParser.TEXT_KEY);

        //When
        Comment c = sut.parseFromMap(key, map);

        //Then
        assertNull("Wrong parsed text", c.getText());
    }
    //</editor-fold>

    //<editor-fold desc="Date tests">
    @Test
    public void testMapWithDate_parseFromMap_createCommentWithCorrectDate() {
        //When
        Comment c = sut.parseFromMap(key, map);

        //Then
        assertEquals("Wrong parsed date", "01/01/2017", c.getData());
    }

    @Test
    public void testMapWithoutDate_parseFromMap_createCommentWithoutDate() {
        //Given
        map.remove(FirebaseCommentsParser.DATE_KEY);

        //When
        Comment c = sut.parseFromMap(key, map);

        //Then
        assertNull("Wrong parsed Date", c.getData());
    }
    //</editor-fold>

}
