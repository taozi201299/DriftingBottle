package com.imnjh.imagepicker.util;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.util.List;

public class NetworkUtil {
    private NetworkUtil() {

    }
    /** 没有网络 */
    public static final int NETWORKTYPE_INVALID = 0;
    /** wap网络 */
    public static final int NETWORKTYPE_WAP = 1;
    /** 2G网络 */
    public static final int NETWORKTYPE_2G = 2;
    /** 3G和3G以上网络，或统称为快速网络 */
    public static final int NETWORKTYPE_3G = 3;
    /** wifi网络 */
    public static final int NETWORKTYPE_WIFI = 4;

    /**
     * 网络是否可用
     *
     * @param Context
     * @return
     */
    public static final boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Gps是否打开
     *
     * @param context
     * @return
     */
    public static final boolean isGpsEnabled(Context context) {
        LocationManager locationManager = ((LocationManager) context.getSystemService(Context.LOCATION_SERVICE));
        List<String> accessibleProviders = locationManager.getProviders(true);
        return accessibleProviders != null && accessibleProviders.size() > 0;
    }
    /**
     * 获取网络状态，wifi,wap,2g,3g.
     *
     * @param context 上下文
     * @return int 网络状态 {@link #NETWORKTYPE_2G},{@link #NETWORKTYPE_3G},          *{@link #NETWORKTYPE_INVALID},{@link #NETWORKTYPE_WAP}* <p>{@link #NETWORKTYPE_WIFI}
     */
    public static int getNetWorkType(Context context) {
        int mNetWorkType = NETWORKTYPE_INVALID;
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            String type = networkInfo.getTypeName();
            if (type.equalsIgnoreCase("WIFI")) {
                mNetWorkType = NETWORKTYPE_WIFI;
            } else if (type.equalsIgnoreCase("MOBILE")) {
                String proxyHost = android.net.Proxy.getDefaultHost();
                mNetWorkType = TextUtils.isEmpty(proxyHost)
                        ? (isFastMobileNetwork(context) ? NETWORKTYPE_3G : NETWORKTYPE_2G)
                        : NETWORKTYPE_WAP;
            }
        } else {
            mNetWorkType = NETWORKTYPE_INVALID;
        }
        return mNetWorkType;
    }

    public static   boolean isFastMobileNetwork(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return false; // 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return false; // 14-64 kbps
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return false; //  50-100 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return true; // 400-1000 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return true; // 600-1400 kbps
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return false; // 100 kbps
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return true; // 2-14 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return true; // 700-1700 kbps
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return true; //1-23 Mbps
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return true; // 400-7000 kbps
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return true; // 1-2 Mbps
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return true; // 5 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return true; // 10-20 Mbps
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return false; //25 kbps
            case TelephonyManager.NETWORK_TYPE_LTE:
                return true; // 10+ Mbps
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return false;
            default:
                return false;
        }
    }
    public static boolean is2G(Context context){

        if(NETWORKTYPE_2G==getNetWorkType(context))
            return true;

        return false;

    }

    /**
     * wifi是否打开
     */
    public static final boolean isWifiEnabled(Context context) {
        ConnectivityManager mgrConn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        TelephonyManager mgrTel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return ((mgrConn.getActiveNetworkInfo() != null && mgrConn.getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED) || mgrTel
                .getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);
    }

    /**
     * 判断当前网络是否是wifi网络
     *
     * @param context
     * @return boolean
     */
    public static final boolean isWifi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if ((activeNetInfo != null) && (activeNetInfo.isConnected()) && (activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI)) {
            return true;
        }
        return false;
    }

    /**
     * 判断当前网络是否是3G网络
     *
     * @param context
     * @return boolean
     */
    public static final boolean is3G(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }
        return false;
    }

    /**
     * 另外还有两个方法判断网络是否可用：
     */

    public static final boolean isNetworkAvailable_0(Context context) {
        ConnectivityManager cm = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        if (cm != null) {
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info != null && info.isConnectedOrConnecting()) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isNetworkAvailable_1(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = cm.getActiveNetworkInfo();
        if (network != null) {
            return network.isAvailable();
        }
        return false;
    }

    /**
     * 更加严谨的写法：
     */
    public static final boolean checkNetwork(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    public static final boolean hasHttpConnectionBug() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO;
    }

    public static final void disableConnectionReuseIfNecessary() {
        // HTTP connection reuse which was buggy pre-froyo
        if (hasHttpConnectionBug()) {
            System.setProperty("http.keepAlive", "false");
        }
    }

}
