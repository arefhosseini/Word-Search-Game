package com.fearefull.wordsearch.features.gameplay;

import com.fearefull.wordsearch.commons.Util;
import com.fearefull.wordsearch.commons.generator.StringListGridGenerator;
import com.fearefull.wordsearch.model.GameData;
import com.fearefull.wordsearch.model.Grid;
import com.fearefull.wordsearch.model.UsedWord;
import com.fearefull.wordsearch.model.Word;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * Created by Aref Hosseini.
 */

public class GameDataCreator {

    public GameData newGameData(final List<Word> words,
                                final int rowCount, final int colCount,
                                final String name) {
        final GameData gameData = new GameData();

        Util.randomizeList(words);

        Grid grid = new Grid(rowCount, colCount);
        int maxCharCount = Math.max(rowCount, colCount);
        List<String> usedStrings =
                new StringListGridGenerator()
                        .setGrid(getStringListFromWord(words, 100, maxCharCount), grid.getArray());

        gameData.addUsedWords(buildUsedWordFromString(usedStrings, words));
        gameData.setGrid(grid);
        if (name == null || name.isEmpty()) {
            String name1 = "Puzzle " +
                    new SimpleDateFormat("HH.mm.ss", Locale.ENGLISH)
                            .format(new Date(System.currentTimeMillis()));
            gameData.setName(name1);
        }
        else {
            gameData.setName(name);
        }
        return gameData;
    }

    private List<UsedWord> buildUsedWordFromString(List<String> strings, List<Word> words) {
        int mysteryWordCount = Util.getRandomIntRange(strings.size() / 2, strings.size());
        List<UsedWord> usedWords = new ArrayList<>();
        for (int i = 0; i < strings.size(); i++) {
            String str = strings.get(i);

            Word selectedWord = null;
            UsedWord uw = new UsedWord();
            uw.setString(str);
            uw.setAnswered(false);

            for (Word word : words) {
                if (word.getShortString().equals(str)) {
                    selectedWord = word;
                    break;
                }
            }

            if (selectedWord != null) {
                uw.setTitle(selectedWord.getString());
            }
//            if (mysteryWordCount > 0) {
//                uw.setIsMystery(true);
//                uw.setRevealCount(Util.getRandomIntRange(0, str.length() - 1));
//                mysteryWordCount--;
//            }

            usedWords.add(uw);
        }

        Util.randomizeList(usedWords);
        return usedWords;
    }

    private List<String> getStringListFromWord(List<Word> words, int count, int maxCharCount) {
        count = Math.min(count, words.size());

        List<String> stringList = new ArrayList<>();
        String temp;
        for (int i = 0; i < words.size(); i++) {
            if (stringList.size() >= count) break;
            temp = words.get(i).getShortString();
            if (temp.length() <= maxCharCount) {
                stringList.add(temp);
            }
        }

        return stringList;
    }
}
