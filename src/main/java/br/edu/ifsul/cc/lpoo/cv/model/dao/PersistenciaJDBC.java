/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.cc.lpoo.cv.model.dao;

import br.edu.ifsul.cc.lpoo.cv.model.Venda;
import br.edu.ifsul.cc.lpoo.cv.model.Produto;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author marin
 */
public class PersistenciaJDBC implements InterfacePersistencia {

    private final String DRIVER = "org.postgresql.Driver";
    private final String USER = "postgres";
    private final String SENHA = "postgres";
    public static final String URL = "jdbc:postgresql://localhost:5432/trabalhoLPOO_CV";
    private Connection con = null;

    public PersistenciaJDBC() throws Exception {

        Class.forName(DRIVER);
        System.out.println("Tentando estabelecer conexao JDBC com :" + URL + " ...");

        this.con = (Connection) DriverManager.getConnection(URL, USER, SENHA);

    }

    @Override
    public Boolean conexaoAberta() {
        try {
            if (con != null) {
                return !con.isClosed();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public void fecharConexao() {
        try {
            this.con.close();
            System.out.println("Fechou conexao JDBC");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Object find(Class c, Object id) throws Exception {
        if (c == Venda.class) {
            //tb_venda
            PreparedStatement ps = this.con.prepareStatement("select id, observacao, valor_total, data from tb_venda where id = ?");
            ps.setInt(1, Integer.parseInt(id.toString()));

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Venda venda = new Venda();

                Calendar data = Calendar.getInstance();

                //Elementos da consulta
                venda.setId(rs.getInt("id"));
                venda.setObservacao(rs.getString("observacao"));
                venda.setValor_total(rs.getFloat("valor_total"));
                data.setTimeInMillis(rs.getDate("data").getTime());
                venda.setData(data);

                ps.close();

                return venda;

            }
        } else if (c == Produto.class) {
            PreparedStatement ps = this.con.prepareStatement("select id, nome, valor, quantidade from tb_produto where id = ?");
            ps.setInt(1, Integer.parseInt(id.toString()));

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Produto produto = new Produto();

                //Elementos da consulta
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getFloat("valor"));
                produto.setQuantidade(rs.getFloat("quantidade"));

                ps.close();

                return produto;
            }
        }

        return null;
    }

    @Override
    public void persist(Object o) throws Exception {

        if (o instanceof Venda) {
            Venda v = (Venda) o;

            if (v.getId() == null) {

                PreparedStatement ps = this.con.prepareStatement("insert into tb_venda (id, observacao, valor_total, data) values (nextval('seq_venda_id'),?,?,?) returning id");

                ps.setInt(1, v.getId());
                ps.setString(2, v.getObservacao());
                ps.setFloat(3, v.getValor_total());
                ps.setTimestamp(4, new Timestamp(v.getData().getTimeInMillis()));
                ps.execute();

            } else {
                PreparedStatement ps = this.con.prepareStatement("update tb_venda set "
                        + "observacao = ?, "
                        + "valor_total = ? "
                        + "data = ? "
                        + "where id = ?");

                ps.setString(1, v.getObservacao());
                ps.setFloat(2, v.getValor_total());
                ps.setTimestamp(3, new Timestamp(v.getData().getTimeInMillis()));
                ps.setInt(4, v.getId());
                ps.execute();

                ps.execute(); //executa
            }
        } else if (o instanceof Produto) {
            Produto p = (Produto) o;

            if (p.getId() == null) {
                PreparedStatement ps = this.con.prepareStatement("insert into tb_produto (id, nome, valor, quantidade) values (nextval('seq_produto_id'), ?, ?, ?) returning id");

                ps.setInt(1, p.getId());
                ps.setString(2, p.getNome());
                ps.setFloat(3, p.getValor());
                ps.setFloat(4, p.getQuantidade());
                ps.execute();

            } else {
                //update
                PreparedStatement ps = this.con.prepareStatement("update tb_produto set "
                        + "nome = ?, "
                        + "valor = ? "
                        + "quantidade = ? "
                        + "where id = ?");
               
                ps.setString(1, p.getNome());
                ps.setFloat(2, p.getValor());
                ps.setFloat(3, p.getQuantidade());
                ps.setInt(4, p.getId());
                ps.execute();

                ps.execute();//executa o comando.
            }
        }
    }

    @Override
    public void remover(Object o) throws Exception {
        if (o instanceof Venda) {

            Venda v = (Venda) o;

            PreparedStatement ps = this.con.prepareStatement("delete from tb_venda where id = ?");
            ps.setInt(1, v.getId());
            ps.execute();

        } else if (o instanceof Produto) {
            Produto p = (Produto) o;
            PreparedStatement ps = this.con.prepareStatement("delete from tb_produto where id = ?");
            ps.setInt(1, p.getId());
            ps.execute();
        }
    }
    
    @Override
    public List<Produto> listProdutos() throws Exception {
        
        List<Produto> lista = null;
                        
        PreparedStatement ps = this.con.prepareStatement("select id, nome, valor, quantidade from tb_produto order by id asc");
        
        ResultSet rs = ps.executeQuery();//executa a query        
      
        lista = new ArrayList();
        while(rs.next()){
            
            Produto p = new Produto();
            p.setId(rs.getInt("id"));
            p.setNome(rs.getString("nome"));
            p.setValor(rs.getFloat("valor"));
            p.setValor(rs.getFloat("quantidade"));
            
            lista.add(p);//adiciona na lista o objetivo que contem as informações de um determinada linha do ResultSet.
            
        }                
        return lista;
    }

}
