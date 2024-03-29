package com.ams.building.server.controller.app;

import com.ams.building.server.constant.Constants;
import com.ams.building.server.response.DetailSubServiceClientResponse;
import com.ams.building.server.service.RequestServiceService;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = -1)
public class DetailSubServiceAppController {

    private static final Logger logger = Logger.getLogger(DetailSubServiceAppController.class);

    @Autowired
    private RequestServiceService requestServiceService;

    @GetMapping(Constants.UrlPath.URL_API_SEARCH_DETAIL_SUB_SERVICE)
    public List<DetailSubServiceClientResponse> getDetailSubServiceBySubServiceId(@RequestParam Long subServiceId) {
        logger.debug("getDetailSubServiceBySubServiceId " + new Gson().toJson(subServiceId));
        List<DetailSubServiceClientResponse> responses = requestServiceService.getDetailSubServiceBySubServiceId(subServiceId);
        logger.debug("getDetailSubServiceBySubServiceId response : " + new Gson().toJson(responses));
        return responses;
    }

}
