package lk.appzone.client;

public class MchoiceAventuraSmsMessage {

	private String correlator;
	private String address;
	private String message;
	private String version;

	public MchoiceAventuraSmsMessage(String correlator, String address, String message, String version) {
		super();
		this.correlator = correlator;
		this.address = address;
		this.message = message;
		this.version = version;
	}

	public String getCorrelator() {
		return correlator;
	}

	public void setCorrelator(String correlator) {
		this.correlator = correlator;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String toString() {
		return String.format("%s : %s : %s : %s", correlator, address, message, version);
	}

}
