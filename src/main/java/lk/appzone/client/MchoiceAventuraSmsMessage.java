/**
	Copyright [2010] [Arunoda Susiripala]
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
		http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
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
