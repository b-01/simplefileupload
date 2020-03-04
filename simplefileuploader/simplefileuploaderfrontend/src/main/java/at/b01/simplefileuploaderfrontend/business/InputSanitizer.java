package at.b01.simplefileuploaderfrontend.business;

import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.Policy;
import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InputSanitizer {

	private static final Logger logger = LoggerFactory
			.getLogger(InputSanitizer.class);
	private static InputSanitizer instance = new InputSanitizer();
	private Policy policy;
	private AntiSamy antiSamy;

	private InputSanitizer() {
		try {
			policy = Policy
					.getInstance(Thread
							.currentThread()
							.getContextClassLoader()
							.getResourceAsStream(
									"at/b01/simplefileuploaderfrontend/config/antisamy.xml"));
			antiSamy = new AntiSamy(policy);
		} catch (PolicyException e) {
			policy = null;
			logger.error("Could not read Policy file!", e);
		}
	}

	public static InputSanitizer getInstance() {
		return instance;
	}

	public String sanitize(String dirtyInput) {
		try {
			CleanResults result = antiSamy.scan(dirtyInput);
			return result.getCleanHTML();
		} catch (ScanException e) {
			logger.error("Scan error!", e);
		} catch (PolicyException e) {
			logger.error("Policy file error!", e);
		}
		return null;
	}
}
