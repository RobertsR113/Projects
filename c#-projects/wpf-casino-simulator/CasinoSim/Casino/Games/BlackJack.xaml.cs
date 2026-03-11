using Casino.MainScreens;
using System.Text.RegularExpressions;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Media;
namespace Casino.Games
{
    public partial class BlackJack : Window
    {
        private readonly MediaPlayer loseSound = new();
        private readonly MediaPlayer victorySound = new();

        private readonly User currentUser;
        private readonly List<Image> cardIndex;
        public BlackJack(User user)
        {
            currentUser = user;
            InitializeComponent();
            cardIndex = new List<Image>{clubs2, diamonds2, hearts2, spades2, clubs3, diamonds3, hearts3, spades3,
                                        clubs4, diamonds4, hearts4, spades4, clubs5, diamonds5, hearts5, spades5,
                                        clubs6, diamonds6, hearts6, spades6, clubs7, diamonds7, hearts7, spades7,
                                        clubs8, diamonds8, hearts8, spades8, clubs9, diamonds9, hearts9, spades9,
                                        clubs10, diamonds10, hearts10, spades10, clubsJack, diamondsJack, heartsJack,
                                        spadesJack, clubsKing, diamondsKing, heartsKing, spadesKing, clubsQueen, diamondsQueen,
                                        heartsQueen, spadesQueen, diamondsAce, clubsAce, heartsAce, spadesAce};

            loseSound.Open(new Uri(System.IO.Path.Combine(AppDomain.CurrentDomain.BaseDirectory, "Sounds/BlackJack/WompWomp.mp3")));
            victorySound.Open(new Uri(System.IO.Path.Combine(AppDomain.CurrentDomain.BaseDirectory, "Sounds/BlackJack/Victory.mp3")));
            loseSound.Volume = 1.0;
            victorySound.Volume = 1.0;
        }
       
        private int bettingAmount = 0;
        private void onEnterBetClick(object sender, RoutedEventArgs e)
        {
            messageDisplay.Opacity = 1;
            betAmount.Text.Replace(" ", "");
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
                catWonGif.Opacity = 0;
                defaultcat.Opacity = 1;

                bettingAmount = int.Parse(betAmount.Text);
                messageDisplay.Content = "Your bet: " + bettingAmount;
                DealerPoints = 0; PlayerPoints = 0;
                betAmount.Opacity = 0; betAmountLabel.Opacity = 0;
                enterButton.Opacity = 0;
                startGame();
            }

        }

        private int DealerPoints = 0;
        private int DealersHiddenCardPoint = 0;
        private int PlayerPoints = 0;
        private async void startGame() 
        {
            messageDisplay.Opacity = 0;
            exitButton.Opacity = 0;
            for (int i = 0; i < 2; i++)
            {
                addPointsAndShowCard("dealer");
                if (i == 0)
                {
                    DealersHiddenCardPoint = DealerPoints;
                    DealerPoints -= DealersHiddenCardPoint;
                }
                dealerPoints.Content = "Dealer points: " + DealerPoints;
                await Task.Delay(700);
            }
            for (int i = 0; i < 2; i++)
            {
                addPointsAndShowCard("player");
                playerPoints.Content = "Your points: " + PlayerPoints;
                await Task.Delay(700);
            }
            if (PlayerPoints == 21)
            {
                DealerPoints += DealersHiddenCardPoint;
                gameEnd("playerWon");
            }
            hitButton.Opacity = 1;
            standButton.Opacity = 1;
        }

        private async void onHitClick(object sender, RoutedEventArgs e)
        {
            if (hitButton.Opacity == 1)
            {
                addPointsAndShowCard("player");
                playerPoints.Content = "Your points: " + PlayerPoints;
                await Task.Delay(700);

                if (PlayerPoints > 21)
                {
                    DealerPoints += DealersHiddenCardPoint;
                    gameEnd("dealerWon");
                }
                else if (PlayerPoints == 21)
                {
                    DealerPoints += DealersHiddenCardPoint;
                    gameEnd("playerWon");
                }
            }
        }
        private async void onStandClick(object sender, RoutedEventArgs e)
        {
            if (standButton.Opacity == 1)
            {
                DealerPoints += DealersHiddenCardPoint;
                dealersSecretCard.Opacity = 0;
                dealerPoints.Content = "Dealer points: " + DealerPoints;
                await Task.Delay(900);
                while (standButton.Opacity == 1)
                {
                    if (DealerPoints > 21)
                        gameEnd("playerWon");
                    else if (DealerPoints == 21 || DealerPoints > PlayerPoints)
                        gameEnd("dealerWon");
                    else
                    {
                        addPointsAndShowCard("dealer");
                        dealerPoints.Content = "Dealer points: " + DealerPoints;
                        await Task.Delay(700);
                    }
                }
            }
        }
        private Random random = new();
        private List<Image> alreadyUsedCards = new();
        private void addPointsAndShowCard(string playerOrDealer)
        {
            int userPoints = 0;
            if (playerOrDealer == "dealer")
                userPoints = DealerPoints;
            else 
                userPoints = PlayerPoints;

            int index = random.Next(cardIndex.Count);
            while (alreadyUsedCards.Contains(cardIndex[index]))
            {
                index = random.Next(cardIndex.Count);
            }
            Image chosenImage = cardIndex[index];

            int points = 0;
            if (index <= 31)
            {
                if (index <= 3) points = 2;
                else if (index <= 7) points = 3;
                else if (index <= 11) points = 4;
                else if (index <= 15) points = 5;
                else if (index <= 19) points = 6;
                else if (index <= 23) points = 7;
                else if (index <= 27) points = 8;
                else points = 9;
            }
            else if (index <= 47)
                points = 10;
            else
            {
                if ((userPoints + 11) <= 21)
                    points = 11;
                else
                    points = 1;
            }

            if (playerOrDealer == "dealer")
            {
                DealerPoints += points;
                showNewCard("dealer", chosenImage);
            }
            else
            {
                PlayerPoints += points;
                showNewCard("player", chosenImage);
            }
            alreadyUsedCards.Add(chosenImage);
        }
        private int dealerx = 207; private int dealerx2 = 642; private int dealerZ = 1;
        private int playerx = 207; private int playerx2 = 642; private int playerZ = 1;
        private List<Image> cardsOnTable = new List<Image>();
        private void showNewCard(string forWho, Image image) 
        {
            if (forWho == "dealer")
            {
                dealerx += 50;
                dealerx2 -= 50;
                image.Margin = new Thickness((dealerx), 10, (dealerx2), 367);
                if (dealerx == 257)
                    Grid.SetZIndex(image, 0);
                else
                    Grid.SetZIndex(image, dealerZ);
                dealerZ++;
                cardsOnTable.Add(image);
            }
            if (forWho == "player")
            {
                playerx += 50;
                playerx2 -= 50;
                image.Margin = new Thickness((playerx), 351, (playerx2), 26);
                Grid.SetZIndex(image, playerZ);
                playerZ++;
                cardsOnTable.Add(image);
            }
        }
        private void gameEnd(string winner) 
        {
            dealersSecretCard.Opacity = 0;
            dealerPoints.Content = "Dealer points: " + DealerPoints;

            if (winner == "dealerWon")
            {
                defaultcat.Opacity = 0;
                catWonGif.Opacity = 1;
  
                messageDisplay.Opacity = 1; messageDisplay.Foreground = (SolidColorBrush)new BrushConverter().ConvertFrom("#FFF11717")!;
                messageDisplay.Content = "Womp womp..You LOST! (-" + (bettingAmount) + ")";
                loseSound.Stop();
                loseSound.Position = TimeSpan.Zero;
                loseSound.Play();

                currentUser.Points -= bettingAmount;
            }
            else if (winner == "playerWon")
            {
                dealersSecretCard.Opacity = 0;
                dealerPoints.Content = "Dealer points: " + DealerPoints;

                messageDisplay.Opacity = 1; messageDisplay.Foreground = (SolidColorBrush)new BrushConverter().ConvertFrom("#FF72D216")!;
                messageDisplay.Content = "GG! You WON! (+" + bettingAmount + ")";
                victorySound.Stop();
                victorySound.Position = TimeSpan.Zero;
                victorySound.Play();

                currentUser.Points += bettingAmount;
            }

            currentUser.userStats.Add(new Stats(true, bettingAmount));
            currentUser.saveToFile(currentUser);

            hitButton.Opacity = 0; standButton.Opacity = 0;
            playAgain.Opacity = 1; exitButton.Opacity = 1;
            Grid.SetZIndex(playAgain, 9999);
            Grid.SetZIndex(exitButton, 9999);
        }
        private void onPlayAgainClick(object sender, EventArgs e)
        {
            if (playAgain.Opacity == 1)
                reset();
        }

        private void reset()
        {
            loseSound.Stop();
            victorySound.Stop();
            while (cardsOnTable.Any())
            {
                Image image = cardsOnTable.First();
                image.Margin = new Thickness(785, 104, 63, 273);
                Grid.SetZIndex(image, 0);
                cardsOnTable.Remove(cardsOnTable.First());
            }
            hitButton.Opacity = 0; standButton.Opacity = 0;
            messageDisplay.Opacity = 0; messageDisplay.Foreground = (SolidColorBrush)new BrushConverter().ConvertFrom("#FFF11717")!; playAgain.Opacity = 0;
            exitButton.Opacity = 1;
            enterButton.Opacity = 1;
            betAmountLabel.Opacity = 1; betAmount.Opacity = 0.65; betAmount.Text = ""; bettingAmount = 0;
            DealerPoints = 0; DealersHiddenCardPoint = 0; dealerPoints.Content = ""; 
            PlayerPoints = 0; playerPoints.Content = "";
            dealerx = 207; dealerx2 = 642; dealerZ = 1; dealersSecretCard.Opacity = 1;
            playerx = 207; playerx2 = 642; playerZ = 1;
            alreadyUsedCards.Clear();
        }
        private void onExitClick(object sender, RoutedEventArgs e)
        {
            if (exitButton.Opacity != 0)
            {
                loseSound.Stop();
                victorySound.Stop();
                currentUser.saveToFile(currentUser);
                GamesScreen gameScreen = new GamesScreen(currentUser);
                gameScreen.gameManager(currentUser, false);
                gameScreen.Show();
                this.Close();
            }
        }
    }
}
