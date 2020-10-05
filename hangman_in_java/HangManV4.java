/*
Author: Christian Stefaniw
Completion Date: June 9th 2020
Version Due Date: June 12th 2020
Final Version Due Date: June 12th 2020
Class: ICS3U Period C
Description: Hangman game. The user has to try to guess a word and the only hint that is given is the number of lines near the bottom of the screen that correspond with the number
of letters in the word. As the user guesses letters by typing on the keyboard, the correct letters will appear on the lines in the order that they appear in the word and a success sound is played. If the user guesses a letter incorrectly
a failure sound is played then a piece of the hangman is shown on the screen. The user can make six incorrect guesses before the hangman is fully built and they lose the game and a lose screen is shown with a failure sound played.
If the user guesses the word a win screen is shown with a success sound played. Each letter that the user guessed incorrectly is shown at the very bottom of the screen. There are three difficulties that the user can choose, one is easy, two
is medium and three is hard.
*/

//imports
import java.awt.*;
import hsa_ufa.*;
import java.io.File;
import java.util.Arrays;
import java.util.Scanner;
import java.io.FileNotFoundException;
import javax.sound.sampled.*;


public class HangManV4 {

    //private variables accesible from anywhere inside of the HangManV1 class
    private static Console c; //hsa console
    private static String data; //string for the word that the user has to guess
    private static int numCorrect = 0; //number of letters guessed correctly
    private static boolean win = false; //if the user has won
    private static int lives = 0; //number of lives
    private static boolean lose = false; //if the user has lost
    private static boolean start = true; //if the user has started the game
    private static boolean seven0 = false;// |
    private static boolean seven1 = false;// |
    private static boolean seven2 = false;// |
    private static boolean seven3 = false;// |
    private static boolean seven4 = false;// |
    private static boolean seven5 = false;// |
    private static boolean seven6 = false;// |
    private static boolean six0 = false;//   |
    private static boolean six1 = false;//   |
    private static boolean six2 = false;//   |
    private static boolean six3 = false;//   |
    private static boolean six4 = false; //  |-----------------------------> Variables to check which index of the word the user guessed correctly so the user cannot guess the same letter twice. Format: "seven""1"
    private static boolean six5 = false; //  |                                                                                                                                                                 |    |
    private static boolean five0 = false;//  |                                                                                                                                              # of letters in word    index of the letter guessed
    private static boolean five1 = false;//  |
    private static boolean five2 = false;//  |
    private static boolean five3 = false;//  |
    private static boolean five4 = false;//  |
    private static boolean four0 = false;//  |
    private static boolean four1 = false;//  |
    private static boolean four2 = false;//  |
    private static boolean four3 = false;//  |
    private static boolean three0 = false;// |
    private static boolean three1 = false;// |
    private static boolean three2 = false;// |
    private static int x = 60; //x value when letters are saved and placed on the screen again
    private static char[] letters; //array of characters that will be filled with incorrect guessed
    private static int place = -1; //adds each time a wrong letter is guessed, later used for selecting indexes in the letters array
    private static boolean resume = false; //resume variable, changes to true when the user resumes the game
    private static char save0 = ' ';//|
    private static int save0P = 0;//  |
    private static char save1 = ' ';//|
    private static int save1P = 1;//  |
    private static char save2 = ' ';//|
    private static int save2P = 2; // ---------------> saving letters and index of letters that the user guessed correctly
    private static char save3 = ' ';//|
    private static int save3P = 3;//  |
    private static char save4 = ' ';//|
    private static int save4P = 4;//  |
    private static char save5 = ' ';//|
    private static int save5P = 5;//  |
    private static char save6 = ' ';//|
    private static int save6P = 6;//  |
    private static boolean seven = false; //used to distinguish word lengths
    private static boolean six = false;//used to distinguish word lengths
    private static boolean five = false;//used to distinguish word lengths
    private static boolean four = false;//used to distinguish word lengths
    private static boolean three = false;//used to distinguish word lengths
    private static int x0 = 0; // used as x pos to place correct words back on the screen when resumed
    private static int x1 = 0;// used as x pos to place correct words back on the screen when resumed
    private static int x2 = 0;// used as x pos to place correct words back on the screen when resumed
    private static int x3 = 0;// used as x pos to place correct words back on the screen when resumed
    private static int x4 = 0;// used as x pos to place correct words back on the screen when resumed
    private static int x5 = 0;// used as x pos to place correct words back on the screen when resumed
    private static int x6 = 0;// used as x pos to place correct words back on the screen when resumed
    private static int placeX2 = 60; // used as x pos to place incorrect guesses when resumed
    private static String allLetters = ""; //string that will be filled with incorrect letters
    private static boolean inGame = false; //set to true when in the main game
    private static boolean drawStart = false; //set to true once start is run for the first time
    private static int index1 = -2;
    private static int index2 = -2; // --------> used to save the index of a same letter that appears multiple times in the word
    private static int index3 = -2;
    private static int level = 1; //level select variable
    private static boolean reset = false; //if all class variables need to be reset i.e the user won or lost and want to play agian so all class variables need to be reset
    private static int numRunsSound = 0; //number of times background sound is run




    public static void main(String[] args) {
        c = new Console(650, 450, "Hang Man"); //create the console
        letters = new char[11]; //init the letters array
        Arrays.fill(letters, ' '); //fill the letters array with empty characters
        Start(c); //Run the start screen
    }

    public static void LoopSong(String filename) throws Exception //looping a .wav file - used for background music, ignores exceptions
    {
        AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(filename));
        Clip clip = AudioSystem.getClip();
        clip.open(inputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public static String GenWord() // method to randomly select a word from the "WordsV1.txt" file, this is the word the user will have to guess
    {
        int rand;
        try {
            Scanner myReader = new Scanner(new File("WordsV4LVL" + level + ".txt")); //depending on the level the filename changes
            rand = (int) (14 * Math.random());
            skipLines(myReader, rand); //skip the same number of lines as the value of random
            data = myReader.nextLine();
        } catch (FileNotFoundException e) { //if the file is not found
            e.printStackTrace();
        }
        return data; //return the word
    }

    public static void play(String filename) { //play a .wav file, name of the wav file passed in as filename
        try
        {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(filename)));
            clip.start();
        }
        catch (Exception exc) //if there is an error/exception print it out
        {
            exc.printStackTrace(System.out);
        }

    }


    public static void Start(Console c)
    {
        c.clear();
        if (!inGame && !drawStart) { //if the player has not been in the game yet and this  is the first time start is being run
            drawStart = true; //set drawStart to true because start is being run
            data = GenWord(); //generate the random word
            Thread t = new Thread(new HangManV4.MainThread(c)); //create the key event listener
            t.start(); //run the key event listener
        }
        Font f1 = new Font("all", Font.BOLD, 20);
        Font f2 = new Font("title", Font.BOLD, 30);
        final String title = "Hangman";
        final String instructions = "Press \"SHIFT\" to View Insctructions";
        final String exit = "Press \"BACKSPACE\" to exit";
        final String author = "By: Christian Stefaniw";

        if (resume) //if the user pressed back to menu from game
        {
            String key = "";
            start = true;
            key = "Press \"SPACE\" to resume";
            c.setFont(f2);
            c.setColor(new Color(0, 191, 255));
            c.drawString(title, 245, 27);// ------------------------> Draw out the start screen with resume instead of select levels
            c.setFont(new Font("Plain", Font.PLAIN, 13));
            c.setColor(Color.black);
            c.drawString(author, 249, 50);
            c.setFont(f1);
            c.drawString(instructions, 145, 170);
            c.drawString(exit, 185, 320);
            c.drawString(key, 190, 245);
            delay(500);
        }
        if (!resume) //if the user pressed back to menu from the instructions
        {

            final String selectL = "Select a Difficulty by Pressing";
            final String Key1 = "\"1\"";
            final String Key2 = "        \"2\"";
            final String Key3 = "                \"3\"";
            c.setFont(f2);
            c.setColor(new Color(0, 191, 255));
            c.drawString(title, 245, 27); // ------------------------> Draw out the start screen with select levels instead of resume on the screen
            c.setFont(new Font("Plain", Font.PLAIN, 13));
            c.setColor(Color.black);
            c.drawString(author, 249, 50);
            c.setFont(f1);
            c.drawString(instructions, 145, 270);
            c.drawString(exit, 185, 370);
            c.drawString(selectL,150, 100);
            c.drawString(Key1, 235, 170);
            c.drawString(Key2, 235, 170);
            c.drawString(Key3, 235, 170);
            delay(500);
        }

    }

    public static void Instructions(Console c)
    {
        c.clear();
        String start = "";
        Font insFont = new Font("instructions", Font.PLAIN, 20);
        Font backFont = new Font("", Font.BOLD, 20);
        Font insTitleFont = new Font("instructions", Font.BOLD, 40);
        //instructions
        final String instructionsTitle = "Instructions";
        final String instructions1 = "Press a letter on your keyboard to guess if that letter is in the word.";
        final String instructions2 = "The unknown word has the same amount of letters as lines on the";
        final String instructions3 = "bottom of the screen. When you guess a letter not in the word";//----------------> "\n" does not work when drawing to screen so I had to make seperate strings for seperate lines
        final String instructions4 = "a piece of the hangman appears, you have six lives. When you";
        final  String instructions5 = "guess an incorrect letter that letter will appear at the bottom of";
        final String instructions6 = "your screen. When you guess all of the letters in the word you win!";
        final String back = "Press ⬅ to return to menu";
        if (resume) //if the user pressed back to menu from the main game
        {
            start = "Press \"SPACE\" to resume"; //user can press space to resume the game
        }
        c.setFont(insTitleFont);
        c.setColor(new Color(0, 191, 255));
        c.drawString(instructionsTitle, 200, 50);
        c.setFont(insFont);
        c.setColor(Color.black);
        c.drawString(instructions1, 5, 115);
        c.drawString(instructions2, 5, 155);
        c.drawString(instructions3, 5, 195);
        c.drawString(instructions4, 5, 235);
        c.drawString(instructions5, 5, 275);
        c.drawString(instructions6, 5, 315);
                    // --------------------------------------------------> Draw above strings to the screen
        c.setFont(backFont);
        c.drawString(start, 210, 420);
        c.drawString(back, 200, 390);
        delay(500); //delay .500 so the user has time to get their finger off the key. Screen will flicker without this delay

    }

    public static void RunBackGroundSound()
    {
        numRunsSound++; //add one to numRunsScound
        if (numRunsSound == 1) { //numRunsSound will only equal one once so the background music will only play once
            try {
                LoopSong("background.wav");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else //if numRunsSound is not equal to 1
        {
            ; //pass
        }
    }

    public static void MainGame(Console c) {
        c.clear();
        RunBackGroundSound();
        c.setColor(Color.black);
        inGame = true; //the user is in the main game
        c.drawRect(100, 380, 450,40); //draw rect where incorrect words appear
        final String back = "Press ⬅ to return to menu";
        final String exit = "Press \"BACKSPACE\" to exit"; //back to menu and exit instructions
        c.setFont(new Font("e&b", Font.PLAIN, 15));
        c.drawString(back, 0, 20);
        c.drawString(exit, 450, 20);//draw back and exit instructions
        placeX2 = 60; //set placeX2 to 60 for string spacing
        if (reset) // if the game was reset ,I.E. the user wanted to play again after they won or lost
        {
            Arrays.fill(letters, ' ');//reset the letters array
            data = GenWord();//create a new word
            //reset the rest of the class vairables
            x = 60;
            reset = false;//reset is now false because the game is being reset
            place = 0;
            numCorrect = 0;
            lives = 0;
            lose = false;
            start = false;
            win = false;
            seven0 = false;
            seven1 = false;
            seven2 = false;
            seven3 = false;
            seven4 = false;
            seven5 = false;
            seven6 = false;
            six0 = false;
            six1 = false;
            six2 = false;
            six3 = false;
            six4 = false;
            six5 = false;
            five0 = false;
            five1 = false;
            five2 = false;
            five3 = false;
            five4 = false;
            four0 = false;
            four1 = false;
            four2 = false;
            four3 = false;
            three0 = false;
            three1 = false;
            three2 = false;
            save0 = ' ';
            save1 = ' ';
            save2 = ' ';
            save3 = ' ';
            save4 = ' ';
            save5 = ' ';
            save6 = ' ';
            x0 = 0;
            x1 = 0;
            x2 = 0;
            x3 = 0;
            x4 = 0;
            x5 = 0;
            x6 = 0;
            index1 = -2;
            index2 = -2;
            index3 = -2;
        }
        if (!reset) { //if the game is not being reset, I.E. the user went back to the menu from the main game
            c.setColor(Color.black);

            if (resume) //if the user resumed the game
            {
                for (int i = 0; i < letters.length; i++) //cycle through letters elements
                {
                    if (allLetters.contains(Character.toString(letters[i]))) //see if the allLetters variable (string with all incorrect guesses) is in the letters array
                    {
                        placeX2 += 75; //if it is in the array then add 75 to place x for a gap between letters on the screen
                        PlaceLetterDone(c, letters[i], placeX2); //place the current letter on the screen with x value placeX2
                    }
                }

                //draw the hangman parts again depending on which life the user was on
                if (lives == 1)
                {
                    DrawHead(c);
                }
                if (lives == 2)
                {
                    DrawHead(c);
                    DrawBody(c);
                }
                if (lives == 3)
                {
                    DrawHead(c);
                    DrawBody(c);
                    DrawArmLeft(c);
                }
                if (lives == 4)
                {
                    DrawHead(c);
                    DrawBody(c);
                    DrawArmLeft(c);
                    DrawArmRight(c);
                }
                if (lives == 5)
                {
                    DrawHead(c);
                    DrawBody(c);
                    DrawArmLeft(c);
                    DrawArmRight(c);
                    DrawLegLeft(c);
                }

                //check the length of the word then set the x values for each letter to the value corresponding to the lines in the middle of the screen and set the length of the word variable to true. Same logic for lines 362-406
                if (data.length() == 7)
                {
                    seven = true;
                    x0 = 80;
                    x1 = 155;
                    x2 = 230;
                    x3 = 305;
                    x4 = 380;
                    x5 = 455;
                    x6 = 530;
                }
                if (data.length() == 6)
                {
                    six = true;
                    x0 = 120;
                    x1 = 195;
                    x2 = 270;
                    x3 = 345;
                    x4 = 420;
                    x5 = 495;
                }
                if (data.length() == 5)
                {
                    five = true;
                    x0 = 160;
                    x1 = 235;
                    x2 = 310;
                    x3 = 385;
                    x4 = 460;
                }
                if (data.length() == 4)
                {
                    four = true;
                    x0 = 185;
                    x1 = 260;
                    x2 = 335;
                    x3 = 410;
                }
                if (data.length() == 3)
                {
                    three = true;
                    x0 = 225;
                    x1 = 300;
                    x2 = 375;
                }
                if (save0 != ' ' && (seven || six || five || four || three)) { //check if index0 has a letter assigned to it and if the length of the word is 7,6,5,4,3 because all of those lengths have a 0 index
                    if (save0P == 0) // this line is not really needed but I added it because I found it made the code easier to read
                    {
                        String output = Character.toString(save0).toUpperCase(); //change to value of save0 to an uppercase string
                        c.drawString(output, x0, 310); // draw the value of output to the screen with x0 as the x value. x0 corresponds with the length of the letter, I.E if the length of the word is 7, x0 would be the x value of the first
                        //                                   line where correct guesses appear for a length of 7.
                    }
                    //continue this same logic from line 387-422
                }
                if (save1 != ' '  && (seven || six || five || four || three)) {
                    if (save1P == 1) {
                        String output = Character.toString(save1).toUpperCase();
                        c.drawString(output, x1, 310);
                    }
                }
                if (save2 != ' '  && (seven || six || five || four || three)) {
                    if (save2P == 2) {
                        String output = Character.toString(save2).toUpperCase();
                        c.drawString(output, x2, 310);
                    }
                }
                if (save3 != ' ' && (seven || six || five || four)) {
                    if (save3P == 3) {
                        String output = Character.toString(save3).toUpperCase();
                        c.drawString(output, x3, 310);
                    }
                }
                if (save4 != ' ' && (seven || six || five)) {
                    if (save4P == 4) {
                        String output = Character.toString(save4).toUpperCase();
                        c.drawString(output, x4, 310);
                    }
                }
                if (save5 != ' '  && (seven || six)) {
                    if (save5P == 5) {
                        String output = Character.toString(save5).toUpperCase();
                        c.drawString(output, x5, 310);
                    }
                }
                if (save6 != ' '  && (seven)) {
                    if (save6P == 6) {
                        String output = Character.toString(save6).toUpperCase();
                        c.drawString(output, x6, 310);
                    }
                }
            }
            int len; // length of the word variable
            int l = 85; //starting x distance for drawing the lines at the bottom of the screen

            c.drawLine(265, 210, 385, 210); //draw where the hangman hangs
            c.drawLine(325, 210, 325, 60);
            c.drawLine(325, 60, 250, 60);
            c.drawLine(250, 60, 250, 85);


            len = data.length(); //get word length

            if (len == 7) {
                l = 5;
            }
            if (len == 6) {
                l = 45;
            }

            if (len == 5) {
                l = 85;
            }
            //              ------------------> configure starting distance for the lines that appear at the bottom of the screen that correspond to number of letters in the word
            if (len == 4) {
                l = 110;
            }

            if (len == 3) {
                l = 150;
            }

            int i = 0;
            while (i < len) { // while i is less than the number of letters in the word then keep drawing lines
                l += 75; // add to the x distance for a gap between lines
                c.drawLine(l, 310, l + 30, 310);    //draw the lines to the screen
                i++;
            }
        }
    }

    public static void skipLines(Scanner s, int lineNum) {//line skipping method used in GenWord() used for randomly selecting a word
        for (int i = 0; i < lineNum; i++) {
            if (s.hasNextLine()) s.nextLine();
        }
    }

    public static void DrawHead(Console c) { //draw the hangmans head
        if (lives == 5) //if the user lost
        {
            c.setColor(Color.RED); //set colour to red
        }
        c.drawOval(240, 85, 20, 20); //draw the head on the screen
    }

    public static void DrawBody(Console c) {//draw the hangmans body
        if (lives == 5)//if the user lost
        {
            c.setColor(Color.RED);//set colour to red
        }
        c.drawLine(250, 105, 250, 140);//draw the body on the screen
    }

    public static void DrawArmLeft(Console c) {//draw the hangmans left arm
        if (lives == 5)//if the user lost
        {
            c.setColor(Color.RED);//set colour to red
        }
        c.drawLine(250, 122, 230, 95);//draw the left arm on the screen
    }

    public static void DrawArmRight(Console c) {//draw the hangmans right arm
        if (lives == 5)//if the user lost
        {
            c.setColor(Color.RED);//set colour to red
        }
        c.drawLine(250, 122, 270, 95);//draw the right arm on the screen
    }

    public static void DrawLegLeft(Console c) {//draw the hangmans left leg
        if (lives == 5)//if the user lost
        {
            c.setColor(Color.RED);//set colour to red
        }
        c.drawLine(250, 140, 230, 157);//draw the left leg on the screen
    }

    public static void DrawLegRight(Console c) {//draw the hangmans right leg
        if (lives == 5)//if the user lost
        {
            c.setColor(Color.RED);//set colour to red
        }
        c.drawLine(250, 140, 270, 157);//draw the right leg on the screen
    }

    public static void WinScreen(Console c, String data, Font gameOverFont)
    {
        String gameOver = "You Win!";
        String word = "The word was: " + data;
        String tryAgain = "Press \"ENTER\" to restart";
        final String back = "Press ⬅ to return to menu";
        final String exit = "Press \"BACKSPACE\" to exit";
        c.setFont(new Font("e&b", Font.PLAIN, 15));
        delay(500);
        c.clear();
        win = true;
        c.setColor(Color.black);
        c.drawString(back, 0, 430);
        c.drawString(exit, 450, 430);
        c.setColor(Color.green);
        c.setFont(gameOverFont);
        c.drawString(gameOver, 180, 200);
        c.setFont(new Font("Plain", Font.PLAIN, 18));
        c.setColor(Color.black);
        c.drawString(word, 220,20);
        c.drawString(tryAgain, 210, 250);
    }

    public static void CheckWin(Font gameOverFont, String data)
    {
        delay(100);
        if (data.length() == 7 && numCorrect == 7) //if the length of the word is 7 and the user has made 7 correct guessed
        {
            c.setColor(Color.green); //set colour to green
            c.drawString(Character.toString(data.charAt(0)).toUpperCase(), 80, 310);
            c.drawString(Character.toString(data.charAt(1)).toUpperCase(), 155, 310);
            c.drawString(Character.toString(data.charAt(2)).toUpperCase(), 230, 310);
            c.drawString(Character.toString(data.charAt(3)).toUpperCase(), 305, 310); // ----------> redraw the correct word in green
            c.drawString(Character.toString(data.charAt(4)).toUpperCase(), 380, 310);
            c.drawString(Character.toString(data.charAt(5)).toUpperCase(), 455, 310);
            c.drawString(Character.toString(data.charAt(6)).toUpperCase(), 530, 310);
            play("win.wav"); //play the win sound
            delay(700); //delay .700 so the user can see the letter turn green
            WinScreen(c, data, gameOverFont); //show the win screen
            //same logic but with diffirent word lengths and required correct guesses for lines 553-601
        }
        if (data.length() == 6 && numCorrect == 6)
        {
            c.setColor(Color.green);
            c.drawString(Character.toString(data.charAt(0)).toUpperCase(), 120, 310);
            c.drawString(Character.toString(data.charAt(1)).toUpperCase(), 195, 310);
            c.drawString(Character.toString(data.charAt(2)).toUpperCase(), 270, 310);
            c.drawString(Character.toString(data.charAt(3)).toUpperCase(), 345, 310);
            c.drawString(Character.toString(data.charAt(4)).toUpperCase(), 420, 310);
            c.drawString(Character.toString(data.charAt(5)).toUpperCase(), 495, 310);
            play("win.wav");
            delay(700);
            WinScreen(c, data, gameOverFont);
        }
        if (data.length() == 5 && numCorrect == 5)
        {
            c.setColor(Color.green);
            c.drawString(Character.toString(data.charAt(0)).toUpperCase(), 160, 310);
            c.drawString(Character.toString(data.charAt(1)).toUpperCase(), 235, 310);
            c.drawString(Character.toString(data.charAt(2)).toUpperCase(), 310, 310);
            c.drawString(Character.toString(data.charAt(3)).toUpperCase(), 385, 310);
            c.drawString(Character.toString(data.charAt(4)).toUpperCase(), 460, 310);
            play("win.wav");
            delay(700);
            WinScreen(c, data, gameOverFont);
        }
        if (data.length() == 4 && numCorrect == 4)
        {
            c.setColor(Color.green);
            c.drawString(Character.toString(data.charAt(0)).toUpperCase(), 185, 310);
            c.drawString(Character.toString(data.charAt(1)).toUpperCase(), 260, 310);
            c.drawString(Character.toString(data.charAt(2)).toUpperCase(), 335, 310);
            c.drawString(Character.toString(data.charAt(3)).toUpperCase(), 410, 310);
            play("win.wav");
            delay(700);
            WinScreen(c, data, gameOverFont);
        }
        if (data.length() == 3 && numCorrect == 3)
        {
            c.setColor(Color.green);
            c.drawString(Character.toString(data.charAt(0)).toUpperCase(), 225, 310);
            c.drawString(Character.toString(data.charAt(1)).toUpperCase(), 300, 310);
            c.drawString(Character.toString(data.charAt(2)).toUpperCase(), 375, 310);
            play("win.wav");
            delay(700);
            WinScreen(c, data, gameOverFont);
        }

    }




    public static void CheckChar(Console c, char letter)
    {
        //reset index1, 2 and 3 to negative 2. Negative 2 because that value is unreachable in my program so it will decrease possible amount bugs
        index1 = -2;
        index2 = -2;
        index3 = -2;
        boolean letterIn = false; //if the guessed letter is already in the letters array
        int index = data.indexOf(letter); //index of the guessed letter in the word
        for (int i = 1; i < data.length(); i++) //cycle through elements of the letters array
        {
            try {
                if (data.charAt(data.indexOf(letter, index + i)) == letter) { //if the character at the first index of the guessed letter plus the current value of i is equal to the letter
                    while (true) { //endless while loop so the program can break this loop and enter the for loop again when an index is found
                        if (index1 == -2) { //if index1 has not been changed
                            index1 = data.indexOf(letter, index + i); //assign the current index plus the i value to index1
                            break; //break the while loop to enter the for loop
                        }
                        if (index2 == -2) {
                            index2 = data.indexOf(letter, index + i);
                            break;
                        }
                        if (index3 == -2) {
                            index3 = data.indexOf(letter, index + i);
                            break;
                        }
                    }
                }
            }
            catch (java.lang.StringIndexOutOfBoundsException e) //if there is an outofbounds error
            {
                break; //exit the loop
            }
        }
        for (int i = 0; i < letters.length; i++) //cycle through elements of letters array
        {
            if (letters[i] == letter) //if the current index of letters is equal to letter
            {
                letterIn = true; //then the current letter has already been chosen
            }
        }
        if (!letterIn) { //if the letter chosen has not been chosen before
            Font gameOverFont = new Font("Serif", Font.ITALIC, 75);
            if (index == -1) { //if the letter is not in the word
                place++; //add one to place, this will be the index that the letter will be placed in the letters array
                letters[place] = letter; //place the guessed letter in the array letters at index place which adds one each time an incorrect guess is made
                x += 75;
                PlaceLetterDone(c, letter, x);
                allLetters = allLetters + letter; //add current letter to all letters string
                if (lives == 0) { //if first incorrect guess
                    play("fail-buzzer-01.wav"); //play incorrect guess sound
                    DrawHead(c); //draw the head
                    delay(500);// delay .500 so the whole hangman wont be drawn - code runs too fast so when the user taps a key once it runs the code multiple times
                } else if (lives == 1) {//draw body if second incorrect guess
                    play("fail-buzzer-01.wav");
                    DrawBody(c);
                    delay(500);
                } else if (lives == 2) {//draw left arm if third incorrect guess
                    play("fail-buzzer-01.wav");
                    DrawArmLeft(c);
                    delay(500);
                } else if (lives == 3) {//draw right arm if fourth incorrect guess
                    play("fail-buzzer-01.wav");
                    DrawArmRight(c);
                    delay(500);
                } else if (lives == 4) {//draw left leg if fifth incorrect guess
                    play("fail-buzzer-01.wav");
                    DrawLegLeft(c);
                    delay(500);
                } else if (lives == 5) {//draw right leg if sixth incorrect guess and paint game over to screen, also repaint the hangman red
                    play("loseGame.wav");
                    DrawHead(c);
                    DrawBody(c); // ----------> because lives is 5 when the hangman is painted the colour will be red
                    DrawArmLeft(c);
                    DrawArmRight(c);
                    DrawLegLeft(c);
                    DrawLegRight(c);
                    String gameOver = "Game Over";
                    String word = "The word was: " + data;
                    String tryAgain = "Press \"ENTER\" to restart";
                    final String back = "Press ⬅ to return to menu";
                    final String exit = "Press \"BACKSPACE\" to exit";
                    lose = true; //set lose to true because the user lost
                    delay(1500);
                    c.clear();
                    c.setColor(Color.red);
                    c.setFont(gameOverFont);
                    c.drawString(gameOver, 150, 200);
                    c.setFont(new Font("Plain", Font.PLAIN, 18));
                    c.setColor(Color.black);
                    c.drawString(tryAgain, 220, 250);
                    c.drawString(word, 220, 20);
                    c.setFont(new Font("e&b", Font.PLAIN, 15));
                    c.drawString(back, 0, 430);
                    c.drawString(exit, 450, 430);

                }

                lives++; //add one to lives
            } else {

                if (data.length() == 7) { //if the length of the word is 7
                    if ((index == 0 || index1 == 0 || index2 == 0 || index3 == 0) && !seven0 ) { //if the letter is at index 0 and length seven index zero has not been chosen yet, draw    |  the selected letter to the screen. Also if index1, 2 and 3 are equal to 0 opposed to their -2 default this means that there are multiple of the same letters in the, draw the letter to the screen at index 0.
                        play("correct.wav"); //play the correct sound                                                                                                               |
                        save0 = letter; // make save0 equal the newly guessed letters, save0 will be drawn to the screen when the user resumes the game after returning to the menu         |
                        String output = Character.toString(letter).toUpperCase(); //change char to string and change to uppercase                                                           |
                        c.drawString(output, 80, 310); // then draw to screen                                                                                                        | ---------> Same logic for lines 722-961, just checks diffirent indexes and word lengths
                        numCorrect++;//add one to the number of correct answers                                                                                                             |
                        seven0 = true;// length of seven index 0 is now true because the user just selected it                                                                              |
                        CheckWin(gameOverFont, data);// check if the user has won                                                                                                           |
                    }
                    if ((index == 1 || index1 == 1 || index2 == 1 || index3 == 1) && !seven1) {
                        play("correct.wav");
                        save1 = letter;
                        String output = Character.toString(letter).toUpperCase();
                        c.drawString(output, 155, 310);
                        numCorrect++;
                        seven1 = true;
                        CheckWin(gameOverFont, data);

                    }

                    if ((index == 2 || index1 == 2 || index2 == 2 || index3 == 2) && !seven2) {
                        play("correct.wav");
                        save2 = letter;
                        String output = Character.toString(letter).toUpperCase();
                        c.drawString(output, 230, 310);
                        numCorrect++;
                        seven2 = true;
                        CheckWin(gameOverFont, data);
                    }

                    if ((index == 3 || index1 == 3 || index2 == 3 || index3 == 3) && !seven3) {
                        play("correct.wav");
                        save3 = letter;
                        String output = Character.toString(letter).toUpperCase();
                        c.drawString(output, 305, 310);
                        numCorrect++;
                        seven3 = true;
                        CheckWin(gameOverFont, data);
                    }


                    if ((index == 4 || index1 == 4 || index2 == 4 || index3 == 4) && !seven4) {
                        play("correct.wav");
                        save4 = letter;
                        String output = Character.toString(letter).toUpperCase();
                        c.drawString(output, 380, 310);
                        numCorrect++;
                        seven4 = true;
                        CheckWin(gameOverFont, data);
                    }

                    if ((index == 5 || index1 == 5 || index2 == 5 || index3 == 5) && !seven5) {
                        play("correct.wav");
                        save5 = letter;
                        String output = Character.toString(letter).toUpperCase();
                        c.drawString(output, 455, 310);
                        numCorrect++;
                        seven5 = true;
                        CheckWin(gameOverFont, data);

                    }

                    if ((index == 6 || index1 == 6 || index2 == 6 || index3 == 6) && !seven6) {
                        play("correct.wav");
                        save6 = letter;
                        String output = Character.toString(letter).toUpperCase();
                        c.drawString(output, 530, 310);
                        numCorrect++;
                        seven6 = true;
                        CheckWin(gameOverFont, data);

                    }

                }

                if (data.length() == 6) {
                    if ((index == 0 || index1 == 0 || index2 == 0 || index3 == 0) && !six0) {
                        play("correct.wav");
                        save0 = letter;
                        String output = Character.toString(letter).toUpperCase();
                        c.drawString(output, 120, 310);
                        numCorrect++;
                        six0 = true;
                        CheckWin(gameOverFont, data);

                    }
                    if ((index == 1 || index1 == 1 || index2 == 1 || index3 == 1) && !six1) {
                        play("correct.wav");
                        save1 = letter;
                        String output = Character.toString(letter).toUpperCase();
                        c.drawString(output, 195, 310);
                        numCorrect++;
                        six1 = true;
                        CheckWin(gameOverFont, data);
                    }
                    if ((index == 2 || index1 == 2 || index2 == 2 || index3 == 2) && !six2) {
                        play("correct.wav");
                        save2 = letter;
                        String output = Character.toString(letter).toUpperCase();
                        c.drawString(output, 270, 310);
                        numCorrect++;
                        six2 = true;
                        CheckWin(gameOverFont, data);
                    }
                    if ((index == 3 || index1 == 3 || index2 == 3 || index3 == 3) && !six3) {
                        play("correct.wav");
                        save3 = letter;
                        String output = Character.toString(letter).toUpperCase();
                        c.drawString(output, 345, 310);
                        numCorrect++;
                        six3 = true;
                        CheckWin(gameOverFont, data);
                    }
                    if ((index == 4 || index1 == 4 || index2 == 4 || index3 == 4) && !six4) {
                        play("correct.wav");
                        save4 = letter;
                        String output = Character.toString(letter).toUpperCase();
                        c.drawString(output, 420, 310);
                        numCorrect++;
                        six4 = true;
                        CheckWin(gameOverFont, data);
                    }
                    if ((index == 5 || index1 == 5 || index2 == 5 || index3 == 5) && !six5) {
                        play("correct.wav");
                        save5 = letter;
                        String output = Character.toString(letter).toUpperCase();
                        c.drawString(output, 495, 310);
                        numCorrect++;
                        six5 = true;
                        CheckWin(gameOverFont, data);
                    }
                }

                if (data.length() == 5) {
                    if ((index == 0 || index1 == 0 || index2 == 0 || index3 == 0) && !five0) {
                        play("correct.wav");
                        save0 = letter;
                        String output = Character.toString(letter).toUpperCase();
                        c.drawString(output, 160, 310);
                        numCorrect++;
                        five0 = true;
                        CheckWin(gameOverFont, data);
                    }
                    if ((index == 1 || index1 == 1 || index2 == 1 || index3 == 1) && !five1) {
                        play("correct.wav");
                        save1 = letter;
                        String output = Character.toString(letter).toUpperCase();
                        c.drawString(output, 235, 310);
                        numCorrect++;
                        five1 = true;
                        CheckWin(gameOverFont, data);
                    }
                    if ((index == 2 || index1 == 2 || index2 == 2 || index3 == 2) && !five2) {
                        play("correct.wav");
                        save2 = letter;
                        String output = Character.toString(letter).toUpperCase();
                        c.drawString(output, 310, 310);
                        numCorrect++;
                        five2 = true;
                        CheckWin(gameOverFont, data);
                    }
                    if ((index == 3 || index1 == 3 || index2 == 3 || index3 == 3) && !five3) {
                        play("correct.wav");
                        save3 = letter;
                        String output = Character.toString(letter).toUpperCase();
                        c.drawString(output, 385, 310);
                        numCorrect++;
                        five3 = true;
                        CheckWin(gameOverFont, data);
                    }
                    if ((index == 4 || index1 == 4 || index2 == 4 || index3 == 4) && !five4) {
                        play("correct.wav");
                        save4 = letter;
                        String output = Character.toString(letter).toUpperCase();
                        c.drawString(output, 460, 310);
                        numCorrect++;
                        five4 = true;
                        CheckWin(gameOverFont, data);
                    }
                }

                if (data.length() == 4) {
                    if ((index == 0 || index1 == 0 || index2 == 0 || index3 == 0) && !four0) {
                        play("correct.wav");
                        save0 = letter;
                        String output = Character.toString(letter).toUpperCase();
                        c.drawString(output, 185, 310);
                        numCorrect++;
                        four0 = true;
                        CheckWin(gameOverFont, data);
                    }
                    if ((index == 1 || index1 == 1 || index2 == 1 || index3 == 1) && !four1) {
                        play("correct.wav");
                        save1 = letter;
                        String output = Character.toString(letter).toUpperCase();
                        c.drawString(output, 260, 310);
                        numCorrect++;
                        four1 = true;
                        CheckWin(gameOverFont, data);
                    }
                    if ((index == 2 || index1 == 2 || index2 == 2 || index3 == 2) && !four2) {
                        play("correct.wav");
                        save2 = letter;
                        String output = Character.toString(letter).toUpperCase();
                        c.drawString(output, 335, 310);
                        numCorrect++;
                        four2 = true;
                        CheckWin(gameOverFont, data);
                    }
                    if ((index == 3 || index1 == 3 || index2 == 3 || index3 == 3) && !four3) {
                        play("correct.wav");
                        save3 = letter;
                        String output = Character.toString(letter).toUpperCase();
                        c.drawString(output, 410, 310);
                        numCorrect++;
                        four3 = true;
                        CheckWin(gameOverFont, data);
                    }
                }

                if (data.length() == 3) {
                    if ((index == 0 || index1 == 0 || index2 == 0 || index3 == 0) && !three0) {
                        play("correct.wav");
                        save0 = letter;
                        String output = Character.toString(letter).toUpperCase();
                        c.drawString(output, 225, 310);
                        numCorrect++;
                        three0 = true;
                        CheckWin(gameOverFont, data);
                    }
                    if ((index == 1 || index1 == 1 || index2 == 1 || index3 == 1) && !three1) {
                        play("correct.wav");
                        save1 = letter;
                        String output = Character.toString(letter).toUpperCase();
                        c.drawString(output, 300, 310);
                        numCorrect++;
                        three1 = true;
                        CheckWin(gameOverFont, data);
                    }
                    if ((index == 2 || index1 == 2 || index2 == 2 || index3 == 2) && !three2) {
                        play("correct.wav");
                        save2 = letter;
                        String output = Character.toString(letter).toUpperCase();
                        c.drawString(output, 375, 310);
                        numCorrect++;
                        three2 = true;
                        CheckWin(gameOverFont, data);
                    }
                }
            }
        }

    }

    public static void PlaceLetterDone(Console c, char letter, int x) //place the letter in the bottom rectangle if the user guessed incorrrectly, takes the letter and x as parameters
    {
        String letterString = Character.toString(letter);//change the char to string so it can be drawn
        c.drawString(letterString, x, 408);//draw the letter to the screen with x value being the x int argument, this value adds 75 every time the user guesses incorrectly
    }

    public static class MainThread  implements Runnable //key event listener thread
    {

        private Console c;//private console vairable accesible from the MainThread class. Needed for passing in parameters

        public MainThread(Console c)//needed for passing in parameters
        {
            this.c = c;//defines the c as the console passed in when the method was called
        }

        public void run () { //main method
            int currentKeyCode;//current key code variable

            while (true) { //infinite loop
                currentKeyCode = c.getKeyCode(); //get the key pressed value
                if (start) //if the user is in the start screen
                {
                    if (!resume) { //if the user did not return to the menu from the main game
                        if (currentKeyCode == 49) { //if the user presses 1
                            c.setColor(Color.green);
                            c.drawString(new String("\"1\""), 235, 170); //paint over the black 1 on screen with a green 1
                            delay(400);
                            level = 1; //set level to one
                            MainGame(c); //run main game
                            start = false; //user is  in main game so start is false
                        }
                        if (currentKeyCode == 50) { //if the user presses 2
                            c.setColor(Color.green);
                            c.drawString(new String("        \"2\""), 235, 170); //paint over the black 2 on screen with a green 2
                            delay(400);
                            level = 2; //set level to two
                            MainGame(c); //run main game
                            start = false; //user is  in main game so start is false
                        }
                        if (currentKeyCode == 51) {//if the user presses 3
                            c.setColor(Color.green);
                            c.drawString(new String("                \"3\""), 235, 170); //paint over the black 3 on screen with a green 3
                            delay(400);
                            level = 3; //set level to three
                            MainGame(c); //run main game
                            start = false; //user is  in main game so start is false
                        }
                    }
                    if (currentKeyCode == 32 && resume) //if space bar is pressed and the user returned to menu from the main game
                    {
                        MainGame(c); //run the main game
                        start = false; //start is no longer true
                    }



                    if (currentKeyCode == Console.VK_SHIFT) //if the user presses shift
                    {
                        Instructions(c); //draw instructions
                        currentKeyCode = Console.VK_UNDEFINED; //reset the current keycode
                    }




                }

                if (currentKeyCode == Console.VK_BACK_SPACE) //if the user presses backspace, user can exit from the main game, start/menu and instructions screens
                {
                    c.clear();
                    final String exit = "Good Bye!";
                    Font exitFont = new Font("Serif", Font.ITALIC, 75);
                    c.setFont(exitFont);
                    c.drawString(exit, 150, 200);
                    delay(700);
                    System.exit(0); //exit the game
                }

                if (currentKeyCode == 37) //if back arrow is pressed
                {
                    if (win || lose) //if they won or lost restart everything
                    {
                        reset = true; //reset the game, I.E. variables
                        resume = false;//set resume to true so screen is painted correctly
                        start = true; //set start to true so user can only press certian keys
                    }
                    if (!inGame) //if they were in the instructions
                    {
                        resume = false; //set resume to false so screen is painted correctly
                    }
                    if (inGame && !win && !lose) //if the user is in the game and they did not lose or win
                    {
                        resume = true; //set resume to true so screen is painted correctly
                        start = true; //set start to true so user can only press certian keys
                    }
                    Start(c);
                }

                if (currentKeyCode == Console.VK_ENTER && (win || lose)) { //if enter is pressed when win or lose is true, I.E. when the user won or lost
                    reset = true; //reset the game, I.E. variables
                    resume = false;
                    MainGame(c); //run the main game
                }

                if (!start) { //if the user is in the main game
                    if (currentKeyCode == 81) //if the letter q is pressed
                    {

                        CheckChar(c, 'q'); //run check char with the char q as a parameter
                        currentKeyCode = Console.VK_UNDEFINED;
                        delay(500); //delay .500 seconds so the user has time to take their finger of the key
                    }
                    //same logic for lines 1102-1272 but selected keys are different
                    if (currentKeyCode == 87) //W
                    {

                        CheckChar(c, 'w');
                        currentKeyCode = Console.VK_UNDEFINED;
                        delay(500);
                    }
                    if (currentKeyCode == 69) //E
                    {
                        currentKeyCode = Console.VK_UNDEFINED;
                        CheckChar(c, 'e');
                        delay(500);
                    }
                    if (currentKeyCode == 82) //R
                    {
                        currentKeyCode = Console.VK_UNDEFINED;
                        CheckChar(c, 'r');
                        delay(500);
                    }
                    if (currentKeyCode == 84) //T
                    {
                        currentKeyCode = Console.VK_UNDEFINED;
                        CheckChar(c, 't');
                        delay(500);
                    }
                    if (currentKeyCode == 89) //Y
                    {

                        CheckChar(c, 'y');
                        currentKeyCode = Console.VK_UNDEFINED;
                        delay(500);
                    }
                    if (currentKeyCode == 85) //U
                    {

                        CheckChar(c, 'u');
                        currentKeyCode = Console.VK_UNDEFINED;
                        delay(500);
                    }
                    if (currentKeyCode == 73) //I
                    {

                        CheckChar(c, 'i');
                        currentKeyCode = Console.VK_UNDEFINED;
                        delay(500);
                    }
                    if (currentKeyCode == 79) //O
                    {

                        CheckChar(c, 'o');
                        currentKeyCode = Console.VK_UNDEFINED;
                        delay(500);
                    }
                    if (currentKeyCode == 80) //P
                    {

                        CheckChar(c, 'p');
                        currentKeyCode = Console.VK_UNDEFINED;
                        delay(500);
                    }
                    if (currentKeyCode == 65) //A
                    {
                        currentKeyCode = Console.VK_UNDEFINED;
                        CheckChar(c, 'a');
                        delay(500);
                    }
                    if (currentKeyCode == 83) //S
                    {

                        CheckChar(c, 's');
                        currentKeyCode = Console.VK_UNDEFINED;
                        delay(500);
                    }
                    if (currentKeyCode == 68) //D
                    {

                        CheckChar(c, 'd');
                        currentKeyCode = Console.VK_UNDEFINED;
                        delay(500);
                    }
                    if (currentKeyCode == 70) //F
                    {

                        CheckChar(c, 'f');
                        currentKeyCode = Console.VK_UNDEFINED;
                        delay(500);
                    }
                    if (currentKeyCode == 71) //G
                    {

                        CheckChar(c, 'g');
                        currentKeyCode = Console.VK_UNDEFINED;
                        delay(500);
                    }
                    if (currentKeyCode == 72) //H
                    {

                        CheckChar(c, 'h');
                        currentKeyCode = Console.VK_UNDEFINED;
                        delay(500);
                    }
                    if (currentKeyCode == 74) //J
                    {

                        CheckChar(c, 'j');
                        currentKeyCode = Console.VK_UNDEFINED;
                        delay(500);
                    }
                    if (currentKeyCode == 75) //K
                    {

                        CheckChar(c, 'k');
                        currentKeyCode = Console.VK_UNDEFINED;
                        delay(500);
                    }
                    if (currentKeyCode == 76) //L
                    {

                        CheckChar(c, 'l');
                        currentKeyCode = Console.VK_UNDEFINED;
                        delay(500);
                    }
                    if (currentKeyCode == 90) //Z
                    {

                        CheckChar(c, 'z');
                        currentKeyCode = Console.VK_UNDEFINED;
                        delay(500);
                    }
                    if (currentKeyCode == 88) //X
                    {

                        CheckChar(c, 'x');
                        currentKeyCode = Console.VK_UNDEFINED;
                        delay(500);
                    }
                    if (currentKeyCode == 67) //C
                    {

                        CheckChar(c, 'c');
                        currentKeyCode = Console.VK_UNDEFINED;
                        delay(500);
                    }
                    if (currentKeyCode == 86) //V
                    {

                        CheckChar(c, 'v');
                        currentKeyCode = Console.VK_UNDEFINED;
                        delay(500);
                    }
                    if (currentKeyCode == 66) //B
                    {

                        CheckChar(c, 'b');
                        currentKeyCode = Console.VK_UNDEFINED;
                        delay(500);
                    }
                    if (currentKeyCode == 78) //N
                    {

                        CheckChar(c, 'n');
                        currentKeyCode = Console.VK_UNDEFINED;
                        delay(500);
                    }
                    if (currentKeyCode == 77) //M
                    {

                        CheckChar(c, 'm');
                        currentKeyCode = Console.VK_UNDEFINED;
                        delay(500);
                    }
                }
            }
        }
    }


    public static void delay(int milli){ //delays
        try{
            Thread.sleep(milli);
        }
        catch(Exception e){
            ;
        }
    }
}