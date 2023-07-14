package com.astra.polytechnic.api;

import com.astra.polytechnic.service.KoleksiService;
import com.astra.polytechnic.service.ManagedLoanService;
import com.astra.polytechnic.service.msMemberService;

public class ApiUtils {
    public static final String API_URL = "http://192.168.193.17:8080/";
//    public static final String API_URL = "http://10.8.2.236:8080/";
//    public static final String API_URL = "http://10.1.3.198:8080/";

    private ApiUtils(){

    }

    public static msMemberService getMemberService() {
        return RetrofitClient.getClient(API_URL).create(msMemberService.class);
    }
    public static KoleksiService getKoleksiService(){
        return RetrofitClient.getClient(API_URL).create(KoleksiService.class);
    }
    public static ManagedLoanService getManagedLoanService(){
        return RetrofitClient.getClient(API_URL).create(ManagedLoanService.class);
    }
}
