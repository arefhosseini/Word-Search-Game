package com.fearefull.wordsearch.custom;

import java.util.Observable;

/**
 * Created by Aref Hosseini on 26/06/17.
 */

public abstract class LetterGridDataAdapter extends Observable {

    public abstract int getColCount();
    public abstract int getRowCount();
    public abstract char getLetter(int row, int col);

}
