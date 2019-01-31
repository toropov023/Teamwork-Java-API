package ca.toropov.api.teamwork.util;

import lombok.Getter;
import lombok.Value;

/**
 * Author: toropov
 * Date: 1/30/2019
 */
@Value
@Getter
public class Pair<L, R> {
    private final L left;
    private final R right;

    private Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public static <L, R> Pair of(L left, R right) {
        return new Pair<L, R>(left, right);
    }
}
