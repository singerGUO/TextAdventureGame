import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import static jdk.internal.org.objectweb.asm.util.Printer.TYPES;

class GameFrame extends JFrame {
    private static Container gameContainer;
    private static JPanel titleNamePanel, startButtonPanel, backGroundStoryButtonPanel, mainTextPanel, choiceButtonPanel, playerPanel, backGroundStoryPanel;
    private static JLabel titleNameLabel, playerPanelLabel, hpLabel, hpLabelNumber, weaponLabel, weaponLabelName, attackLabel, attackLabelNumber;
    private static Font titleFont = new Font("Times New Roman", Font.PLAIN, 40);
    private static Font normalFont = new Font("Times New Roman", Font.PLAIN, 28);
    private static Font playerLabelFont = new Font("Times New Roman", Font.PLAIN, 22);
    private static JButton startButton, backGroundStoryButton, choiceButton, choiceButton1, choiceButton2, choiceButton3, choiceButton4;
    private static JTextArea mainTextArea, backGroundTextArea, backGroundStoryTitle;
    private static final int windowWidth = 800, windowHeight = 600;
    private static IntroScreenHandler introScreenHandler = new IntroScreenHandler();
    private static TitleScreenHandler titleScreenHandler = new TitleScreenHandler();
    private static ChoiceHandler choiceHandler = new ChoiceHandler();
    private static boolean drinkSpring = false, banditDefeated = false;
    private static int monsterCount =0;

    //stats
    private static String[] monsterName = {"Bandit","Wild Beast", "Dragon"};
    private static int[] monsterHP = {20,40,60};
    private static int[] monsterAttack = {4,15,20};
    private static int playerHP,  playerAttack;
    private static String weapon, position;


    public GameFrame(){
        setSize(windowWidth, windowHeight);
        getContentPane().setBackground(Color.BLACK);
        setLayout(null);

        gameContainer = getContentPane();
        createTitleNamePanel();
        createTitleNameLabel();
        createStartButtonPanel();
        createStartButton();

        titleNamePanel.add(titleNameLabel);
        startButtonPanel.add(startButton);

        gameContainer.add(titleNamePanel);
        gameContainer.add(startButtonPanel);
    }

    private static void createTitleNamePanel() {
        titleNamePanel = new JPanel();
        titleNamePanel.setBounds(100, 100, 600, 150);
        titleNamePanel.setBackground(Color.black);
    }

    private static JLabel createTitleNameLabel() {
        titleNameLabel = new JLabel("The Adventure of the bigBoi");
        titleNameLabel.setForeground(Color.white);
        titleNameLabel.setFont(titleFont);
        return titleNameLabel;
    }

    private static void createStartButtonPanel() {
        startButtonPanel = new JPanel();
        startButtonPanel.setBounds(300, 400, 200, 100);
        startButtonPanel.setBackground(Color.black);
    }

    private static void createStartButton() {
        startButton = new JButton("START");
        startButton.setBackground(Color.black);
        startButton.setForeground(Color.RED);
        startButton.setFont(normalFont);
        startButton.addActionListener(titleScreenHandler);
    }

    private static void createGameScreen() {
        backGroundStoryPanel.setVisible(false);
        backGroundStoryButtonPanel.setVisible(false);

        createMainTextPanel();

        createButtonPanel();

        createPlayerPanel();

        playerSetup();
    }

    private static void createMainTextPanel(){
        mainTextPanel = new JPanel();
        mainTextPanel.setBounds(100, 100, 600, 250);
        mainTextPanel.setBackground(Color.BLACK);
        gameContainer.add(mainTextPanel);

        createMainTextArea();
    }

    private static void createMainTextArea(){
        mainTextArea = new JTextArea();
        mainTextArea.setBounds(100, 100, 600, 250);
        mainTextArea.setBackground(Color.black);
        mainTextArea.setForeground(Color.white);
        mainTextArea.setFont(normalFont);
        mainTextArea.setLineWrap(true);
        mainTextPanel.add(mainTextArea);
    }

    private static void createButtonPanel(){
        choiceButtonPanel = new JPanel();
        choiceButtonPanel.setBounds(250, 350, 300, 150);
        choiceButtonPanel.setBackground(Color.BLACK);
        choiceButtonPanel.setLayout(new GridLayout(4, 1));
        gameContainer.add(choiceButtonPanel);

        choiceButton1 = newChoiceButton("Choice 1", "c1");
        choiceButton2 = newChoiceButton("Choice 2", "c2");
        choiceButton3 = newChoiceButton("Choice 3", "c3");
        choiceButton4 = newChoiceButton("Choice 4", "c4");

        choiceButtonPanel.add(choiceButton1);
        choiceButtonPanel.add(choiceButton2);
        choiceButtonPanel.add(choiceButton3);
        choiceButtonPanel.add(choiceButton4);
    }

    private static JButton newChoiceButton(String buttonName, String actionCommandName) {
        choiceButton = new JButton(buttonName);
        choiceButton.setForeground(Color.BLACK);
        choiceButton.setFont(normalFont);
        choiceButton.setFocusPainted(false);
        choiceButton.addActionListener(choiceHandler);
        choiceButton.setActionCommand(actionCommandName);
        return choiceButton;
    }

    private static void createPlayerPanel() {
        playerPanel = new JPanel();
        playerPanel.setBounds(100, 15, 600, 50);
        playerPanel.setBackground(Color.BLACK);
        playerPanel.setLayout(new GridLayout(1, 6));
        gameContainer.add(playerPanel);

        attackLabel = newPlayerPanelLabels("Attack: ");
        attackLabelNumber = newPlayerPanelLabels("");
        hpLabel = newPlayerPanelLabels("HP: ");
        hpLabelNumber = newPlayerPanelLabels("");
        weaponLabel = newPlayerPanelLabels("Weapon: ");
        weaponLabelName = newPlayerPanelLabels("");

        playerPanel.add(hpLabel);
        playerPanel.add(hpLabelNumber);
        playerPanel.add(weaponLabel);
        playerPanel.add(weaponLabelName);
        playerPanel.add(attackLabel);
        playerPanel.add(attackLabelNumber);
    }

    private static JLabel newPlayerPanelLabels(String labelName){
        playerPanelLabel = new JLabel(labelName);
        playerPanelLabel.setText(labelName);
        playerPanelLabel.setFont(playerLabelFont);
        playerPanelLabel.setForeground(Color.white);
        return playerPanelLabel;
    }

    private static void playerSetup() {
        playerHP = 10;
        playerAttack =5;
        weapon = "Dagger";
        weaponLabelName.setText(weapon);
        hpLabelNumber.setText("" + playerHP);
        attackLabelNumber.setText("" + playerAttack);
        townGate();
    }

    private static void createIntroScreen(){
        titleNamePanel.setVisible(false);
        startButtonPanel.setVisible(false);

        createBackGroundStoryPanel();
        createBackGroundStoryArea();
        createBackGroundStoryTitle();

        createBackGroundStoryButtonPanel();
        createBackGroundStoryButton();

        backGroundStoryButtonPanel.add(backGroundStoryButton);

        gameContainer.add(backGroundStoryPanel);
        gameContainer.add(backGroundStoryButtonPanel);
    }

    private static void createBackGroundStoryPanel(){
        backGroundStoryPanel = new JPanel();
        backGroundStoryPanel.setLayout(null);
        backGroundStoryPanel.setBounds(70, 70, windowWidth - 30, windowHeight - 300);
        backGroundStoryPanel.setBackground(Color.BLACK);
    }

    private static void createBackGroundStoryArea(){
        backGroundTextArea = new JTextArea("It is sunday and there is a party in the caslte. \n But you are not invited becasue you are not cool engouh \n go on an adventure to gain some respects!");
        backGroundTextArea.setBounds(50, 100, windowWidth - 50, 200);
        backGroundTextArea.setBackground(Color.BLACK);
        backGroundTextArea.setForeground(Color.green);
        backGroundTextArea.setFont(normalFont);
        backGroundTextArea.setLineWrap(true);
        backGroundStoryPanel.add(backGroundTextArea);
    }

    private static void createBackGroundStoryTitle(){
        backGroundStoryTitle = new JTextArea("Background Story");
        backGroundStoryTitle.setFont(new Font("Serif", Font.BOLD, 48));
        backGroundStoryTitle.setBounds(50, 30, windowWidth - 50, 50);
        backGroundStoryTitle.setBackground(Color.BLACK);
        backGroundStoryTitle.setForeground(Color.YELLOW);
        backGroundStoryPanel.add(backGroundStoryTitle);
    }

    private static void createBackGroundStoryButtonPanel() {
        backGroundStoryButtonPanel = new JPanel();
        backGroundStoryButtonPanel.setBounds(290, 400, 200, 100);
        backGroundStoryButtonPanel.setBackground(Color.black);
    }

    private static void createBackGroundStoryButton() {
        backGroundStoryButton = new JButton("START");
        backGroundStoryButton.setBackground(Color.black);
        backGroundStoryButton.setForeground(Color.RED);
        backGroundStoryButton.setFont(normalFont);
        backGroundStoryButton.addActionListener(introScreenHandler);
    }

    private static void townGate() {
        position = "townGate";
        mainTextArea.setText("You are at the gate of the town. \nA guard is standing in front of you. \n\nWhat do you do?");
        choiceButton1.setText("Talk to the guard");
        choiceButton2.setText("Attack the guard");
        choiceButton3.setText("Leave");
        choiceButton4.setText("");
    }

    private static void talkGuard() {
        position = "talkGuard";
        mainTextArea.setText("Guard: show me your invitation ");
        choiceButton1.setText(">");
        choiceButton2.setText("");
        choiceButton3.setText("");
        choiceButton4.setText("");
    }

    private static void attackGuard() {
        position = "attackGuard";
        mainTextArea.setText("Guard: Hey don't be stupid!\n\nThe guard fought back and hit you hard.\n(You receive 3 damage)");
        playerHP = playerHP - 2;
        hpLabelNumber.setText("" + playerHP);
        choiceButton1.setText(">");
        choiceButton2.setText("");
        choiceButton3.setText("");
        choiceButton4.setText("");
    }

    private static void crossRoad() {
        position = "crossRoad";
        mainTextArea.setText("You are in the middle of nowhere.\nIf you go south, you will go back to the town.");
        choiceButton1.setText("Go north");
        choiceButton2.setText("Go east");
        choiceButton3.setText("Go south");
        choiceButton4.setText("Go west");
    }

    private static void north() {
        position = "north";
        if(!drinkSpring) {
            mainTextArea.setText("There is a river. \nYou drank the water and it made you feel good. \n\n(Your attack increased by 5)");
            playerAttack += 5;
            attackLabelNumber.setText("" + playerAttack);
            drinkSpring = true;
        }
        else{
            mainTextArea.setText("You are not thirsty anymore.");
        }
        hpLabelNumber.setText("" + playerHP);
        choiceButton1.setText("Go south");
        choiceButton2.setText("");
        choiceButton3.setText("");
        choiceButton4.setText("");
    }

    private static void east() {
        position = "east";
        if(!banditDefeated) {
            mainTextArea.setText("You walked into a forest and you encounter a " + monsterName[monsterCount]);
            choiceButton1.setText("fight");
            choiceButton2.setText("run");
            choiceButton3.setText("");
            choiceButton4.setText("");
        }
        else{
            mainTextArea.setText("You walked into a forest. The path looks safe. ");
            choiceButton1.setText("Go inside");
            choiceButton2.setText("run");
            choiceButton3.setText("");
            choiceButton4.setText("");
        }
    }

    private static void west() {
        position = "west";
        mainTextArea.setText("You walked into a Wisdom Village.");
        choiceButton1.setText("Go East");
        choiceButton2.setText("");
        choiceButton3.setText("");
        choiceButton4.setText("");
    }

    private static void fight() {
        position = "fight";
        mainTextArea.setText("Enemy HP: " + monsterHP[0] + "\n\nWhat do you do?");
        choiceButton1.setText("Attack");
        choiceButton2.setText("");
        choiceButton3.setText("");
        choiceButton4.setText("");
    }

    private static void playerAttack() {
        position = "playerAttack";

        mainTextArea.setText("You attacked " + monsterName[monsterCount]+". Delivered " + playerAttack + " damage!");

        monsterHP[monsterCount] = monsterHP[monsterCount] - playerAttack;

        choiceButton1.setText(">");
        choiceButton2.setText("");
        choiceButton3.setText("");
        choiceButton4.setText("");
    }

    private static void monsterAttack() {
        position = "monsterAttack";



        mainTextArea.setText("The"+monsterName[monsterCount]+" attacked you and gave " + monsterAttack[monsterCount] + " damage!");

        playerHP = playerHP - monsterAttack[monsterCount];
        hpLabelNumber.setText("" + playerHP);

        choiceButton1.setText(">");
        choiceButton2.setText("");
        choiceButton3.setText("");
        choiceButton4.setText("");
    }

    private static void win() {
        position = "win";

        if(monsterHP[0] < 1){
            mainTextArea.setText("You defeated the" + monsterName[monsterCount] + " !");
            mainTextArea.setText("You obtained a sword from the bandit!\nAttack damage + 2 ");
            weapon = "Sword";
            weaponLabelName.setText(weapon);
            playerAttack += 2;
            attackLabelNumber.setText("" + playerAttack);

            banditDefeated = true;
            monsterHP[0] = 2;
        }

        choiceButton1.setText("Go back");
        choiceButton2.setText("");
        choiceButton3.setText("");
        choiceButton4.setText("");
    }

    private static void lose() {
        position = "lose";
        mainTextArea.setText("You are dead!\n\n");
        disableChoices();
    }

    private static void ending() {
        position = "ending";
        mainTextArea.setText("Guard: Wow! you are so cool, i guess i can let you in!");
        disableChoices();
    }

    private static void disableChoices(){
        choiceButton1.setText("");
        choiceButton2.setText("");
        choiceButton3.setText("");
        choiceButton4.setText("");
        choiceButton1.setVisible(false);
        choiceButton2.setVisible(false);
        choiceButton3.setVisible(false);
        choiceButton4.setVisible(false);
    }

    public static class TitleScreenHandler implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            createIntroScreen();
        }
    }

    public static class IntroScreenHandler implements ActionListener {
        public void actionPerformed(ActionEvent event){
            createGameScreen();
        }
    }

    public static class ChoiceHandler implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String yourChoice = event.getActionCommand();
            switch (position) {
                case "townGate":
                    switch (yourChoice) {
                        case "c1":
                            if (monsterHP[2] < 1)
                                ending();
                            else
                                talkGuard();
                            break;
                        case "c2":
                            attackGuard();
                            if(playerHP < 1)
                                lose();
                            break;
                        case "c3":
                            crossRoad();
                            break;
                    }
                    break;
                case "talkGuard":
                    switch (yourChoice) {
                        case "c1":
                            townGate();
                            break;
                    }
                    break;
                case "attackGuard":
                    switch (yourChoice) {
                        case "c1":
                            townGate();
                            break;
                    }
                    break;
                case "crossRoad":
                    switch (yourChoice) {
                        case "c1":
                            north();
                            break;
                        case "c2":
                            east();
                            break;
                        case "c3":
                            townGate();
                            break;
                        case "c4":
                            west();
                            break;
                    }
                    break;
                case "north":
                    switch (yourChoice) {
                        case "c1":
                            crossRoad();
                            break;
                    }
                    break;
                case "east":
                    switch (yourChoice) {
                        case "c1":
                            if(choiceButton1.getText().equals("Go inside")){
                                //Forest();
                            }
                            else fight();
                            break;
                        case "c2":
                            crossRoad();
                    }
                    break;
                case "west":
                    switch (yourChoice) {
                        case "c1":
                            crossRoad();
                            break;
                    }
                    break;
                case "fight":
                    switch (yourChoice) {
                        case "c1":
                            playerAttack();
                            break;
                    }
                    break;
                case "playerAttack":
                    switch (yourChoice) {
                        case "c1":
                            if (monsterHP[monsterCount] < 1) {
                                win();
                                monsterCount++;
                                banditDefeated = true;
                            } else {
                                monsterAttack();
                            }
                            break;
                    }
                    break;
                case "monsterAttack":
                    switch (yourChoice) {
                        case "c1":
                            if (playerHP < 1) {
                                lose();
                            } else {
                                fight();
                            }
                            break;
                    }
                    break;
                case "win":
                    switch (yourChoice) {
                        case "c1":
                            crossRoad();
                    }
                    break;
            }
        }
    }
}
