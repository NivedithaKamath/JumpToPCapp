package com.example.jumptopc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    LinearLayout desktopLinearLayout, startLinearLayout, exitLinearLayout,bottomLinearLayoutLeft,bottomLinearLayoutRight,libraryLinearLayout,biologyLinearLayout,chemistryLinearLayout,diyLinearLayout,historyLinearLayout,howToLinearLayout,kidsLinearLayout;
    ListView desktopListView, startListView,libraryListView,biologyListView,chemistryListView,diyListView,historyListView,howToListView,kidsListView;
    FrameLayout frameLayout,bottomFrameLayout;
    Button exitButton,startButton,settingsButton,bluetoothButton,wifiButton,brightnessButton,dateTimeButton,libraryButton;
    TextView exitTextView;
    PackageManager packageManager,manager;
    private List<DesktopApps> apps;
    private List<AppList> app;
    int visibility;
    ImageButton keyboardButtonOpen,keyboardButtonClose;
    SharedPreferences sharedPreferences;
    String url;

    String[] allowedDesktopApps = {"Gmail", "Chrome", "Drive", "Gallery"};
    String[] libraryListItems={"History","Chemistry","Biology","How to series","Kids","DIYs"};
    String[] biologyListItems={"Carbon... SO SIMPLE: Crash Course Biology #1","45-minute Tips to Score more than 90% in Class 12 Board Exam: Biology","Board Exam Analysis: CBSE Class 12 Biology 2020","Science Booster Series - Class 10 Biology","Meritnation NEET Bytes (Biology): Heredity and variation: Part-2","Meritnation NEET Bytes (Biology): Heredity and variation: Part-1"};
    String[] chemistryListItems={"Chemistry - SI and Derived Units","AIIMS 2018 - Complete Paper Analysis | Chemistry","NEET Chemistry: Electrochemistry - L1","NEET Chemistry: Electrochemistry - L2","NEET Chemistry: Electrochemistry - L3","NEET: Electrochemistry - L4"};
    String[] diyListItems={"Sesame Street: DIY Sock Puppies with Nina, Elmo, and Abby","DIY Economical Face Mask At Home","DIY Face Mask","DIY Hand Sanitizer"};
    String[] historyListItems={"The Agricultural Revolution: Crash Course World History #1","History vs. Genghis Khan","Crash Course History of Science Preview","History of the Union Jack","Why the UK Election Results are the Worst in History."};
    String[] howToListItems={"How To Hack Your To-Do List","How to Save the World from Email","How to Get Rich","How To Become World Class At Anything In 6 Months Or Less: 4 Hour Chef","How To Move And Pack Your House","How to Foolproof Your Budget"};
    String[] kidsListItems={"Stories For Kids","Kids Stories (English)","Yoga For Kids","Kids and COVID-19 | FAQ","Traditional Lullaby Song for kids"};

    private static String baseUrl="https://www.youtube.com/watch?v=";
    private static String imageUrl="https://img.youtube.com/vi/";
    private static String imageDefault="/default.jpg";
    String[] biologyImage={imageUrl+"QnQe0xW_JY4"+imageDefault,imageUrl+"qtzlmOe0fro"+imageDefault,imageUrl+"q2F9ZTOiwrc"+imageDefault,imageUrl+"MfC5c13xSBo"+imageDefault,imageUrl+"conSoVBzWnA"+imageDefault,imageUrl+"CJ60yTr9xvk"+imageDefault};
    String[] chemistryImage={imageUrl+"WV7FiOtCvHU"+imageDefault,imageUrl+"CjKnZWmCcak"+imageDefault,imageUrl+"W1emUNwTRKM"+imageDefault,imageUrl+"cIaBgN4nhCA"+imageDefault,imageUrl+"qkOrl0GnAWk"+imageDefault,imageUrl+"IXDA3r63p5g"+imageDefault};
    String[] diyImage={imageUrl+"gafVGrXTNg0"+imageDefault,imageUrl+"fAKl4vVxTv0"+imageDefault,imageUrl+"RRofKRr5_JM"+imageDefault,imageUrl+"mMtuzEX3cSI"+imageDefault};
    String[] historyImage={imageUrl+"Yocja_N5s1I"+imageDefault,imageUrl+"Eq-Wk3YqeH4"+imageDefault,imageUrl+"-hjGgFgnYIA"+imageDefault,imageUrl+"WVZQapdkwLo"+imageDefault,imageUrl+"r9rGX91rq5I"+imageDefault};
    String[] howToImage={imageUrl+"PKqBfVNWCEQ"+imageDefault,imageUrl+"DoAdoSSjNSM"+imageDefault,imageUrl+"CV7hAAcApnE"+imageDefault,imageUrl+"WzyvvkiUIKY"+imageDefault,imageUrl+"p0GQQT747KY/"+imageDefault,imageUrl+"j74DfIOLvfM"+imageDefault};
    String[] kidsImage={imageUrl+"7eBnRP5jx48"+imageDefault,imageUrl+"55vyFBtZ4EA"+imageDefault,imageUrl+"ASA213fYEjg"+imageDefault,imageUrl+"7vdAgTO2zvY"+imageDefault,imageUrl+"O9t15cBRPwI"+imageDefault,imageUrl+"CJ60yTr9xvk"+imageDefault};

    @RequiresApi(api = Build.VERSION_CODES.M)
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
        biologyLinearLayout=(LinearLayout)findViewById(R.id.biologyLinearLayout);
        chemistryLinearLayout=(LinearLayout)findViewById(R.id.chemistryLinearLayout);
        diyLinearLayout=(LinearLayout)findViewById(R.id.diyLinearLayout);
        historyLinearLayout=(LinearLayout)findViewById(R.id.historyLinearLayout);
        howToLinearLayout=(LinearLayout)findViewById(R.id.howToLinearLayout);
        kidsLinearLayout=(LinearLayout)findViewById(R.id.kidsLinearLayout);
        biologyListView=(ListView) findViewById(R.id.biologyListView);
        chemistryListView=(ListView) findViewById(R.id.chemistryListView);
        diyListView=(ListView) findViewById(R.id.diyListView);
        historyListView=(ListView) findViewById(R.id.historyListView);
        howToListView=(ListView) findViewById(R.id.howToListView);
        kidsListView=(ListView) findViewById(R.id.kidsListView);

        loadDesktopApps();
        loadDesktopListView();
        addDesktopClickListener();

        loadStartApps();
        loadStartListView();
        addStartClickListener();

        date();

        libraryList();
        thumbnailBiologyList();
        thumbnailChemistryList();
        thumbnailDIYList();
        thumbnailHistoryList();
        thumbnailHowToList();
        thumbnailKidsList();

        //displays a dialog box to exit from the app
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut();
            }
        });

        //on click, sets the visibility of the given widgets
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                desktopListView.setVisibility(View.VISIBLE);
                startListView.setVisibility(View.GONE);
                libraryLinearLayout.setVisibility(View.GONE);
                biologyLinearLayout.setVisibility(View.GONE);
                chemistryLinearLayout.setVisibility(View.GONE);
                diyLinearLayout.setVisibility(View.GONE);
                historyLinearLayout.setVisibility(View.GONE);
                howToLinearLayout.setVisibility(View.GONE);
                kidsLinearLayout.setVisibility(View.GONE);
            }
        });

        //displays the taskbar
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

        //opens the list of installed apps of the phone
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

        //opens the settings page
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Intent intent=getPackageManager().getLaunchIntentForPackage("com.android.settings");
                intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION,ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                rectangle();
                setLandscapeOrientationLock();
                startActivity(intent);
            }
        });

        //opens the bluetooth page
        bluetoothButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
                intent1.addFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                intent1.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION,ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                rectangle();
                setLandscapeOrientationLock();
                startActivity(intent1);
            }
        });

        //opens the wifi page
        wifiButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent(Settings.ACTION_WIFI_SETTINGS);
                intent2.addFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                intent2.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION,ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                rectangle();
                setLandscapeOrientationLock();
                startActivity(intent2);
            }
        });

        //opens the brightness page
        brightnessButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Intent intent3=new Intent(Settings.ACTION_DISPLAY_SETTINGS);
                intent3.addFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                intent3.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION,ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                rectangle();
                setLandscapeOrientationLock();
                startActivity(intent3);
            }
        });

        //opens the list of subjects
        libraryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                visibility=libraryLinearLayout.getVisibility();
                if (visibility==View.GONE){
                    libraryLinearLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        //disables the taskbar
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

        //displays the date and time page
        dateTimeButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Settings.ACTION_DATE_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION,ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                rectangle();
                setLandscapeOrientationLock();
                startActivity(intent);
            }
        });

        permissionDialog();
        onClickLibraryList();
        onClickBiologyList();
        onClickChemistryList();
        onClickDiyList();
        onClickHistoryList();
        onClickHowToList();
        onClickKidsList();

    }

    //on click, sets the visibility of the list of subjects to visible
    private void onClickLibraryList(){
        libraryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    //Biology
                    case 0:
                        visibility = biologyLinearLayout.getVisibility();
                        if (visibility == View.GONE) {
                            biologyLinearLayout.setVisibility(View.VISIBLE);
                        }
                        break;
                    //Chemistry
                    case 1:
                        visibility = chemistryLinearLayout.getVisibility();
                        if (visibility == View.GONE) {
                            chemistryLinearLayout.setVisibility(View.VISIBLE);
                        }
                        break;
                    //DIYs
                    case 2:
                        visibility = diyLinearLayout.getVisibility();
                        if (visibility == View.GONE) {
                            diyLinearLayout.setVisibility(View.VISIBLE);
                        }
                        break;
                    //History
                    case 3:
                        visibility = historyLinearLayout.getVisibility();
                        if (visibility == View.GONE) {
                            historyLinearLayout.setVisibility(View.VISIBLE);
                        }
                        break;
                    //Hoe to series
                    case 4:
                        visibility = howToLinearLayout.getVisibility();
                        if (visibility == View.GONE) {
                            howToLinearLayout.setVisibility(View.VISIBLE);
                        }
                        break;
                    //Kids
                    case 5:
                        visibility = kidsLinearLayout.getVisibility();
                        if (visibility == View.GONE) {
                            kidsLinearLayout.setVisibility(View.VISIBLE);
                        }
                        break;
                }
            }
        });
    }

    //redirects to the VideoActivity page and displays the selected video under biology
    private void onClickBiologyList(){
        biologyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        url=baseUrl+"QnQe0xW_JY4&list=PL3EED4C1D684D3ADF";
                        goToVideoActivity();
                        break;
                    case 1:
                        url=baseUrl+"qtzlmOe0fro&list=PL8z4bJQlT_9M9BEuqEbmWjVuxG90ZJ0-l&index=9";
                        goToVideoActivity();
                        break;
                    case 2:
                        url=baseUrl+"q2F9ZTOiwrc&list=PL8z4bJQlT_9M9BEuqEbmWjVuxG90ZJ0-l&index=12";
                        goToVideoActivity();
                        break;
                    case 3:
                        url=baseUrl+"MfC5c13xSBo&list=PL8z4bJQlT_9MQy7G3SwAVKGbfhcWbVUwN&index=1";
                        goToVideoActivity();
                        break;
                    case 4:
                        url=baseUrl+"conSoVBzWnA&list=PL8z4bJQlT_9O3P-MqI_nWKdtNd2HHZ9pA&index=3";
                        goToVideoActivity();
                        break;
                    case 5:
                        url=baseUrl+"CJ60yTr9xvk&list=PL8z4bJQlT_9O3P-MqI_nWKdtNd2HHZ9pA&index=6";
                        goToVideoActivity();
                        break;
                }
            }
        });
    }

    //redirects to the VideoActivity page and displays the selected video under chemistry
    private void onClickChemistryList(){
        chemistryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        url=baseUrl+"WV7FiOtCvHU&list=PLEry65uVHEikdocBNqoH5l6pCNkIxRBI0";
                        goToVideoActivity();
                        break;
                    case 1:
                        url=baseUrl+"CjKnZWmCcak&list=PLsgHooHkqhhNgU00i00pgDnIvnSu4h2Pl&index=3";
                        goToVideoActivity();
                        break;
                    case 2:
                        url=baseUrl+"W1emUNwTRKM&list=PLsgHooHkqhhPx8PUmYV2q6n6IbpGnCDlg&index=1";
                        goToVideoActivity();
                        break;
                    case 3:
                        url=baseUrl+"cIaBgN4nhCA&list=PLsgHooHkqhhPx8PUmYV2q6n6IbpGnCDlg&index=2";
                        goToVideoActivity();
                        break;
                    case 4:
                        url=baseUrl+"qkOrl0GnAWk&list=PLsgHooHkqhhPx8PUmYV2q6n6IbpGnCDlg&index=3";
                        goToVideoActivity();
                        break;
                    case 5:
                        url=baseUrl+"IXDA3r63p5g&list=PLsgHooHkqhhPx8PUmYV2q6n6IbpGnCDlg&index=4";
                        goToVideoActivity();
                        break;
                }
            }
        });
    }

    //redirects to the VideoActivity page and displays the selected video under diy
    private void onClickDiyList(){
        diyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        url=baseUrl+"gafVGrXTNg0&list=PL8TioFHubWFsYmcGTuRkA75ZYmJSmDjmH";
                        goToVideoActivity();
                        break;
                    case 1:
                        url=baseUrl+"fAKl4vVxTv0";
                        goToVideoActivity();
                        break;
                    case 2:
                        url=baseUrl+"RRofKRr5_JM";
                        goToVideoActivity();
                        break;
                    case 3:
                        url=baseUrl+"mMtuzEX3cSI";
                        goToVideoActivity();
                        break;
                }
            }
        });
    }

    //redirects to the VideoActivity page and displays the selected video under history
    private void onClickHistoryList(){
        historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        url=baseUrl+"Yocja_N5s1I&list=PLBDA2E52FB1EF80C9";
                        goToVideoActivity();
                        break;
                    case 1:
                        url=baseUrl+"Eq-Wk3YqeH4&list=PLJicmE8fK0Ehj95_A5aaOvfzkKTrt3G3W&index=2&t=0s";
                        goToVideoActivity();
                        break;
                    case 2:
                        url=baseUrl+"-hjGgFgnYIA&list=PL8dPuuaLjXtNppY8ZHMPDH5TKK2UpU8Ng";
                        goToVideoActivity();
                        break;
                    case 3:
                        url=baseUrl+"WVZQapdkwLo&list=PLqs5ohhass_TWuJqc36II6McLxqLcRJfO";
                        goToVideoActivity();
                        break;
                    case 4:
                        url=baseUrl+"r9rGX91rq5I&list=PLqs5ohhass_Tpgf5mu4R1EHvDPs2Bk_rY";
                        goToVideoActivity();
                        break;
                }
            }
        });
    }

    //redirects to the VideoActivity page and displays the selected video under how to series
    private void onClickHowToList(){
        howToListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        url=baseUrl+"PKqBfVNWCEQ&list=PLV2sCNkKbhht-CdlZXeGFlPDTitaA15UX&index=2";
                        goToVideoActivity();
                        break;
                    case 1:
                        url=baseUrl+"DoAdoSSjNSM&list=PLV2sCNkKbhht-CdlZXeGFlPDTitaA15UX&index=3";
                        goToVideoActivity();
                        break;
                    case 2:
                        url=baseUrl+"CV7hAAcApnE&list=PLV2sCNkKbhht-CdlZXeGFlPDTitaA15UX&index=4";
                        goToVideoActivity();
                        break;
                    case 3:
                        url=baseUrl+"WzyvvkiUIKY&list=PLV2sCNkKbhht-CdlZXeGFlPDTitaA15UX&index=5";
                        goToVideoActivity();
                        break;
                    case 4:
                        url=baseUrl+"p0GQQT747KY&list=PLV2sCNkKbhht-CdlZXeGFlPDTitaA15UX&index=7";
                        goToVideoActivity();
                        break;
                    case 5:
                        url=baseUrl+"j74DfIOLvfM&list=PLV2sCNkKbhht-CdlZXeGFlPDTitaA15UX&index=8";
                        goToVideoActivity();
                        break;
                }
            }
        });
    }

    //redirects to the VideoActivity page and displays the selected video under kids
    private void onClickKidsList(){
        kidsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        url=baseUrl+"7eBnRP5jx48&list=PLhuEelKSHsFnJxqcMvKB093R8h4ep_Tim";
                        goToVideoActivity();
                        break;
                    case 1:
                        url=baseUrl+"55vyFBtZ4EA&list=PLhuEelKSHsFnJxqcMvKB093R8h4ep_TIm&index=6";
                        goToVideoActivity();
                        break;
                    case 2:
                        url=baseUrl+"ASA213fYEjg";
                        goToVideoActivity();
                        break;
                    case 3:
                        url=baseUrl+"7vdAgTO2zvY";
                        goToVideoActivity();
                        break;
                    case 4:
                        url=baseUrl+"O9t15cBRPwI&list=PL54FD685741AD8C27&index=5";
                        goToVideoActivity();
                        break;
                    case 5:
                        url=baseUrl+"CJ60yTr9xvk&list=PL8z4bJQlT_9O3P-MqI_nWKdtNd2HHZ9pA&index=6";
                        goToVideoActivity();
                        break;
                }
            }
        });
    }

    //contains a list of subjects
    public void libraryList(){
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,R.layout.library_list,libraryListItems){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView==null){
                    convertView=getLayoutInflater().inflate(R.layout.library_list,null);
                }
                TextView listItemName=(TextView)convertView.findViewById(R.id.listTextView);
                listItemName.setText(libraryListItems[position]);
                return convertView;
            }
        };
        libraryListView.setAdapter(arrayAdapter);
        Collections.sort(Arrays.asList(libraryListItems), new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                return s.compareTo(t1);
            }
        });
    }

    //displays a dialog box to exit the app
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

    //loads 4 apps which will be displayed in the desktop
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
            for(String allowedDesktopApp : allowedDesktopApps) {
                if(allowedDesktopApp.equalsIgnoreCase(appList.name.toString())){
                    apps.add(appList);
                }
            }
        }
    }

    //loads the 4 apps in the listView
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
                try {
                    setLandscapeOrientationLock();
                } catch (Exception e) {
                    permissionDialog();
                }
                startActivity(intent);
            }
        });
    }

    //loads all the apps present in the phone, in a list view
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

    //loads all the apps in the  list view
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
                intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                rectangle();
                setLandscapeOrientationLock();
                startActivity(intent);
            }
        });
    }

    //sets the size of the screen, when its in split screen mode
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

    //gives the current date and time
    public void date(){
        Date today = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("DD-MM-YYYY \n hh:mm:ss");
        String dateToStr = format.format(today);
        dateTimeButton.setText(dateToStr);
    }

    //sets the orientation of all the apps to landscape
    private void setLandscapeOrientationLock() {
        try {
            android.provider.Settings.System.putInt(getContentResolver(),
                    android.provider.Settings.System.ACCELEROMETER_ROTATION,1);
        }
        catch (Exception e){
            permissionDialog();
        };
    }

    //opens a dialog box to get permission from the user
    private void permissionDialog(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(getApplicationContext())) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setTitle("Jump2PC needs your permission to change phone's system settings");
                builder.setMessage("Press yes to continue");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
    }

    //redirects to the VideoActivity
    private void goToVideoActivity(){
        sharedPreferences=getSharedPreferences("pref",MODE_PRIVATE);
        final SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("url",url);
        editor.commit();
        Intent intent = new Intent(HomeActivity.this, VideoActivity.class);
        startActivity(intent);
    }

    //contains list of thumbnails and video names of video under biology
    private void thumbnailBiologyList(){
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,R.layout.thumbnail_library_list,biologyListItems){
            @Override
            public int getCount() {
                return biologyImage.length;
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                convertView=getLayoutInflater().inflate(R.layout.thumbnail_library_list,null);
                ImageView imageView=(ImageView)convertView.findViewById(R.id.thumbnailImage);
                TextView textView=(TextView)convertView.findViewById(R.id.videoNameTextView);
                Picasso.with(HomeActivity.this).load(biologyImage[position]).into(imageView);
                textView.setText(biologyListItems[position]);
                return convertView;
            }
        };
        biologyListView.setAdapter(arrayAdapter);
    }

    //contains list of thumbnails and video names of video under chemistry
    private void thumbnailChemistryList(){
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,R.layout.thumbnail_library_list,chemistryListItems){
            @Override
            public int getCount() {
                return chemistryImage.length;
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                convertView=getLayoutInflater().inflate(R.layout.thumbnail_library_list,null);
                ImageView imageView=(ImageView)convertView.findViewById(R.id.thumbnailImage);
                TextView textView=(TextView)convertView.findViewById(R.id.videoNameTextView);
                Picasso.with(HomeActivity.this).load(chemistryImage[position]).into(imageView);
                textView.setText(chemistryListItems[position]);
                return convertView;
            }
        };
        chemistryListView.setAdapter(arrayAdapter);
    }

    //contains list of thumbnails and video names of video under diy
    private void thumbnailDIYList(){
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,R.layout.thumbnail_library_list,diyListItems){
            @Override
            public int getCount() {
                return diyImage.length;
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                convertView=getLayoutInflater().inflate(R.layout.thumbnail_library_list,null);
                ImageView imageView=(ImageView)convertView.findViewById(R.id.thumbnailImage);
                TextView textView=(TextView)convertView.findViewById(R.id.videoNameTextView);
                Picasso.with(HomeActivity.this).load(diyImage[position]).into(imageView);
                textView.setText(diyListItems[position]);
                return convertView;
            }
        };
        diyListView.setAdapter(arrayAdapter);
    }

    //contains list of thumbnails and video names of video under history
    private void thumbnailHistoryList(){
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,R.layout.thumbnail_library_list,historyListItems){
            @Override
            public int getCount() {
                return historyImage.length;
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                convertView=getLayoutInflater().inflate(R.layout.thumbnail_library_list,null);
                ImageView imageView=(ImageView)convertView.findViewById(R.id.thumbnailImage);
                TextView textView=(TextView)convertView.findViewById(R.id.videoNameTextView);
                Picasso.with(HomeActivity.this).load(historyImage[position]).into(imageView);
                textView.setText(historyListItems[position]);
                return convertView;
            }
        };
        historyListView.setAdapter(arrayAdapter);
    }

    //contains list of thumbnails and video names of video under how to series
    private void thumbnailHowToList(){
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,R.layout.thumbnail_library_list,howToListItems){
            @Override
            public int getCount() {
                return howToImage.length;
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                convertView=getLayoutInflater().inflate(R.layout.thumbnail_library_list,null);
                ImageView imageView=(ImageView)convertView.findViewById(R.id.thumbnailImage);
                TextView textView=(TextView)convertView.findViewById(R.id.videoNameTextView);
                Picasso.with(HomeActivity.this).load(howToImage[position]).into(imageView);
                textView.setText(howToListItems[position]);
                return convertView;
            }
        };
        howToListView.setAdapter(arrayAdapter);
    }

    //contains list of thumbnails and video names of video under kids
    private void thumbnailKidsList(){
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,R.layout.thumbnail_library_list,kidsListItems){
            @Override
            public int getCount() {
                return kidsImage.length;
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                convertView=getLayoutInflater().inflate(R.layout.thumbnail_library_list,null);
                ImageView imageView=(ImageView)convertView.findViewById(R.id.thumbnailImage);
                TextView textView=(TextView)convertView.findViewById(R.id.videoNameTextView);
                Picasso.with(HomeActivity.this).load(kidsImage[position]).into(imageView);
                textView.setText(kidsListItems[position]);
                return convertView;
            }
        };
        kidsListView.setAdapter(arrayAdapter);
    }

}