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
	public <T extends Component> List<T> getAllComponentsOfType( Class<T> componentType)
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
	 * @param componentType
	 * @return					the keySet of the results of the search
	 */
	public <T extends Component> Set<Integer> getAllEntitiesPossessingComponent( Class<T> componentType)
	{
		//Make a temporary HashMap out of entries in componentDB; get only the values of the key "componentType"
		HashMap<Integer, ? extends Component> dbResult = componentDB.get(componentType);
		
		//check to make sure dbResult isn't null; if it is, return an empty HashSet
		if(dbResult == null)
			return new HashSet<Integer>();
		
		//return the HashSet of all entities containing componentType
		return dbResult.keySet();
	}
	
	/**
	 * Adds a component to an Entity.
	 * @param entity	The entity you're adding the component to
	 * @param component	The component you're adding
	 */
	public <T extends Component> void addComponent(int entity, T component)
	{
		//make a temporary HashMap... you get the idea
		//important to note that the put() that comes later actually affects componentDB, not just the temporary HashMap
		HashMap<Integer, ? extends Component> dbResult = componentDB.get(component.getClass());
		
		//if the component you're adding is the first of it's kind we need to create a key for it in componentDB
		if(dbResult == null)
		{
			dbResult = new HashMap<Integer, T>();
			componentDB.put(component.getClass(), dbResult);
		}
		
		//put the instance of the component into the DB, attached to the entity it's related to
		((HashMap<Integer, T>)dbResult).put(entity, component);
	}
	
	public <T extends Component> boolean hasComponent(int entity, Class<T> component)
	{
		HashMap<Integer, ? extends Component> dbResult = componentDB.get(component.getClass());
		if(dbResult == null) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Makes an Entity by adding it to the Entity List
	 * @return	the ID of the entity
	 */
	public int createEntity()
	{
		//make a temporary integer via generateNewEntityID()
		int newID = generateNewEntityID();
		
		if(newID<0)
		{
			//oh snap
			return 0;
		} else {
			Entities.add(newID);
			return newID;
		}
	}
	
	/**
	 * Removes an entity from the Entity List
	 * @param entity	the ID of the entity
	 */
	public void killEntity(int entity)
	{
		synchronized(this)
		{
			if(Entities.contains(entity)) {
				Entities.remove(entity);
			} else {
				throw new IllegalArgumentException("killEntity FAIL: "+entity+" does not exist");
			}
		}
	}
	
	/**
	 * Generates a new Entity ID. Since an entity is just a key in a database, we use integers as they're easy to keep track of.
	 * @return	a new/unused ID value
	 */
	public int generateNewEntityID()
	{
		synchronized(this)
		{
			if(nextEntityID<Integer.MAX_VALUE)
			{
				return nextEntityID++;
			} else {
				for(int i = 1; i<Integer.MAX_VALUE; i++)
				{
					if(!Entities.contains(i))
						return i;
				}
				throw new Error("ERROR: no available Entity IDs; too many entities!");
			}
		}
	}
	
}
