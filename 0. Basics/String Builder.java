// Reverse word 
    public String reverseWord(String word) {
        // Handle null input gracefully
        if (word == null) {
            return null;
        }

        // 1. Create a StringBuilder object with the given word.
        StringBuilder sb = new StringBuilder(word);

        // 2. Use the reverse() method to reverse the characters within the StringBuilder.
        sb.reverse();

        // 3. Convert the StringBuilder back to a String and return it.
        return sb.toString();
    }


// Reverse words in a sentence
public String reverseWords(String s) {
        // Handle null or empty/blank strings
        if (s == null || s.trim().isEmpty()) {
            return ""; // Or return s.trim() if you want to preserve only internal spaces
        }

        // 1. Trim leading/trailing spaces and split the string by one or more spaces.
        //    "\\s+" regex means "one or more whitespace characters".
        String[] words = s.trim().split("\\s+");

        // 2. Convert the array of words to a List.
        List<String> wordList = Arrays.asList(words);

        // 3. Reverse the order of elements in the list.
        Collections.reverse(wordList);

        // 4. Use StringBuilder to efficiently build the result string.
        StringBuilder reversedSentence = new StringBuilder();

        // 5. Iterate through the reversed list and append words to the StringBuilder.
        for (int i = 0; i < wordList.size(); i++) {
            reversedSentence.append(wordList.get(i));

            // Add a space after each word, except for the last one.
            if (i < wordList.size() - 1) {
                reversedSentence.append(" ");
            }
        }

        // 6. Convert the StringBuilder content back to an immutable String and return.
        return reversedSentence.toString();
    }
