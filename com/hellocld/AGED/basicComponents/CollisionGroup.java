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
public class CollisionGroup implements Component {
	public HashMap<String, Set<Integer>> groups = new HashMap<String, Set<Integer>>();
	
	//just so it isn't completely null when systems check it, let's add a base group all objects will go in by default
	public CollisionGroup() {
		groups.put("", new HashSet<Integer>());
	}
	
	public void createGroup(String group) {
		groups.put(group, null);
	}
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
	
	public void removeFromGroup(String group, int entity) {
		if(groups.containsKey(group)) {
			if(groups.get(group).contains(entity)) {
				groups.get(group).remove(entity);
			} else { 
				throw new IllegalArgumentException("removeFromGroup failed: CollisionGroup "+group+" does not contain entity "+entity);
			}
		} else {
			throw new IllegalArgumentException("remove FromGroup failed: CollisionGroup "+group+" does not exist");
		}

	}
}
