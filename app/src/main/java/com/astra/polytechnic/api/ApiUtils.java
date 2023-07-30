package com.astra.polytechnic.api;

import com.astra.polytechnic.service.KeranjangService;
import com.astra.polytechnic.service.KoleksiService;
import com.astra.polytechnic.service.ManagedLoanService;
import com.astra.polytechnic.service.*;

public class ApiUtils {
//    public static final String API_URL = "http://192.168.203.17:8080/";
    public static final String API_URL = "http://10.8.10.173:8080/";

    private ApiUtils(){

    }

    public static msuserService getMemberService() {
        return RetrofitClient.getClient(API_URL).create(msuserService.class);
    }
    public static KoleksiService getKoleksiService(){
        return RetrofitClient.getClient(API_URL).create(KoleksiService.class);
    }
    public static ManagedLoanService getManagedLoanService(){
        return RetrofitClient.getClient(API_URL).create(ManagedLoanService.class);
    }

    public static msprodiService getAllProdi(){
        return RetrofitClient.getClient(API_URL).create(msprodiService.class);
    }

    public static KeranjangService getKeranjang(){
        return RetrofitClient.getClient(API_URL).create(KeranjangService.class);
    }
}
