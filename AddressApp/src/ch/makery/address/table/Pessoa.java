/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.makery.address.table;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "Pessoa")
public class Pessoa implements Serializable {
    @Id
    @Column(name = "cod_CPF", unique = true)
    private String codCPF;
    
    @Column(name = "nom_prim", nullable = false)
    private String nomPrim;
    
    @Column(name = "nom_ult", nullable = false)
    private String nomUlt;
    
    @Column(name = "rua", nullable = false)
    private String rua;
    
    @Column(name = "cidade", nullable = false)
    private String cidade;
    
    @Column(name = "cod_postal", nullable = false)
    private String codPostal;
    
    @Column(name = "data_nasc", nullable = false)
    private String dataNasc;

    public Pessoa() {
    }

    public Pessoa(String codCPF, String nomPrim, String nomUlt, String rua, String cidade, String codPostal, String dataNasc) {
        this.codCPF = codCPF;
        this.nomPrim = nomPrim;
        this.nomUlt = nomUlt;
        this.rua = rua;
        this.cidade = cidade;
        this.codPostal = codPostal;
        this.dataNasc = dataNasc;
    }
    

    public String getCpf() {
        return codCPF;
    }

    public void setCpf(String codCPF) {
        this.codCPF = codCPF;
    }

    public String getNomPrim() {
        return nomPrim;
    }

    public void setNomPrim(String nomPrim) {
        this.nomPrim = nomPrim;
    }

    public String getNomUlt() {
        return nomUlt;
    }

    public void setNomUlt(String nomUlt) {
        this.nomUlt = nomUlt;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }
    
        
}
