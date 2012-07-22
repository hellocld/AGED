/**
 * 
 */
package com.hellocld.AGED.basicComponents;

import java.util.HashSet;
import java.util.Set;

import com.hellocld.AGED.core.Component;
import com.hellocld.AGED.util.Pair;

/**
 * This component works with the EntityGroup component to create
 * pairs of groups that are checked for collisions between one another
 * @author CLD
 *
 */
public class CollideSet implements Component {
	//a HashSet that will store each pair of groups to be checked against one another
	public Set<Pair<String, String>> pairs = new HashSet<Pair<String, String>>();
	
	public void addCheck(String group1, String group2) {
		pairs.add(new Pair<String, String>(group1, group2));
	}
	
	public void removeCheck(String group1, String group2) {
		if(pairs.contains(new Pair<String, String>(group1, group2))) {
			pairs.remove(new Pair<String, String>(group1, group2));
		} else if (pairs.contains(new Pair<String, String>(group2, group1))) {
			pairs.remove(new Pair<String, String>(group2, group1));
		} else {
			throw new IllegalArgumentException("removeCheck failed: Pair("+group1+", "+group2+") does not exist");
		}
	}
}
