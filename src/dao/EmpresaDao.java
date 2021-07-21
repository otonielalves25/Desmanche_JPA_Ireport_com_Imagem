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
import modelo.Empresa;

/**
 *
 * @author Tony
 */
public class EmpresaDao {
    // CRIANDO AS VARIAVERES DA CLASSES
    private EntityManager em;
    private EntityManagerFactory emf;

    // CRIA A FUNÇÃO PUBLICA PRA TODOS USAREM
    private EntityManager getEM() {
        emf = ConexaoJPA.getConexao(); //PEGANDO A CONEXÃO DA CLASSE CONEXAO
        return emf.createEntityManager();
    }

    // INSERIDO NOVO NO BANCO //////////////////////////////////////////////////
    public void inserir(Empresa obj) {
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
    public void atualizar(Empresa obj) {
        
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
            Empresa obj = em.find(Empresa.class, codigo);
            em.remove(obj);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    // RETORNA POR ID //////////////////////////////////////////////////////////
    public Empresa getPorID(int id) {
        em = getEM();
        Empresa obj = em.find(Empresa.class, id);
        em.close();
        return obj;
        
    }

    //lISTA TUDO ///////////////////////////////////////////////////////////////
    public ArrayList<Empresa> getListagem() {
        em = getEM();
        TypedQuery<Empresa> query = em.createQuery("SELECT p FROM Empresa p", Empresa.class);
        ArrayList<Empresa> results = (ArrayList<Empresa>) query.getResultList();
        em.close();
        return results;
    }

    // VERIFICA SE JÁ TEM CADASTRO /////////////////////////////////////////////
    public boolean verificaCadastrado(String procura) {
        em = getEM();
        String sql = "FROM Empresa x WHERE x = '" + procura + "'";
        Query query = em.createQuery(sql);
        ArrayList results = (ArrayList) query.getResultList();
        em.close();
        if (results.size() > 0) {
            return true;
        } else {
            return false;
        }
        
    }
}
