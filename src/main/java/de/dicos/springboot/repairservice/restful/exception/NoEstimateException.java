package de.dicos.springboot.repairservice.restful.exception;

public class NoEstimateException extends RuntimeException
{
	public NoEstimateException(String model, String action)
	{
		super("No price estimate for car model '" + model + "' and action '" + action + "' available");
	}
}
