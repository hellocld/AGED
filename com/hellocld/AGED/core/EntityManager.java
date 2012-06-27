/**
 * 
 */
package com.hellocld.AGED.core;

import java.util.List;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * So this is the core Entity Manager, half of the heart of the whole system (the other half being the System Manager).
 * This class handles the creating, adding and removing of entities and their components, as well as searching through
 * the entity database for specific entities or components.
 * 
 * Based HEAVILY on/stolen from http://entity-systems.wikidot.com/rdbms-with-code-in-systems
 * @author CLD
 *
 */
public class EntityManager {
	//a ticker for Entity IDs
	int nextEntityID = 1;
	//a list of ALL the entities
	List<Integer> Entities;
	/*
	 * The big fat HashMap database containing the entities and components.
	 * - The key is the type of Component (this keeps things tidy by only allowing components to be entered once, as well as preventing one from
	 *   duplicating components in entities; it also makes collecting all entities containing a certain component much easier)
	 * - The value is another HashMap, which contains:
	 * 		-key: The entity ID
	 * 		-value: the instance of the component (so you can make the values of the component unique to that particular entity)
	 */
	HashMap<Class, HashMap<Integer, ? extends Component>> componentDB;
	
	//Initialize the EntityManager
	public EntityManager()
	{
		Entities = new LinkedList<Integer>();
		componentDB = new HashMap<Class, HashMap<Integer, ? extends Component>>();
	}
	
	/**
	 * This returns the instance of a particular component for a specific entity.
	 * If the component or entity do not exist, it throws an error
	 * @param entity			The entity you are searching
	 * @param componentType		The component you're searching for (by type)
	 * @return					Returns the component if successful
	 */
	public <T extends Component> T getComponent(int entity, Class<T> componentType)
	{
		//Make a temporary HashMap out of entries in componentDB; get only the values of the key "componentType"
		HashMap<Integer, ? extends Component> dbResult = componentDB.get(componentType);
		
		//make sure what you're looking for actually exists
		if(dbResult == null)
			throw new IllegalArgumentException("getComponent FAIL: there are no entities with a Component of class: "+componentType);
		T result = (T) dbResult.get(entity);
		if(result == null)
			throw new IllegalArgumentException("getComponent FAIL: "+entity+" does not possess Component of class\nmissing:"+componentType);
		return result;
	}
	
	/**
	 * Searches the database for all components of a specific type		
	 * @param componentType	The type of component you're searching for
	 * @return				A list of all the components of the searched type
	 */
	public <T extends Component> List<T> getAllComponentsOfType(Class<T> componentType)
	{
		//Make a temporary HashMap out of entries in componentDB; get only the values of the key "componentType"
		HashMap<Integer, ? extends Component> dbResult = componentDB.get(componentType);
		
		//double-check to make sure componentType actually exists; if not, return an empty list
		if(dbResult == null)
			return new LinkedList<T>();
		
		//return the list of all entities containing componentType
		return (List<T>) dbResult.values();
	}
	
	/**
	 * Returns a set of all entities containing "componentType"
	 * 
	 * @param <T>
	 * @param componentType
	 * @return					the keySet of the results of the search
	 */
	public <T extends Component> Set<Integer> getAllEntitiesPossessingComponent(Class<T> componentType)
	{
		//Make a temporary HashMap out of entries in componentDB; get only the values of the key "componentType"
		HashMap<Integer, ? extends Component> dbResult = componentDB.get(componentType);
		
		//check to make sure dbResult isn't null; if it is, return an empty HashSet
		if(dbResult == null)
			return new HashSet<Integer>();
		
		//return the HashSet of all entities containing componentType
		return dbResult.keySet();
	}
}
