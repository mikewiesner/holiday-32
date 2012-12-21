package com.mwiesner.holiday32.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import com.mwiesner.holiday32.domain.HolidayRequest;
import com.mwiesner.holiday32.service.EmployeeService;
import com.mwiesner.holiday32.service.HolidayRequestService;

@RequestMapping("/holidayrequests")
@Controller
public class HolidayRequestController {

	@Autowired
    HolidayRequestService holidayRequestService;

	@Autowired
    EmployeeService employeeService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid HolidayRequest holidayRequest, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, holidayRequest);
            return "holidayrequests/create";
        }
        uiModel.asMap().clear();
        holidayRequestService.saveHolidayRequest(holidayRequest);
        return "redirect:/holidayrequests/" + encodeUrlPathSegment(holidayRequest.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new HolidayRequest());
        List<String[]> dependencies = new ArrayList<String[]>();
        if (employeeService.countAllEmployees() == 0) {
            dependencies.add(new String[] { "employee", "employees" });
        }
        uiModel.addAttribute("dependencies", dependencies);
        return "holidayrequests/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("holidayrequest", holidayRequestService.findHolidayRequest(id));
        uiModel.addAttribute("itemId", id);
        return "holidayrequests/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("holidayrequests", holidayRequestService.findHolidayRequestEntries(firstResult, sizeNo));
            float nrOfPages = (float) holidayRequestService.countAllHolidayRequests() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("holidayrequests", holidayRequestService.findAllHolidayRequests());
        }
        addDateTimeFormatPatterns(uiModel);
        return "holidayrequests/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid HolidayRequest holidayRequest, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, holidayRequest);
            return "holidayrequests/update";
        }
        uiModel.asMap().clear();
        holidayRequestService.updateHolidayRequest(holidayRequest);
        return "redirect:/holidayrequests/" + encodeUrlPathSegment(holidayRequest.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, holidayRequestService.findHolidayRequest(id));
        return "holidayrequests/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        HolidayRequest holidayRequest = holidayRequestService.findHolidayRequest(id);
        holidayRequestService.deleteHolidayRequest(holidayRequest);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/holidayrequests";
    }

	void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("holidayRequest_fromdate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("holidayRequest_todate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }

	void populateEditForm(Model uiModel, HolidayRequest holidayRequest) {
        uiModel.addAttribute("holidayRequest", holidayRequest);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("employees", employeeService.findAllEmployees());
    }

	String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
}
