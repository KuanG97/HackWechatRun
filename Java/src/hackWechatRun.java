/**
 * Created by Evilmass on 2017/4/7.
 */

import okhttp3.*;
import java.io.IOException;
import java.util.Date;

public class hackWechatRun {
    int mSteps = null;
    int uid = null;
    String pc = "pc_value";
    int mCalories, mDistance, mDuration = mSteps;
    int mDate = generateDate();

    public int generateDate()
    {
        Date date = new Date();  //创建时间对象
        System.out.println(date); //显示时间和日期
        long msec = date.getTime()/1000;
        return (int)msec;
    }

    public String generateKey()
    {
        String mToken = MD5Util.MD5(String.format("ldl_pro@" + Integer.toString(mDate) + "#" + Integer.toString(mSteps) + "$" + Integer.toString(mDistance) + "^" + Integer.toString(mCalories) + "&" + Integer.toString(mDuration)));
        return mToken.toLowerCase();
    }

    public  void upload()
    {
        RequestBody formBody = new FormBody.Builder()
                .add("pc", pc)
                .add("stats", "[{\"calories\": "+mCalories+", \"key\": "+"\""+generateKey()+"\", \"distance\": "+mDistance+", \"duration\": "+mDuration+", \"steps\": "+mSteps+", \"date\": "+mDate+"}]")
                .build();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://walk.ledongli.cn/rest/dailystats/upload/v3?uid="+Integer.toString(uid))
                .addHeader("user-agent", "okhttp/3.5.0")
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("accept-encoding", "gzip")
                .addHeader("host", "walk.ledongli.cn")
                .post(formBody)
                .build();
        try {
            Response response = client.newCall(request).execute();
            System.out.print(response);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception
    {
        System.setProperty("http.proxySet", "true");
        System.setProperty("http.proxyHost", "127.0.0.1");
        System.setProperty("http.proxyPort", "8888");
        hackWechatRun object = new hackWechatRun();
        object.upload();
    }
}
