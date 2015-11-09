package com.artemisa.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "configuration")
public class Configuration extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = -6904639834390041541L;
	
	
	private String configurationName;
	
	
	private String configurationValue;
        
        @Column(name = "configuration_name")
	public String getConfigurationName() {
		return configurationName;
	}

	public void setConfigurationName(String configurationName) {
		this.configurationName = configurationName;
	}

        @Column(name = "configuration_value")
	public String getConfigurationValue() {
		return configurationValue;
	}

	public void setConfigurationValue(String configurationValue) {
		this.configurationValue = configurationValue;
	}	
}
