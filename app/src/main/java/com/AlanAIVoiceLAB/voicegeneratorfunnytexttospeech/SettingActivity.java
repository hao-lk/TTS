package com.AlanAIVoiceLAB.voicegeneratorfunnytexttospeech;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_setting)
public class SettingActivity extends AppCompatActivity {

    @ViewById(R.id.spinnerLanguage)
    Spinner mSpinnerLanguage;
    @ViewById(R.id.spinnerSex)
    Spinner mSpinnerSex;

    @AfterViews
    void afterViews() {
        List<String> listLanguage = new ArrayList<>();
        listLanguage.add("English");
        listLanguage.add("French");
        listLanguage.add("Mandarin");
        listLanguage.add("Spanish");
        listLanguage.add("Hindustani");
        listLanguage.add("Arabic");
        listLanguage.add("German");
        listLanguage.add("Japanese");
        listLanguage.add("Russian");
        listLanguage.add("Italian");
        listLanguage.add("Portuguese");
        listLanguage.add("Malay");
        ArrayAdapter<String> adapterLanguage = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                listLanguage);
        adapterLanguage.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        mSpinnerLanguage.setAdapter(adapterLanguage);

        List<String> listSex = new ArrayList<>();
        listSex.add("ALEX");
        listSex.add("TOM");
        listSex.add("MARY");
        ArrayAdapter<String> adapterSex = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                listSex
        );
        adapterSex.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        mSpinnerSex.setAdapter(adapterSex);
    }
}
