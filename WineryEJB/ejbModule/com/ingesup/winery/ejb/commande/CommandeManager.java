package com.ingesup.winery.ejb.commande;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.ejb3.annotation.RemoteBinding;

import com.ingesup.winery.entity.Commande;

@Stateless
@RemoteBinding(jndiBinding = "ejb/commande")
public class CommandeManager implements RemoteCommandeManager {
    @PersistenceContext(name = "em", unitName = "pu/tp/winery")
    private EntityManager em;

    public Commande save(Commande commande) {
	em.persist(commande);
	return commande;
    }

    public void delete(Commande commande) {
	em.remove(em.merge(commande));

    }

    public Commande getById(Long id) {
	Commande commande;
	commande = (Commande) em.find(Commande.class, id);
	return commande;
    }

    @SuppressWarnings("unchecked")
    public List<Commande> getByIdClient(Long idClient) {
	List<Commande> liste = null;
	StringBuilder sb = new StringBuilder();
	sb.append("SELECT c ");
	sb.append("FROM Commande c ");
	sb.append("WHERE c.client.id =:idClient ");
	sb.append("ORDER BY c.id DESC ");
	Query query = em.createQuery(sb.toString());
	query.setParameter("idClient", idClient);
	liste = (List<Commande>) query.getResultList();
	return liste;
    }

    public Commande update(Commande commande) {
	return em.merge(commande);
    }

}
