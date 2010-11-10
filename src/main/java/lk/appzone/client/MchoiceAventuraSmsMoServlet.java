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

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class MchoiceAventuraSmsMoServlet extends HttpServlet {

	HttpServletRequest request;
	HttpServletResponse response;
	
	private final static Logger logger = LoggerFactory.getLogger(MchoiceAventuraSmsMoServlet.class);
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		request = req;
		response = resp;
		
		String correlator = req.getParameter("correlator");
		String version = req.getParameter("version");
		String address = req.getParameter("address");
		String message = req.getParameter("message");
		
		logger.debug("getting parameters: {} :{} :" + address + " :" + message, correlator, version);
		
		if(isEmpty(correlator) || isEmpty(version) || isEmpty(address) || isEmpty(message)) {
			logger.debug("some required parameters are not provided");
			resp.getWriter().println("Not Adequete Parameters");
		} else {
			
			MchoiceAventuraSmsMessage sms = new MchoiceAventuraSmsMessage(correlator, address, message, version);
			logger.debug("transferring message into processing: {}", sms);
			onMessage(sms);
		}
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}
	
	private boolean isEmpty(String content) {
		
		if(content == null || content.trim().isEmpty()) {
			return true;
		} else {
			return false;
		}
	}



	protected abstract void onMessage(MchoiceAventuraSmsMessage sms);
}
