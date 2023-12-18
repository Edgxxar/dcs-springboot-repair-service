/**
 * (c) DICOS GmbH, 2023
 *
 * $Id$
 */

package de.dicos.springboot.repairservice.restful.api;

import de.dicos.springboot.repairservice.gen.model.RepairEstimate;
import de.dicos.springboot.repairservice.gen.model.RepairRequest;
import de.dicos.springboot.repairservice.restful.AdministrationService;
import de.dicos.springboot.repairservice.restful.RepairEstimatorService;
import de.dicos.springboot.repairservice.restful.exception.NoEstimateException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * @author jtibke
 */
@NoArgsConstructor
@Slf4j
@RestController
@RequestMapping(path = "/rest/repair-request")
public class RepairRequestController
{
	// /////////////////////////////////////////////////////////
	// Class Members
	// /////////////////////////////////////////////////////////

	@Autowired
	private RepairEstimatorService repairEstimatorService;

	@Autowired
	private AdministrationService administrationService;

	// /////////////////////////////////////////////////////////
	// Constructors
	// /////////////////////////////////////////////////////////


	// /////////////////////////////////////////////////////////
	// Methods
	// /////////////////////////////////////////////////////////

	@GetMapping(value = "/estimate", produces = MediaType.APPLICATION_JSON_VALUE)
	public RepairEstimate getRepairEstimate(@RequestParam String carModel, @RequestParam String[] actions)
	{
		if (actions.length == 0)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "at least one repair action required");
		try {
			return repairEstimatorService.getEstimate(carModel, actions);
		} catch (NoEstimateException e) {
			// return 400 if estimate not found
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public void sendRepairRequest(@ParameterObject RepairRequest request)
		throws Exception
	{
		log.info("sending new repair request to administration");

		administrationService.postRepairRequest(request);
	}

	// /////////////////////////////////////////////////////////
	// Inner Classes
	// /////////////////////////////////////////////////////////


}
