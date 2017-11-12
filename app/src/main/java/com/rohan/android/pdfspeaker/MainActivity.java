package com.rohan.android.pdfspeaker;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    PDFView pdfView;
    TextToSpeech tts;
    Button speak, stop, next, prev;
    Uri resultUri;
    String path;
    private final int CODE_REQUEST = 1212;
    //public String text = null;
    public File myfile = null;
    PdfReader reader = null;
    public ArrayList<Integer> bookmarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Intent rcv = getIntent();
        int page = rcv.getIntExtra("page", 0);
        final String path = rcv.getStringExtra("path_rtrn");


        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.US);
                    // Toast.makeText(MainActivity.this, "TTS INITIALIZED", Toast.LENGTH_SHORT).show();
                }
            }
        });
        speak = (Button) findViewById(R.id.speak);
        stop = (Button) findViewById(R.id.stop);
        next = (Button) findViewById(R.id.next_page);
        prev = (Button) findViewById(R.id.prev_page);
        bookmarks = new ArrayList<>();

        pdfView = (PDFView) findViewById(R.id.pdfView);
        //pdfView.fromAsset("s2.pdf").load();
        pdfView.fromAsset("s1.pdf").load();
//        File dir = Environment.getExternalStorageDirectory();
//        pdfView.fromFile(myfile).defaultPage(page - 1).load();
        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("hhhh", "myfile: " + myfile);
//                try {
//                    PDFDocument pdf = new PDFDocument(myfile.getAbsolutePath(), null);
//                    String text = pdf.getText(1);
//                    Log.e("hhhh", "onClick: "+text );
//                } catch (PDFException e) {
//                    e.printStackTrace();
//                }


                int aa;
                aa = pdfView.getCurrentPage();
                Log.e("hhhh", "aa: " + pdfView.getPageCount());
                String text = null; //Extracting the content from the different pages
                try {
//                    PdfTextExtractor parser = new PdfTextExtractor(reader);
                    reader = new PdfReader(String.valueOf(myfile));
                    Log.e("hhhh", "onClick: " + reader);
                    text = PdfTextExtractor.getTextFromPage(reader, aa + 1, new SimpleTextExtractionStrategy()).trim();
                } catch (IOException e) {
                    Log.e("hhhh", "message: " + e.getMessage());
                    e.printStackTrace();
                }
                Log.e("hhhh", "onClick: " + text);
                Toast.makeText(MainActivity.this, "speaking..", Toast.LENGTH_SHORT).show();
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tts.isSpeaking()) {
                    // Toast.makeText(MainActivity.this,"stoping tts", Toast.LENGTH_SHORT).show();
                    tts.stop();
                } else {
                    Toast.makeText(MainActivity.this, "Not speaking at all!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdfView.jumpTo(pdfView.getCurrentPage() + 1);
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdfView.jumpTo(pdfView.getCurrentPage() - 1);

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mMenuInflator = getMenuInflater();
        mMenuInflator.inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.menu_more:
                goto_homepage();
                break;
            case R.id.menu_share:
                file_chooser();
                break;
            case R.id.menu_like:
                add_bookmark();
                break;
            case R.id.menu_setting:
//                view_bookmark();
                break;
        }
        return true;
    }

    public void goto_homepage() {
        //Toast.makeText(this, "go to Homepage..", Toast.LENGTH_LONG).show();
        pdfView.jumpTo(0);
    }

    public void add_bookmark() {
        //Toast.makeText(this, "add bookmark..", Toast.LENGTH_SHORT).show();
        int pg_no = pdfView.getCurrentPage() + 1;
        bookmarks.add(pg_no);
        Toast.makeText(this, "Bookmark added" + bookmarks, Toast.LENGTH_SHORT).show();
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
//                pdfView.fromUri(resultUri).load();
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

                pdfView.fromUri(resultUri).load();

                String path = resultUri.getLastPathSegment();

                String final_name = resultUri.getLastPathSegment();
                final_name = final_name.replace("primary:", "");
                final_name = "/" + final_name;
                File dir = Environment.getExternalStorageDirectory();
                myfile = new File(dir, final_name+".pdf");
                Log.d("hhhh", "onActivityResult: "+myfile);
            }


        }

        super.onActivityResult(requestCode, resultCode, data);
    }


}

