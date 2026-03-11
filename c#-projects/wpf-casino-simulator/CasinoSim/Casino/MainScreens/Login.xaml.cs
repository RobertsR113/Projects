using System.Windows;

namespace Casino.MainScreens
{
    public partial class Login : Window
    {
        public Login()
        {
            InitializeComponent();
        }

        private int noUsername = 0; private int noPassword = 0;  private int loginAttempt = 0; private int passwordAttempt = 0; 
        private bool isFailedAttempt()
        {
            messageDisplay.Opacity = 1;
            if (string.IsNullOrEmpty(userNameField.Text))
            {
                messageDisplay.Content = "Please enter a user name! (" + noUsername + ")";
                noUsername++; noPassword = 0; loginAttempt = 0; passwordAttempt = 0;
                return true;
            }
            else if (string.IsNullOrEmpty(passwordField.Password))
            {
                messageDisplay.Content = "Please enter a password! (" + noPassword + ")";
                noUsername = 0; noPassword++; loginAttempt = 0; passwordAttempt = 0;
                return true;
            }

            User user = User.loadUserFromFile(userNameField.Text.ToString());
            if (string.IsNullOrEmpty(user.UserName))
            {
                messageDisplay.Content = "Username does not exist! Try again! (" + loginAttempt + ")";
                noUsername = 0; noPassword = 0; loginAttempt++; passwordAttempt = 0;
                return true;
            }
            else if (passwordField.Password != user.UserPassword)
            {

                messageDisplay.Content = "Incorrect password! (" + passwordAttempt + ")";
                noUsername = 0; noPassword = 0; loginAttempt = 0; passwordAttempt++;
                return true;
            }
            messageDisplay.Opacity = 0;
            return false;
        }
        private void onLoginClick(object sender, RoutedEventArgs e)
        {
            if (isFailedAttempt())
                return;
           
            User user = User.loadUserFromFile(userNameField.Text);
            GamesScreen gameScreen = new GamesScreen(user);
            gameScreen.gameManager(user, false);
            gameScreen.Show();
            this.Close();
        }
        
        private void onRegisterClick(object sender, RoutedEventArgs e)
        {
            Register register = new Register();
            register.Show();
            this.Close();
        }
    }
}