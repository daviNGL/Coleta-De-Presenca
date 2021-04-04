package com.davingl.coletadepresenca.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.davingl.coletadepresenca.R;
import com.davingl.coletadepresenca.core.AppUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DisciplinasActivity extends AppCompatActivity {

    private TextView textViewDataHora;
    private TextView textViewLocalizacao;
    private Spinner spinnerDisciplinas;
    private Button buttonRegistrarPresenca;
    private String diaDaSemana;
    private int diaDaSemanaNumero;
    private Location localizacaoUsuario;
    private static Location localizacaoUnicid;

    private Intent presencaRegistradaActivity;
    private Bundle bundle;

    //Vetor auxiliar de dias da semana
    private final String arrayDiasSemana[] =
            {"Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado"};





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disciplinas);

        this.textViewDataHora           = (TextView) findViewById(R.id.textViewDataHora);
        this.textViewLocalizacao        = (TextView) findViewById(R.id.textViewLocalizacao);
        this.spinnerDisciplinas         = (Spinner) findViewById(R.id.spinnerDisciplinas);
        this.buttonRegistrarPresenca    = (Button) findViewById(R.id.buttonRegistrarPresenca);

        this.bundle = new Bundle();
        this.presencaRegistradaActivity = new Intent(DisciplinasActivity.this, PresencaRegistradaActivity.class);

        this.localizacaoUsuario = new Location("locationUsuario");

        //Exibe o dia e a hora no topo da tela
        exibeDataHora();

        //Seleciona a disciplina correta de acordo com o dia da semana
        selecionaDisciplina();


        //Seta a localização da UNICID
        localizacaoUnicid = new Location("locationUnicid");
        localizacaoUnicid.setLatitude(-23.53628);
        localizacaoUnicid.setLongitude(-46.56033);
    }





    @Override
    protected void onResume() {

        super.onResume();

        //Busca a localização do usuario
        buscaLocalizacao();

        String txtLocalizacao = "Localização: " + this.localizacaoUsuario.getLatitude() + "  " + this.localizacaoUsuario.getLongitude();

        //Atualiza a localização na tela
        this.textViewLocalizacao.setText(txtLocalizacao);
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
                this.buttonRegistrarPresenca.setEnabled(false);

        }

        this.spinnerDisciplinas.setEnabled(false);

    }





    /**
     * Descobre o dia da semana e a data, formata e exibe no TextView textViewDataHora
     */
    private void exibeDataHora() {

        //Descobre a data e formata pro formato dd/mm/aaaa
        String dataFormatada = AppUtil.buscarDataFormatada();

        //Descobre o dia da semana
        int dia = AppUtil.buscarDiaDaSemana() - 1;

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






    /**
     * Método ativado quando clica no botão de registrar presença
     */
    public void btnRegistrar(View view){

        //Atualiza a localização do usuário
        buscaLocalizacao();

        //Verifica se o usuário está na UNICID
        boolean localizazoesIguais = AppUtil.compararLocalizacoes(this.localizacaoUsuario, localizacaoUnicid);
        //Verifica se está dentro do horário de aula.
        boolean horasIguais = AppUtil.compararHoras(this.diaDaSemanaNumero);


        if(localizazoesIguais){

            if (horasIguais) {

                registraPresenca();

            } else {

                String msg = "Fora do horário de aula.";

                Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
            }

        }else{

            String msg = "Usuário precisa estar localizado na Unicid.";

            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();

        }

    }






    private void registraPresenca() {

        String dataHoraRegistro = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());

        //Salva valores que podem ser usados na prox Activity
        this.bundle.putString("aulaDeHoje", this.textViewDataHora.getText().toString());
        this.bundle.putString("local", this.textViewLocalizacao.getText().toString());
        this.bundle.putString("presencaReg", "Presença registrada em " + dataHoraRegistro);
        this.bundle.putInt("disciplinaSelect", this.diaDaSemanaNumero);

        //Passa o bundle pra Activity
        this.presencaRegistradaActivity.putExtras(this.bundle);

        startActivity(this.presencaRegistradaActivity);

    }






    /**
     * Busca a localização atual do usuário e salva em localizacaoUsuario
     */
    private void buscaLocalizacao() {

        //Precisa implementar esse método corretamente

        ///////////////// LOCALIZAÇÃO FAKE ////////////////////
//        this.localizacaoUsuario.setLatitude(35.72405);
//        this.localizacaoUsuario.setLongitude(139.15889);

        //////////////// LOCALIZACAO UNICID ///////////////////
        this.localizacaoUsuario.setLatitude(-23.53628);
        this.localizacaoUsuario.setLongitude(-46.56033);

    }


}