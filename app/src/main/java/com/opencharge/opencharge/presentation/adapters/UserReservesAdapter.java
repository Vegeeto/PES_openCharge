package com.opencharge.opencharge.presentation.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.opencharge.opencharge.R;
import com.opencharge.opencharge.domain.Entities.Point;
import com.opencharge.opencharge.domain.Entities.Reserve;
import com.opencharge.opencharge.domain.Entities.User;
import com.opencharge.opencharge.domain.use_cases.PointByIdUseCase;
import com.opencharge.opencharge.domain.use_cases.ReserveConfirmAsConsumerUseCase;
import com.opencharge.opencharge.domain.use_cases.ReserveRejectUseCase;
import com.opencharge.opencharge.domain.use_cases.UserByIdUseCase;
import com.opencharge.opencharge.presentation.locators.UseCasesLocator;

import java.util.Objects;


/**
 * Created by Victor on 06/06/2017.
 */

public class UserReservesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context context;
    private Reserve item;
    private View.OnClickListener listener;


    public class ViewHolderReserve extends RecyclerView.ViewHolder {

        private TextView propietari;
        private TextView address;
        private TextView state;
        private Button cancelBtn;
        private Button finalitzaBtn;
        private ImageView stateIcon;



        public ViewHolderReserve(View itemView) {
            super(itemView);
            propietari = (TextView) itemView.findViewById(R.id.pointOwner);
            address = (TextView) itemView.findViewById(R.id.pointAddressR);
            state = (TextView) itemView.findViewById(R.id.reserveState);
            cancelBtn = (Button) itemView.findViewById(R.id.btnCancelarReserva);
            finalitzaBtn = (Button) itemView.findViewById(R.id.btnFinalitzarReserva);
            stateIcon = (ImageView) itemView.findViewById(R.id.stateIcon);
        }

        public final void bindReserve(Reserve reserve) {

            final UseCasesLocator useCasesLocator = UseCasesLocator.getInstance();
            UserByIdUseCase userByIdUseCase = useCasesLocator.getUserByIdUseCase(new UserByIdUseCase.Callback() {
                @Override
                public void onUserRetrieved(User user) {
                    propietari.setText(user.getUsername());
                }
            });
            userByIdUseCase.setUserId(reserve.getSupplierUserId());
            userByIdUseCase.execute();

            PointByIdUseCase pointByIdUseCase = useCasesLocator.getPointByIdUseCase(new PointByIdUseCase.Callback() {
                @Override
                public void onPointRetrieved(Point point) {
                    address.setText(point.getAddress());
                }
            });
            pointByIdUseCase.setPointId(reserve.getPointId());
            pointByIdUseCase.execute();

            state.setText(reserve.getState());

            if (Objects.equals(reserve.getState(), Reserve.REJECTED)) {
                Drawable drawable = context.getResources().getDrawable(R.drawable.ic_event_busy_black_24dp);
                stateIcon.setImageDrawable(drawable);
            }

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //ReserveRejectUseCase reserveRejectUseCase = useCasesLocator.getRes
                }
            });

            if (!reserve.getCanConfirm()) {
                finalitzaBtn.setVisibility(View.GONE);
            } else {

            }

        }

    }

    public UserReservesAdapter(Context context, Reserve item) {
        this.context = context;
        this.item = item;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v;
        RecyclerView.ViewHolder viewHolder;
        switch(viewType) {
            default:    //Inflate the layout with comments information
                v = LayoutInflater.from(this.context).inflate(R.layout.content_user_reserves_recycler, parent, false);
                viewHolder = new ViewHolderReserve(v);
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
            default:
                ((ViewHolderReserve) holder).bindReserve(item);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }

}
