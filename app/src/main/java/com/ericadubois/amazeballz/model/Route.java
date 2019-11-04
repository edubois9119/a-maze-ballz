package com.ericadubois.amazeballz.model;



class Route {

  public Direction direction;
  public Cell destination;

  public Route (Direction direction, Cell destination){
    //the direction you need to travel to get to this destination cell
    this.direction = direction;
    //the cell the route will go towards
    this.destination = destination;
  }


//
//  private Node destination;
//  private Direction direction;
//
//  public Route(Node destination, Direction direction) {
//    this.destination = destination;
//    this.direction = direction;
//  }
//
//  public static Route of(Node destination, Direction direction) {
//    return new Route(destination, direction);
//  }
//
//  public Node getDestination() {
//    return destination;
//  }
//
//  public Direction getDirection() {
//    return direction;
//  }
//
//  @Override
//  public String toString() {
//    return "Route{" +
//        "destination=" + destination +
//        ", direction=" + direction +
//        '}';
//  }
}
