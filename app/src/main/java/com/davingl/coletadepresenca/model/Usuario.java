package com.davingl.coletadepresenca.model;

public class Usuario {

    private String rgm;
    private String senha;
    private String nome;

    /**
     * Construtor padrão.
     */
    public Usuario(){

    }

    /**
     * Construtor com argumentos.
     * @param rgm RGM do usuário.
     * @param senha Senha do usuário.
     * @param nome Nome do usuário.
     */
    public Usuario(String rgm, String senha, String nome) {
        this.rgm = rgm;
        this.senha = senha;
        this.nome = nome;
    }

    public String getRgm() {
        return rgm;
    }

    public void setRgm(String rgm) {
        this.rgm = rgm;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "rgm='" + rgm + '\'' +
                ", senha='" + senha + '\'' +
                ", nome='" + nome + '\'' +
                '}';
    }
}
