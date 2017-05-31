package com.opencharge.opencharge.presentation.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.util.List;

import static com.opencharge.opencharge.domain.Entities.Point.getDrawableForAccess;
import static com.opencharge.opencharge.domain.Entities.Point.getDrawableForConnector;

/**
 * Created by Oriol on 10/4/2017.
 */

public class PointsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Point item;
    private View.OnClickListener listener;
    private Context context;

    public class ViewHolderPoint extends RecyclerView.ViewHolder {

        private TextView adreca;
        private ImageView accessImage;
        private TextView access;
        private TextView coords;
        private LinearLayout connectorLayout;



        public ViewHolderPoint(View itemView) {
            super(itemView);
            adreca = (TextView) itemView.findViewById(R.id.adreca);
            accessImage = (ImageView) itemView.findViewById(R.id.accessImage);
            access = (TextView) itemView.findViewById(R.id.access);
            connectorLayout = (LinearLayout) itemView.findViewById(R.id.connector_layout);
            coords = (TextView) itemView.findViewById(R.id.coords);
        }

        public final void bindPoint(Point p) {
            //Posar la informació d'un punt a la vista

            adreca.setText(p.getAddress());
            Drawable drawable = context.getResources().getDrawable(Point.getDrawableForAccess(p.getAccessType()));
            accessImage.setImageDrawable(drawable);
            access.setText(p.getAccessType());
            coords.setText("(" + String.valueOf(p.getLatCoord()) + ", " + String.valueOf(p.getLonCoord() + ")"));


            List<String> connectorList = p.getConnectorTypeList();
            for(int i = 0; i < p.getConnectorTypeList().size(); ++i){
                TextView connector = new TextView(itemView.getContext());
                connector.setLayoutParams(new RecyclerView.LayoutParams(
                        RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT));
                connector.setText(connectorList.get(i));
                connector.setPadding(0, 20, 0, 0);// in pixels (left, top, right, bottom). The same as setting drawable
                connector.setCompoundDrawablesWithIntrinsicBounds(0, 0, getDrawableForConnector(connectorList.get(i)), 0);
                connectorLayout.addView(connector);
            }
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
