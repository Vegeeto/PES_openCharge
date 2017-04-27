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

import com.opencharge.opencharge.R;
import com.opencharge.opencharge.domain.Entities.Point;
import com.opencharge.opencharge.domain.use_cases.AddCommentUseCase;
import com.opencharge.opencharge.domain.use_cases.impl.AddCommentUseCaseImpl;
import com.opencharge.opencharge.presentation.locators.UseCasesLocator;

/**
 * Created by Oriol on 10/4/2017.
 */

public class PointsAdapter extends RecyclerView.Adapter<PointsAdapter.ViewHolder> implements View.OnClickListener {

    private Point item;
    private View.OnClickListener listener;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView adreca;
        private TextView access;
        private TextView connector;
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

        public ViewHolder(View itemView) {
            super(itemView);
            adreca = (TextView) itemView.findViewById(R.id.adreca);
            access = (TextView) itemView.findViewById(R.id.access);
            connector = (TextView) itemView.findViewById(R.id.connector);

            cancel = (Button) itemView.findViewById(R.id.cancelBtn);
            send = (Button) itemView.findViewById(R.id.sendBtn);
            comment = (EditText) itemView.findViewById(R.id.commentBox);

            comment.addTextChangedListener(filterTextWatcher);

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    comment.setText("");
                }
            });


            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

        }

        public void bindPoint(Point p) {
            //Posar la informació d'un punt a la vista
            adreca.setText(p.getLatCoord() + " " + p.getLonCoord());
            adreca.setText(p.getAddress());
            access.setText(p.getAccessType());
            connector.setText(p.getConnectorType());

            int drawable = Point.getDrawableForAccess(p.getAccessType());
            access.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable, 0);

            drawable = Point.getDrawableForConnector(p.getConnectorType());
            connector.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable, 0);
        }


    }

    public PointsAdapter(Context context, Point item) {
        this.context = context;
        this.item = item;
    }

    @Override
    public PointsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        switch(viewType) {
            case 0: //Inflate the layout with point information
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_recycler, parent, false);
                v.setOnClickListener(this);
                break;
            case 1: //Replace the layout: inflate with scheduler layout
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_recycler, parent, false);
                break;
            default: //Replace the layout: inflate with comment layout
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_recycler_comment, parent, false);
                break;
        }

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
        switch(position) {
            case 0: holder.bindPoint(item);
                break;
            case 1:
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
