/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import conexao.ConexaoJPA;
import java.awt.List;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import modelo.Funcionario;

/**
 *
 * @author Tony
 */
public class FuncionarioDao {
    // CRIANDO AS VARIAVERES DA CLASSES
    private EntityManager em;
    private EntityManagerFactory emf;

    // CRIA A FUNÇÃO PUBLICA PRA TODOS USAREM
    private EntityManager getEM() {
        emf = ConexaoJPA.getConexao(); //PEGANDO A CONEXÃO DA CLASSE CONEXAO
        return emf.createEntityManager();
    }

    // INSERIDO NOVO NO BANCO //////////////////////////////////////////////////
    public void inserir(Funcionario obj) {
        try {
            em = getEM();
            em.getTransaction().begin();
            em.persist(obj);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            em.getTransaction().rollback();
            
        } finally {
            em.close();
            
        }
        
    }

    /// ALTERAR ////////////////////////////////////////////////////////////////
    public void atualizar(Funcionario obj) {
        
        try {
            em = getEM();
            em.getTransaction().begin();
            em.merge(obj);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            em.getTransaction().rollback();
            
        } finally {
            em.close();
        }
    }

    // EXCLUIR /////////////////////////////////////////////////////////////////
    public void excluir(int codigo) {
        
        try {
            em = getEM();
            em.getTransaction().begin();
            Funcionario obj = em.find(Funcionario.class, codigo);
            em.remove(obj);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    // RETORNA POR ID //////////////////////////////////////////////////////////
    public Funcionario getPorID(int id) {
        em = getEM();
        Funcionario obj = em.find(Funcionario.class, id);
        em.close();
        return obj;
        
    }

    //lISTA TUDO ///////////////////////////////////////////////////////////////

    public ArrayList<Funcionario> getListagem() {
        em = getEM();
        TypedQuery<Funcionario> query = em.createQuery("SELECT f FROM Funcionario f", Funcionario.class);
        ArrayList<Funcionario> results = (ArrayList<Funcionario>) query.getResultList();
        em.close();
        return results;
    }

    // VERIFICA SE JÁ TEM CADASTRO /////////////////////////////////////////////
    public boolean verificaCadastrado(String procura) {
        em = getEM();
        String sql = "FROM Funcionario f WHERE f = '" + procura + "'";
        Query query = em.createQuery(sql);
        List results = (List) query.getResultList();
        em.close();
        if(results.getRows() > 0) return true; else return false;
        
    }
}
