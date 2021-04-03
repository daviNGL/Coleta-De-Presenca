package com.davingl.coletadepresenca.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.davingl.coletadepresenca.R;
import com.davingl.coletadepresenca.dao.UsuarioDAO;

public class MainActivity extends AppCompatActivity {

    private UsuarioDAO usuarioDao;          //Acesso a base de dados
    private EditText editTextRGM;           //RGM informado pelo usuario
    private EditText editTextSenha;         //Senha informada pelo usuario
    private TextView textViewErroAcesso;    //Label abaixo dos botões para exibir msg de erro
    private Long rgm;                       //Auxiliar pra armazenar rgm
    private String senha;                   //Auxiliar pra armazenar a senha
    private Intent disciplinasActivity;     //Activity que será chamada



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.editTextRGM = findViewById(R.id.editTextRGM);
        this.editTextSenha = findViewById(R.id.editTextSenha);

        this.textViewErroAcesso = findViewById(R.id.textViewErroAcesso);

        this.disciplinasActivity = new Intent(MainActivity.this, DisciplinasActivity.class);
    }


    /*
     *   Limpa os dados preenchidos ao voltar para mainAcativity.
     */
    @Override
    protected void onRestart() {
        super.onRestart();

        if (!this.editTextRGM.getText().toString().isEmpty()) {

            this.editTextRGM.setText("");
        }

        if (!this.editTextSenha.getText().toString().isEmpty()) {

            this.editTextSenha.setText("");
        }

        if (!this.textViewErroAcesso.getText().toString().isEmpty()) {

            this.textViewErroAcesso.setText("");
        }
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


        //Busca o usuario na base de dados
        this.usuarioDao = new UsuarioDAO(this.rgm, this.senha);
        int acessoBase = this.usuarioDao.validaLogin();


        //Verifica se o usuario foi encontrado na base
        switch (acessoBase){
            case 0:
                //Vai para a outra activity
                this.startActivity(this.disciplinasActivity);
                break;
            case 1:
                this.textViewErroAcesso.setText("RGM inválido!");
                break;
            case 2:
                this.textViewErroAcesso.setText("Senha incorreta!");
                break;
            default:
                this.textViewErroAcesso.setText("Erro inesperado.");
                break;
        }

    }



    /**
     * Encerra a aplicação após o usuário clicar no botão 'Sair'
     * @param view
     */
    public void encerrarAplicacao(View view){

        finish();

    }

}