# java-aggregation

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/info.debatty/java-aggregation/badge.svg)](https://maven-badges.herokuapp.com/maven-central/info.debatty/java-aggregation)

Java implementation of aggregation operators: WOWA, OWA and WA.

## Download
Using maven:
```
<dependency>
    <groupId>info.debatty</groupId>
    <artifactId>java-aggregation</artifactId>
    <version>RELEASE</version>
</dependency>
```

Or check the [releases](https://github.com/tdebatty/java-aggregation/releases).

## WOWA

The **Weighted Ordered Weighted Aggregation** operator is a generalization of the classical weighted average (WA) operator and the Ordered Weighted Average (Yager, 1988).

In the OWA operator, the input values are ordered by decreasing value before the weights are used to compute a weighted sum. The max() operator is for example a special case of the OWA operator, where the fist weight is 1 and all others are 0.

The WOWA operator allows to combine the WA and OWA operator. It allows to aggregate a set of values considering both the importance of the sources that have supplied the values (as in the weighted average) and the degree of compensation or tradeoff between large and small values (as in the OWA operator).

It was presented by V. Torra in "The Weighted OWA operator", Int. J. of Intel. Systems, 12 (1997) 153-166 and "The WOWA operator and the interpolation function W*: Chen and Otto's interpolation method revisited", Fuzzy Sets and Systems, 113:3 (2000) 389-396.


## Example

```java
import info.debatty.java.aggregation.OWA;
import info.debatty.java.aggregation.WOWA;

public class WOWAExample {
    public static void main(String[] args) {
        double[] values = new double[] {0.4, 0.2, 0.3, 0.1, 0.0};
        double[] weights = new double[] {0.1, 0.2, 0.3, 0.4, 0.0};
        double[] ordered_weights = new double[] {0.1, 0.2, 0.3, 0.4, 0.0};

        WOWA wowa = new WOWA(weights, ordered_weights);
        // Will show 0.194296875
        System.out.println(wowa.aggregate(values));
    }
}
```