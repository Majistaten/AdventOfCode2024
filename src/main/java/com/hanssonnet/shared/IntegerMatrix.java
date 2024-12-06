package com.hanssonnet.shared;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class IntegerMatrix implements Iterable<List<Integer>> {

    private List<List<Integer>> matrix;

    public IntegerMatrix(List<List<Integer>> matrix) {
        this.matrix = matrix;
    }

    public List<Integer> getRow(int index) {
        return matrix.get(index);
    }

    public Integer getValue(int row, int col) {
        return matrix.get(row).get(col);
    }

    @Override
    public Iterator<List<Integer>> iterator() {
        return matrix.iterator();
    }

    public void removeAll(List<List<Integer>> toRemove) {
        matrix.removeAll(toRemove);
    }

    public void removeAll(IntegerMatrix toRemove) {
        matrix.removeAll(toRemove.matrix);
    }

    public void addAll(List<List<Integer>> toAdd) {
        matrix.addAll(toAdd);
    }

    public void addAll(IntegerMatrix toAdd) {
        matrix.addAll(toAdd.matrix);
    }

    public Stream<List<Integer>> stream() {
        return matrix.stream();
    }
}

