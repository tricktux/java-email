package com.molina.emails;

import java.io.File;
import org.ini4j.*;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

class EmailBuilder {
	final static Log logger = LogFactory.getLog(EmailBuilder.class);

	public boolean config(String configFile) {
		if (configFile.isEmpty()) {
			logger.error("Invalid function input");
			return false;
		}

		try {
			Wini ini = new Wini(new FileReader(configFile));
		} catch(Exception e) {
			return false;
		}
		return true;
	}

}
