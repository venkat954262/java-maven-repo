package com.arbiva.apfgc.invoice.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

import org.apache.commons.lang.StringUtils;

public class ReflectionExample {

	@SuppressWarnings("unchecked")
	public static <V> V get(Object object, String fieldName) {
		Class<?> clazz = object.getClass();
		while (clazz != null) {
			try {
				Field field = clazz.getDeclaredField(fieldName);
				field.setAccessible(true);
				return (V) field.get(object);
			} catch (NoSuchFieldException e) {
				clazz = clazz.getSuperclass();
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}
		}
		return null;
	}

	public static <T> T parse(Object source, Class<T> targetClazz) 
			throws InstantiationException, IllegalAccessException {
		Class<?> sourceClazz = source.getClass();
		Source tagetAnn = targetClazz.getAnnotation(Source.class);
		T instance = targetClazz.newInstance();
		if(sourceClazz != null && sourceClazz.getSimpleName().equalsIgnoreCase(tagetAnn.value())) {
			try {
				Field[] fields = targetClazz.getDeclaredFields();
				for (Field field : fields) {
					field.setAccessible(true);
					Mapper mapper = null;//field.getAnnotationsByType(Mapper.class)[0];
					Field sourceField = sourceClazz.getDeclaredField(mapper.target());
					sourceField.setAccessible(true);
					field.set(instance, sourceField.get(source));
				}
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}
		}
		return instance;
	}

	public static void get(Object object) throws NoSuchFieldException {
		Class<?> clazz = object.getClass();
		if (clazz != null) {
			try {
				Field[] fields = clazz.getDeclaredFields();
				for (Field field : fields) {
					field.setAccessible(true);
					System.out.println(field.getType() + " : "
							+ field.getName() + " : " + field.get(object));
				}
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}
		}
	}

	public static void main(String[] args) throws NoSuchFieldException, InstantiationException, IllegalAccessException {
		Employee employee = new Employee("Emp102", "Srinivas", "Aluri");
		//get(employee);
		EmployeeDTO employeeDTO = parse(employee, EmployeeDTO.class);
		System.out.println(employeeDTO);
	}

}

class Employee {

	public Employee() {
	}

	public Employee(String empId, String fName, String lName) {
		this.empId = empId;
		this.fName = fName;
		this.lName = lName;
	}

	private String empId;

	private String fName;

	private String lName;

	/**
	 * @return the empId
	 */
	public String getEmpId() {
		return empId;
	}

	/**
	 * @param empId
	 *            the empId to set
	 */
	public void setEmpId(String empId) {
		this.empId = empId;
	}

	/**
	 * @return the fName
	 */
	public String getfName() {
		return fName;
	}

	/**
	 * @param fName
	 *            the fName to set
	 */
	public void setfName(String fName) {
		this.fName = fName;
	}

	/**
	 * @return the lName
	 */
	public String getlName() {
		return lName;
	}

	/**
	 * @param lName
	 *            the lName to set
	 */
	public void setlName(String lName) {
		this.lName = lName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Employee [empId=" + empId + ", fName=" + fName + ", lName="
				+ lName + "]";
	}

}

@Source("Employee")
class EmployeeDTO {

	public EmployeeDTO() {
	}

	public EmployeeDTO(String employeeId, String firstName, String lastName) {
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@Mapper(target = "empId")
	private String employeeId;

	@Mapper(target = "fName")
	private String firstName;

	@Mapper(target = "lName")
	private String lastName;

	/**
	 * @return the employeeId
	 */
	public String getEmployeeId() {
		return employeeId;
	}

	/**
	 * @param employeeId
	 *            the employeeId to set
	 */
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EmployeeDTO [employeeId=" + employeeId + ", firstName="
				+ firstName + ", lastName=" + lastName + "]";
	}

}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface Source {
	String value() default StringUtils.EMPTY;
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Mapper {

	public enum Type {
		LIST, SET, CUSTOM, NORMAL
	}

	Type type() default Type.NORMAL;

	String target() default StringUtils.EMPTY;

}
