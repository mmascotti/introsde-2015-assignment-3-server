package dao;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Measure;
import model.Person;

public class Dao {

	static EntityManager em;

	static {
		String uri_str = System.getenv("DATABASE_URL");

		Map<String, String> db_properties = new HashMap<String, String>();

		if (uri_str != null)
			db_properties = configureForHeroku(uri_str);

		EntityManagerFactory f = Persistence.createEntityManagerFactory("assignment3", db_properties);

		em = f.createEntityManager();
	}

	/**
	 * Extracts the database connection properties from the parameter 'uri_str'.
	 * @param uri_str database uri
	 * @return a {@link Map} containing the database connection properties
	 */
	private static Map<String, String> configureForHeroku(String uri_str) {
		Map<String, String> properties = new HashMap<String, String>();

		URI uri = null;
		try {
			uri = new URI(uri_str);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		String username = uri.getUserInfo().split(":")[0];
		String password = uri.getUserInfo().split(":")[1];
		String dbUrl = "jdbc:postgresql://" + uri.getHost() + ':' + uri.getPort() + uri.getPath();

		properties.put("javax.persistence.jdbc.url", dbUrl);
		properties.put("javax.persistence.jdbc.user", username);
		properties.put("javax.persistence.jdbc.password", password);

		return properties;
	}

	/**
	 * @return a {@link List} of all persons in the database
	 */
	@SuppressWarnings("unchecked")
	public static List<Person> getAllPeople() {
		String query = "Select p from Person p";
		Query q = em.createQuery(query);
		return q.getResultList();
	}

	/*** 
	 * @param id
	 * @return the person identified by 'id'
	 */
	public static Person getPersonById(Long id) {
		Person ret = em.find(Person.class, id);
		return ret;
	}

	/**
	 * Saves or updates the person.
	 * @param p person to save
	 * @return the saved or updated {@link Person}
	 */
	public static Person savePerson(Person p) {
		EntityTransaction t = em.getTransaction();
		t.begin();

		if (p.getId() == null)
			em.persist(p);
		else
			p = em.merge(p);

		t.commit();

		return p;
	}

	/**
	 * Deletes the person identified by 'id' from the database.
	 * @param id
	 */
	public static void deletePerson(Long id) {
		EntityTransaction t = em.getTransaction();
		t.begin();
		Person p = em.find(Person.class, id);
		em.remove(p);
		t.commit();
	}

	/**
	 * Saves or updates the specified {@link Measure}.
	 * @param measure
	 * @return the updated measure
	 */
	public static Measure saveMeasure(Measure measure) {
		EntityTransaction t = em.getTransaction();
		t.begin();
		
		if (measure.getDateRegistered() == null)
			measure.setDateRegistered(new Date());

		if (measure.getMid() == null)
			em.persist(measure);
		else
			measure = em.merge(measure);

		t.commit();

		return measure;
	}

	/**
	 * 
	 * @param id person id
	 * @param measureType measuretype
	 * @return the history for the measure of type 'measureType' for the person identified by 'id'
	 */
	@SuppressWarnings("unchecked")
	public static List<Measure> getHistory(Long id, String measureType) {
		String query = 	"Select m " + 
						"from Measure m " + 
						"where m.person.id = :id and m.measureType = :type " + 
						"order by m.dateRegistered desc";

		Query q = em.createQuery(query);
		q.setParameter("id", id);
		q.setParameter("type", measureType);

		List<Measure> ret = q.getResultList();
		ret.remove(0); //remove the most recent measure (which is part of the current healthprofile, not the history)
		
		return ret;
	}

	/**
	 * @return a {@link List} of all measuretypes that are in use in the database
	 */
	@SuppressWarnings("unchecked")
	public static List<String> getMeasureTypes() {
		String query = 	"Select distinct m.measureType " + 
						"from Measure m ";

		Query q = em.createQuery(query);
		return q.getResultList();
	}

	/**
	 * @param mid measure id
	 * @return the {@link Measure} identified by 'mid'
	 */
	public static Measure getMeasureById(Long mid) {
		Measure  ret = em.find(Measure.class, mid);
		return ret;
	}
}
