/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.cc.lpoo.cv.model;

import java.util.Calendar;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author marin
 */
@Entity
@Table(name = "tb_consulta")
public class Consulta {

    @Id
    @SequenceGenerator(name = "seq_consulta", sequenceName = "seq_consulta_id", allocationSize = 1)
    @GeneratedValue(generator = "seq_consulta", strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar data_consulta;

    @Column
    private String observacao;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar data_retorno;

    @Column
    private Float valor;

    @OneToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @OneToMany
    @JoinColumn(name = "receita_id")
    private List<Receita> receita;

    @OneToOne
    @JoinColumn(name = "medico_id")
    private Medico medico;

    public Consulta() {

    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the data_consulta
     */
    public Calendar getData_consulta() {
        return data_consulta;
    }

    /**
     * @param data_consulta the data_consulta to set
     */
    public void setData_consulta(Calendar data_consulta) {
        this.data_consulta = data_consulta;
    }

    /**
     * @return the observacao
     */
    public String getObservacao() {
        return observacao;
    }

    /**
     * @param observacao the observacao to set
     */
    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    /**
     * @return the data_retorno
     */
    public Calendar getData_retorno() {
        return data_retorno;
    }

    /**
     * @param data_retorno the data_retorno to set
     */
    public void setData_retorno(Calendar data_retorno) {
        this.data_retorno = data_retorno;
    }

    /**
     * @return the valor
     */
    public Float getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(Float valor) {
        this.valor = valor;
    }

    /**
     * @return the pet
     */
    public Pet getPet() {
        return pet;
    }

    /**
     * @param pet the pet to set
     */
    public void setPet(Pet pet) {
        this.pet = pet;
    }

    /**
     * @return the receita
     */
    public List<Receita> getReceita() {
        return receita;
    }

    /**
     * @param receita the receita to set
     */
    public void setReceita(List<Receita> receita) {
        this.receita = receita;
    }

    /**
     * @return the medico
     */
    public Medico getMedico() {
        return medico;
    }

    /**
     * @param medico the medico to set
     */
    public void setMedico(Medico medico) {
        this.medico = medico;
    }

}
