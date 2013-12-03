package com.ingesup.winery.ejb.produit;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.ejb3.annotation.RemoteBinding;

import com.ingesup.winery.entity.Produit;

@Stateless
@RemoteBinding(jndiBinding = "ejb/produit")
public class ProduitManager implements RemoteProduitManager {
    @PersistenceContext(name = "em", unitName = "pu/tp/winery")
    private EntityManager em;

    public Produit save(Produit produit) {
	em.persist(produit);
	return produit;
    }

    public void delete(Produit produit) {
	em.remove(em.merge(produit));

    }

    public Produit getById(Long id) {
	Produit produit;
	produit = (Produit) em.find(Produit.class, id);
	return produit;
    }

    @SuppressWarnings("unchecked")
    public List<Produit> getAll() {
	Query query = em.createQuery("SELECT p FROM produit p ORDER BY p.appellation");
	return (List<Produit>) query.getResultList();
    }

    public Produit update(Produit produit) {
	return em.merge(produit);
    }
    
    

}
