using Casino.MainScreens;
using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace Casino.Games
{
    /// <summary>
    /// Interaction logic for Slots.xaml
    /// </summary>
    public partial class Slots : Window
    {
        private User currentUser;
        public Slots(User user)
        {
            currentUser = user;
            InitializeComponent();
        }

        private bool canSpin = false;
        private int bettingAmount = 0;
        private void enterBetClick(object sender, RoutedEventArgs e)
        {
            messageDisplay.Opacity = 1;
            betAmount.Text = betAmount.Text.Replace(" ", "");
            string isNr = "^[0-9]+$";
            if (string.IsNullOrEmpty(betAmount.Text))
                messageDisplay.Content = "Please enter an amount!";
            else if (!Regex.IsMatch(betAmount.Text, isNr))
                messageDisplay.Content = "Invalid input!";
            else if (int.Parse(betAmount.Text) <= 0)
                messageDisplay.Content = "Bet amount must be more than 0!";
            else if (currentUser.Points < int.Parse(betAmount.Text.ToString()))
                messageDisplay.Content = "You don't have enough points! Your points: " + currentUser.Points;
            else
            {
                messageDisplay.Content = "Success! Flick the lever now!";
                canSpin = true;
                bettingAmount = int.Parse(betAmount.Text);

                betAmountLabel.Opacity = 0;
                betAmount.Opacity = 0;
                enterBetButton.Opacity = 0;

            }
        }

        private async void onSpinClick(object sender, RoutedEventArgs e)
        {
            if (!canSpin)
            {
                messageDisplay.Opacity = 1;
                messageDisplay.Content = "Enter a valid bet amount first! (Press Enter)";
                return;
            }

            canSpin = false;
            Up.Opacity = 0;
            Down.Opacity = 1.0;
            messageDisplay.Opacity = 0;
            betAmountLabel.Opacity = 0;
            betAmount.Opacity = 0;

            Random random = new Random();

            List<Image> slot1 = new List<Image> { A, B, C, D, E };
            List<Image> slot2 = new List<Image> { A1, B1, C1, D1, E1 };
            List<Image> slot3 = new List<Image> { A2, B2, C2, D2, E2 };

            int start1 = random.Next(slot1.Count);
            int start2 = random.Next(slot2.Count);
            int start3 = random.Next(slot3.Count);

            int stop1 = random.Next(12, 18);
            int stop2 = stop1 + random.Next(6, 12);
            int stop3 = stop2 + random.Next(6, 12);
            int totalIterations = stop3 + 1;

            Image lastImage1 = null; 
            Image lastImage2 = null; 
            Image lastImage3 = null;

            int delay = 50;
            int delayIncrement = 20;
            int maxDelay = 800;

            for (int i = 0; i < totalIterations; i++)
            {
                if (i <= stop1)
                {
                    int idx1 = (start1 + i) % slot1.Count;
                    Image img1 = slot1[idx1];

                    if (lastImage1 != null && lastImage1 != img1) 
                        lastImage1.Opacity = 0;
                    img1.Opacity = 1.0;
                    lastImage1 = img1;
                }
                if (i <= stop2)
                {
                    int idx2 = (start2 + i) % slot2.Count;
                    Image img2 = slot2[idx2];
                    if (lastImage2 != null && lastImage2 != img2) 
                        lastImage2.Opacity = 0;
                    img2.Opacity = 1.0;
                    lastImage2 = img2;
                }
                if (i <= stop3)
                {
                    int idx3 = (start3 + i) % slot3.Count;
                    Image img3 = slot3[idx3];
                    if (lastImage3 != null && lastImage3 != img3) 
                        lastImage3.Opacity = 0;
                    img3.Opacity = 1.0;
                    lastImage3 = img3;
                }

                await Task.Delay(delay);
                delay = Math.Min(maxDelay, delay + delayIncrement);
            }

            Up.Opacity = 1.0;
            Down.Opacity = 0;
            betAmountLabel.Opacity = 1.0;
            betAmount.Opacity = 1.0;
            betAmount.Text = "";

            int firstSlotIndex = slot1.IndexOf(lastImage1);

            int secondSlotIndex = slot2.IndexOf(lastImage2);

            int thirdSlotIndex = slot3.IndexOf(lastImage3);

            if (firstSlotIndex == secondSlotIndex && firstSlotIndex == thirdSlotIndex &&
                firstSlotIndex >= 0)
            {
                if (slot1[firstSlotIndex] == E)
                {
                    won.Content = "!!OMG JACKPOT!! +" + (bettingAmount * 3);
                    currentUser.Points += (bettingAmount * 3);
                    currentUser.userStats.Add(new Stats(true, bettingAmount));
                    currentUser.saveToFile(currentUser);
                }
                else
                {
                    won.Content = "YOU WON! +" + bettingAmount;
                    currentUser.Points += bettingAmount;
                    currentUser.userStats.Add(new Stats(true, bettingAmount));
                    currentUser.saveToFile(currentUser);
                }
            }
            else
            {
                won.Content = "Womp womp! -" + bettingAmount;
                currentUser.Points -= bettingAmount;
                currentUser.userStats.Add(new Stats(false, bettingAmount));
                currentUser.saveToFile(currentUser);
            }

            betAmountLabel.Opacity = 1;
            betAmount.Opacity = 1;
            enterBetButton.Opacity = 1;
            canSpin = true;
        }
        private void onExitClick(object sender, EventArgs e) 
        {
            currentUser.saveToFile(currentUser);
            GamesScreen gameScreen = new GamesScreen(currentUser);
            gameScreen.gameManager(currentUser, false);
            gameScreen.Show();
            this.Close();
        }
    }
}
