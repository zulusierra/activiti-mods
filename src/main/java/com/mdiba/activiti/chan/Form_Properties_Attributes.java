package com.mdiba.activiti.chan;

import java.util.List;

public class Form_Properties_Attributes {
	String formpropid;
	String name;
	Boolean writeable;
	String formproptype;
	String value;
	List<Enum_Values_Attributes> enum_values_attributes;
	
	public String getFormpropid() {
		return formpropid;
	}
	public void setFormpropid(String formpropid) {
		this.formpropid = formpropid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getWriteable() {
		return writeable;
	}
	public void setWriteable(Boolean writeable) {
		this.writeable = writeable;
	}
	public String getFormproptype() {
		return formproptype;
	}
	public void setFormproptype(String formproptype) {
		this.formproptype = formproptype;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public List<Enum_Values_Attributes> getEnum_values_attributes() {
		return enum_values_attributes;
	}
	public void setEnum_values_attributes(
			List<Enum_Values_Attributes> enum_values_attributes) {
		this.enum_values_attributes = enum_values_attributes;
	}
}
