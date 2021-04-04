package com.davingl.coletadepresenca.controller;

import com.davingl.coletadepresenca.dao.UsuarioDAO;
import com.davingl.coletadepresenca.model.Usuario;

import java.util.List;

public class UsuarioController {

    private UsuarioDAO dao;

    public UsuarioController(){

        this.dao = new UsuarioDAO();

    }



    public Usuario buscarUsuario(String rgm){
        return this.dao.selectById(rgm);
    }



//
//
//    public boolean inserir(Usuario usuario){
//        return this.dao.insert(usuario);
//    }
//
//    public boolean alterar(Usuario usuario){
//        return this.dao.update(usuario);
//    }
//
//    public boolean deletar(String rgm){
//        return this.dao.delete(rgm);
//    }
//
//    public List<Usuario> listar(){
//        return this.dao.selectAll();
//    }


}
