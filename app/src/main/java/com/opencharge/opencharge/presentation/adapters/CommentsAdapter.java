package com.opencharge.opencharge.presentation.adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.opencharge.opencharge.R;
import com.opencharge.opencharge.domain.Entities.Comment;
import com.opencharge.opencharge.domain.Entities.User;
import com.opencharge.opencharge.domain.use_cases.UserByIdUseCase;
import com.opencharge.opencharge.presentation.fragments.UserInfoFragment;
import com.opencharge.opencharge.presentation.locators.UseCasesLocator;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by V on 17/05/2017.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> implements View.OnClickListener {

    private ArrayList<Comment> comments;
    private View.OnClickListener listener;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView profilePhoto;
        private TextView username;
        private TextView date;
        private TextView content;


        public ViewHolder(View itemView) {
            super(itemView);

            profilePhoto = (CircleImageView) itemView.findViewById(R.id.profilephoto);
            username = (TextView) itemView.findViewById(R.id.username);
            date = (TextView) itemView.findViewById(R.id.date);
            content = (TextView) itemView.findViewById(R.id.comment);

        }

        public void bindComment(Comment comment) {
            UseCasesLocator useCasesLocator = UseCasesLocator.getInstance();
            UserByIdUseCase userByIdUseCase = useCasesLocator.getUserByIdUseCase(new UserByIdUseCase.Callback() {
                @Override
                public void onUserRetrieved(final User user) {
                    if (user != null) {
                        username.setText(user.getUsername());
                        Picasso.with(context).load(user.getPhoto()).into(profilePhoto);
                        //profilePhoto.setImageBitmap(user.getPhoto());
                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                FragmentManager fm = ((FragmentActivity) context).getSupportFragmentManager();
                                UserInfoFragment fragment = UserInfoFragment.newInstance(user.getId());
                                fm.beginTransaction().replace(R.id.content_frame, fragment).commit();
                            }
                        });
                    }
                    else {
                        username.setText("Usuari no existent");
                    }
                }
            });

            userByIdUseCase.setUserId(comment.getUser());
            userByIdUseCase.execute();

            date.setText(comment.getDate());
            content.setText(comment.getText());
        }


    }

    public CommentsAdapter(Context context, ArrayList<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public CommentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_cards_comments_layout, parent, false);
        v.setOnClickListener(this);

        return new ViewHolder(v);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (comments.get(position) != null) {
            holder.bindComment(comments.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

}
