package com.opencharge.opencharge.domain.use_cases.impl;

import android.util.SparseBooleanArray;

import com.opencharge.opencharge.domain.Entities.Service;
import com.opencharge.opencharge.domain.executor.Executor;
import com.opencharge.opencharge.domain.executor.MainThread;
import com.opencharge.opencharge.domain.repository.ServiceRepository;
import com.opencharge.opencharge.domain.use_cases.ServiceCreateUseCase;
import com.opencharge.opencharge.domain.use_cases.base.AbstractUseCase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Oriol on 9/5/2017.
 */

public class ServiceCreateUseCaseImpl extends AbstractUseCase implements ServiceCreateUseCase {

    private ServiceCreateUseCase.Callback callback;
    private ServiceRepository serviceRepository;

    private String pointId;
    private Date day;
    private Date startHour;
    private Date endHour;
    private SparseBooleanArray repetitions;
    private Date lastRepeat;



    public ServiceCreateUseCaseImpl(Executor threadExecutor,
                                    MainThread mainThread,
                                    ServiceRepository serviceRepository,
                                    ServiceCreateUseCase.Callback callback) {
        super(threadExecutor, mainThread);

        this.serviceRepository = serviceRepository;
        this.callback = callback;
        this.repetitions = new SparseBooleanArray();
    }

    @Override
    public void run() {
        boolean onlyOneService = (lastRepeat != null && repetitions.size() > 0);
        if (onlyOneService) {
            createServiceForDay(day);
        }
        else {
            createRepeatedServices();
        }
    }

    private void createServiceForDay(Date day) {
        Service newService = new Service(day, startHour, endHour);
        serviceRepository.createService(pointId, newService, new ServiceRepository.CreateServiceCallback() {
            @Override
            public void onServiceCreated() {
                postService();
            }

            @Override
            public void onError() {
                postError();
            }
        });
    }

    private void createRepeatedServices() {
        ArrayList<Service> services = new ArrayList<>();
        Date currentDay = day;
        while (currentDay.before(lastRepeat)) {
            int dayOfWeek = getDayOfWeekForDate(currentDay);
            if (repetitions.get(dayOfWeek)) {
                Service newService = new Service(currentDay, startHour, endHour);
                services.add(newService);
            }
            incrementOneDayToDate(currentDay);
        }

        Service[] servicesArray = (Service[]) services.toArray();
        serviceRepository.createServices(pointId, servicesArray, new ServiceRepository.CreateServicesCallback() {
            @Override
            public void onServicesCreated() {
                postService();
            }

            @Override
            public void onError() {
                postError();
            }
        });
    }

    private int getDayOfWeekForDate(Date day) {
        Calendar c = Calendar.getInstance();
        c.setTime(day);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    private void incrementOneDayToDate(Date day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day);
        calendar.add(Calendar.DATE, 1);
        day.setTime(calendar.getTimeInMillis());
    }

    private void postService() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onServiceCreated();
            }
        });
    }

    private void postError() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onError();
            }
        });
    }

    @Override
    public void setServiceParameters(String pointId, Date day, Date startHour, Date endHour) {
        this.pointId = pointId;
        this.day = day;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    @Override
    public void setRepeatMonday() {
        repetitions.put(0, true);
    }

    @Override
    public void setRepeatTuesday() {
        repetitions.put(1, true);
    }

    @Override
    public void setRepeatWednesday() {
        repetitions.put(2, true);
    }

    @Override
    public void setRepeatThursday() {
        repetitions.put(3, true);
    }

    @Override
    public void setRepeatFriday() {
        repetitions.put(4, true);
    }

    @Override
    public void setRepeatSaturday() {
        repetitions.put(5, true);
    }

    @Override
    public void setRepeatSunday() {
        repetitions.put(6, true);
    }

    @Override
    public void setLastRepeat(Date lastRepeat) {
        this.lastRepeat = lastRepeat;
    }
}
