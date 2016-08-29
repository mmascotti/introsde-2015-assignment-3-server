package introsde.assignment.soap;

import java.util.List;

import javax.jws.WebService;

import dao.Dao;
import model.Measure;
import model.Person;

@WebService(endpointInterface = "introsde.assignment.soap.People", serviceName="PeopleWS")
public class PeopleWSImplementation implements People{
	
	/**
	 * @return a {@link List} of all {@link Person} in the database
	 */
	public List<Person> readPersonList() {
		List<Person> ret = Dao.getAllPeople();
		return ret;
	}
	
	/**
	 * @return the {@link Person} identified by 'id'
	 */
	@Override
	public Person readPerson(Long id) {
		Person p = Dao.getPersonById(id);
		return p;
	}

	/**
	 * Updates the {@link Person}.
	 * @return the updated person
	 */
	@Override
	public Person updatePerson(Person p) {
		p = Dao.savePerson(p);
		return p;
	}

	/**
	 * Creates a new {@link Person} 'p' in the database.
	 */
	@Override
	public Person createPerson(Person p) {
		p = Dao.savePerson(p);
		
		if (p.getCurrentHealth() != null){
			List<Measure> hp_list = p.getCurrentHealth();
			for (int i = 0; i < hp_list.size(); i++){
				Measure m = hp_list.get(i);
				m.setPerson(p);
				Dao.saveMeasure(m);
			}
		}		
		return p;
	}

	/**
	 * Deletes the person identified by 'id' from the database.
	 */
	@Override
	public void deletePerson(Long id) {
		Dao.deletePerson(id);	
	}

	/**
	 * @return the history of all measures with type 'measureType' of the person identified
	 */
	@Override
	public List<Measure> readPersonHistory(Long id, String measureType) {
		List<Measure> history = Dao.getHistory(id, measureType);
		return history;
	}

	/**
	 * @return a {@link List} of all measure types that are in use
	 */
	@Override
	public List<String> readMeasureTypes() {
		List<String> ret = Dao.getMeasureTypes();
		return ret;
	}

	/**
	 * @return the measure with measure id 'mid' of measuretype 'measureType' for the person identified by 'id'
	 */
	@Override
	public Measure readPersonMeasure(Long id, String measureType, Long mid) {
		Measure ret = Dao.getMeasureById(mid);
		
		if (ret == null)
			return null;
		
		if (ret.getMeasureType().equals(measureType) && ret.getPerson().getId().equals(id))
			return ret;
		
		return null;
	}

	/**
	 * Saves the {@link Measure} 'm' for the {@link Person} identified by 'id'.
	 */
	@Override
	public Measure savePersonMeasure(Long id, Measure m) {
		Person p =  Dao.getPersonById(id);
		if (p == null)
			throw new RuntimeException("No person with id=" + id);
		
		m = p.newMeasure(m);
		return Dao.saveMeasure(m);
	}

	/**
	 * Updates the {@link Measure} 'm' for the {@link Person} identified by 'id'.
	 */
	@Override
	public Measure updatePersonMeasure(Long id, Measure m) {	
		Person p =  Dao.getPersonById(id);
		if (p == null)
			throw new RuntimeException("No person with id=" + id);
		
		if (m.getPerson() == null)
			m.setPerson(p);
		
		return Dao.saveMeasure(m);
	}
}
