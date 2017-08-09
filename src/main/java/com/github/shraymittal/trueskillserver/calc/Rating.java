package com.github.shraymittal.trueskillserver.calc;

import java.io.Serializable;
import lombok.Getter;
import org.apache.commons.math3.distribution.NormalDistribution;

@Getter
public class Rating implements Serializable {

  public static final double MU = 25d;

  public static final double SIGMA = 25d / 3d;

  public static final double BETA = SIGMA / 2d;

  public static final double TAU = SIGMA / 100d;

  public static final double DRAW = 0.1;

  private double mean;

  private double variance;

  private int games;

  public Rating() {
    mean = MU;
    variance = Math.pow(SIGMA, 2);
    games = 0;
  }

  public double update(Rating o) {
    double tVar = variance + Math.pow(TAU, 2);
    double oVar = o.variance + Math.pow(TAU, 2);
    double c = Math.sqrt(2d * Math.pow(BETA, 2) + tVar + oVar);
    double t = (this.mean - o.mean) / c;
    NormalDistribution dist = new NormalDistribution(0d, 1d);
    NormalDistribution thisDist = new NormalDistribution(mean, Math.sqrt(tVar));
    double epsilon = dist.inverseCumulativeProbability((DRAW + 1d) / 2d) * Math.sqrt(2d) * BETA;
    return this.mean + (tVar / c) * (thisDist.density(t - epsilon) / thisDist.cumulativeProbability(t - epsilon));
  }

}
