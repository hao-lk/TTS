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

import com.AlanAIVoiceLAB.voicegeneratorfunnytexttospeech.Utils.RateThisApp;
import com.AlanAIVoiceLAB.voicegeneratorfunnytexttospeech.Utils.Utils;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.shockwave.pdfium.PdfDocument;

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
public class MainActivity extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener,
        OnPageErrorListener {

    private static final String TAG = "qqq";
    public final int REQUEST_CODE_IMPORT = 110;
    public final int RESULT_CODE_IMPORT = 111;
    private final int CODE_REQUEST = 1212;
    //public String text = null;
    public File myfile = null;
    public ArrayList<Integer> bookmarks;
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
    PdfReader reader = null;
    String pdfFileName;
    Integer pageNumber = 0;
    Uri uri;
    private TextToSpeech mTextToSpeech;

    @AfterViews
    void afterView() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_info_black_24dp);
        Intent rcv = getIntent();


        mTextToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    mTextToSpeech.setLanguage(Locale.US);
                }
            }
        });
        mBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int aa;
                aa = mPdfView.getCurrentPage();
                String text = null;
                try {
                    reader = new PdfReader(String.valueOf(myfile));
                    text = PdfTextExtractor.getTextFromPage(reader, aa + 1, new SimpleTextExtractionStrategy()).trim();
                } catch (IOException e) {
                    Log.e(TAG, "onClick: " + e.getMessage());
                    e.printStackTrace();
                }
                Toast.makeText(MainActivity.this, "Speaking...", Toast.LENGTH_SHORT).show();
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

    @OptionsItem(R.id.menu_share)
    void clickMenuShare() {
        shareApp();
    }

    @OptionsItem(R.id.menu_like)
    void clickMenuLike() {
        RateThisApp.showRateDialog(MainActivity.this, R.style.MyAlertDialogStyle);
    }


    public void shareApp() {
        String message = Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName()).toString();
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(share, "Title of the dialog the system will open"));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_IMPORT) {
            if (resultCode == RESULT_OK) {
                uri = Uri.parse(String.valueOf(data.getExtras().getString(Utils.KEY_FILE_NAME)));
                File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                myfile = new File(dir, Utils.getFileName(MainActivity.this, uri));
                displayFromUri(uri);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void displayFromUri(Uri uri) {
        mPdfView.fromUri(uri)
                .defaultPage(pageNumber)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .spacing(10) // in dp
                .onPageError(this)
                .load();
    }

    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = mPdfView.getDocumentMeta();
        Log.e(TAG, "title = " + meta.getTitle());
        Log.e(TAG, "author = " + meta.getAuthor());
        Log.e(TAG, "subject = " + meta.getSubject());
        Log.e(TAG, "keywords = " + meta.getKeywords());
        Log.e(TAG, "creator = " + meta.getCreator());
        Log.e(TAG, "producer = " + meta.getProducer());
        Log.e(TAG, "creationDate = " + meta.getCreationDate());
        Log.e(TAG, "modDate = " + meta.getModDate());
    }

    @Override
    public void onPageChanged(int page, int pageCount) {

    }

    @Override
    public void onPageError(int page, Throwable t) {
        Log.e(TAG, "Cannot load page " + page);
    }
}