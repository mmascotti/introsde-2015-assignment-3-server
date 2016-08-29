package introsde.assignment.soap;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import model.Measure;
import model.Person;

@WebService
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL)
public interface People {
	
	@WebMethod(operationName="readPersonList")
	@WebResult(name="personlist")
	List<Person> readPersonList();

	@WebMethod(operationName="readPerson")
	@WebResult(name="person_result") 
	Person readPerson(@WebParam(name="personId") Long id);
	
	@WebMethod(operationName="updatePerson")
	@WebResult(name="person_result") 
	Person updatePerson(@WebParam(name="p") Person p);

	@WebMethod(operationName="createPerson")
	@WebResult(name="person_result") 
	Person createPerson(@WebParam(name="p") Person p);

	@WebMethod(operationName="deletePerson")
	void deletePerson(@WebParam(name="personId") Long id);

	@WebMethod(operationName="readPersonHistory")
	@WebResult(name="measurelist") 
	List<Measure> readPersonHistory(
			@WebParam(name="personId") Long id, 
			@WebParam(name="measuretye") String measureType);

	@WebMethod(operationName="readMeasureTypes")
	@WebResult(name="measuretypelist") 
	List<String> readMeasureTypes();

	@WebMethod(operationName="readPersonMeasure")
	@WebResult(name="measure_result")
	Measure readPersonMeasure(
			@WebParam(name="personId") Long id, 
			@WebParam(name="measuretype") String measureType, 
			@WebParam(name="mid")Long mid);

	@WebMethod(operationName="savePersonMeasure")
	@WebResult(name="measure_result")
	Measure savePersonMeasure(
			@WebParam(name="personId") Long id, 
			@WebParam(name="measure_param") Measure m);

	@WebMethod(operationName="updatePersonMeasure")
	@WebResult(name="measure_result") 
	Measure updatePersonMeasure(
			@WebParam(name="personId") Long id, 
			@WebParam(name="m") Measure m);
}
