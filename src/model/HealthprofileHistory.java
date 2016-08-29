package model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Wrapper class for the history of the healthprofile. <p>
 * Example:
 * <pre> 
 *  &lt;healthProfile-history>
 *      &lt;measure>
 *         ...
 *      &lt;/measure>
 *       &lt;measure>
 *         ...
 *      &lt;/measure>
 * &lt;/healthProfile-history>
 * </pre>
 */
@XmlRootElement(name="healthProfile-history")
@XmlAccessorType(XmlAccessType.FIELD)
public class HealthprofileHistory {
	
	@XmlElement
	private List<Measure> measures;
	
	public List<Measure> getMeasures() { return measures; }
	public void setMeasures(List<Measure> measures) { this.measures = measures; }
}
