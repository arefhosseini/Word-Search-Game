package com.fearefull.wordsearch.data;

import com.fearefull.wordsearch.model.Word;

import java.util.List;

/**
 * Created by abdularis on 18/07/17.
 */

public interface WordDataSource {

    List<Word> getWords();

}
