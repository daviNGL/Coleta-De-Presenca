package com.davingl.coletadepresenca.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.davingl.coletadepresenca.R;
import com.davingl.coletadepresenca.controller.UsuarioController;
import com.davingl.coletadepresenca.dao.UsuarioDAO;
import com.davingl.coletadepresenca.model.Usuario;

public class MainActivity extends AppCompatActivity {

    private EditText editTextRGM;           //RGM informado pelo usuario
    private EditText editTextSenha;         //Senha informada pelo usuario
    private TextView textViewErroAcesso;    //Label abaixo dos botões para exibir msg de erro
    private CheckBox checkBoxLocalFake;     //Se ativado, utiliza localizacao fake da Unicid;

    private String rgm;                     //Auxiliar pra armazenar rgm
    private String senha;                   //Auxiliar pra armazenar a senha

    private Intent disciplinasActivity;     //Activity que será chamada

    private UsuarioController usuarioController;          //Acesso a base de dados



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.editTextRGM = findViewById(R.id.editTextRGM);
        this.editTextSenha = findViewById(R.id.editTextSenha);
        this.checkBoxLocalFake = findViewById(R.id.checkBoxLocalizacaoFake);

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


        //Recupera rgm e senha do formulário de login
        this.rgm    = this.editTextRGM.getText().toString();
        this.senha  = this.editTextSenha.getText().toString();



        //Verifica se o campo do RGM foi deixado em branco
        if(this.rgm.isEmpty() || this.rgm == null){

            this.editTextRGM.setError("Campo obrigatório.");
            this.textViewErroAcesso.setText("RGM inválido!");
            return;

        }



        //Verifica se o campo da senha foi deixado em branco
        if(this.senha.isEmpty() || this.senha == null){

            this.editTextSenha.setError("Campo obrigatório.");
            this.textViewErroAcesso.setText("Senha inválida!");
            return;

        }



        //Busca o usuario na base de dados
        this.usuarioController = new UsuarioController();
        Usuario usuarioBuscado = this.usuarioController.buscarUsuario(this.rgm);



        //Verifica se o usuario foi encontrado na base
        if(usuarioBuscado == null || !usuarioBuscado.getSenha().equals(this.senha)){

            this.textViewErroAcesso.setText("Usuário ou senha inválidos.");

        }else{

            Bundle bundle = new Bundle();
            bundle.putBoolean("isLocalFake", this.checkBoxLocalFake.isChecked());

            this.disciplinasActivity.putExtras(bundle);

            startActivity(this.disciplinasActivity);

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