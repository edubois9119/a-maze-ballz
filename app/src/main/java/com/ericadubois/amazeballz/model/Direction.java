package com.ericadubois.amazeballz.model;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Set;

public enum Direction {
  NORTH(-1, 0),
  EAST(0, 1),
  SOUTH(1, 0),
  WEST(0, -1);
//  NORTH(-1, 0, Direction.SOUTH),
//  EAST(0, 1, Direction.WEST),
//  SOUTH(1, 0, Direction.NORTH),
//  WEST(0, -1, Direction.EAST);

  public final int rowOffset;
  public final int columnOffset;
//  public final Direction opposite;

  Direction(int rowOffset, int columnOffset) {
    this.rowOffset = rowOffset;
    this.columnOffset = columnOffset;
//    this.opposite = opposite;
  }
//  final Direction getOpposite(){
//    switch (this){
//      case EAST:
//        return WEST;
//      case WEST:
//        return EAST;
//      case NORTH:
//        return SOUTH;
//      default:
//        return NORTH;
//    }
//  }
  static final Direction getOpposite(Direction direction){
    switch (direction){
      case EAST:
        return WEST;
      case WEST:
        return EAST;
      case NORTH:
        return SOUTH;
      default:
        return NORTH;
    }
  }
}
