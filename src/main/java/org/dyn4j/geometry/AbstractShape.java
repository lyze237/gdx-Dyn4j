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
package org.dyn4j.geometry;

import org.dyn4j.DataContainer;

/**
 * Base implementation of the {@link Shape} interface.
 * @author William Bittle
 * @version 4.2.1
 * @since 1.0.0
 */
public abstract class AbstractShape implements Shape, Transformable, DataContainer {
	/** Identity Transform instance */
	private static final Transform IDENTITY = new Transform();
	
	/** The center of this {@link Shape} */
	protected DynVector2 center;
	
	/** The maximum radius */
	protected double radius;
	
	/** Custom user data object */
	protected Object userData;
	
	/**
	 * Minimal constructor.
	 * @param radius the rotation radius; must be greater than zero
	 * @throws IllegalArgumentException if radius is zero or less
	 */
	protected AbstractShape(double radius) {
		this(new DynVector2(), radius);
	}
	
	/**
	 * Full constructor.
	 * @param center the center
	 * @param radius the rotation radius; must be greater than zero
	 * @throws IllegalArgumentException if radius is zero or less
	 * @throws NullPointerException if center is null
	 */
	protected AbstractShape(DynVector2 center, double radius) {
		this.center = center;
		this.radius = radius;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("HashCode=").append(this.hashCode())
		.append("|Center=").append(this.center)
		.append("|Radius=").append(this.radius);
		return sb.toString();
	}
	
	/* (non-Javadoc)
	 * @see org.dyn4j.geometry.Shape#getCenter()
	 */
	@Override
	public DynVector2 getCenter() {
		return this.center;
	}
	
	/* (non-Javadoc)
	 * @see org.dyn4j.geometry.Shape#getRadius()
	 */
	@Override
	public double getRadius() {
		return this.radius;
	}
	
	/* (non-Javadoc)
	 * @see org.dyn4j.DataContainer#getUserData()
	 */
	@Override
	public Object getUserData() {
		return this.userData;
	}
	
	/* (non-Javadoc)
	 * @see org.dyn4j.DataContainer#setUserData(java.lang.Object)
	 */
	@Override
	public void setUserData(Object userData) {
		this.userData = userData;
	}
	
	/* (non-Javadoc)
	 * @see org.dyn4j.geometry.Shape#rotate(double)
	 */
	@Override
	public void rotate(double theta) {
		this.rotate(theta, 0.0, 0.0);
	}
	
	/* (non-Javadoc)
	 * @see org.dyn4j.geometry.Shape#rotate(org.dyn4j.geometry.Rotation)
	 */
	@Override
	public void rotate(Rotation rotation) {
		this.rotate(rotation, 0.0, 0.0);
	}
	
	/* (non-Javadoc)
	 * @see org.dyn4j.geometry.Shape#rotateAboutCenter(double)
	 */
	@Override
	public void rotateAboutCenter(double theta) {
		this.rotate(theta, this.center.x, this.center.y);
	}
	
	/* (non-Javadoc)
	 * @see org.dyn4j.geometry.Transformable#rotate(double, org.dyn4j.geometry.Vector)
	 */
	@Override
	public void rotate(double theta, DynVector2 point) {
		this.rotate(theta, point.x, point.y);
	}
	
	/* (non-Javadoc)
	 * @see org.dyn4j.geometry.Transformable#rotate(org.dyn4j.geometry.Rotation, org.dyn4j.geometry.Vector)
	 */
	@Override
	public void rotate(Rotation rotation, DynVector2 point) {
		this.rotate(rotation, point.x, point.y);
	}
	
	/* (non-Javadoc)
	 * @see org.dyn4j.geometry.Transformable#rotate(double, double, double)
	 */
	@Override
	public void rotate(double theta, double x, double y) {
		this.rotate(new Rotation(theta), x, y);
	}
	
	/*
	 * Subclasses of {@link AbstractShape} should override just this method
	 * if they need to perform additional operations on rotations.
	 */
	/* (non-Javadoc)
	 * @see org.dyn4j.geometry.Transformable#rotate(org.dyn4j.geometry.Rotation, double, double)
	 */
	@Override
	public void rotate(Rotation rotation, double x, double y) {
		// only rotate the center if the point about which
		// we are rotating is not the center
		if (!this.center.equals(x, y)) {
			this.center.rotate(rotation, x, y);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.dyn4j.geometry.Transformable#translate(double, double)
	 */
	@Override
	public void translate(double x, double y) {
		this.center.add(x, y);
	}
	
	/* (non-Javadoc)
	 * @see org.dyn4j.geometry.Transformable#translate(org.dyn4j.geometry.Vector)
	 */
	@Override
	public void translate(DynVector2 vector) {
		this.translate(vector.x, vector.y);
	}
	
	/* (non-Javadoc)
	 * @see org.dyn4j.geometry.Shape#project(org.dyn4j.geometry.Vector2)
	 */
	@Override
	public Interval project(DynVector2 n) {
		return this.project(n, IDENTITY);
	}
	
	/* (non-Javadoc)
	 * @see org.dyn4j.geometry.Shape#contains(org.dyn4j.geometry.Vector2)
	 */
	@Override
	public boolean contains(DynVector2 point) {
		return this.contains(point, IDENTITY, true);
	}
	
	/* (non-Javadoc)
	 * @see org.dyn4j.geometry.Shape#contains(org.dyn4j.geometry.Vector2, org.dyn4j.geometry.Transform)
	 */
	@Override
	public boolean contains(DynVector2 point, Transform transform) {
		return this.contains(point, transform, true);
	}
	
	/* (non-Javadoc)
	 * @see org.dyn4j.geometry.Shape#createAABB()
	 */
	@Override
	public AABB createAABB() {
		return this.createAABB(IDENTITY);
	}

	/* (non-Javadoc)
	 * @see org.dyn4j.geometry.Shape#computeAABB(org.dyn4j.geometry.AABB)
	 */
	@Override
	public void computeAABB(AABB aabb) {
		this.computeAABB(IDENTITY, aabb);
	}
	
	/* (non-Javadoc)
	 * @see org.dyn4j.geometry.Shape#createAABB(org.dyn4j.geometry.Transform)
	 */
	@Override
	public AABB createAABB(Transform transform) {
		AABB aabb = new AABB(0,0,0,0);
		this.computeAABB(transform, aabb);
		return aabb;
	}
}
