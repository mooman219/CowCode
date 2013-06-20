package com.gmail.mooman219.frame.file;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class FileBase{
    public File file;
    public ArrayList<String> body = new ArrayList<String>();

    public FileBase(String s){
        file = new File(s);
        setUpFile(file);
    }

    public FileBase(File f){
        setUpFile(f);
    }

    public FileBase setUpFile(File f){
        file = f;
        try {
            if(!file.exists()){
                file.createNewFile();
            }
            body = getRawFile();
            updateFile();
        } catch (Exception e) {
        }
        return this;
    }

    /**
     *
     * Unsaved Write Methods (Faster)
     * You need to run 'updateFile()' when you are done though
     *
     */

    /**
     * Clears all text within the config file from memory.
     */

    public void clearBody(){
        body.clear();
    }

    /**
     * Writes the string on the given line.
     * If the line doesn't exist, it will be added to the end of the file.
     * If the line does exist, it will push all data before the given line number down one spot and write the given string into the given line number.
     * This changes the text in memory (Faster). You need to run the 'updateFile()' method to pass the changes to the hard disk.
     * @param inputStr - A String that will be written on the file.
     * @param line - The line number that will be written to.
     * @return An ArrayList<String> of all the text in the file in memory, line by line, in increasing order from 0;
     */

    public ArrayList<String> setWriteAddBefore(String inputStr, int line){
        if(line >= body.size()) {
            body.add(inputStr);
        } else {
            body.add(line, inputStr);
        }
        return body;
    }

    /**
     * Writes the string on the given line.
     * If the line doesn't exist, it will be added to the end of the file.
     * If the line does exist, it will push all data after the given line number down one spot and write the given string into the given line number.
     * This changes the text in memory (Faster). You need to run the 'updateFile()' method to pass the changes to the hard disk.
     * @param inputStr - A String that will be written on the file.
     * @param line - The line number that will be written to.
     * @return An ArrayList<String> of all the text in the file in memory, line by line, in increasing order from 0;
     */

    public ArrayList<String> setWriteAddAfter(String inputStr, int line){
        line++;
        if(line >= body.size()) {
            body.add(inputStr);
        } else {
            body.add(line, inputStr);
        }
        return body;
    }

    /**
     * Writes the string to the end of the file.
     * This changes the text in memory (Faster). You need to run the 'updateFile()' method to pass the changes to the hard disk.
     * @param inputStr - A String that will be written on the file.
     * @return An ArrayList<String> of all the text in the file in memory, line by line, in increasing order from 0;
     */

    public ArrayList<String> setWriteAdd(String inputStr){
        body.add(inputStr);
        return body;
    }

    /**
     * Writes the string on the given line.
     * If the line doesn't exist, it will be added to the end of the file.
     * If the line does exist, it will overwrite it.
     * This changes the text in memory (Faster). You need to run the 'updateFile()' method to pass the changes to the hard disk.
     * @param inputStr - A String that will be written on the file.
     * @param line - The line number that will be written to.
     * @return An ArrayList<String> of all the text in the file in memory, line by line, in increasing order from 0;
     */

    public ArrayList<String> setWriteLine(String inputStr, int line){
        if(line >= body.size()) {
            body.add(inputStr);
        } else {
            body.set(line, inputStr);
        }
        return body;
    }

    /**
     * The file is cleared on the hard disk and in memory,
     * This changes the text in memory (Faster). You need to run the 'updateFile()' method to pass the changes to the hard disk.
     * Then a ArrayList<String> is written to it.
     * @param text - An ArrayList<String> that will be written on the file.
     * @return An ArrayList<String> of all the text in the file in memory after the wipe, line by line, in increasing order from 0;
     */

    public ArrayList<String> setWriteNew(ArrayList<String> text){
        body = text;
        return body;
    }

    /**
     * The file is cleared on the hard disk and in memory,
     * Then a string is written to it.
     * This changes the text in memory (Faster). You need to run the 'updateFile()' method to pass the changes to the hard disk.
     * @param inputStr - A string that will be written on the file
     * @return An ArrayList<String> of all the text in the file in memory after the wipe, line by line, in increasing order from 0;
     */

    public ArrayList<String> setWriteNew(String inputStr){
        body.clear();
        body.add(inputStr);
        return body;
    }

    /**
     *
     * Saved Write Methods (Slower)
     *
     */

    /**
     * Clears all text within the config file from the hard disk and memory.
     */

    public void wipeFile(){
        body.clear();
        updateFile();
    }

    /**
     * Writes the string on the given line.
     * If the line doesn't exist, it will be added to the end of the file.
     * If the line does exist, it will push all data before the given line number down one spot and write the given string into the given line number.
     * This instantly saves all changes to the hard disk.
     * @param inputStr - A String that will be written on the file.
     * @param line - The line number that will be written to.
     * @return An ArrayList<String> of all the text in the file in memory, line by line, in increasing order from 0;
     */

    public ArrayList<String> writeAddBefore(String inputStr, int line){
        if(line >= body.size()) {
            body.add(inputStr);
        } else {
            body.add(line, inputStr);
        }
        return updateFile();
    }

    /**
     * Writes the string on the given line.
     * If the line doesn't exist, it will be added to the end of the file.
     * If the line does exist, it will push all data after the given line number down one spot and write the given string into the given line number.
     * This instantly saves all changes to the hard disk.
     * @param inputStr - A String that will be written on the file.
     * @param line - The line number that will be written to.
     * @return An ArrayList<String> of all the text in the file in memory, line by line, in increasing order from 0;
     */

    public ArrayList<String> writeAddAfter(String inputStr, int line){
        line++;
        if(line >= body.size()) {
            body.add(inputStr);
        } else {
            body.add(line, inputStr);
        }
        return updateFile();
    }

    /**
     * Writes the string to the end of the file.
     * This instantly saves all changes to the hard disk.
     * @param inputStr - A String that will be written on the file.
     * @return An ArrayList<String> of all the text in the file in memory, line by line, in increasing order from 0;
     */

    public ArrayList<String> writeAdd(String inputStr){
        body.add(inputStr);
        return updateFile();
    }

    /**
     * Writes the string on the given line.
     * If the line doesn't exist, it will be added to the end of the file.
     * If the line does exist, it will overwrite it.
     * This instantly saves all changes to the hard disk.
     * @param inputStr - A String that will be written on the file.
     * @param line - The line number that will be written to.
     * @return An ArrayList<String> of all the text in the file in memory, line by line, in increasing order from 0;
     */

    public ArrayList<String> writeLine(String inputStr, int line){
        if(line >= body.size()) {
            body.add(inputStr);
        } else {
            body.set(line, inputStr);
        }
        return updateFile();
    }

    /**
     * The file is cleared on the hard disk and in memory,
     * This instantly saves all changes to the hard disk.
     * Then a ArrayList<String> is written to it.
     * @param text - An ArrayList<String> that will be written on the file.
     * @return An ArrayList<String> of all the text in the file in memory after the wipe, line by line, in increasing order from 0;
     */

    public ArrayList<String> writeNew(ArrayList<String> text){
        body = text;
        return updateFile();
    }

    /**
     * The file is cleared on the hard disk and in memory,
     * Then a string is written to it.
     * This instantly saves all changes to the hard disk.
     * @param inputStr - A string that will be written on the file
     * @return An ArrayList<String> of all the text in the file in memory after the wipe, line by line, in increasing order from 0;
     */

    public ArrayList<String> writeNew(String inputStr){
        body.clear();
        body.add(inputStr);
        return updateFile();
    }

    /**
     *
     * Read Methods
     *
     */

    /**
     * @param lineNumber - The line number in the body.
     * @return A string of the line.
     */

    public String getBodyLine(int lineNumber){
        if(lineNumber >= body.size() || lineNumber < 0) {
            return "";
        }
        return body.get(lineNumber);
    }

    /**
     * @return An ArrayList<String> of all the text in the file in memory, line by line, in increasing order from 0;
     */

    public ArrayList<String> getBody(){
        return body;
    }

    /**
     * @return An ArrayList<String> of all the text in the file on the hard disk, line by line, in increasing order from 0;
     */

    public ArrayList<String> getRawFile(){
        ArrayList<String> text = new ArrayList<String>();
        try {
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()){
                text.add(reader.nextLine());
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Error Scanning File...");
            e.printStackTrace();
        }

        return text;
    }

    /**
     *
     * Save Methods
     *
     */

    /**
     * Sets the text within the file on the hard disk, to the text in memory
     * @return An ArrayList<String> of all the text in the file, line by line, in increasing order from 0;
     */

    public ArrayList<String> reloadBody(){
        body = getRawFile();
        return body;
    }

    /**
     * Sets the text in memory, to the file on the hard disk.
     * @return An ArrayList<String> of all the text in the file, line by line, in increasing order from 0;
     */

    public ArrayList<String> updateFile(){
        try {
            PrintStream input = new PrintStream(file);
            for(String a:body){
                input.println(a);
            }
            input.close();
        } catch (Exception e) {
            System.out.println("Error Updating File...");
            e.printStackTrace();
        }
        return body;
    }
}