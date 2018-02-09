package com.example.user.apptest;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    int imagerequest = 23;
    ImageView im;
    String imagename;
    /*声明日期及时间变量*/
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;
    ListView listView;
    Myadapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.listview);
        adapter = new Myadapter();
        listView.setAdapter(adapter);

        im = findViewById(R.id.mainimage);
         /*取得目前日期与时间*/

        Calendar c=Calendar.getInstance();
        mYear=c.get(Calendar.YEAR);
        mMonth=c.get(Calendar.MONTH);
        mDay=c.get(Calendar.DAY_OF_MONTH);
        mHour=c.get(Calendar.HOUR_OF_DAY);
        mMinute=c.get(Calendar.MINUTE);
        //命名照片
        imagename = new StringBuilder().append("i").append(mYear).append("_")
                .append(format(mMonth + 1)).append("_")
                .append(format(mDay)).append(".jpg").toString();

        //點擊imageView後拍照放入
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //利用intent調用相機拍照
                Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                File f = new File(getExternalFilesDir("PHOTO"), imagename);
                Log.d("name", imagename);
                //Log.d("path", String.valueOf(Environment.getExternalStorageDirectory()));

                Uri uri = FileProvider.getUriForFile(MainActivity.this, "com.example.user.apptest.provider", f);
                it.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(it, imagerequest);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == imagerequest) {
            if (resultCode == RESULT_OK) {
                File f = new File(getExternalFilesDir("PHOTO"), imagename);
                try {
                    InputStream is = new FileInputStream(f);
                    Log.d("BMP", "Can READ:" + is.available());
                    Bitmap bmp = getFitImage(is);
                    im.setImageBitmap(bmp);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    //解出照片格式
    public static Bitmap getFitImage(InputStream is) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        byte[] bytes = new byte[0];
        try {
            bytes = readStream(is);
            //BitmapFactory.decodeStream(inputStream, null, options);
            Log.d("BMP", "byte length:" + bytes.length);
            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
            System.gc();
            // Log.d("BMP", "Size:" + bmp.getByteCount());
            return bmp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //解出照片格式用的readStream方法
    public static byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inStream.close();
        return outStream.toByteArray();
    }

    //讀取資料夾中照片的方法
    public void loadphoto() {
        File f = new File(getExternalFilesDir("PHOTO"), imagename);
        try {
            InputStream is = new FileInputStream(f);
            //Log.d("BMP", "Can READ:" + is.available());
            Bitmap bmp = getFitImage(is);
            im.setImageBitmap(bmp);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*日期时间显示两位数的方法*/
    private String format(int x)
    {
        String s=""+x;
        if(s.length()==1) s="0"+s;
        return s;
    }
    class Myadapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return 7;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
            View v = inflater.inflate(R.layout.mylayout,null);

            ImageView imageView = v.findViewById(R.id.imageView);
            TextView textView = v.findViewById(R.id.textView);
            File f = new File(getExternalFilesDir("PHOTO"), imagename);
            try {
                InputStream is = new FileInputStream(f);
                //Log.d("BMP", "Can READ:" + is.available());
                Bitmap bmp = getFitImage(is);
                imageView.setImageBitmap(bmp);
                textView.setText(f.getName());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return v;
        }
    }
}
