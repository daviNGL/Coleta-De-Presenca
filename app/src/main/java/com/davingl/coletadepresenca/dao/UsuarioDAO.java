package com.davingl.coletadepresenca.dao;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe para simular o acesso a base de dados para conferir o login.
 * Há uma lista com 5 usuarios em memória, que sempre é carregada ao executar a aplicação.
 */
public class UsuarioDAO {

    private Long rgm;
    private String senha;
    private Map<Long, String> listaUsuarios;

    //Construtor padrão
    public UsuarioDAO(){

        this.listaUsuarios = new HashMap<>();

        this.listaUsuarios.put(new Long(111111111), "ana123");
        this.listaUsuarios.put(new Long(222222222), "davi123");
        this.listaUsuarios.put(new Long(333333333), "jefter123");
        this.listaUsuarios.put(new Long(444444444), "lucas123");
        this.listaUsuarios.put(new Long(555555555), "victor123");
        this.listaUsuarios.put(new Long(1), "a");

    }

    //Construtor com argumentos
    public UsuarioDAO(Long rgm, String senha) {

        this();

        this.rgm = rgm;
        this.senha = senha;
    }


    //Getters and setters
    public Long getRgm() {
        return rgm;
    }

    public void setRgm(Long rgm) {
        this.rgm = rgm;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int validaLogin(){

        String pass = this.listaUsuarios.get(this.rgm);

        //RGM inesistente
        if(pass == null)
            return 1;

        //Senha invalida
        if(!this.senha.equals(pass))
            return 2;

        //Dados OK
        return 0;
    }

}
