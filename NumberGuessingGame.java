import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;

public class NumberGuessingGame extends JFrame implements ActionListener {
	private static final int MIN_RANGE = 1;
    private static final int MAX_RANGE = 100;
    private static final int DEFAULT_ATTEMPTS = 5;
    private static final int POINTS_PER_ATTEMPT = 10;

    private int numberOfAttempts;
    private int score;
    private int highScore;

    private JLabel titleLabel;
    private JLabel guessLabel;
    private JTextField guessTextField;
    private JButton submitButton;
    private JTextArea feedbackTextArea;
    private JLabel scoreLabel;
    private JLabel highScoreLabel;
    private ImageIcon backgroundImage;

    public NumberGuessingGame() {
        setTitle("Number Guessing Game");
        setSize(600,600); // Set the size of the window
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Load background image
        try {
            URL imageUrl = new URL("https://wallpaperaccess.com/full/202978.jpg");
            BufferedImage image = ImageIO.read(imageUrl);
            backgroundImage = new ImageIcon(image);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setLayout(new BorderLayout());
        add(backgroundLabel);

        // Add title label
        titleLabel = new JLabel("Welcome to the Number Guessing Game!", JLabel.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Centurion Old", Font.BOLD, 40));
        backgroundLabel.add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setOpaque(false); // Make the panel transparent
        backgroundLabel.add(centerPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        // Add guess label
        guessLabel = new JLabel("Enter Your Guess:");
        guessLabel.setForeground(Color.WHITE);
        guessLabel.setFont(new Font("Centurion Old", Font.BOLD, 30));
        centerPanel.add(guessLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;

        // Add guess text field
        guessTextField = new JTextField(10);
        guessTextField.setFont(new Font("Arial", Font.PLAIN, 25)); // Set font size for the text field
        centerPanel.add(guessTextField, gbc);

        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.EAST;

        // Add submit button
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        submitButton.setBackground(new Color(255, 140, 0));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Centurion Old", Font.BOLD, 18));
        submitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                submitButton.setBackground(new Color(255, 165, 0));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                submitButton.setBackground(new Color(255, 140, 0));
            }
        });
        centerPanel.add(submitButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;

        // Add feedback text area
        feedbackTextArea = new JTextArea(5, 30);
        feedbackTextArea.setFont(new Font("Arial", Font.PLAIN, 24)); // Set font size for the text area
        feedbackTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(feedbackTextArea);
        centerPanel.add(scrollPane, gbc);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(1, 2));
        southPanel.setOpaque(false); // Make the panel transparent
        backgroundLabel.add(southPanel, BorderLayout.SOUTH);

        // Add score label
        scoreLabel = new JLabel("Score: 0", JLabel.CENTER);
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Centurion Old", Font.BOLD, 30)); // Set font size for the score label
        southPanel.add(scoreLabel);

        // Add high score label
        highScoreLabel = new JLabel("High Score: 0", JLabel.CENTER);
        highScoreLabel.setForeground(Color.WHITE);
        highScoreLabel.setFont(new Font("Centurion Old", Font.BOLD, 30)); // Set font size for the high score label
        southPanel.add(highScoreLabel);

        numberOfAttempts = DEFAULT_ATTEMPTS;
        score = 0;
        highScore = 0;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String userInput = guessTextField.getText().trim();
            if (!userInput.matches("\\d+")) {
                feedbackTextArea.setText("Error: Please enter a valid number.");
                return;
            }

            int userGuess = Integer.parseInt(userInput);
            if (userGuess < MIN_RANGE || userGuess > MAX_RANGE) {
                feedbackTextArea.setText("Error: Please guess a number between " + MIN_RANGE + " and " + MAX_RANGE + ".");
                return;
            }

            int randomNumber = MIN_RANGE + (int) (Math.random() * ((MAX_RANGE - MIN_RANGE) + 1));

            if (userGuess == randomNumber) {
                feedbackTextArea.setText("Congratulations! You guessed the correct number.");
                score += numberOfAttempts * POINTS_PER_ATTEMPT;
                scoreLabel.setText("Score: " + score);
                if (score > highScore) {
                    highScore = score;
                    highScoreLabel.setText("High Score: " + highScore);
                }
                numberOfAttempts = DEFAULT_ATTEMPTS; // Reset attempts for next game
            } else {
                numberOfAttempts--;
                if (numberOfAttempts > 0) {
                    feedbackTextArea.setText("Incorrect guess! You have " + numberOfAttempts + " attempts remaining.\n" +
                            (userGuess < randomNumber ? "Try a higher number." : "Try a lower number."));
                } else {
                    feedbackTextArea.setText("Sorry, you've run out of attempts.\nThe correct number was: " + randomNumber);
                    numberOfAttempts = DEFAULT_ATTEMPTS; // Reset attempts for next game
                    score = 0; // Reset score for next game
                    scoreLabel.setText("Score: " + score);
                }
            }

            guessTextField.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NumberGuessingGame game = new NumberGuessingGame();
            game.setVisible(true);
        });
    }
}
