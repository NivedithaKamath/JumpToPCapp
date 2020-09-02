package com.example.jumptopc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    LinearLayout desktopLinearLayout, startLinearLayout, exitLinearLayout,bottomLinearLayoutLeft,bottomLinearLayoutRight,libraryLinearLayout;
    ListView desktopListView, startListView,libraryListView;
    FrameLayout frameLayout,bottomFrameLayout;
    Button exitButton,startButton,settingsButton,bluetoothButton,wifiButton,brightnessButton,dateTimeButton,libraryButton;
    TextView exitTextView;
    PackageManager packageManager,manager;
    private List<DesktopApps> apps;
    private List<AppList> app;
    int visibility;
    ImageButton keyboardButtonOpen,keyboardButtonClose;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        desktopLinearLayout=(LinearLayout)findViewById(R.id.desktopLinearLayout);
        startLinearLayout=(LinearLayout)findViewById(R.id.startLinearLayout);
        exitLinearLayout=(LinearLayout)findViewById(R.id.exitLinearLayout);
        frameLayout=(FrameLayout)findViewById(R.id.frameLayout);
        bottomFrameLayout=(FrameLayout)findViewById(R.id.bottomFrameLayout);
        desktopListView=(ListView) findViewById(R.id.desktopListView);
        startListView=(ListView) findViewById(R.id.startListView);
        bottomLinearLayoutLeft=(LinearLayout)findViewById(R.id.bottomLinearLayoutLeft);
        bottomLinearLayoutRight=(LinearLayout)findViewById(R.id.bottomLinearLayoutRight);
        exitTextView=(TextView) findViewById(R.id.exitTextView);
        exitButton=(Button)findViewById(R.id.exitButton);
        wifiButton=(Button)findViewById(R.id.wifiButton);
        keyboardButtonOpen=(ImageButton)findViewById(R.id.keyboardButtonOpen);
        keyboardButtonClose=(ImageButton) findViewById(R.id.keyboardButtonClose);
        startButton=(Button)findViewById(R.id.startButton);
        settingsButton=(Button)findViewById(R.id.settingsButton);
        bluetoothButton=(Button)findViewById(R.id.bluetoothButton);
        brightnessButton=(Button)findViewById(R.id.brightnessButton);
        dateTimeButton=(Button)findViewById(R.id.dateTimeButton);
        libraryButton=(Button)findViewById(R.id.libraryButton);
        libraryLinearLayout=(LinearLayout)findViewById(R.id.libraryLinearLayout);
        libraryListView=(ListView) findViewById(R.id.libraryListView);

        loadDesktopApps();
        loadDesktopListView();
        addDesktopClickListener();

        loadStartApps();
        loadStartListView();
        addStartClickListener();

        date();

        //shows a pop up on click, to exit the  app
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut();
            }
        });

        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                desktopListView.setVisibility(View.VISIBLE);
                startListView.setVisibility(View.GONE);
                libraryLinearLayout.setVisibility(View.GONE);
            }
        });

        //on click sets the state of taskbar to visible
        keyboardButtonOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                visibility=bottomFrameLayout.getVisibility();
                if (visibility==View.GONE) {
                    keyboardButtonOpen.setVisibility(View.INVISIBLE);
                    keyboardButtonClose.setVisibility(View.VISIBLE);
                    bottomFrameLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        //on click, shows all the apps are present
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                visibility=startListView.getVisibility();
                if (visibility==View.GONE){
                    startListView.setVisibility(View.VISIBLE);
                    desktopListView.setVisibility(View.GONE);
                }
            }
        });

        //on click, opens the settings app
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Intent intent=getPackageManager().getLaunchIntentForPackage("com.android.settings");
                intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION,ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                rectangle();
                startActivity(intent);
            }
        });

        //on click, opens the bluetooth page
        bluetoothButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
                intent1.addFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                intent1.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION,ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                rectangle();
                startActivity(intent1);
            }
        });

        //on click, opens the wifi page
        wifiButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent(Settings.ACTION_WIFI_SETTINGS);
                intent2.addFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                intent2.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION,ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                rectangle();
                startActivity(intent2);
            }
        });

        //on click, opens the brightness page
        brightnessButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Intent intent3=new Intent(Settings.ACTION_DISPLAY_SETTINGS);
                intent3.addFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                intent3.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION,ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                rectangle();
                startActivity(intent3);
            }
        });

        //on click, opens the list of library items
        libraryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                visibility=libraryLinearLayout.getVisibility();
                if (visibility==View.GONE){
                    libraryLinearLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        //sets the taskbar to invisible
        keyboardButtonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                visibility=bottomLinearLayoutLeft.getVisibility();
                if (visibility==View.VISIBLE) {
                    keyboardButtonOpen.setVisibility(View.VISIBLE);
                    keyboardButtonClose.setVisibility(View.INVISIBLE);
                    bottomFrameLayout.setVisibility(View.GONE);
                }
            }
        });

        //on click, opens the date and time page
        dateTimeButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Settings.ACTION_DATE_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION,ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                rectangle();
                startActivity(intent);
            }
        });

    }

    //creates a pop up to exit from the app
    public void logOut(){
        final AlertDialog.Builder builder=new AlertDialog.Builder(HomeActivity.this);
        builder.setMessage("Are you sure you want to exit?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    //loads 4 apps on the desktop
    private void loadDesktopApps(){
        packageManager=getPackageManager();
        apps=new ArrayList<>();
        Intent intent=new Intent(Intent.ACTION_MAIN,null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> availableApps= packageManager.queryIntentActivities(intent,0);
        for (ResolveInfo ri : availableApps) {
            DesktopApps appList = new DesktopApps();
            appList.lable = ri.activityInfo.packageName;
            appList.name = ri.loadLabel(packageManager);
            appList.icon = ri.loadIcon(packageManager);
            if (appList.name.equals("Gmail") || appList.name.equals("Chrome") || appList.name.equals("Drive") || appList.name.equals("Gallery")) {
                apps.add(appList);
            }
        }
    }

    //loads the desktop  app in a list view
    private void loadDesktopListView(){
        desktopListView=(ListView)findViewById(R.id.desktopListView);
        ArrayAdapter<DesktopApps> adapter=new ArrayAdapter<DesktopApps>(this,R.layout.desktop_apps,apps){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView==null){
                    convertView=getLayoutInflater().inflate(R.layout.desktop_apps,null);
                }
                ImageView appIcon=(ImageView)convertView.findViewById(R.id.icon);
                appIcon.setImageDrawable(apps.get(position).icon);
                TextView appName=(TextView) convertView.findViewById(R.id.name);
                appName.setText(apps.get(position).name);
                return convertView;

            }
        };
        desktopListView.setAdapter(adapter);
        desktopListView.setFocusable(false);
        desktopListView.setFocusableInTouchMode(false);
    }

    //on click, opens the selected app
    private void addDesktopClickListener(){
        desktopListView.setFocusableInTouchMode(false);
        desktopListView.setFocusable(false);
        desktopListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent=packageManager.getLaunchIntentForPackage(apps.get(position).lable.toString());
                intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                rectangle();
                startActivity(intent);
            }
        });
    }

    //loads all the apps present in the phone
    private void loadStartApps(){
        manager=getPackageManager();
        app=new ArrayList<>();
        Intent intent=new Intent(Intent.ACTION_MAIN,null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> availableApps= manager.queryIntentActivities(intent,0);
        for (ResolveInfo ri : availableApps){
            AppList appList=new AppList();
            appList.lable=ri.activityInfo.packageName;
            appList.name=ri.loadLabel(manager);
            appList.icon=ri.loadIcon(manager);
            app.add(appList);
        }
        Collections.sort(app, new Comparator<AppList>() {
            @Override
            public int compare(AppList appList, AppList t1) {
                return appList.name.toString().compareTo(t1.name.toString());
            }
        });
    }

    //loads all the apps in a list view
    private void loadStartListView(){
        startListView=(ListView)findViewById(R.id.startListView);
        ArrayAdapter<AppList> adapter=new ArrayAdapter<AppList>(this,R.layout.app_list,app){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView==null){
                    convertView=getLayoutInflater().inflate(R.layout.app_list,null);
                }
                ImageView appIcon=(ImageView)convertView.findViewById(R.id.icon);
                appIcon.setImageDrawable(app.get(position).icon);
                TextView appName=(TextView) convertView.findViewById(R.id.name);
                appName.setText(app.get(position).name);
                return convertView;

            }
        };
        startListView.setAdapter(adapter);
    }

    //on click, opens the selected app
    private void addStartClickListener(){
        startListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent=manager.getLaunchIntentForPackage(app.get(position).lable.toString());
                startActivity(intent);
            }
        });
    }

    //set the size of the screen when in split screen mode
    public void rectangle(){
        Rect rect=new Rect(0,0,100,100);
        ActivityOptions activityOptions= null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            activityOptions = ActivityOptions.makeBasic();
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            ActivityOptions bounds=activityOptions.setLaunchBounds(rect);
        }
    }

    //displays the current date and time
    public void date(){
        Date today = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd \n hh:mm:ss");
        String dateToStr = format.format(today);
        dateTimeButton.setText(dateToStr);
    }

}
