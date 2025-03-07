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
package org.dyn4j.collision.narrowphase;

import java.util.List;
import java.util.PriorityQueue;

import org.dyn4j.geometry.DynVector2;

/**
 * Represents a simplex that is progressively expanded by splitting
 * the closest edge to the origin by adding new points.
 * <p>
 * This class is used with the {@link Epa} class to maintain the state
 * of the algorithm.
 * <p>
 * Given the way the simplex is expanded, the winding can be computed initially
 * and will never change.
 * @author William Bittle 
 * @version 4.1.0
 * @since 3.2.0
 */
final class ExpandingSimplex {
	/** The winding direction of the simplex */
	private final int winding;
	
	/** The priority queue of simplex edges */
	private final PriorityQueue<ExpandingSimplexEdge> queue;
	
	/**
	 * Minimal constructor.
	 * @param simplex the starting simplex from GJK
	 */
	public ExpandingSimplex(List<DynVector2> simplex) {
		int size = simplex.size();		

		// compute the winding
		this.winding = this.getSimplexWinding(simplex);
		// build the initial edge queue
		this.queue = new PriorityQueue<ExpandingSimplexEdge>();
		
		for (int i = 0; i < size; i++) {
			// compute j
			int j = i + 1 == size ? 0 : i + 1;
			// get the points that make up the current edge
			DynVector2 a = simplex.get(i);
			DynVector2 b = simplex.get(j);
			// create the edge
			this.queue.add(new ExpandingSimplexEdge(a, b, this.winding));
		}
	}
	
	/**
	 * Returns the winding of the given simplex.
	 * <p>
	 * Returns -1 if the winding is Clockwise.<br>
	 * Returns 1 if the winding is Counter-Clockwise.
	 * <p>
	 * This method will continue checking all edges until
	 * an edge is found whose cross product is less than 
	 * or greater than zero.
	 * <p>
	 * This is used to get the correct edge normal of
	 * the simplex.
	 * @param simplex the simplex
	 * @return int the winding
	 */
	private final int getSimplexWinding(List<DynVector2> simplex) {
		int size = simplex.size();
		for (int i = 0; i < size; i++) {
			int j = i + 1 == size ? 0 : i + 1;
			DynVector2 a = simplex.get(i);
			DynVector2 b = simplex.get(j);
			if (a.cross(b) > 0) {
				return 1;
			} else if (a.cross(b) < 0) {
				return -1;
			}
		}
		return 0;
	}
	
	/**
	 * Returns the winding of the simplex. Returns 0 in the case of a
	 * degenerate simplex, -1 in the clockwise winding case, 1 in the
	 * counter clockwise winding case.
	 * @return int
	 * @since 4.1.0
	 */
	public final int getWinding() {
		return this.winding;
	}
	
	/**
	 * Returns the edge on the simplex that is closest to the origin.
	 * @return {@link ExpandingSimplexEdge} the closest edge to the origin
	 */
	public final ExpandingSimplexEdge getClosestEdge() {
		return this.queue.peek(); // O(1)
	}
	
	/**
	 * Expands the simplex (the closest edge) by the given point.
	 * <p>
	 * Removes the closest edge to the origin and adds
	 * two new edges using the given point and the removed
	 * edge's vertices.
	 * @param point the new point
	 */
	public final void expand(DynVector2 point) {
		// remove the edge we are splitting
		ExpandingSimplexEdge edge = this.queue.poll(); // O(log n)
		// create two new edges
		ExpandingSimplexEdge edge1 = new ExpandingSimplexEdge(edge.point1, point, this.winding);
		ExpandingSimplexEdge edge2 = new ExpandingSimplexEdge(point, edge.point2, this.winding);
		this.queue.add(edge1); // O(log n)
		this.queue.add(edge2); // O(log n)
	}
	
	/**
	 * Returns the number of edges in the simplex.
	 * @since 4.1.0
	 * @return int
	 */
	public final int size() {
		return this.queue.size();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		int i = 0;
		for (ExpandingSimplexEdge edge : this.queue) {
			if (i != 0) sb.append(",");
			sb.append(edge.point1)
			  .append("=")
			  .append(edge.distance);
			i++;
		}
		sb.append("]");
		return sb.toString();
	}
}
