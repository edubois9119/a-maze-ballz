package com.ericadubois.amazeballz.model;


public enum Direction {
  NORTH(-1, 0),
  EAST(0, 1),
  SOUTH(1, 0),
  WEST(0, -1);

  public final int rowOffset;
  public final int columnOffset;
  public final Direction opposite;

  Direction(int rowOffset, int columnOffset) {
    this.rowOffset = rowOffset;
    this.columnOffset = columnOffset;
    this.opposite = Direction.values()[(ordinal() + 2) % Direction.values().length];
  }

  public int getRowOffset() {
    return rowOffset;
  }

  public int getColumnOffset() {
    return columnOffset;
  }

  public Direction getOpposite() {
    return opposite;
  }

}
