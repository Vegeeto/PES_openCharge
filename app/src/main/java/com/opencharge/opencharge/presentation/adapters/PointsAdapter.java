package com.opencharge.opencharge.presentation.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Debug;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.opencharge.opencharge.R;
import com.opencharge.opencharge.domain.Entities.Comment;
import com.opencharge.opencharge.domain.Entities.Point;
import com.opencharge.opencharge.domain.helpers.DateConversion;
import com.opencharge.opencharge.domain.helpers.impl.DateConversionImpl;
import com.opencharge.opencharge.domain.use_cases.AddCommentUseCase;
import com.opencharge.opencharge.presentation.fragments.ShowCommentsFragment;
import com.opencharge.opencharge.presentation.locators.UseCasesLocator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

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
        private TextView lat;
        private TextView lng;
        private LinearLayout connectorLayout;



        public ViewHolderPoint(View itemView) {
            super(itemView);
            adreca = (TextView) itemView.findViewById(R.id.adreca);
            access = (TextView) itemView.findViewById(R.id.access);
            connectorLayout = (LinearLayout) itemView.findViewById(R.id.connector_layout);
            lat = (TextView) itemView.findViewById(R.id.lat);
            lng = (TextView) itemView.findViewById(R.id.lng);
        }

        public final void bindPoint(Point p) {
            //Posar la informació d'un punt a la vista

            adreca.setText(p.getAddress());
            access.setText(p.getAccessType());
            lat.setText(String.valueOf(p.getLatCoord()));
            lng.setText(String.valueOf(p.getLonCoord()));


            List<String> connectorList = p.getConnectorTypeList();
            for(int i = 0; i < p.getConnectorTypeList().size(); ++i){
                TextView connector = new TextView(itemView.getContext());
                connector.setLayoutParams(new RecyclerView.LayoutParams(
                        RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT));
                connector.setText(connectorList.get(i));
                connector.setPadding(0, 20, 0, 0);// in pixels (left, top, right, bottom)
                connectorLayout.addView(connector);
            }

            int drawable = Point.getDrawableForAccess(p.getAccessType());
            access.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable, 0);

            //drawable = Point.getDrawableForConnector(p.getConnectorType());
            drawable = Point.getDrawableForConnector("TEST");
            //connector.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable, 0);
        }

    }


    public class ViewHolderComment extends RecyclerView.ViewHolder {

        private Button cancel;
        private Button send;
        private EditText comment;
        private ImageButton morecomments;

        private TextWatcher filterTextWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                send.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()!=0) send.setEnabled(true);
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
            morecomments = (ImageButton) itemView.findViewById(R.id.moreCommentsBtn);

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
                    DateConversion dc = new DateConversionImpl();
                    String date = dc.ConvertLongToDateFormat(System.currentTimeMillis());
                    getAddCommentUseCase.setCommentParameters(item.getId(), "Mock usuari", comment.getText().toString(), date);
                    getAddCommentUseCase.execute();
                }
            });

            morecomments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    android.app.FragmentTransaction ft = ((Activity) context).getFragmentManager().beginTransaction();
                    ft.setCustomAnimations(R.animator.slide_up, R.animator.slide_down);
                    ShowCommentsFragment fragment = ShowCommentsFragment.newInstance(item.getId());
                    ft.replace(R.id.content_frame, fragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            });

        }

        public void bindComment(Comment c) {
            //Posar la informació d'un comentari a la vista
            //En aquest cas, com que l'estem creant no hem de posar res
        }


    }

    public PointsAdapter(Activity context, Point item) {
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
            default: //Inflate the layout with comments information
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
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        switch  (position) {
            case 0: return 0;
            default: return 1;
        }
    }


}
