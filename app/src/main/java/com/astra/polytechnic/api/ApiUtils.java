package com.astra.polytechnic.api;

import com.astra.polytechnic.service.KeranjangService;
import com.astra.polytechnic.service.KoleksiService;
import com.astra.polytechnic.service.ManagedLoanService;
import com.astra.polytechnic.service.*;

public class ApiUtils {
    public static final String API_URL = "http://192.168.71.17:8080/";
//    public static final String API_URL = "http://10.8.2.236:8080/";

    private ApiUtils(){

    }

    // Setiap Service harus didefinisikan methodnya disini agar terhubung dengan RetrofitClient
    public static UserService getMemberService() {
        return RetrofitClient.getClient(API_URL).create(UserService.class);
    }
    public static KoleksiService getKoleksiService(){
        return RetrofitClient.getClient(API_URL).create(KoleksiService.class);
    }
    public static ManagedLoanService getManagedLoanService(){
        return RetrofitClient.getClient(API_URL).create(ManagedLoanService.class);
    }
    public static ProdiService getAllProdi(){
        return RetrofitClient.getClient(API_URL).create(ProdiService.class);
    }
    public static BannerService getBannerService(){
        return RetrofitClient.getClient(API_URL).create(BannerService.class);
    }
    public static KeranjangService getKeranjang(){
        return RetrofitClient.getClient(API_URL).create(KeranjangService.class);
    }
    public static BookingService getBooking(){
        return RetrofitClient.getClient(API_URL).create(BookingService.class);
    }
}
