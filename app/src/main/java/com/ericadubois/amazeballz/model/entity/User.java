/**
 * This work is Copyright 2019, Erica DuBois. ALL RIGHTS RESERVED.
 */

package com.ericadubois.amazeballz.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * The User Entity. Creates the User table in the database.
 */
@Entity(
    indices = @Index(value = "oauth_key", unique = true)
)
public class User {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "user_id")
  private long id;

  @ColumnInfo(name = "oauth_key")
  private String oauthKey;

  /**
   * Gets User id.
   *
   * @return the id
   */
  public long getId() {
    return id;
  }

  /**
   * Sets User id.
   *
   * @param id the id
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * Gets oauth key.
   *
   * @return the oauth key
   */
  public String getOauthKey() {
    return oauthKey;
  }

  /**
   * Sets oauth key.
   *
   * @param oauthKey the oauth key
   */
  public void setOauthKey(String oauthKey) {
    this.oauthKey = oauthKey;
  }
}

