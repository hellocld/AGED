/**
 * 
 */
package com.hellocld.AGED.entity;

import com.hellocld.AGED.component.BasicComponent;

/**
 * @author CLD
 *
 */
public interface IEntity {
	
	void update();
	
	void add(BasicComponent component);
	
	void remove();
	
	void die();
}
