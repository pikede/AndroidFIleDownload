package com.example.prince.jobservices;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.util.Log;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DocumentService extends JobIntentService {

    final Handler mHandler = new Handler();
    private static final int JOB_ID = 1000;
    private static final String TAG= "Jobintent";
    DownloadManager dm;


    static void enqueueWork(Context context, Intent work){
        enqueueWork(context, DocumentService.class, JOB_ID, work);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        // We have received work to do.  The system or framework is already
        // holding a wake lock for us at this point, so we can just go.
        Log.d(TAG,"on handle intent");
        dm=(DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        Uri ur= Uri.parse("http://www.africau.edu/images/default/sample.pdf");
        DownloadManager.Request request= new DownloadManager.Request(ur);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        Long reference= dm.enqueue(request);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"Destroy");
        toast("Download Complete");
    }

    // Helper for showing tests
    private void toast(final CharSequence text) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(DocumentService.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
