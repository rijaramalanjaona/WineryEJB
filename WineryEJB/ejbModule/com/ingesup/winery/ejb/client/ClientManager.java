package com.ingesup.winery.ejb.client;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.ejb3.annotation.RemoteBinding;

import com.ingesup.winery.entity.Client;

@Stateless
@RemoteBinding(jndiBinding = "ejb/client")
public class ClientManager implements RemoteClientManager {
    @PersistenceContext(name = "em", unitName = "pu/tp/winery")
    private EntityManager em;

    public Client save(Client client) {
	em.persist(client);
	return client;
    }

    public void delete(Client client) {
	em.remove(em.merge(client));

    }

    public Client getById(Long id) {
	Client client;
	client = (Client) em.find(Client.class, id);
	return client;
    }

    @SuppressWarnings("unchecked")
    public Client isIdentificationOk(String login, String password) {
	StringBuilder sb = new StringBuilder();
	sb.append("SELECT c ");
	sb.append("FROM client c ");
	sb.append("WHERE c.login =:login ");
	sb.append("AND c.password =:password ");
	Query query = em.createQuery(sb.toString());
	query.setParameter("login", login).setParameter("password", password);
	query.setMaxResults(1);
	List<Client> result = (List<Client>) query.getResultList();
	if (!result.isEmpty()) {
	    return result.get(0);
	}
	return null;
    }

}
