/**
 * (c) DICOS GmbH, 2023
 *
 * $Id$
 */

package de.dicos.springboot.repairservice.restful;

import java.io.IOException;
import java.util.List;

import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.apache.cxf.jaxrs.provider.BinaryDataProvider;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import de.dicos.springboot.repairservice.gen.api.DefaultApi;
import de.dicos.springboot.repairservice.gen.model.RepairRequest;

/**
 *
 * @author jtibke
 */
@Component
public class AdministrationService
{
	// /////////////////////////////////////////////////////////
	// Class Members
	// /////////////////////////////////////////////////////////

	private DefaultApi api;

	// /////////////////////////////////////////////////////////
	// Constructors
	// /////////////////////////////////////////////////////////

	public AdministrationService() throws IOException
	{
		JAXRSClientFactoryBean clientFactoryBean = new JAXRSClientFactoryBean();
		clientFactoryBean.setAddress("https://123.123.123.123:1234");
		clientFactoryBean.setProviders(List.of(new BinaryDataProvider<Object>(), new JacksonJsonProvider()));
		clientFactoryBean.setServiceClass(DefaultApi.class);
		api = DefaultApi.class.cast(clientFactoryBean.create());
	}

	// /////////////////////////////////////////////////////////
	// Methods
	// /////////////////////////////////////////////////////////

	public void postRepairRequest(RepairRequest repairRequest)
	{
		api.repairRequestPost(repairRequest);
	}

	// /////////////////////////////////////////////////////////
	// Inner Classes
	// /////////////////////////////////////////////////////////


}
