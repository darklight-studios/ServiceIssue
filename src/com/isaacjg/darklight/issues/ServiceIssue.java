package com.isaacjg.darklight.issues;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.ijg.darklight.sdk.core.Issue;

public class ServiceIssue extends Issue {

	public static final int ENABLED = 0;
	public static final int DISABLED = 1;
	
	private String service;
	private int status;
	
	public ServiceIssue() {
		super("Service Issue", "Service \"[servicename]\" has been [status]");
	}

	public void setInfo(String service, int status) {
		this.service = service;
		this.status = status;
		if (status == ENABLED) {
			setDescription(getDescription().replace("[servicename]", service).replace("[status]", "enabled"));
		} else if (status == DISABLED) {
			setDescription(getDescription().replace("[servicename]", service).replace("[status]", "disabled"));
		}
	}
	
	@Override
	public boolean isFixed() {
		try {
			Process p = Runtime.getRuntime().exec("cmd.exe /c sc query " + service);
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				if (status == DISABLED) {
					if (line.contains("1060") || line.contains("1058") || line.contains("1048") || line.contains("STOPPED")) {
						return true;
					}
				} else if (status == ENABLED) {
					if (line.contains("RUNNING")) {
						return true;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}
