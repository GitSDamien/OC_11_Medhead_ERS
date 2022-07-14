package com.medhead.gateway.controller;

import com.medhead.gateway.configuration.AppConstant;
import com.medhead.gateway.dao.ApiKeyDao;
import com.medhead.gateway.model.Routes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class GatewayController {

    @RequestMapping(value = "/routes", method = RequestMethod.GET)
    public List<Routes> getListRouteIds(@RequestHeader("gatewayKey") String gatewayKey) {
        if(!gatewayKey.equals(AppConstant.ADMIN_TOKEN)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You cannot consume this service. Please check your api key.");
        }
        List<Routes> routes = List.of(
            new Routes(AppConstant.SERVICE_A_PATH, AppConstant.SERVICE_A_URI, AppConstant.SERVICE_A_KEY)
//            , new Routes(AppConstant.SERVICE_B_PATH, AppConstant.SERVICE_B_URI, AppConstant.SERVICE_B_KEY)
        );
        return routes;
    }

    // token add

    // token revoke

}
