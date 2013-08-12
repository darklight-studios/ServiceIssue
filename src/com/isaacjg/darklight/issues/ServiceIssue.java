package com.isaacjg.darklight.issues;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.ijg.darklight.sdk.core.Issue;

/*
 * ServiceIssue - An Issue for Darklight Nova Core
 * Copyright © 2013 Isaac Grant
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * ServiceIssue is an Issue for Darklight Nova Core that checks the
 * status of a given service
 * 
 * @author Isaac Grant
 */

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
