package com.davingl.coletadepresenca;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Date;

public class DisciplinasActivity extends AppCompatActivity {

    private TextView textViewDataHora;
    private String diaDaSemana;
    private int diaDaSemanaNumero;
    private Spinner spinnerDisciplinas;

    //Vetor auxiliar de dias da semana
    private final String arrayDiasSemana[] =
            {"Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disciplinas);

        this.textViewDataHora   = (TextView) findViewById(R.id.textViewDataHora);
        this.spinnerDisciplinas = (Spinner) findViewById(R.id.spinnerDisciplinas);

        //Exibe o dia e a hora no topo da tela
        exibeDataHora();

        //Seleciona a disciplina correta de acordo com o dia da semana
        selecionaDisciplina();


    }

    /**
     * Seleciona a disciplina correta no Spinner com base no dia da semana.
     */
    private void selecionaDisciplina() {

        switch (this.diaDaSemanaNumero){

            case 1:
                this.spinnerDisciplinas.setSelection(0);
                break;
            case 2:
                this.spinnerDisciplinas.setSelection(1);
                break;
            case 3:
                this.spinnerDisciplinas.setSelection(2);
                break;
            case 4:
                this.spinnerDisciplinas.setSelection(3);
                break;
            case 5:
                this.spinnerDisciplinas.setSelection(4);
                break;
            default:
                this.spinnerDisciplinas.setSelection(5);

        }

        //this.spinnerDisciplinas.setEnabled(false);

    }


    /**
     * Descobre o dia da semana e a data, formata e exibe no TextView textViewDataHora
     */
    private void exibeDataHora() {

        //Descobre a data e formata pro formato dd/mm/aaaa
        String dataFormatada = new SimpleDateFormat("dd/MM/yyyy").format(new Date());

        //Descobre o dia da semana
        int dia = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        dia--;
        String diaDaSemana = arrayDiasSemana[dia];

        //Exibe dia da semana e data no TextView textViewDataHora
        this.textViewDataHora.setText("Aula de hoje ("+ diaDaSemana +" - "+ dataFormatada +")");

        //Salva o dia da semana pra uso no Spinner
        this.diaDaSemanaNumero = dia;
    }


    /**
     * Método que é disparado quando clicar no botão cancelar. Apenas volta pra Activity anteior.
     * @param view
     */
    public void btnCancelar(View view){
        finish();
    }

}