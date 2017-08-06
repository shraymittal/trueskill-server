package com.github.shraymittal.trueskillserver.calc;

import java.io.Serializable;
import lombok.Getter;

@Getter
public class Rating implements Serializable {

  private double mean;

  private double variance;

  private int games;

  public Rating() {
    mean = 25d;
    variance = 25d / 3d;
    games = 0;
  }

}
