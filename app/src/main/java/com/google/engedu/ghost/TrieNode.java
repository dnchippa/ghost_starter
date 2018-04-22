/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.ghost;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Random;


public class TrieNode {
    private HashMap<String, TrieNode> children;
    private boolean isWord;
    Random random = new Random();

    public TrieNode() {
        children = new HashMap<>();
        isWord = false;
    }

    public void add(String s) {
        isWord = false;
        if(!children.containsKey(s.substring(0,1))){
            children.put(s.substring(0,1), new TrieNode());
//            Log.d("ADD", s);
        }
        if(s.length()>1) {
            children.get(s.substring(0,1)).add(s.substring(1));
        }else{
            isWord = true;
//            Log.d("ADD", "isWord");
        }

    }

    public boolean isWord(String s) {

        if(s.length() == 1) {
            return children.get(s.substring(0,1)).isWord;
        }
//        If children doesn't contain the key, it doesn't exist.
        if(s.length() > 1){
            if(children.containsKey(s.substring(0,1))){
                Log.d("IS", "Recursing to " + s.substring(0,1));
                return children.get(s.substring(0,1)).isWord(s.substring(1));
            }else{
                return false;
            }
        }
//        If the letter checked against children is the end of a word, return true
        Log.d("ISWORD", "FALSE WORD");
        return false;
    }

    public String getAnyWordStartingWith(String s) {
        String word = "";
        Log.d("GAW", s);
        Log.d("GAW", "LENGTH OF S " + s.length());

//        Recurse down. If it doesn't contain a key, is not a word. Otherwise, move down.
        if(s.length() > 1){
            if(children.containsKey(s.substring(0,1))){
                return s.substring(0,1) + children.get(s.substring(0,1)).getAnyWordStartingWith(s.substring(1));
            }else{
                return null;
            }
        }

        if(s.length() == 1){
            Log.d("GAW", s.substring(0,1) + " is the node.");
            if(isWord){
                Log.d("GAW", " and is a word");
                return s;
            }
            if(children.containsKey(s)){
                Log.d("GAW", " and not a word. moving down with " + s.substring(0,1));
                return s.substring(0,1) + children.get(s.substring(0,1)).getAnyWordStartingWith(s.substring(1));

            }
        }

        //        If there is no characters left, return find a random letter in the list.
        Log.d("GAW", s + " IS NOT WORD. Finding other characters to move to.");
        ArrayList<String> list = new ArrayList<>();
        for(String key : children.keySet()){
            list.add(key);
            Log.d("GAWLIST", key);
        }
        if(list.isEmpty()){
            Log.d("GAWLIST", "LIST IS EMPTY");
            return null;
        }else {
            String key = list.get(random.nextInt(list.size()));
            Log.d("GAW", "RETURNING! RANDOM KEY! " + key);
            return s + children.get(key).getAnyWordStartingWith(key);
        }
    }

    public String getGoodWordStartingWith(String s) {
        return null;
    }
}