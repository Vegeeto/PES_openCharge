package com.opencharge.opencharge.presentation.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.InflateException;
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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.opencharge.opencharge.R;
import com.opencharge.opencharge.domain.Entities.Comment;
import com.opencharge.opencharge.domain.Entities.Point;
import com.opencharge.opencharge.domain.Entities.User;
import com.opencharge.opencharge.domain.helpers.DateConversion;
import com.opencharge.opencharge.domain.helpers.impl.DateConversionImpl;
import com.opencharge.opencharge.domain.use_cases.AddCommentUseCase;
import com.opencharge.opencharge.domain.use_cases.CommentsListUseCase;
import com.opencharge.opencharge.domain.use_cases.UserByIdUseCase;
import com.opencharge.opencharge.presentation.fragments.ShowCommentsFragment;
import com.opencharge.opencharge.presentation.fragments.UserInfoFragment;
import com.opencharge.opencharge.presentation.locators.UseCasesLocator;

import java.util.List;

import static com.opencharge.opencharge.domain.Entities.Point.getDrawableForConnector;

/**
 * Created by Oriol on 10/4/2017.
 */

public class PointsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context context;
    private Point item;
    private View.OnClickListener listener;

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
                connector.setPadding(0, 0, 0, 5);
                connector.setCompoundDrawablesWithIntrinsicBounds(getDrawableForConnector(connectorList.get(i)), 0, 0, 0); //(left, top, right, bottom)
                connectorLayout.addView(connector);
            }
        }

    }

    public class ViewHolderUser extends RecyclerView.ViewHolder {

        private TextView username;
        private TextView email;

        public ViewHolderUser(View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.username);
            email = (TextView) itemView.findViewById(R.id.email);
        }

        public final void bindUser(String userId) {
            //Posar la informació d'un usuari a la vista
            UseCasesLocator useCasesLocator = UseCasesLocator.getInstance();
            UserByIdUseCase userByIdUseCase = useCasesLocator.getUserByIdUseCase(new UserByIdUseCase.Callback() {
                @Override
                public void onUserRetrieved(final User user) {
                    if (user != null) {
                        username.setText(user.getUsername());
                        email.setText(user.getEmail());

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
                        username.setText("L'usuari ha borrat el seu compte");
                        email.setVisibility(View.INVISIBLE);
                    }
                }
            });
            userByIdUseCase.setUserId(userId);
            userByIdUseCase.execute();
        }

    }


    public class ViewHolderMap extends RecyclerView.ViewHolder implements OnMapReadyCallback {

        private GoogleMap mMap;

        public ViewHolderMap(View itemView) {
            super(itemView);

            SupportMapFragment mapFragment = new SupportMapFragment();

            FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.map_recycler, mapFragment).commit();

            mapFragment.getMapAsync(this);
            if (mMap != null) {
                onMapReady(mMap);
            }
        }

        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            mMap.getUiSettings().setScrollGesturesEnabled(false);
            mMap.getUiSettings().setZoomGesturesEnabled(false);
            LatLng position = new LatLng(item.getLatCoord(), item.getLonCoord());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(position);
            BitmapDescriptor icon;
            switch (item.getAccessType()) {
                case Point.PUBLIC_ACCESS:
                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
                    break;
                case Point.PRIVATE_ACCESS:
                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
                    break;
                case Point.PARTICULAR_ACCESS:
                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
                    break;
                default:
                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);
                    break;
            }
            markerOptions.icon(icon);
            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 14)); //40.000 km / 2^n, n=14
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

            final UseCasesLocator useCasesLocator = UseCasesLocator.getInstance();

            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    AddCommentUseCase getAddCommentUseCase = useCasesLocator.getAddCommentUseCase(context, new AddCommentUseCase.Callback(){
                        @Override
                        public void onCommentAdded(String id) {
                            Toast.makeText(view.getContext(), "Missatge afegit!", Toast.LENGTH_SHORT).show();
                            comment.setText("");
                        }
                    });
                    DateConversion dc = new DateConversionImpl();
                    String date = dc.ConvertLongToString(System.currentTimeMillis());
                    getAddCommentUseCase.setCommentParameters(item.getId(), comment.getText().toString(), date);
                    getAddCommentUseCase.execute();
                }
            });

            morecomments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    CommentsListUseCase commentsListUseCase = useCasesLocator.getCommentsListUseCase(new CommentsListUseCase.Callback() {
                        @Override
                        public void onCommentsRetrieved(Comment[] comments) {
                            if (comments.length > 0) {
                                FragmentTransaction ft = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                                ft.setCustomAnimations(R.anim.pop_in, R.anim.pop_out);
                                ShowCommentsFragment fragment = ShowCommentsFragment.newInstance(item.getId());
                                ft.replace(R.id.content_frame, fragment);
                                ft.addToBackStack(null);
                                ft.commit();
                            } else {
                                Toast.makeText(view.getContext(), "No hi ha comentaris!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    commentsListUseCase.setPointId(item.getId());
                    commentsListUseCase.execute();
                }
            });

        }

    }

    public PointsAdapter(Context context, Point item) {
        this.context = context;
        this.item = item;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v;
        RecyclerView.ViewHolder viewHolder;
        switch(viewType) {
            case 0:     //Inflate the layout with point information
                v = LayoutInflater.from(this.context).inflate(R.layout.content_recycler, parent, false);
                viewHolder = new ViewHolderPoint(v);
                break;
            case 1:     //Inflate the layout with user information
                v = LayoutInflater.from(this.context).inflate(R.layout.content_user, parent, false);
                viewHolder = new ViewHolderUser(v);
                break;
            case 2:     //Inflate the layout with map information
                v = LayoutInflater.from(this.context).inflate(R.layout.content_map, parent, false);
                viewHolder = new ViewHolderMap(v);
                break;
            default:    //Inflate the layout with comments information
                v = LayoutInflater.from(this.context).inflate(R.layout.content_recycler_comment, parent, false);
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
            case 1:
                if (item.userId != null) {
                    ViewHolderUser userHolder = (ViewHolderUser) holder;
                    userHolder.bindUser(item.userId);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        switch  (position) {
            case 0: return 0;
            case 1: return 1;
            case 2: return 2;
            default: return 3;
        }
    }

}
