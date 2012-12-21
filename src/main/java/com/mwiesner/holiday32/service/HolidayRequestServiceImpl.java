package com.mwiesner.holiday32.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mwiesner.holiday32.domain.HolidayRequest;
import com.mwiesner.holiday32.repo.HolidayRequestRepository;


@Service
@Transactional
public class HolidayRequestServiceImpl implements HolidayRequestService {


	@Autowired
    HolidayRequestRepository holidayRequestRepository;

	public long countAllHolidayRequests() {
        return holidayRequestRepository.count();
    }

	public void deleteHolidayRequest(HolidayRequest holidayRequest) {
        holidayRequestRepository.delete(holidayRequest);
    }

	public HolidayRequest findHolidayRequest(Long id) {
        return holidayRequestRepository.findOne(id);
    }

	public List<HolidayRequest> findAllHolidayRequests() {
        return holidayRequestRepository.findAll();
    }

	public List<HolidayRequest> findHolidayRequestEntries(int firstResult, int maxResults) {
        return holidayRequestRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveHolidayRequest(HolidayRequest holidayRequest) {
        holidayRequestRepository.save(holidayRequest);
    }

	public HolidayRequest updateHolidayRequest(HolidayRequest holidayRequest) {
        return holidayRequestRepository.save(holidayRequest);
    }
}
