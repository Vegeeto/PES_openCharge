package com.opencharge.opencharge.domain.repository.impl;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.opencharge.opencharge.domain.Entities.Comment;
import com.opencharge.opencharge.domain.Entities.Point;
import com.opencharge.opencharge.domain.parsers.CommentsParser;
import com.opencharge.opencharge.domain.parsers.impl.FirebaseCommentsParser;
import com.opencharge.opencharge.domain.repository.CommentsRepository;

import java.util.Map;

/**
 * Created by DmnT on 26/04/2017.
 */

public class FirebaseCommentsRepository implements CommentsRepository {

    private CommentsParser commentsParser;
    private FirebaseDatabase database;

    public FirebaseCommentsRepository() {
        this.commentsParser = new FirebaseCommentsParser();
        this.database = FirebaseDatabase.getInstance();
    }


    @Override
    public void createComment(String point_id, Comment comment, final CreateCommentCallback callback) {

        //modificar aqui:
        DatabaseReference myRef = database.getReference("Points");
        myRef = myRef.child(point_id);
        myRef = myRef.child("Comments");
        myRef.push().setValue(comment, new DatabaseReference.CompletionListener() {

            @Override
            public void onComplete(DatabaseError de, DatabaseReference dr) {
                System.out.println("Record saved!");
                String commentId = dr.getKey();
                callback.onCommentCreated(commentId);
            }

            ;
        });
    }

    @Override
    public void getComments(String point_id, final GetCommentsCallback callback) {
        DatabaseReference myRef = database.getReference("Points");
        myRef = myRef.child(point_id);
        myRef = myRef.child("Comments");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Comment[] comments = parseCommentsFromDataSnapshot(dataSnapshot);
                callback.onCommentsRetrieved(comments);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO
                Log.e("FirebaseRepo","ERROR: "+databaseError.toString());
            }
        });

    }

    private Comment[] parseCommentsFromDataSnapshot(DataSnapshot dataSnapshot) {
        Comment[] comments = new Comment[(int)dataSnapshot.getChildrenCount()];
        int index = 0;
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            Comment comment = parseCommentFromSnapshot(snapshot);
            if (comment != null) {
                comments[index] = comment;
                ++index;
            }
        }

        return comments;
    }

    private Comment parseCommentFromSnapshot(DataSnapshot snapshot) {
        if (snapshot.getValue() instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
            String key = snapshot.getKey();
            return commentsParser.parseFromMap(key, map);
        }
        return null;
    }
}
