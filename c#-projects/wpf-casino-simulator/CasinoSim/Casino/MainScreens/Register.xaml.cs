using System.Windows;

namespace Casino.MainScreens
{
    public partial class Register : Window
    {
        public Register()
        {
            InitializeComponent();
        }

        private int noUsername = 0; private int noPassword = 0; private int noSecondPassword = 0; private int userAlreadyExists = 0;
        private bool isFailedAttempt()
        {
            messageDisplay.Opacity = 1;
            if (string.IsNullOrEmpty(userNameField.Text))
            {
                messageDisplay.Text = "Enter a username! (" + noUsername + ")";
                noUsername++; noPassword = 0; userAlreadyExists = 0; noSecondPassword = 0;
                return true;
            }
            else if (string.IsNullOrEmpty(passwordField.Password))
            {
                messageDisplay.Text = "Enter a password! (" + noPassword + ")";
                noUsername = 0; noPassword++; userAlreadyExists = 0; noSecondPassword = 0;
                return true;
            }
            else if (string.IsNullOrEmpty(passwordField2.Password))
            {
                messageDisplay.Text = "Passwords do not match! (" + noSecondPassword + ")";
                noUsername = 0; noPassword = 0; userAlreadyExists = 0; noSecondPassword++;
                return true;
            }
            else if (passwordField2.Password != passwordField.Password) 
            {
                messageDisplay.Text = "Passwords do not match! (" + noSecondPassword + ")";
                noUsername = 0; noPassword = 0; userAlreadyExists = 0; noSecondPassword++;
                return true;
            }

            User user = User.loadUserFromFile(userNameField.Text);
            if (!string.IsNullOrEmpty(user.UserName))
            {
                messageDisplay.Text = "Username already exists! Try again! (" + userAlreadyExists + ")";
                noUsername = 0; noPassword = 0; userAlreadyExists++; noSecondPassword = 0;
                return true;
            }
            messageDisplay.Opacity = 0;
            return false;
        }
        private void onRegisterClick(object sender, RoutedEventArgs e)
        {
            while (isFailedAttempt())
                return;
            
            User user = new User(userNameField.Text, passwordField.Password);
            user.saveToFile(user);
            GamesScreen gameScreen = new GamesScreen(user);
            gameScreen.gameManager(user, true);
            gameScreen.Show();
            this.Close();
        }

        private void onCancelClick(object sender, RoutedEventArgs e)
        {
            Login login = new Login();
            login.Show();
            this.Close();
        }
    }
}
