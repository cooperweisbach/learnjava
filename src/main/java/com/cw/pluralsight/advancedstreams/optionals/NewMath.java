package com.cw.pluralsight.advancedstreams.optionals;

import java.util.Optional;

public class NewMath {

    public static Optional<Double> inv(Double d) {
        return d != 0d ? Optional.of(1d/d) : Optional.empty();
    }

    public static Optional<Double> sqrt(Double d) {
        return d < 0d ? Optional.empty(): Optional.of(Math.sqrt(d));
    }
}
