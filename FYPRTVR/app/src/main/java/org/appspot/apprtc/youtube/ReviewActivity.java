package org.appspot.apprtc.youtube;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.api.services.youtube.model.VideoSnippet;

import org.appspot.apprtc.R;

public class ReviewActivity extends Activity {
    VideoView mVideoView;
    MediaController mc;
    private String mChosenAccountName;
    private Uri mFileUri;
    VideoSnippet snippet = new VideoSnippet();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_review);
        Button uploadButton = (Button) findViewById(R.id.upload_button);
        Intent intent = getIntent();
        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            uploadButton.setOnClickListener(uploadButtonOnclickListener);
            uploadButton.setVisibility(View.GONE);
            setTitle(R.string.playing_the_video_in_upload_progress);
        }
        mFileUri = intent.getData();
        loadAccount();

        reviewVideo(mFileUri);

    }

    private void reviewVideo(Uri mFileUri) {
        try {
            mVideoView = (VideoView) findViewById(R.id.videoView);
            mc = new MediaController(this);
            mVideoView.setMediaController(mc);
            mVideoView.setVideoURI(mFileUri);
            mc.show();
            mVideoView.start();
        } catch (Exception e) {
            Log.e(this.getLocalClassName(), e.toString());
        }
    }

    private void loadAccount() {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(this);
        mChosenAccountName = sp.getString(YoutubeMain.ACCOUNT_KEY, null);
        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.review, menu);
        return true;
    }

    public void uploadVideo(View view) {
        if (mChosenAccountName == null) {
            return;
        }
        // if a video is picked or recorded.
        if (mFileUri != null) {
            Intent uploadIntent = new Intent(this, UploadService.class);
            uploadIntent.setData(mFileUri);
            uploadIntent.putExtra(YoutubeMain.ACCOUNT_KEY, mChosenAccountName);
            startService(uploadIntent);
            Toast.makeText(this, R.string.youtube_upload_started,
                    Toast.LENGTH_LONG).show();
            // Go back to MainActivity after upload
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private Button.OnClickListener uploadButtonOnclickListener = new Button.OnClickListener(){
        @Override
        public void onClick(View view) {
            AlertDialog.Builder editDialog = new AlertDialog.Builder(ReviewActivity.this);

            editDialog.setTitle("Upload Video");

            final EditText editText = new EditText(ReviewActivity.this);
            final EditText editText1 = new EditText(ReviewActivity.this);
            editText.setText(snippet.getTitle());
            editText1.setText(snippet.getDescription());
            editDialog.setView(editText);
            editDialog.setView(editText1);

            editDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                // do something when the button is clicked
                public void onClick(DialogInterface arg0, int arg1) {
                    snippet.setTitle(editText.getText().toString());
                    snippet.setDescription(editText1.getText().toString());


                }
            });
            editDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                // do something when the button is clicked
                public void onClick(DialogInterface arg0, int arg1) {
                    //...
                }
            });
            editDialog.show();
            }
    };
}
