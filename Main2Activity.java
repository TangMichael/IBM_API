package com.example.michaelnguyentang.hackothon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ibm.watson.developer_cloud.service.exception.ForbiddenException;
import com.ibm.watson.developer_cloud.service.exception.NotFoundException;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Main2Activity extends AppCompatActivity implements Runnable{

    public static String apipath;
    public VisualRecognition service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        Intent intent = getIntent();
        String path = intent.getStringExtra(MainActivity.bestpath);
        apipath = path;
        (new Thread(new Main2Activity())).start();

    }

    public void run(){
        service = new VisualRecognition(
                VisualRecognition.VERSION_DATE_2016_05_20
        );
        service.setApiKey("7f2daee8b7f01422103d477d947c72895896bd58");
        getPathIBM(apipath, service);
    }


    public synchronized void getPathIBM(String path, VisualRecognition service){



        try{
            Log.d("TAG","OK");
            InputStream imagesStream = new FileInputStream(path);
            Log.d("TEST1","TEST1");
            String []arr =path.split("/");
            ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
                    .imagesFile(imagesStream)
                    .imagesFileContentType("jpg")
                    .imagesFilename(arr[arr.length-1])
                    .build();
            //Log.d(classifyOptions.toString(),"okkk");
            Log.d("TEST2","TEST2");
            ClassifiedImages result = service.classify(classifyOptions).execute();
            Log.d("TEST","TEST");
            Log.d("Tag",result.toString());
        }catch(FileNotFoundException ex){
            Log.e("error","file not found");
        }
        catch(NotFoundException ex){
            Log.e("error", "not found exception");
        }

        catch(ForbiddenException ex){
            Log.e("error", "authentication not good");
        }

    }
}
