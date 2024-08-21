import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    static AtomicInteger threeLetterWords = new AtomicInteger();
    static AtomicInteger fourLetterWords = new AtomicInteger();
    static AtomicInteger fiveLetterWords = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {

        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread threadForThreeLetterNames = new Thread(() -> {
            for (String s : texts) {
                if ((s.length() == 3) && isPalindrome(s) || consistsOfOneLetter(s) || isInAlphabeticalSequence(s)) {
                    threeLetterWords.getAndIncrement();
                }
            }
        });

        Thread threadForFourLetterNames = new Thread(() -> {
            for (String s : texts) {
                if ((s.length() == 4) && isPalindrome(s) || consistsOfOneLetter(s) || isInAlphabeticalSequence(s)) {
                    fourLetterWords.getAndIncrement();
                }
            }
        });

        Thread threadForFiveLetterNames = new Thread(() -> {
            for (String s : texts) {
                if ((s.length() == 5) && isPalindrome(s) || consistsOfOneLetter(s) || isInAlphabeticalSequence(s)) {
                    fiveLetterWords.getAndIncrement();
                }
            }
        });

        threadForThreeLetterNames.start();
        threadForFourLetterNames.start();
        threadForFiveLetterNames.start();

        threadForThreeLetterNames.join();
        threadForFourLetterNames.join();
        threadForFiveLetterNames.join();

        System.out.println("Красивых слов с длиной 3: " + threeLetterWords.get() + " шт.");
        System.out.println("Красивых слов с длиной 4: " + fourLetterWords.get() + " шт.");
        System.out.println("Красивых слов с длиной 5: " + fiveLetterWords.get() + " шт.");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isPalindrome(String name) {
        int length = name.length();
        for (int i = 0; i < (length / 2); i++) {
            if (name.charAt(i) != name.charAt(length - i - 1)) {
                return false;
            }
        }
        return true;
    }

    public static boolean consistsOfOneLetter(String name) {
        int length = name.length();
        for (int i = 0; i < length; i++) {
            if (name.charAt(i) != name.charAt(length - 1)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isInAlphabeticalSequence(String name) {
        String sortedWord = name.chars().sorted().toString();
        return name.equals(sortedWord);
    }
}