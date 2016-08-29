package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@XmlRootElement(name="person")
@XmlAccessorType(XmlAccessType.FIELD)
public class Person implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@XmlElement(name="personId")
	private Long id;
	
	@XmlElement
	private String firstname;
	
	@XmlElement
	private String lastname;
	
	@XmlElement
	@Temporal(TemporalType.DATE)
	private Date birthdate;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinTable(name="person_currentHealth" ,
		joinColumns={@JoinColumn(name="id", referencedColumnName="id")},
		inverseJoinColumns={@JoinColumn(name="mid", referencedColumnName="mid")})
	@XmlElement
	private List<Measure> currentHealth;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinTable(name="person_historyHealth" ,
		joinColumns={@JoinColumn(name="id", referencedColumnName="id")},
		inverseJoinColumns={@JoinColumn(name="mid", referencedColumnName="mid")})
	@XmlTransient
	private List<Measure> healthHistory;
	
	public Long getId() { return id; }
	public String getFirstname() { return firstname; }
	public String getLastname() { return lastname; }
	public Date getBirthdate() { return birthdate; }
	public List<Measure> getCurrentHealth() { return currentHealth; }
	public List<Measure> getHealthHistory() { return healthHistory;	}
	
	public void setId(Long id) { this.id = id; }
	public void setFirstname(String firstname) { this.firstname = firstname; }
	public void setLastname(String lastname) { this.lastname = lastname; }
	public void setBirthdate(Date birthdate) { this.birthdate = birthdate; }
	public void setCurrentHealth(List<Measure> currentHealth) { this.currentHealth = currentHealth;	}
	public void setHealthHistory(List<Measure> healthHistory) { this.healthHistory = healthHistory;	}
	
	public Measure newMeasure(Measure measure){
		archiveMeasure(measure.getMeasureType());
		measure.setPerson(this);
		currentHealth.add(measure);
		return measure;
	}
	
	private void archiveMeasure(String measuretype) {		
		for (int i = 0; i < currentHealth.size(); i++){
			Measure m = currentHealth.get(i);			
			if (m.getMeasureType().equals(measuretype)) {
				currentHealth.remove(m);
				healthHistory.add(m);
				break;
			}
		}
	}
}
