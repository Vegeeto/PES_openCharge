package com.opencharge.opencharge.presentation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.opencharge.opencharge.R;
import com.opencharge.opencharge.domain.Entities.Comment;
import com.opencharge.opencharge.domain.Entities.Point;
import com.opencharge.opencharge.domain.use_cases.AddCommentUseCase;
import com.opencharge.opencharge.presentation.locators.UseCasesLocator;

import java.util.Date;

/**
 * Created by Oriol on 10/4/2017.
 */

public class PointsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Point item;
    private Comment comment;
    private View.OnClickListener listener;
    private Context context;

    public class ViewHolderPoint extends RecyclerView.ViewHolder {

        private TextView adreca;
        private TextView access;
        private TextView connector;
        private TextView lat;
        private TextView lng;

        public ViewHolderPoint(View itemView) {
            super(itemView);
            adreca = (TextView) itemView.findViewById(R.id.adreca);
            access = (TextView) itemView.findViewById(R.id.access);
            connector = (TextView) itemView.findViewById(R.id.connector);
            lat = (TextView) itemView.findViewById(R.id.lat);
            lng = (TextView) itemView.findViewById(R.id.lng);
        }

        public final void bindPoint(Point p) {
            //Posar la informació d'un punt a la vista
            adreca.setText(p.getAddress());
            access.setText(p.getAccessType());
            connector.setText(p.getConnectorType());
            lat.setText(String.valueOf(p.getLatCoord()));
            lng.setText(String.valueOf(p.getLonCoord()));

            int drawable = Point.getDrawableForAccess(p.getAccessType());
            access.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable, 0);

            drawable = Point.getDrawableForConnector(p.getConnectorType());
            connector.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable, 0);
        }

    }


    //Not implemented yet
    public class ViewHolderSchedule extends RecyclerView.ViewHolder {

        private TextView schedule;

        public ViewHolderSchedule(View itemView) {
            super(itemView);
            schedule = (TextView) itemView.findViewById(R.id.horari);
        }

        public void bindSchedule(Object o) {
            //Posar la informació d'un horari a la vista
        }


    }


    public class ViewHolderComment extends RecyclerView.ViewHolder {

        private Button cancel;
        private Button send;
        private EditText comment;

        private TextWatcher filterTextWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                send.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count != 0) send.setEnabled(true);
                else send.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }


        };

        public ViewHolderComment(View itemView) {
            super(itemView);

            cancel = (Button) itemView.findViewById(R.id.cancelBtn);
            send = (Button) itemView.findViewById(R.id.sendBtn);
            comment = (EditText) itemView.findViewById(R.id.commentBox);

            send.setEnabled(false);

            comment.addTextChangedListener(filterTextWatcher);

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    comment.setText("");
                }
            });


            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    UseCasesLocator useCasesLocator = UseCasesLocator.getInstance();
                    AddCommentUseCase getAddCommentUseCase = useCasesLocator.getAddCommentUseCase(new AddCommentUseCase.Callback(){
                        @Override
                        public void onCommentAdded(String id) {
                            Toast.makeText(view.getContext(), "Missatge afegit!", Toast.LENGTH_SHORT).show();
                            comment.setText("");
                        }
                    });
                    getAddCommentUseCase.setCommentParameters(item.getId(), "Mock usuari", comment.getText().toString(), new Date().toString());
                    getAddCommentUseCase.execute();
                }
            });

        }

        public void bindComment(Comment c) {
            //Posar la informació d'un comentari a la vista
            //En aquest cas, com que l'estem creant no hem de posar res
        }


    }

    public PointsAdapter(Context context, Point item) {
        this.context = context;
        this.item = item;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        RecyclerView.ViewHolder viewHolder;
        switch(viewType) {
            case 0: //Inflate the layout with point information
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_recycler, parent, false);
                viewHolder = new ViewHolderPoint(v);
                v.setOnClickListener(this);
                break;
            case 1: //Replace the layout: inflate with scheduler layout
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_schedule, parent, false);
                viewHolder = new ViewHolderSchedule(v);
                break;
            default: //Replace the layout: inflate with comment layout
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_recycler_comment, parent, false);
                viewHolder = new ViewHolderComment(v);
                break;
        }

        return viewHolder;
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch(position) {
            case 0:  ((ViewHolderPoint) holder).bindPoint(item);
                break;
            case 1: ((ViewHolderSchedule) holder).bindSchedule(new Object());
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        switch  (position) {
            case 0: return 0;
            case 1: return 1;
            default: return 2;
        }
    }


}
