package com.opencharge.opencharge.domain.use_cases.impl;

import android.util.Log;
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
        boolean onlyOneService = (lastRepeat == null || repetitions.size() == 0);
        if (onlyOneService) {
            createServiceForDay(day);
        } else {
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
                Date serviceDay = new Date(currentDay.getTime());
                Service newService = new Service(serviceDay, startHour, endHour);
                services.add(newService);
            }
            incrementOneDayToDate(currentDay);
        }

        Service[] servicesArray = services.toArray(new Service[services.size()]);
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
        repetitions.put(Calendar.MONDAY, true);
    }

    @Override
    public void setRepeatTuesday() {
        repetitions.put(Calendar.TUESDAY, true);
    }

    @Override
    public void setRepeatWednesday() {
        repetitions.put(Calendar.WEDNESDAY, true);
    }

    @Override
    public void setRepeatThursday() {
        repetitions.put(Calendar.THURSDAY, true);
    }

    @Override
    public void setRepeatFriday() {
        repetitions.put(Calendar.FRIDAY, true);
    }

    @Override
    public void setRepeatSaturday() {
        repetitions.put(Calendar.SATURDAY, true);
    }

    @Override
    public void setRepeatSunday() {
        repetitions.put(Calendar.SUNDAY, true);
    }

    @Override
    public void setLastRepeat(Date lastRepeat) {
        this.lastRepeat = lastRepeat;
    }
}
