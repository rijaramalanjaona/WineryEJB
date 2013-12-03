package com.ingesup.winery.ejb.detail;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.ejb3.annotation.RemoteBinding;

import com.ingesup.winery.entity.DetailCommande;

@Stateless
@RemoteBinding(jndiBinding = "ejb/detailCommande")
public class DetailCommandeManager implements RemoteDetailCommandeManager {
    @PersistenceContext(name = "em", unitName = "pu/tp/winery")
    private EntityManager em;

    public DetailCommande save(DetailCommande detailCommande) {
	em.persist(detailCommande);
	return detailCommande;
    }

    public void delete(DetailCommande detailCommande) {
	em.remove(em.merge(detailCommande));

    }

    public DetailCommande getById(Long id) {
	DetailCommande detailCommande;
	detailCommande = (DetailCommande) em.find(DetailCommande.class, id);
	return detailCommande;
    }

    @SuppressWarnings("unchecked")
    public List<DetailCommande> getByIdCommande(Long idCommande) {
	List<DetailCommande> liste = null;
	StringBuilder sb = new StringBuilder();
	sb.append("SELECT d ");
	sb.append("FROM DetailCommande d ");
	sb.append("WHERE d.commande.id =:idCommande ");
	Query query = em.createQuery(sb.toString());
	query.setParameter("idCommande", idCommande);
	liste = (List<DetailCommande>) query.getResultList();
	return liste;
    }

}
