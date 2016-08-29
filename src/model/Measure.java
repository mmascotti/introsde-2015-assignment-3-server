package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@XmlRootElement(name="measure")
@XmlAccessorType(XmlAccessType.FIELD)
public class Measure implements Serializable, Cloneable{
	
	public final static String STRING_TYPE = "string";
	public final static String INTEGER_TYPE = "integer";
	public final static String REAL_TYPE = "real";
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@XmlElement
	private Long mid;
	
	@Temporal(TemporalType.TIMESTAMP)
	@XmlElement
	private Date dateRegistered;
	
	@XmlElement
	private String measureType;
	
	@XmlElement
	private String measureValue;
	
	@XmlElement
	private String measureValueType;
		
	@XmlTransient
	private Person person;
	
	public Long getMid() { return mid; }
	public Date getDateRegistered() { return dateRegistered; }
	public String getMeasureType() { return measureType; }
	public String getMeasureValue() { return measureValue; }
	public String getMeasureValueType() { return measureValueType; }
	public Person getPerson() { return person; }
	
	public void setMid(Long mid) { this.mid = mid; }
	public void setDateRegistered(Date dateRegistered) { this.dateRegistered = dateRegistered; }
	public void setMeasureType(String measureType) { this.measureType = measureType; }
	public void setMeasureValue(String measureValue) { this.measureValue = measureValue; }
	public void setMeasureValueType(String measureValueType) { this.measureValueType = measureValueType; }
	public void setPerson(Person person) { this.person = person; }
	
	@Override
	protected Measure clone()  {
		Measure ret = new Measure();
		ret.setDateRegistered(this.dateRegistered);
		ret.setMeasureType(this.measureType);
		ret.setMeasureValue(this.measureValue);
		ret.setMeasureValueType(this.measureValueType);
		ret.setMid(this.mid);
		return ret;
	}
}
