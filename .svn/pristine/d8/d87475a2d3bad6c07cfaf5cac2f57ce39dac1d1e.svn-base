package com.gtis.portal.service;


import com.gtis.portal.entity.PfCalendar;
import com.gtis.portal.entity.PfDistrict;
import com.gtis.portal.model.Ztree;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedHashMap;
import java.util.List;


public interface PfCalendarService extends BaseService<PfCalendar, String> {

    public PfCalendar findInitCalendar(String calId);

    public void update(PfCalendar calendar);
    /**
     * 根据年月获取日历
     * @param year
     * @param month
     * @return
     */
    public List<PfCalendar> getCalendarListByYM(Integer year,Integer month);

    public String createCalByYearMonth(Integer year,Integer month);

    public String createCalByYear(Integer year);

    public void deleteCalByYearMonth(Integer year,Integer month);

    public void deleteCalByYearMonth(Integer year);
}
