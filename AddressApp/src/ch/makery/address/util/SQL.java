/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.makery.address.util;

import ch.makery.address.table.Pessoa;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author USER
 */
public class SQL {
    private static final EntityManagerFactory CONNECTION;

    static {
        CONNECTION = Persistence.createEntityManagerFactory("AddressApp");
    }
    
    public static boolean addPerson(Pessoa pessoa) {
        EntityManager manager = CONNECTION.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            // Save the person object
            manager.persist(pessoa);
            System.out.println(transaction.isActive());

            transaction.commit();
        } catch (Exception ex) {
            // If there are any exceptions, roll back the changes
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println(ex);
            return false;
        } finally {
            manager.close();
            return true;
        }
    }
    
    public static List<Pessoa> getPersonList(){
        List<Pessoa> pessoas = null;

        EntityManager manager = CONNECTION.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();
            //lista de pessoas
            pessoas = manager.createQuery("SELECT p FROM Pessoa p",
                    Pessoa.class).getResultList();

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            manager.close();
        }
        return pessoas;
    }
    
    public static boolean deletePerson(String codCPF){
        EntityManager manager = CONNECTION.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            Pessoa pessoa = manager.find(Pessoa.class, codCPF);
            manager.remove(pessoa);

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        } finally {
            manager.close();
            return true;
        }
    }
    
    public static boolean updatePerson(Pessoa pessoa, String codCPF){
        EntityManager manager = CONNECTION.createEntityManager();
        EntityTransaction transaction = null;
        Boolean ans = null;
        try {
            transaction = manager.getTransaction();
            transaction.begin();

            Pessoa pessoaTeste = manager.find(Pessoa.class, codCPF);
            
            if(pessoaTeste != null){
                pessoaTeste.setCidade(pessoa.getCidade());
                pessoaTeste.setRua(pessoa.getRua());
                pessoaTeste.setCodPostal(pessoa.getCodPostal());
                pessoaTeste.setDataNasc(pessoa.getDataNasc());
                pessoaTeste.setNomPrim(pessoa.getNomPrim());
                pessoaTeste.setNomUlt(pessoa.getNomUlt());
                if(!pessoa.getCpf().equals(codCPF)){
                    Query query = manager.createQuery("UPDATE Pessoa p SET cod_CPF = '" + pessoa.getCpf() + "' WHERE cod_cpf = '" + codCPF + "' " );
                    query.executeUpdate();
                }
            }
            
            
            ans = true;
            transaction.commit();
        } catch (Exception ex) {
            // If there are any exceptions, roll back the changes
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println(ex);
            ans = false;
        } finally {
            // Close the EntityManager
            manager.close();
        }
        return ans;
    }
    
    

}
