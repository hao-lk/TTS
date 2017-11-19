package com.AlanAIVoiceLAB.voicegeneratorfunnytexttospeech;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.AlanAIVoiceLAB.voicegeneratorfunnytexttospeech.Utils.Utils;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.File;

@SuppressLint("Registered")
@EActivity(R.layout.activity_import)
public class ImportActivity extends AppCompatActivity {
    private final int REQUEST_CODE_FILE = 1212;
    @ViewById(R.id.tvChooseFile)
    TextView mTvChooseFile;
    private Uri mResultUri;

    @Click(R.id.btnChooseFile)
    void clickBtnChooseFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        try {
            startActivityForResult(intent, REQUEST_CODE_FILE);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_FILE) {
            if (resultCode == RESULT_OK) {
                mResultUri = data.getData();
                mTvChooseFile.setText(Utils.getFileName(ImportActivity.this, mResultUri));
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