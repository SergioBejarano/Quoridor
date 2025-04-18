package domain;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Square implements Serializable{
	
	private int[] location;
	private String name;
	
	/**
	 * Creates a square given location.
	 * @param location It is a vector.
	 */
	public Square(int[] location, String name)	{
		this.location = location;
		this.name = name;
	}
	 
	
	/** 
	 * Returns the name of this.
	 * @param
	 * @return name
	 */
	public String getName()	{
		return name;
	} 
	
	/**
	 * Returns the location of this.
	 * @param
	 * @return location 
	 */
	public int[] getLocation()	{
		return location;
	}
	
	
	/**
	 * Defines a behavior for the pawn 
	 * 
	 * @param pawn
	 */
	public abstract void act();
	
	
	
}
