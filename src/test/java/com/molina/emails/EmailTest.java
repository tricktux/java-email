/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.molina.emails;

import org.junit.Test;
import static org.junit.Assert.*;

public class EmailTest {
	private static String PATH_TO_CONFIG = "../../email_config.ini";
  @Test
  public void testBuildingEmail() {
		Email email = EmailBuilder(PATH_TO_CONFIG);
		assertTrue(email != null);
  }
}
