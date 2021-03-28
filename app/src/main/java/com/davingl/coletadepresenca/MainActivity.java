package com.davingl.coletadepresenca;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText editTextRGM;
    private EditText editTextSenha;
    private Long rgm;
    private String senha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.editTextRGM = findViewById(R.id.editTextRGM);
        this.editTextSenha = findViewById(R.id.editTextSenha);

    }

    /**
     * Método que é disparado ao clicar no botão de Login.
     * Recupera RGM e senha da view, e acessa a base de dados.
     * @param view
     */
    public void validaLogin(View view){


        //Tenta converter o rgm para um Long e seta no atributo "rgm"
        try{
            this.rgm = Long.parseLong( this.editTextRGM.getText().toString() );
        }catch (NumberFormatException ex){
            this.rgm = 0l;
        }

        //Recupera a senha e seta no atributo "senha"
        this.senha = this.editTextSenha.getText().toString();


        System.out.println("RGM = " + this.rgm);
        System.out.println("Senha = " + this.senha);

    }

    /**
     * Encerra a aplicação após o usuário clicar no botão 'Sair'
     * @param view
     */
    public void encerrarAplicacao(View view){

        finish();

    }

}