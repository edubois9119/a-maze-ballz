/**
 * This work is Copyright 2019, Erica DuBois. ALL RIGHTS RESERVED.
 */

package com.ericadubois.amazeballz.pojos;

/**
 * Helper class used for building mazes. Specifies Direction and Destination cell.
 */
class Route {

  Direction direction;

  Cell destination;

  /**
   * Instantiates a new Route with the given direction and destination
   *
   * @param direction   the direction
   * @param destination the destination
   */
  public Route (Direction direction, Cell destination){
    //the direction you need to travel to get to this destination cell
    this.direction = direction;
    //the cell the route will go towards
    this.destination = destination;
  }
}
