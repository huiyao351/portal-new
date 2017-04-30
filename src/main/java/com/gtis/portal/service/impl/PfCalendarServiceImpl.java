package com.gtis.portal.service.impl;

import com.gtis.common.util.UUIDGenerator;
import com.gtis.portal.entity.PfCalendar;
import com.gtis.portal.entity.PfDistrict;
import com.gtis.portal.entity.QPfCalendar;
import com.gtis.portal.entity.QPfDistrict;
import com.gtis.portal.model.Ztree;
import com.gtis.portal.service.PfCalendarService;
import com.gtis.portal.service.PfDistrictService;
import com.gtis.portal.util.CalendarUtil;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PfCalendarServiceImpl extends BaseServiceImpl<PfCalendar, String> implements PfCalendarService {

    public PfCalendar findInitCalendar(String calId){
        if (StringUtils.isNotBlank(calId)){
            PfCalendar calendar = findById(calId);
            if (calendar != null){
                calendar.setAmBeginStr(CalendarUtil.formateToHMOnlyStr(calendar.getAmBegin()));
                calendar.setAmEndStr(CalendarUtil.formateToHMOnlyStr(calendar.getAmEnd()));
                calendar.setPmBeginStr(CalendarUtil.formateToHMOnlyStr(calendar.getPmBegin()));
                calendar.setPmEndStr(CalendarUtil.formateToHMOnlyStr(calendar.getPmEnd()));
                return calendar;
            }
        }
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void update(PfCalendar calendar){
        if (calendar != null && StringUtils.isNotBlank(calendar.getCalId())){
            calendar.setAmBegin(CalendarUtil.formateDateByHMDate(CalendarUtil.formateDatetoStr(calendar.getCalDate())+" "+calendar.getAmBeginStr()));
            calendar.setAmEnd(CalendarUtil.formateDateByHMDate(CalendarUtil.formateDatetoStr(calendar.getCalDate())+" "+calendar.getAmEndStr()));
            calendar.setPmBegin(CalendarUtil.formateDateByHMDate(CalendarUtil.formateDatetoStr(calendar.getCalDate())+" "+calendar.getPmBeginStr()));
            calendar.setPmEnd(CalendarUtil.formateDateByHMDate(CalendarUtil.formateDatetoStr(calendar.getCalDate())+" "+calendar.getPmEndStr()));
            super.update(calendar);
        }
    }
    /**
     * 根据年月获取日历
     * @param year
     * @param month
     * @return
     */
    public List<PfCalendar> getCalendarListByYM(Integer year,Integer month){
        List<PfCalendar> calendarList = new ArrayList<PfCalendar>();
        QPfCalendar qPfCalendar = QPfCalendar.pfCalendar;
        JPQLQuery query = new JPAQuery(em);

        if (year != null && month != null){
            calendarList = query.from(qPfCalendar).where(qPfCalendar.calDate.year().eq(year).and(qPfCalendar.calDate.month().eq(month))).orderBy(qPfCalendar.calDate.asc()).list(qPfCalendar);
        }
        return calendarList;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String createCalByYearMonth(Integer year,Integer month){
        String msg = "";
        if (isok(year,month)){
            List<PfCalendar> calendarList = getCalendarListByYM(year, month);
            if (!(calendarList != null && calendarList.size() > 0)){
                createCalendar(year, month);
            }else {
                msg = year+"年度"+month+"月份已经创建日历记录，请重新选择！";
            }
        }else {
            msg = "不存在"+year+"年度"+month+"月份，请检查参数！";
        }
        return msg;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String createCalByYear(Integer year){
        String msg = "";
        if (year != null && year < 2999 && year > 2000) {
            for (int j = 1; j <= 12; j++) {
                List<PfCalendar> calendarList = getCalendarListByYM(year, j);
                if (!(calendarList != null && calendarList.size() > 0)){
                    createCalendar(year, j);
                }
            }
        }else {
            msg = "不存在"+year+"年度，请检查参数！";
        }
        return msg;
    }

    private void createCalendar(Integer year,Integer month){
        int days = CalendarUtil.getDaysOfYearMonth(year, month);
        month--;
        for (int i = 1; i <= days; i++) {
            Calendar cal = Calendar.getInstance();
            cal.set(year,month,i);
            String calType = CalendarUtil.getCalTypeByDate(cal);
            String calWeek = CalendarUtil.getWeekByDate(cal.getTime());

            PfCalendar obj = new PfCalendar();
            obj.setCalId(UUIDGenerator.generate18());
            obj.setCalDate(CalendarUtil.formatDate(cal.getTime()));
            obj.setCalType(calType);
            obj.setCalWeek(calWeek);
            cal.set(year, month, i, 8, 30, 0);
            obj.setAmBegin(cal.getTime());
            cal.set(year, month, i, 12, 00, 0);
            obj.setAmEnd(cal.getTime());
            cal.set(year, month, i, 13, 30, 0);
            obj.setPmBegin(cal.getTime());
            cal.set(year, month, i, 17, 30, 0);
            obj.setPmEnd(cal.getTime());
            insert(obj);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteCalByYearMonth(Integer year,Integer month){
        if (isok(year,month)){
            QPfCalendar qPfCalendar = QPfCalendar.pfCalendar;
            new JPADeleteClause(em, qPfCalendar).where(qPfCalendar.calDate.year().eq(year).and(qPfCalendar.calDate.month().eq(month))).execute();
        }
    }
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteCalByYearMonth(Integer year){
        if (year != null && year < 2999 && year > 2000) {
            QPfCalendar qPfCalendar = QPfCalendar.pfCalendar;
            new JPADeleteClause(em, qPfCalendar).where(qPfCalendar.calDate.year().eq(year)).execute();
        }
    }

    private boolean isok(Integer year,Integer month){
        if (year != null && month != null) {
            if (year < 2999 && year > 2000 && month > 0 && month < 13) {
                return true;
            }
        }
        return false;
    }
}
