/*
 * Copyright (c) 2010-2022 William Bittle  http://www.dyn4j.org/
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted 
 * provided that the following conditions are met:
 * 
 *   * Redistributions of source code must retain the above copyright notice, this list of conditions 
 *     and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice, this list of conditions 
 *     and the following disclaimer in the documentation and/or other materials provided with the 
 *     distribution.
 *   * Neither the name of the copyright holder nor the names of its contributors may be used to endorse or 
 *     promote products derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR 
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND 
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR 
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL 
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, 
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER 
 * IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT 
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.dyn4j.dynamics.contact;

import org.dyn4j.collision.manifold.ManifoldPointId;
import org.dyn4j.geometry.DynVector2;

/**
 * Represents a contact of a {@link ContactConstraint}.
 * @author William Bittle
 * @version 5.0.1
 * @since 4.0.0
 */
public interface Contact {
	/**
	 * Returns the {@link ManifoldPointId} for this contact.
	 * <p>
	 * This identifies the contact to warm-starting.
	 * @return {@link ManifoldPointId}
	 * @since 3.1.2
	 */
	public ManifoldPointId getId();
	
	/**
	 * Returns the world space collision point.
	 * @return {@link DynVector2} the collision point in world space
	 */
	public DynVector2 getPoint();
	
	/**
	 * Returns the penetration depth of this point.
	 * @return double the penetration depth
	 */
	public double getDepth();
	
	/**
	 * Returns true if this contact will be ignored during
	 * contact solving.
	 * <p>
	 * A contact is ignored when it's part of a sensor {@link ContactConstraint}
	 * or if the {@link ContactConstraint} has been manually disabled.
	 * One last situation is when the contact is part of a {@link ContactConstraint}
	 * that has linearly dependent contacts - one of them will be solved
	 * and the other will be ignored.
	 * @return boolean
	 * @since 5.0.1
	 */
	public boolean isIgnored();
}
