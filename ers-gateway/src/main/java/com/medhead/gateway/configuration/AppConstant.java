package com.medhead.gateway.configuration;

import com.medhead.gateway.model.ApiKey;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppConstant {

    public static final String ADMIN_TOKEN = "424C-481A-DA17-4908";

    public static final String SERVICE_A_KEY = "424C481A-DA17-4908-85B1-6740E7808B0D";
    public static final String SERVICE_A_PATH = "/api/v1/**";
    public static final String SERVICE_A_URI = "http://ers-api:8081/";

//    public static final String SERVICE_B_KEY = "EE44BAD9-A3DA-46FA-B4E0-7DE7C2681ABF";
//    public static final String SERVICE_B_PATH = "/api/service-b/**";
//    public static final String SERVICE_B_URI = "http://ers-api:8081/";

    public static final List<ApiKey> apiKeys = Arrays.asList(
        new ApiKey(1, "343C-ED0B-4137-B27E", SERVICE_A_KEY)
//        ,new ApiKey(2, "343C-ED0B-4137-B27E", SERVICE_B_KEY)
//        ,new ApiKey(3, "FA48-EF0C-427E-8CCF", SERVICE_B_KEY)
    );

}
