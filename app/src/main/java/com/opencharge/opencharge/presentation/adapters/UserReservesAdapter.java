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
import android.widget.Toast;

import com.opencharge.opencharge.R;
import com.opencharge.opencharge.domain.Entities.Point;
import com.opencharge.opencharge.domain.Entities.Reserve;
import com.opencharge.opencharge.domain.Entities.User;
import com.opencharge.opencharge.domain.helpers.DateConversion;
import com.opencharge.opencharge.domain.helpers.impl.DateConversionImpl;
import com.opencharge.opencharge.domain.use_cases.PointByIdUseCase;
import com.opencharge.opencharge.domain.use_cases.ReserveConfirmAsConsumerUseCase;
import com.opencharge.opencharge.domain.use_cases.ReserveRejectUseCase;
import com.opencharge.opencharge.domain.use_cases.UserByIdUseCase;
import com.opencharge.opencharge.presentation.locators.UseCasesLocator;

import java.util.List;
import java.util.Objects;


/**
 * Created by Victor on 06/06/2017.
 */

public class UserReservesAdapter extends RecyclerView.Adapter<UserReservesAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private List<Reserve> items;
    private View.OnClickListener listener;


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView propietari;
        private TextView address;
        private TextView state;
        private TextView dataR;
        private TextView horaI;
        private TextView horaF;
        private Button cancelBtn;
        private Button finalitzaBtn;
        private ImageView stateIcon;



        public ViewHolder(View itemView) {
            super(itemView);
            propietari = (TextView) itemView.findViewById(R.id.pointOwner);
            address = (TextView) itemView.findViewById(R.id.pointAddressR);
            state = (TextView) itemView.findViewById(R.id.reserveState);
            dataR = (TextView) itemView.findViewById(R.id.reserveDateU);
            horaI = (TextView) itemView.findViewById(R.id.reserveHourIniU);
            horaF = (TextView) itemView.findViewById(R.id.reserveHourFiU);
            cancelBtn = (Button) itemView.findViewById(R.id.btnCancelarReserva);
            finalitzaBtn = (Button) itemView.findViewById(R.id.btnFinalitzarReserva);
            stateIcon = (ImageView) itemView.findViewById(R.id.stateIcon);
        }

        public final void bindReserve(Reserve reserve) {

            final UseCasesLocator useCasesLocator = UseCasesLocator.getInstance();
            UserByIdUseCase userByIdUseCase = useCasesLocator.getUserByIdUseCase(new UserByIdUseCase.Callback() {
                @Override
                public void onUserRetrieved(User user) {
                    if (user == null) {
                        propietari.setText("Usuari esborrat");
                    }
                    else {
                        propietari.setText(user.getUsername());
                    }
                }
            });
            userByIdUseCase.setUserId(reserve.getSupplierUserId());
            userByIdUseCase.execute();

            PointByIdUseCase pointByIdUseCase = useCasesLocator.getPointByIdUseCase(new PointByIdUseCase.Callback() {
                @Override
                public void onPointRetrieved(Point point) {
                    if (point == null) {
                        address.setText("El punt ja no existeix");
                    }
                    else {
                        address.setText(point.getAddress());
                    }
                }
            });
            pointByIdUseCase.setPointId(reserve.getPointId());
            pointByIdUseCase.execute();

            DateConversion dateConversion = new DateConversionImpl();
            dataR.setText(dateConversion.ConvertDateToString(reserve.getDay()));
            horaI.setText(dateConversion.ConvertTimeToString(reserve.getStartHour()));
            horaF.setText(dateConversion.ConvertTimeToString(reserve.getEndHour()));

            state.setText(reserve.getState());

            if (Objects.equals(reserve.getState(), Reserve.REJECTED)) {
                Drawable drawable = context.getResources().getDrawable(R.drawable.ic_event_busy_black_24dp);
                stateIcon.setImageDrawable(drawable);
            }

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ReserveRejectUseCase reserveRejectUseCase = useCasesLocator.getReserveRejectUseCase();
                    reserveRejectUseCase.execute();
                    state.setText(Reserve.REJECTED);
                    Drawable drawable = context.getResources().getDrawable(R.drawable.ic_event_busy_black_24dp);
                    stateIcon.setImageDrawable(drawable);
                    cancelBtn.setVisibility(View.GONE);
                }
            });

            if (!reserve.getCanConfirm()) {
                finalitzaBtn.setVisibility(View.GONE);
            } else {
                finalitzaBtn.setVisibility(View.VISIBLE);
                ReserveConfirmAsConsumerUseCase reserveConfirmAsConsumerUseCase = useCasesLocator.getReserveConfirmAsConsumerUseCase();
                reserveConfirmAsConsumerUseCase.execute();
                if (reserve.isMarkedAsFinishedBySupplier()) {
                    state.setText(Reserve.ACCEPTED);
                    finalitzaBtn.setVisibility(View.GONE);
                    cancelBtn.setVisibility(View.GONE);
                }
            }

        }

    }

    public UserReservesAdapter(Context context, List<Reserve> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public UserReservesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_user_reserves_recycler, parent, false);
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
        if (items.get(position) != null) {
            holder.bindReserve(items.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
