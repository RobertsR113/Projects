using Casino.MainScreens;
using System.Text.RegularExpressions;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Media;

namespace Casino.Games
{
    /// <summary>
    /// Interaction logic for Roulette.xaml
    /// </summary>
    public partial class Roulette : Window
    {
        private readonly MediaPlayer rouletteSpinSound = new();
        private readonly MediaPlayer loseSound = new();
        private readonly MediaPlayer victorySound = new();

        private readonly User currentUser;

        private int bettingAmount = 0;
        public Roulette(User user)
        {
            currentUser = user;
            InitializeComponent();

            onRedClick.Opacity = 0;
            onBlackClick.Opacity = 0;
            onOddClick.Opacity = 0;
            onEvenClick.Opacity = 0;
            specificNrLabel.Opacity = 0;
            specificNr.Opacity = 0;
            enterClickButton.Opacity = 0;
            winningChoice.Opacity = 0;
            winningChoice2.Opacity = 0;
            winningNr.Opacity = 0;
            playAgainButton.Opacity = 0;

            RouletteSpin.Opacity = 0;


            rouletteSpinSound.Open(new Uri(System.IO.Path.Combine(AppDomain.CurrentDomain.BaseDirectory, "Sounds/Roulette/WheelSpin.mp3")));
            rouletteSpinSound.Volume = 1.0;
            loseSound.Open(new Uri(System.IO.Path.Combine(AppDomain.CurrentDomain.BaseDirectory, "Sounds/BlackJack/WompWomp.mp3")));
            loseSound.Volume = 1.0;
            victorySound.Open(new Uri(System.IO.Path.Combine(AppDomain.CurrentDomain.BaseDirectory, "Sounds/BlackJack/Victory.mp3")));
            victorySound.Volume = 1.0;
        }
        private void onEnterBetClick(object sender, RoutedEventArgs e)
        {
            betAmount.Text = betAmount.Text.Replace(" ", "");
            string isNr = "^[0-9]+$";
            if (string.IsNullOrEmpty(betAmount.Text))
                messageDisplay.Content = "Please enter an amount!";
            else if (!Regex.IsMatch(betAmount.Text, isNr))
                messageDisplay.Content = "Invalid input!";
            else if (int.Parse(betAmount.Text) <= 0)
                messageDisplay.Content = "Bet amount must be more than 0!";
            else if (currentUser.Points < int.Parse(betAmount.Text.ToString()))
                messageDisplay.Content = "You don't have enough points! \nYour points: " + currentUser.Points;
            else
            {
                messageDisplay.Foreground = Brushes.Lime;
                messageDisplay.Content = "Success! You may choose now!";
                bettingAmount = int.Parse(betAmount.Text);

                betAmount.Opacity = 0;
                betAmountLabel.Opacity = 0;
                enterButton.Opacity = 0;

                enterClickButton.Opacity = 1;
                onRedClick.Opacity = 1;
                onBlackClick.Opacity = 1;
                onOddClick.Opacity = 1;
                onEvenClick.Opacity = 1;
                specificNrLabel.Opacity = 1;
                specificNr.Opacity = 1;
            }
        }

        private Button? userChosenButton = null;
        private string userChosenButtonContent = "empty";
        private void onRedClick_Click(object sender, RoutedEventArgs e)
        {
            if (onRedClick.Opacity != 0)
                chosenButton(onRedClick);
        }

        private void onBlackClick_Click(object sender, RoutedEventArgs e)
        {
            if (onBlackClick.Opacity != 0)
                chosenButton(onBlackClick);
        }

        private void onEvenClick_Click(object sender, RoutedEventArgs e)
        {
            if (onEvenClick.Opacity != 0)
                chosenButton(onEvenClick);
        }

        private void onOddClick_Click(object sender, RoutedEventArgs e)
        {
            if (onOddClick.Opacity != 0)
                chosenButton(onOddClick);
        }
        private void chosenButton(Button selectedButton)
        {
            Button[] buttons = { onRedClick, onBlackClick, onEvenClick, onOddClick };

            foreach (var btn in buttons)
            {
                btn.BorderBrush = new SolidColorBrush(Colors.Transparent);
                btn.BorderThickness = new Thickness(1);
            }

            selectedButton.BorderBrush = new SolidColorBrush(Colors.LightGreen);
            selectedButton.BorderThickness = new Thickness(5);

            userChosenButton = selectedButton;
            userChosenButtonContent = (string)selectedButton.Content;
        }

        private void MoveWinningNr(int number)
        {
            List<int> row1 = new List<int> { 3, 6, 9, 12, 15, 18, 21, 24, 27, 30, 33, 36 };
            List<int> row2 = new List<int> { 2, 5, 8, 11, 14, 17, 20, 23, 26, 29, 32, 35 };
            List<int> row3 = new List<int> { 1, 4, 7, 10, 13, 16, 19, 22, 25, 28, 31, 34 };

            int rowIndex = 0;
            int colIndex = 0;

            if (row1.Contains(number))
                colIndex = row1.IndexOf(number);
            else if (row2.Contains(number))
            {
                rowIndex++;
                colIndex = row2.IndexOf(number);
            }
            else if (row3.Contains(number))
            {
                rowIndex += 2;
                colIndex = row3.IndexOf(number);
            }

            int startLeft = 307;
            int startTop = 9;

            int horizontalStep = 40;
            int verticalStep = 53;

            int newLeft = startLeft + (colIndex * horizontalStep);
            int newTop = startTop + (rowIndex * verticalStep);

            Canvas.SetLeft(winningNr, newLeft);
            Canvas.SetTop(winningNr, newTop);
            winningNr.Opacity = 1;
        }

        private void MoveWinningChoice(string choice, Image image)
        {
            int startLeft = 384;
            int startTop = 161;
            int horizontalStep = 79;

            List<string> choices = new List<string> { "even", "red", "black", "odd" };

            int colIndex = choices.IndexOf(choice);

            int newLeft = startLeft + (colIndex * horizontalStep);
            int newTop = startTop;

            Canvas.SetLeft(image, newLeft);
            Canvas.SetTop(image, newTop);

            winningChoice.Opacity = 1;
            winningChoice2.Opacity = 1;
        }

        private void onEnterClick(object sender, RoutedEventArgs e)
        {
            if (enterClickButton.Opacity == 0)
                return;
            if (!string.IsNullOrEmpty(specificNr.Text))
            {
                if (validNrGuessInput())
                    startRoulette();
            }
            else if (userChosenButton != null)
                startRoulette();
            else
            {
                messageDisplay.Foreground = Brushes.DarkRed;
                messageDisplay.Content = "You haven't chosen anything!";
            }
        }
        private bool validNrGuessInput()
        {
            specificNr.Text = specificNr.Text.Replace(" ", "").Trim();

            string isValidNr = @"^([1-9]|1[0-9]|2[0-9]|3[0-6])$";

            if (string.IsNullOrEmpty(specificNr.Text))
            {
                messageDisplay.Foreground = Brushes.DarkRed;
                messageDisplay.Content = "Please enter an amount!";
                return false;
            }
            else if (!Regex.IsMatch(specificNr.Text, isValidNr))
            {
                messageDisplay.Foreground = Brushes.DarkRed;
                messageDisplay.Content = "Invalid input! (From 0 to 36!)";
                return false;
            }
            return true;
        }

        private async void startRoulette() 
        {
            messageDisplay.Foreground = Brushes.Black;
            messageDisplay.Content = "You chose: ";
            if (!string.IsNullOrEmpty(specificNr.Text))
                messageDisplay.Content += " Nr: " + specificNr.Text;
            if (userChosenButton != null)
                messageDisplay.Content += " " + userChosenButtonContent;

            RouletteSpin.Opacity = 1;
            Grid.SetZIndex(RouletteSpin, 9999);
            rouletteSpinSound.Stop();
            rouletteSpinSound.Position = TimeSpan.Zero;
            rouletteSpinSound.Play();
            await Task.Delay(5000);

            Random random = new Random();
            int RandomSystemNr = random.Next(1, 37);
            MoveWinningNr(RandomSystemNr);

            string evenOrOdd = "";
            if (RandomSystemNr % 2 == 0)
            {
                MoveWinningChoice("even", winningChoice);
                evenOrOdd = "even";
            }
            else
            {
                MoveWinningChoice("odd", winningChoice2);
                evenOrOdd = "odd";
            }

            List<int> blackColor = new List<int> { 2, 3, 4, 6, 8, 10, 11, 13, 15, 17, 20, 22, 24, 26, 28, 29, 31, 33, 35 };

            string color = "";
            if (blackColor.Contains(RandomSystemNr))
                color = "black";
            else
                color = "red";
            MoveWinningChoice(color, winningChoice2);

            messageDisplay.Content += "\nWinning: Nr: " + RandomSystemNr + " " + evenOrOdd.ToUpper() + " " + color.ToUpper();

            bool correctNr = false;
            bool correctColor = false;
            bool correctOddOrEven = false;
            if (!string.IsNullOrEmpty(specificNr.Text))
            {
                if (RandomSystemNr == int.Parse(specificNr.Text))
                    correctNr = true;
            }
            if (userChosenButton != null)
            {
                if (userChosenButtonContent.ToLower().Equals(color))
                    correctColor = true;
                if (userChosenButtonContent.ToLower().Equals(evenOrOdd))
                    correctOddOrEven = true;
            }

            if (correctNr && correctColor || correctNr && correctOddOrEven)
            {
                messageDisplay.Content += "\nLEGENDARY!   +" + (bettingAmount * 3);
                messageDisplay.Foreground = Brushes.LimeGreen;
                currentUser.Points += (bettingAmount * 3);
                currentUser.userStats.Add(new Stats(true, bettingAmount));
                currentUser.saveToFile(currentUser);
                victorySound.Stop();
                victorySound.Position = TimeSpan.Zero;
                victorySound.Play();
                playAgainButton.Opacity = 1;
            }
            else if (correctNr || correctColor || correctOddOrEven) 
            {
                messageDisplay.Content += "\nNice!   +" + (bettingAmount * 2);
                messageDisplay.Foreground = Brushes.LimeGreen;
                currentUser.Points += (bettingAmount * 2);
                currentUser.userStats.Add(new Stats(true, bettingAmount));
                currentUser.saveToFile(currentUser);
                victorySound.Stop();
                victorySound.Position = TimeSpan.Zero;
                victorySound.Play();
                playAgainButton.Opacity = 1;
            }
            else
            {
                messageDisplay.Content += "\nWomp womp...   -" + bettingAmount;
                messageDisplay.Foreground = Brushes.Red;
                currentUser.Points -= bettingAmount;
                currentUser.userStats.Add(new Stats(false, bettingAmount));
                currentUser.saveToFile(currentUser);
                loseSound.Stop();
                loseSound.Position = TimeSpan.Zero;
                loseSound.Play();
                playAgainButton.Opacity = 1;
            }
            rouletteSpinSound.Stop();
            RouletteSpin.Opacity = 0;
        }

        private void reset() 
        {
            messageDisplay.Content = "";

            onRedClick.Opacity = 0;
            onBlackClick.Opacity = 0;
            onOddClick.Opacity = 0;
            onEvenClick.Opacity = 0;
            specificNrLabel.Opacity = 0;
            specificNr.Opacity = 0;

            enterClickButton.Opacity = 0;

            winningChoice.Opacity = 0;
            winningChoice2.Opacity = 0;
            winningNr.Opacity = 0;

            RouletteSpin.Opacity = 0;

            betAmount.Opacity = 1;
            betAmountLabel.Opacity = 1;
        }

        private void onExitClick(object sender, RoutedEventArgs e)
        {
            currentUser.saveToFile(currentUser);
            GamesScreen gameScreen = new GamesScreen(currentUser);
            gameScreen.gameManager(currentUser, false);
            gameScreen.Show();
            this.Close();
        }

        private void onPlayAgainClick(object sender, RoutedEventArgs e)
        {
            if (playAgainButton.Opacity == 0)
                return;
            reset();
            playAgainButton.Opacity = 0;
        }
    }
}
