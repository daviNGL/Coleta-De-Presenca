package com.davingl.coletadepresenca;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Date;

public class DisciplinasActivity extends AppCompatActivity {

    private TextView textViewDataHora;

    //Vetor auxiliar de dias da semana
    private final String arrayDiasSemana[] =
            {"Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado", "Domingo"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disciplinas);

        this.textViewDataHora = findViewById(R.id.textViewDataHora);

        exibeDataHora();
    }

    private void exibeDataHora() {

        //Descobre a data e formata pro formato dd/mm/aaaa
        String dataFormatada = new SimpleDateFormat("dd/MM/yyyy").format(new Date());

        //Descobre o dia da semana
        int dia = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        dia--;
        String diaSemana = arrayDiasSemana[dia];

        //Exibe dia da semana e data no TextView textView dataHora
        this.textViewDataHora.setText("Aula de hoje ("+ diaSemana +" - "+ dataFormatada +")");
    }


}