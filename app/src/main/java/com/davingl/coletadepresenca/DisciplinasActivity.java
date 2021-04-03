package com.davingl.coletadepresenca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DisciplinasActivity extends AppCompatActivity {

    private TextView textViewDataHora;
    private TextView textViewLocalizacao;
    private Spinner spinnerDisciplinas;
    private String diaDaSemana;
    private int diaDaSemanaNumero;
    private Location localizacaoUsuario;
    private static Location localizacaoUnicid;

    private Intent presencaRegistradaActivity;
    private Bundle bundle;

    //Localizacao
    private static final String TAG = "DisciplinasActivity";
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Boolean mLocationPermissionsGranted = false;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

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

        //Atualiza a localização na tela
        this.textViewLocalizacao.setText("Localização: " +
                this.localizacaoUsuario.getLatitude() + "  " + this.localizacaoUsuario.getLongitude());
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

        this.spinnerDisciplinas.setEnabled(false);

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



    /**
     * Método ativado quando clica no botão de registrar presença
     */
    public void btnRegistrar(View view){

        //Atualiza a localização do usuário
        buscaLocalizacao();

        //Verifica se o usuário está na UNICID
        boolean localizazoesIguais = comparaLocalizacoes();

        if(localizazoesIguais){
            registraPresenca();
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
     * Compara a localização do usuário com a da Unicid
     * @return bool
     */
    private boolean comparaLocalizacoes() {

        if( localizacaoUnicid.getLatitude()  == this.localizacaoUsuario.getLatitude() &&
            localizacaoUnicid.getLongitude() == this.localizacaoUsuario.getLongitude()  )
                return true;

        return false;
    }






    /**
     * Busca a localização atual do usuário e salva em localizacaoUsuario
     */
    private void buscaLocalizacao() {
        //Obtem permissao para localizacao
        getLocationPermission();

        Log.d(TAG, "getDeviceLocation: getting the devices current location");
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try{
            if(mLocationPermissionsGranted){
                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {

                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "onComplete: localizacao encnotrada!");
                            Location currentLocation = (Location) task.getResult();

                            SetLocation(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()));

                        }else{
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(DisciplinasActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
        ///////////////// LOCALIZAÇÃO FAKE ////////////////////
        //this.localizacaoUsuario.setLatitude(35.72405);
        //this.localizacaoUsuario.setLongitude(139.15889);
        //////////////// LOCALIZACAO UNICID ///////////////////
        //this.localizacaoUsuario.setLatitude(-23.53628);
        //this.localizacaoUsuario.setLongitude(-46.56033);
    }

    private void SetLocation(LatLng latLng){
        this.localizacaoUsuario.setLatitude(latLng.latitude);
        this.localizacaoUsuario.setLongitude(latLng.longitude);
    }

    /**
     * Obtem Permissao para localização
     * @return bool
     */
    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
                //initMap();
            }else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                            LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }
}