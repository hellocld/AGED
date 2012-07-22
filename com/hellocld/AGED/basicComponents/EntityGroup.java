/**
 * 
 */
package com.hellocld.AGED.basicComponents;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.hellocld.AGED.core.Component;

/**
 * Contains a HashMap<String, HashSet> of which each key is a collision group.
 * @author CLD
 *
 */
public class EntityGroup implements Component {
	public HashMap<String, Set<Integer>> groups = new HashMap<String, Set<Integer>>();
	
	//just so it isn't completely null when systems check it, let's add a base group for anything and everything
	public EntityGroup() {
		groups.put("", new HashSet<Integer>());
	}
	
	//make a new group with an empty HashSet attached to it
	/**
	 * This creates a new entity group. If the group already exists, it doesn't do anything
	 * @param group	The name of the group
	 */
	public void createGroup(String group) {
		//if groups doesn't already have a key labelled "group", add it to the map with a new empty Set attached
		if(!groups.containsKey(group)) {
			groups.put(group, new HashSet<Integer>());
		}
	}
	
	/**
	 * Add an entity to a group.
	 * @param group		The name of the group
	 * @param entity	The entity being added to the group
	 */
	public void addToGroup(String group, int entity) {
		if(groups.containsKey(group)) {
			if(groups.get(group).contains(entity)) {
				throw new IllegalArgumentException("addToGroup failed: CollisionGroup "+group+" already contains entity "+entity);
			} else { 
				groups.get(group).add(entity);
			}
		} else {
			throw new IllegalArgumentException("addToGroup failed: CollisionGroup "+group+" does not exist");
		}
	}
	
	/**
	 * Removes an entity from a group.
	 * @param group		The name of the group
	 * @param entity	The entity being removed from the group
	 */
	public void removeFromGroup(String group, int entity) {
		if(groups.containsKey(group)) {
			if(groups.get(group).contains(entity)) {
				groups.get(group).remove(entity);
			} else { 
				throw new IllegalArgumentException("removeFromGroup failed: CollisionGroup "+group+" does not contain entity "+entity);
			}
		} else {
			throw new IllegalArgumentException("removeFromGroup failed: CollisionGroup "+group+" does not exist");
		}
	}
	
	public Set<Integer> getGroup(String group) {
		if(groups.containsKey(group)) {
			return groups.get(group);
		} else {
			throw new IllegalArgumentException("getGroup failed: group "+group+" does not exist");
		}
	}
}
