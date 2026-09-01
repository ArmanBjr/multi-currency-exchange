package com.example.demo1.Utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class FixedSizeList<E> extends javafx.collections.ModifiableObservableListBase<E> {
    private final ObservableList<E> backingList;

    public FixedSizeList(List<E> list) {
        this.backingList = FXCollections.observableArrayList(list);
    }

    @Override
    public E get(int index) {
        return backingList.get(index);
    }

    @Override
    public int size() {
        return backingList.size();
    }

    @Override
    protected void doAdd(int index, E element) {
        backingList.add(index, element);
    }

    @Override
    protected E doSet(int index, E element) {
        return backingList.set(index, element);
    }

    @Override
    protected E doRemove(int index) {
        return backingList.remove(index);
    }
}
