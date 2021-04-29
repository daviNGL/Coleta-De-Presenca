package com.davingl.coletadepresenca.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.davingl.coletadepresenca.R;
import com.davingl.coletadepresenca.core.AppUtil;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DisciplinasActivity extends AppCompatActivity implements LocationListener {

    private TextView textViewDataHora;
    private TextView textViewLocalizacao;
    private Spinner spinnerDisciplinas;
    private Button buttonRegistrarPresenca;
    private int diaDaSemanaNumero;

    private Intent presencaRegistradaActivity;
    private Bundle bundle;

    private Bundle bundleEntrada;



    LocationManager locationManager;
    double latitude;
    double longitude;
    boolean usarLocalFake;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disciplinas);

        this.textViewDataHora = (TextView) findViewById(R.id.textViewDataHora);
        this.textViewLocalizacao = (TextView) findViewById(R.id.textViewLocalizacao);
        this.spinnerDisciplinas = (Spinner) findViewById(R.id.spinnerDisciplinas);
        this.buttonRegistrarPresenca = (Button) findViewById(R.id.buttonRegistrarPresenca);

        this.bundle = new Bundle();
        this.bundleEntrada = this.getIntent().getExtras();
        this.presencaRegistradaActivity = new Intent(DisciplinasActivity.this, PresencaRegistradaActivity.class);

        this.usarLocalFake = this.bundleEntrada.getBoolean(getString(R.string.strUsarLozalizacaoFake));


        exibirDataHora();

        selecionarDisciplina();


        if(usarLocalFake){

            this.latitude = AppUtil.LATITUDE_UNICID;
            this.longitude = AppUtil.LONGITUDE_UNICID;
            this.textViewLocalizacao.setText("Localização: " + latitude + ", " + longitude);

        }else {


            locationManager = (LocationManager) getApplication().getSystemService(Context.LOCATION_SERVICE);

            boolean permissaoOK = verificarPermissoes();
            boolean gpsOK = verificarStatusGPS();

            Log.i("APP_PERMI", "onCreate: -> " + permissaoOK + " - " + gpsOK);

            if (permissaoOK && gpsOK)
                buscarLocalizacao();
            else {
                this.latitude = 0.0000;
                this.longitude = 0.0000;
                this.textViewLocalizacao.setText("Localização: 0.0000, 0.0000");
            }


            Log.i("APP_PERMI", "onCreate: -> " + textViewLocalizacao.getText().toString());


        }
    }

    @Override
    protected void onResume() {

        super.onResume();

        if(usarLocalFake){

            this.latitude = AppUtil.LATITUDE_UNICID;
            this.longitude = AppUtil.LONGITUDE_UNICID;
            this.textViewLocalizacao.setText("Localização: " + latitude + ", " + longitude);

        }else {

            //Busca a localização do usuario
            buscarLocalizacao();

        }

    }


    private boolean verificarStatusGPS() {

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            AlertDialog.Builder dialogoBox = new AlertDialog.Builder(this);
            dialogoBox.setTitle("Localização desativada.");
            dialogoBox.setIcon(android.R.drawable.ic_dialog_alert);
            dialogoBox.setMessage("Favor, para usar esta aplicação, ativar a localização do dispositivo.");

            //Concordou
            dialogoBox.setPositiveButton("Ativar", (dialog, which) -> {
                Intent intentSettings = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intentSettings);
            });

            //Não concordou
            dialogoBox.setNegativeButton("Fechar", (dialog, which) -> Toast.makeText(DisciplinasActivity.this,
                    "Não será possível registrar presença com a localização desativada.", Toast.LENGTH_LONG).show());

            dialogoBox.show();

            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                return false;
        }

        return true;
    }


    private boolean verificarPermissoes() {

        String permission = Manifest.permission.ACCESS_FINE_LOCATION;
        String[] permissionRequired = new String[]{permission};

        if (ContextCompat.checkSelfPermission(DisciplinasActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(DisciplinasActivity.this, permissionRequired, 100);

            if (ContextCompat.checkSelfPermission(DisciplinasActivity.this, permission) != PackageManager.PERMISSION_GRANTED)
                return false;

        }

        return true;
    }


    /**
     * Seleciona a disciplina correta no Spinner com base no dia da semana.
     */
    private void selecionarDisciplina() {

        switch (this.diaDaSemanaNumero) {

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
                this.buttonRegistrarPresenca.setBackgroundColor(getColor(R.color.color_botao_desativado));

        }

        this.spinnerDisciplinas.setEnabled(false);

    }


    /**
     * Descobre o dia da semana e a data, formata e exibe no TextView textViewDataHora
     */
    private void exibirDataHora() {

        //Descobre a data e formata pro formato dd/mm/aaaa
        String dataFormatada = AppUtil.buscarDataFormatada();

        //Descobre o dia da semana
        int dia = AppUtil.buscarDiaDaSemana() - 1;

        String diaDaSemana = AppUtil.arrayDiasSemana[dia];

        //Exibe dia da semana e data no TextView textViewDataHora
        this.textViewDataHora.setText("Aula de hoje (" + diaDaSemana + " - " + dataFormatada + ")");

        //Salva o dia da semana pra uso no Spinner
        this.diaDaSemanaNumero = dia;
    }


    /**
     * Método que é disparado quando clicar no botão cancelar. Apenas volta pra Activity anteior.
     * @param view
     */
    public void btnCancelar(View view) {
        finish();
    }


    /**
     * Método ativado quando clica no botão de registrar presença
     */
    public void btnRegistrar(View view) {

        if(this.spinnerDisciplinas.getSelectedItemPosition() > 4){

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Não há nenhuma aula hoje");
            alertDialog.setIcon(android.R.drawable.ic_dialog_info);
            alertDialog.setMessage("Não há nenhuma disciplina programada para o dia de hoje, impossível registrar presença.");
            alertDialog.setPositiveButton("Entendi", null);
            alertDialog.show();

            return;
        }

        if(usarLocalFake){

            this.latitude = AppUtil.LATITUDE_UNICID;
            this.longitude = AppUtil.LONGITUDE_UNICID;
            this.textViewLocalizacao.setText("Localização: " + latitude + ", " + longitude);

            //Verifica se está dentro do horário de aula.
            boolean horarioOK = AppUtil.checarHorario(this.diaDaSemanaNumero);

            if(horarioOK){

                registrarPresenca();

            }else{

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("Erro ao registrar presença");
                alertDialog.setIcon(android.R.drawable.ic_delete);
                alertDialog.setMessage("Fora do horario de aula, tente novamente mais tarde.");
                alertDialog.setPositiveButton("Fechar", null);
                alertDialog.show();

                return;

            }



        }


        boolean permissaoOK = verificarPermissoes();

        if(!permissaoOK){
            Toast.makeText(DisciplinasActivity.this, "Aplicação não tem acesso a localização do dispositivo.", Toast.LENGTH_LONG);
            return;
        }

        boolean gpsOK = verificarStatusGPS();

        if(!gpsOK){
            Toast.makeText(DisciplinasActivity.this, "Localização do dispositivo precisa estar ativada.", Toast.LENGTH_LONG);
            return;
        }

        //Atualiza a localização do usuário
        buscarLocalizacao();

        //Verifica se o usuário está na UNICID
        boolean localizacaoOK = AppUtil.checarLocalizacao(this.latitude, this.longitude);

        //Verifica se está dentro do horário de aula.
        boolean horarioOK = AppUtil.checarHorario(this.diaDaSemanaNumero);


        if (localizacaoOK) {

            if (horarioOK) {

                registrarPresenca();

            } else {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("Erro ao registrar presença");
                alertDialog.setIcon(android.R.drawable.ic_delete);
                alertDialog.setMessage("Fora do horario de aula, tente novamente mais tarde.");
                alertDialog.setPositiveButton("Fechar", null);
                alertDialog.show();
            }

        } else {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Erro ao registrar presença");
            alertDialog.setIcon(android.R.drawable.ic_delete);
            alertDialog.setMessage("Usuário não localizado na UNICID.");
            alertDialog.setPositiveButton("Fechar", null);
            alertDialog.show();

        }

    }


    private void registrarPresenca() {

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
     * Busca a localização atual do usuário.
     */
    private void buscarLocalizacao() {


        long minDistance = 1;   //5 metros
        long minTime = 500; //5 segundos

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, this);
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {

        DecimalFormat dc = new DecimalFormat("#.#####");
        String strLat = "";
        String strLong = "";

        try{

            this.latitude = location.getLatitude();
            this.longitude = location.getLongitude();

            strLat = dc.format(this.latitude).replace(",", ".");
            strLong = dc.format(this.longitude).replace(",", ".");

            this.latitude = Double.parseDouble(strLat);
            this.longitude = Double.parseDouble(strLong);

        }catch (NumberFormatException ex){

            this.latitude = 0.0000;
            this.longitude = 0.0000;

            strLat = "0.00000";
            strLong = "0.00000";
        }


        String txtLocal = "Localização: " + strLat + ", " + strLong;

        textViewLocalizacao.setText(txtLocal);
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }


    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }


    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

}