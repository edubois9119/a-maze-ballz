package com.ericadubois.amazeballz.model;


/**
 * The type Route.
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
