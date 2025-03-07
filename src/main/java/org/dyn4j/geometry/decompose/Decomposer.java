/*
 * Copyright (c) 2010-2021 William Bittle  http://www.dyn4j.org/
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
package org.dyn4j.geometry.decompose;

import java.util.List;

import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.DynVector2;

/**
 * Represents an algorithm to decompose a given polygon (as a list of points) into {@link Convex} pieces.
 * @author William Bittle
 * @version 4.2.0
 * @since 2.2.0
 */
public interface Decomposer {
	/**
	 * Performs the decomposition on the given polygon returning a list of {@link Convex} shapes.
	 * @param points the polygon vertices
	 * @return List&lt;{@link Convex}&gt;
	 * @throws NullPointerException if points is null or contains null points
	 * @throws IllegalArgumentException if points contains less than 4 points
	 */
	public List<Convex> decompose(DynVector2... points);
	
	/**
	 * Performs the decomposition on the given polygon returning a list of {@link Convex} shapes.
	 * @param points the polygon vertices
	 * @return List&lt;{@link Convex}&gt;
	 * @throws NullPointerException if points is null or contains null points
	 * @throws IllegalArgumentException if points contains less than 4 points
	 * @since 4.2.0
	 */
	public List<Convex> decompose(List<DynVector2> points);
}
