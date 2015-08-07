package test.example.com.broadcasttest;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity {
    private IntentFilter intentFilter;
    private LocalReceiver localReceiver;
    private  LocalBroadcastManager localBroadcastManager;
//    private  NetworkChangeReceiver networkChangeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        localBroadcastManager=LocalBroadcastManager.getInstance(this);//获取实例
        Button button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("test.example.com.broadcasttest.LOCAL_BROADCAST");
//                sendOrderedBroadcast(intent,null);
                localBroadcastManager.sendBroadcast(intent);//发送本地广播
            }
        });
        intentFilter = new IntentFilter();
        intentFilter.addAction("test.example.com.broadcast.LOCAL_BROADCAST");//广播接收器想要监听什么广播，就在这里添加相应的action就行了
//        networkChangeReceiver=new NetworkChangeReceiver();
//        registerReceiver(networkChangeReceiver, intentFilter);//实现监听网络动态变化的功能
        localReceiver=new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver,intentFilter);//注册本地广播监听器
    }
/*
*?动态注册的广播接收器一定要取消注册
 */
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(networkChangeReceiver);
        localBroadcastManager.unregisterReceiver(localReceiver);
    }

//    class NetworkChangeReceiver extends BroadcastReceiver{
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            //每当网络状态发生变化时，onreceive方法就会得到执行
//            ConnectivityManager connectionManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo networkInfo=connectionManager.getActiveNetworkInfo();
//            if(networkInfo!=null&&networkInfo.isAvailable()){
//                Toast.makeText(context,"network is available",Toast.LENGTH_SHORT).show();
//            }else{
//                Toast.makeText(context,"network is unavailable",Toast.LENGTH_SHORT).show();
//            }
//
//        }
//    }
    class LocalReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context,"received local broadcast",Toast.LENGTH_SHORT).show();
        }
    }
}
