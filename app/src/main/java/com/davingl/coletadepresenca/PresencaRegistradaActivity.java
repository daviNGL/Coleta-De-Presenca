package com.davingl.coletadepresenca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

public class PresencaRegistradaActivity extends AppCompatActivity {

    private TextView textViewAulaFinal;
    private TextView textViewLocalizacaoFinal;
    private TextView textViewDataRegFinal;
    private Spinner spinnerDisciplinasFinal;

    private Intent lastIntent;
    private Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presenca_registrada);

        this.textViewAulaFinal          = (TextView) findViewById(R.id.textViewAulaFinal);
        this.textViewLocalizacaoFinal   = (TextView) findViewById(R.id.textViewLocalizacaoFinal);
        this.textViewDataRegFinal       = (TextView) findViewById(R.id.textViewDataRegFinal);
        this.spinnerDisciplinasFinal    = (Spinner) findViewById(R.id.spinnerDisciplinasFinal);

        this.lastIntent = getIntent();
        this.bundle     = this.lastIntent.getExtras();

        setaValores();

    }

    private void setaValores() {

        this.textViewAulaFinal.setText( this.bundle.getString("aulaDeHoje") );
        this.textViewLocalizacaoFinal.setText( this.bundle.getString("local") );
        this.textViewDataRegFinal.setText( this.bundle.getString("presencaReg") );

        int pos = this.bundle.getInt("disciplinaSelect") - 1;
        this.spinnerDisciplinasFinal.setSelection(pos);
        this.spinnerDisciplinasFinal.setEnabled(false);


    }

    public void fecharAplicacao(View view){

        finishAffinity();
    }


}