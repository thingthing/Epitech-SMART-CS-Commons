package eip.smart.cscommons;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by vincent buresi on 10/2/14.
 */
public class CSCommons {
	private static class JSONTestObject {
		public String	jsonVersion	= CSCommons.JSON_VERSION;
		public String	jsonJarName	= "jackson-core-" + CSCommons.JSON_VERSION + ".jar";

		public JSONTestObject() {}
	}

	public static String getJacksonVersion() {
		return CSCommons.JSON_VERSION;
	}

	public static String getVersion() {
		return CSCommons.PROJECT_VERSION;
	}

	public static String testJacksonVersion() {
		ObjectMapper mapper = new ObjectMapper();
		JSONTestObject inputObject = new JSONTestObject();

		String jsonOutput = null;
		try {

			jsonOutput = mapper.writeValueAsString(inputObject);

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		String jsonVersion = null;
		String jsonJarName = null;

		try {

			JSONTestObject outputObject = mapper.readValue(jsonOutput, JSONTestObject.class);
			jsonVersion = outputObject.jsonVersion;
			jsonJarName = outputObject.jsonJarName;
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (jsonVersion != null && jsonJarName != null)
			return "JSON test :\nJSON String : " + jsonOutput + "\nJSON object version : " + jsonVersion + " jarName : " + jsonJarName;
		return "JSON test failure ! Please check your project settings.";
	}

	public final static String	PROJECT_VERSION	= "0.1b";

	public final static String	JSON_VERSION	= "2.4.3";
}
