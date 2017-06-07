package com.opencharge.opencharge.presentation.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
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
import com.opencharge.opencharge.domain.helpers.DateConversion;
import com.opencharge.opencharge.domain.helpers.impl.DateConversionImpl;
import com.opencharge.opencharge.domain.use_cases.PointByIdUseCase;
import com.opencharge.opencharge.domain.use_cases.ReserveConfirmAsSupplierUseCase;
import com.opencharge.opencharge.domain.use_cases.ReserveRejectUseCase;
import com.opencharge.opencharge.domain.use_cases.UserByIdUseCase;
import com.opencharge.opencharge.presentation.locators.UseCasesLocator;

import java.util.List;
import java.util.Objects;

/**
 * Created by Victor on 07/06/2017.
 */

public class SupplierReservesAdapter extends RecyclerView.Adapter<SupplierReservesAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private List<Reserve> items;
    private View.OnClickListener listener;


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView solicitant;
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
            solicitant = (TextView) itemView.findViewById(R.id.userApplicant);
            address = (TextView) itemView.findViewById(R.id.pointAddressRS);
            state = (TextView) itemView.findViewById(R.id.reserveStateSupplier);
            dataR = (TextView) itemView.findViewById(R.id.reserveDate);
            horaI = (TextView) itemView.findViewById(R.id.reserveHourIni);
            horaF = (TextView) itemView.findViewById(R.id.reserveHourFi);
            cancelBtn = (Button) itemView.findViewById(R.id.btnRechazarReserva);
            finalitzaBtn = (Button) itemView.findViewById(R.id.btnFinalitzarReservaSupplier);
            stateIcon = (ImageView) itemView.findViewById(R.id.stateIconSupplier);
        }

        public final void bindReserve(final Reserve reserve) {

            final UseCasesLocator useCasesLocator = UseCasesLocator.getInstance();
            UserByIdUseCase userByIdUseCase = useCasesLocator.getUserByIdUseCase(new UserByIdUseCase.Callback() {
                @Override
                public void onUserRetrieved(User user) {
                    if (user == null) {
                        solicitant.setText("Usuari esborrat");
                    }
                    else {
                        solicitant.setText(user.getUsername());
                    }
                }
            });
            userByIdUseCase.setUserId(reserve.getConsumerUserId());
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
                cancelBtn.setVisibility(View.GONE);
                finalitzaBtn.setVisibility(View.GONE);
            }

            if (Objects.equals(reserve.getState(), Reserve.ACCEPTED)) {
                cancelBtn.setVisibility(View.GONE);
                finalitzaBtn.setVisibility(View.GONE);
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
                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cancelBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("DECLINAR RESERVA");
                                builder.setIcon(R.drawable.ic_warning_black_24dp);
                                builder.setMessage("Segur que vols declinar la reserva?")
                                        .setCancelable(false)
                                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                ReserveConfirmAsSupplierUseCase reserveConfirmAsSupplierUseCase = useCasesLocator.getReserveConfirmAsSupplierUseCase();
                                                reserveConfirmAsSupplierUseCase.execute();
                                                if (reserve.isMarkedAsFinishedByConsumer()) {
                                                    state.setText(Reserve.ACCEPTED);
                                                    finalitzaBtn.setVisibility(View.GONE);
                                                    cancelBtn.setVisibility(View.GONE);
                                                }
                                            }
                                        })
                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });
                                builder.show();
                            }
                        });
                    }
                });
            }

        }

    }

    public SupplierReservesAdapter(Context context, List<Reserve> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public SupplierReservesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
