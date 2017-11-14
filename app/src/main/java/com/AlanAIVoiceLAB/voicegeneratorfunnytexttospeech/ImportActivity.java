package com.AlanAIVoiceLAB.voicegeneratorfunnytexttospeech;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.File;

@SuppressLint("Registered")
@EActivity(R.layout.activity_import)
public class ImportActivity extends AppCompatActivity {
    @ViewById(R.id.tvChooseFile)
    TextView mTvChooseFile;

    private final int REQUEST_CODE_FILE = 1212;
    private Uri mResultUri;
    private File myfile;
    private String mPath;

    @Click(R.id.btnChooseFile)
    void clickBtnChooseFile() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        // intent.putExtra(Intent.EXTRA_MIME_TYPES,ary);
        startActivityForResult(intent, REQUEST_CODE_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_FILE) {
            if (resultCode == RESULT_OK) {

                mResultUri = data.getData();
                mPath = mResultUri.getPath();
                Log.e("hhhhh", "mPath: " + mPath);
                String final_name = mResultUri.getLastPathSegment();
                final_name = final_name.replace("primary:", "");
                final_name = "/" + final_name;
                File dir = Environment.getExternalStorageDirectory();
                myfile = new File(dir, mPath);
                Log.d("hhhh", "onActivityResult: " + myfile);
                mTvChooseFile.setText(myfile.toString());
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Click(R.id.btnCancel)
    void clickBtnCancel() {
        onBackPressed();
    }

    @Click(R.id.btnImportFile)
    void clickBtnImportFile() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Utils.KEY_FILE_NAME, mResultUri.toString());
        Log.d("hhhh", "clickBtnImportFile: " + mResultUri);
        setResult(RESULT_OK, intent);
        finish();
    }
}
