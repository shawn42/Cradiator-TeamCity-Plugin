
/*
 * Cradiator TeamCity Plugin Copyright (c) 2009 Peter van der Woude, James Summerton
 *
 *    This file is part of Cradiator - http://cradiator.codeplex.com/
 *
 *    Cradiator is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    Cradiator is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *    Original idea and TeamCity plugin from Team Piazza Copyright (c) 2007-2009 Nat Pryce.
 */

package com.cradiator.TeamCityStatusPlugin;

public enum BuildStatus {
    // in order of severity
    SUCCESS,
    UNKNOWN,
    FAILURE;

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }

    public BuildStatus mostSevere(BuildStatus other) {
        return this.compareTo(other) > 0 ? this : other;
    }

    public static final String BUILDING = "Building";

    public String toStringReflectingCurrentlyBuilding(boolean isBuilding) {
        return this + (isBuilding ? " " + BUILDING : "");
    }
}
