package com.mwiesner.holiday32.service;

import java.util.List;

import com.mwiesner.holiday32.domain.HolidayRequest;

public interface HolidayRequestService { 

	public abstract long countAllHolidayRequests();


	public abstract void deleteHolidayRequest(HolidayRequest holidayRequest);


	public abstract HolidayRequest findHolidayRequest(Long id);


	public abstract List<HolidayRequest> findAllHolidayRequests();


	public abstract List<HolidayRequest> findHolidayRequestEntries(int firstResult, int maxResults);


	public abstract void saveHolidayRequest(HolidayRequest holidayRequest);


	public abstract HolidayRequest updateHolidayRequest(HolidayRequest holidayRequest);

}
