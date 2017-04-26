package com.opencharge.opencharge.domain.repository.impl;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.opencharge.opencharge.domain.Entities.Comment;
import com.opencharge.opencharge.domain.repository.CommentsRepository;

/**
 * Created by DmnT on 26/04/2017.
 */

public class FirebaseCommentsRepository implements CommentsRepository {

    private FirebaseDatabase database;

    public FirebaseCommentsRepository() {
        this.database = FirebaseDatabase.getInstance();
    }


    public void createComment(String point_id, Comment comment, final CreateCommentCallback callback) {

        //modificar aqui:
        DatabaseReference myRef = database.getReference("Points");
        myRef = myRef.child(point_id);
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
}