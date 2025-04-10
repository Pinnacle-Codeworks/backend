package com.markguiang.backend.event.service;

import com.markguiang.backend.event.model.Event;
import com.markguiang.backend.event.model.Schedule;
import com.markguiang.backend.event.repository.ScheduleRepository;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public List<Schedule> createScheduleList(Event event) {
        List<Schedule> scheduleList = event.getScheduleList();
        for (Schedule schedule: scheduleList) {
            schedule.setEvent(event);
        }
        return IterableUtils.toList(this.scheduleRepository.saveAll(scheduleList));
    }

    @Transactional
    public List<Schedule> updateScheduleList(Event event) {
        List<Schedule> scheduleList = event.getScheduleList();
        List<Schedule> scheduleListDb = scheduleRepository.findScheduleByEventId(event.getEventId());
        Map<Long, Schedule> scheduleMap = scheduleList.stream()
                .collect(Collectors.toMap(Schedule::getScheduleId, schedule -> schedule));

        List<Schedule> schedulesToDelete = new ArrayList<>();
        for (Schedule scheduleDb: scheduleListDb) {
            Long scheduleDbId = scheduleDb.getScheduleId();
            if (scheduleMap.containsKey(scheduleDbId)) {
                continue;
            }
            schedulesToDelete.add(scheduleDb);
        }

        scheduleRepository.deleteAll(schedulesToDelete);
        return IterableUtils.toList(this.scheduleRepository.saveAll(scheduleList));
    }
}