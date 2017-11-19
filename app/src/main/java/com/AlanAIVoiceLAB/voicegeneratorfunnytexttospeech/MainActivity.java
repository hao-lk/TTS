package com.AlanAIVoiceLAB.voicegeneratorfunnytexttospeech;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.AlanAIVoiceLAB.voicegeneratorfunnytexttospeech.Utils.ImageFilePath;
import com.AlanAIVoiceLAB.voicegeneratorfunnytexttospeech.Utils.Utils;
import com.github.barteksc.pdfviewer.PDFView;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

@SuppressLint("Registered")
@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.main_menu)
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @ViewById(R.id.pdfView)
    PDFView mPdfView;

    @ViewById(R.id.btnPlay)
    Button mBtnPlay;

    @ViewById(R.id.btnStop)
    Button mBtnStop;

    @ViewById(R.id.btnNext)
    Button mBtnNext;

    @ViewById(R.id.btnBack)
    Button mBtnBack;

    @ViewById(R.id.toolbar)
    Toolbar mToolbar;

    Uri resultUri;
    String final_name;
    private final int CODE_REQUEST = 1212;
    public final int REQUEST_CODE_IMPORT = 110;
    public final int RESULT_CODE_IMPORT = 111;
    //public String text = null;
    public File myfile = null;
    PdfReader reader = null;
    public ArrayList<Integer> bookmarks;
    private TextToSpeech mTextToSpeech;

    @AfterViews
    void afterView() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_info_black_24dp);
        Intent rcv = getIntent();
        int page = rcv.getIntExtra("page", 0);
        final String path = rcv.getStringExtra("path_rtrn");


        mTextToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    mTextToSpeech.setLanguage(Locale.US);
                    // Toast.makeText(MainActivity.this, "TTS INITIALIZED", Toast.LENGTH_SHORT).show();
                }
            }
        });
//        mBtnPlay = (Button) findViewById(R.id.btnPlay);
//        mBtnStop = (Button) findViewById(R.id.btnStop);
//        mBtnNext = (Button) findViewById(R.id.btnNext);
//        mBtnBack = (Button) findViewById(R.id.btnBack);
        bookmarks = new ArrayList<>();
        mPdfView.fromAsset("s1.pdf").load();
//        mPdfView.fromFile(myfile).defaultPage(page - 1).load();
        mBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("hhhh", "myfile: " + resultUri);
                File dir = new File(Environment.getExternalStorageState());

                int aa;
                aa = mPdfView.getCurrentPage();
                String text = null; //Extracting the content from the different pages
                try {
                    reader = new PdfReader(new File(dir,final_name).toString());
                    text = PdfTextExtractor.getTextFromPage(reader, aa + 1, new SimpleTextExtractionStrategy()).trim();
                } catch (IOException e) {
                    Log.e(TAG, "onClick: " + e.getMessage());
                    e.printStackTrace();
                }
                Log.e("hhhh", "text: " + text);
                Toast.makeText(MainActivity.this, "speaking..", Toast.LENGTH_SHORT).show();
                mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
            }
        });

        mBtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTextToSpeech.isSpeaking()) {
                    mTextToSpeech.stop();
                } else {
                    Toast.makeText(MainActivity.this, "Not speaking at all!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPdfView.jumpTo(mPdfView.getCurrentPage() + 1);
            }
        });

        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPdfView.jumpTo(mPdfView.getCurrentPage() - 1);

            }
        });
    }

    @OptionsItem(android.R.id.home)
    void clickHome() {
        InfoActivity_.intent(this).start();
    }

    @Click(R.id.btnImport)
    void clickBtnImport() {
        ImportActivity_.intent(this).startForResult(REQUEST_CODE_IMPORT);
    }

    @Click(R.id.btnExport)
    void clickBtnExport() {
        ExportActivity_.intent(this).start();
    }

    @OptionsItem(R.id.menu_more)
    void clickMenuMore() {

    }

    @OptionsItem(R.id.menu_share)
    void clickMenuShare() {
        goto_homepage();
    }

    @OptionsItem(R.id.menu_like)
    void clickMenuLike() {
        file_chooser();
    }

    @OptionsItem(R.id.menu_setting)
    void clickMenuSetting() {
        add_bookmark();
    }

    public void goto_homepage() {
        //Toast.makeText(this, "go to Homepage..", Toast.LENGTH_LONG).show();
        mPdfView.jumpTo(0);
    }

    public void add_bookmark() {
        //Toast.makeText(this, "add bookmark..", Toast.LENGTH_SHORT).show();
//        int pg_no = mPdfView.getCurrentPage() + 1;
//        bookmarks.add(pg_no);
//        Toast.makeText(this, "Bookmark added" + bookmarks, Toast.LENGTH_SHORT).show();
        SettingActivity_.intent(this).start();

    }

    public void file_chooser() {
        //Toast.makeText(MainActivity.this, "inside file chooser ", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        // intent.putExtra(Intent.EXTRA_MIME_TYPES,ary);
        startActivityForResult(intent, CODE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_REQUEST) {
            if (resultCode == RESULT_OK) {

                /*
                 * code chinh
                 */
//                resultUri = data.getData();
//
//                mPdfView.fromUri(resultUri).load();
//
//                path = resultUri.getLastPathSegment();
//                Log.e("hhhh", "onActivityResult: " + resultUri);

//                String final_name = resultUri.getLastPathSegment();
//                final_name = final_name.replace("primary:", "");
//                final_name = "/" + final_name;
//                File dir = Environment.getExternalStorageDirectory();
//                myfile = new File(resultUri.toString());
//                Log.d("hhhh", "myfile: " + myfile);
//            Log.d("hhhh", "final_name: "+final_name);

                /*
                 * demo
                 */

                resultUri = data.getData();
                File auxFile = new File(resultUri.getPath());
                String pdfPath = auxFile.getAbsolutePath();
                String aaaa= ImageFilePath.getPath(MainActivity.this, resultUri);
                Log.d(TAG, "aaaa" + pdfPath);

//                Log.e("hhhhh", "resultUri: " + resultUri);
                mPdfView.fromUri(resultUri).load();

//                String path = resultUri.getPath();
//                String final_name = resultUri.getLastPathSegment();
//                final_name = final_name.replace("primary:", "");
//                String path = myfile.getPath();
//                String[] list = myfile.toString().split("/");
//                final_name = list[list.length - 1];
//                File dir = Environment.getExternalStorageDirectory();

//                File file = new File(dir, path);
            }
        } else if (requestCode == REQUEST_CODE_IMPORT) {
            if (resultCode == RESULT_OK) {
                String string = data.getExtras().getString(Utils.KEY_FILE_NAME);
                resultUri = Uri.parse(string);
                mPdfView.fromUri(resultUri).load();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}
