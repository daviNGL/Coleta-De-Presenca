package com.davingl.coletadepresenca.dao;

import com.davingl.coletadepresenca.model.Usuario;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe para simular o acesso a base de dados para conferir o login.
 * Há uma lista com 5 usuarios em memória, que sempre é carregada ao executar a aplicação.
 */
public class UsuarioDAO {

    private Map<String, Usuario> listaUsuarios;

    //Construtor padrão
    public UsuarioDAO(){

        this.listaUsuarios = new HashMap<>();

        popularLista();

    }


    /**
     * Cadastra alguns usuário ficticios na lista de usuários.
     */
    private void popularLista() {

        Usuario userTest = new Usuario("1", "a", "Teste");

        Usuario usuario1 = new Usuario("111111111", "ana123", "Ana");
        Usuario usuario2 = new Usuario("222222222", "davi123", "Davi");
        Usuario usuario3 = new Usuario("333333333", "jeferson123", "Jeferson");
        Usuario usuario4 = new Usuario("444444444", "lucas123", "Mendes");
        Usuario usuario5 = new Usuario("555555555", "victor123", "Victor");


        this.listaUsuarios.put(usuario1.getRgm(), usuario1);
        this.listaUsuarios.put(usuario2.getRgm(), usuario2);
        this.listaUsuarios.put(usuario3.getRgm(), usuario3);
        this.listaUsuarios.put(usuario4.getRgm(), usuario4);
        this.listaUsuarios.put(usuario5.getRgm(), usuario5);

        this.listaUsuarios.put(userTest.getRgm(), userTest);

    }


    /**
     * Busca um usuário na base pelo RGM.
     * @param rgm RGM do usuário.
     * @return O usuário se encontrar, ou null se não encontrar.
     */
    public Usuario selectById(String rgm) {

        Usuario user = this.listaUsuarios.get(rgm);

        return user;
    }
}
