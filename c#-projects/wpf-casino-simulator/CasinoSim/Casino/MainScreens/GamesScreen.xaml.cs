using Casino.Games;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace Casino.MainScreens
{
    /// <summary>
    /// Interaction logic for GamesScreen.xaml
    /// </summary>
    public partial class GamesScreen : Window
    {
        public static User CurrentUser = new User("", "");

        public GamesScreen(User user)
        {
            InitializeComponent();
            CurrentUser = User.loadUserFromFile(user.UserName);
        }

        public void gameManager(User user, bool isNew)
        {
            if (user != null)
                CurrentUser = user;

            if (user?.userStats == null || !user.userStats.Any())
                displayName.Content = "Welcome " + CurrentUser.UserName + " !!!";
            else
                displayName.Content = "Welcome back " + CurrentUser.UserName + " !";

            displayStats.Text = CurrentUser.showStats(CurrentUser);
            displayPoints.Content = "Your points: " + CurrentUser.Points;
        }

        private void onRouletteClick(object sender, RoutedEventArgs e)
        {
            Roulette roulette = new Roulette(CurrentUser);
            roulette.Show();
            this.Close();
        }

        private void onSlotsClick(object sender, RoutedEventArgs e)
        {
            Slots slots = new Slots(CurrentUser);
            slots.Show();
            this.Close();
        }

        private void onBlackjackClick(object sender, RoutedEventArgs e)
        {
            BlackJack blackjack = new BlackJack(CurrentUser);
            blackjack.Show();
            this.Close();
        }

        private void onExitClick(object sender, RoutedEventArgs e)
        {
            Login login = new Login();
            login.Show();
            this.Close();
        }
    }
}
