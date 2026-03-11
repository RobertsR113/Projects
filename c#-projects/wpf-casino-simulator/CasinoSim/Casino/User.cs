using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

namespace Casino
{
    public class User
    {
        public string UserName { get; set; } = "";
        public string UserPassword { get; set; } = "";
        public int Points { get; set; }
        public List<Stats> userStats { get; set; }

        public User()
        {
            Points = 50;
            userStats = new List<Stats>();
        }

        public User(string name, string password) : this()
        {
            UserName = name;
            UserPassword = password;
        }

        public string showStats(User user)
        {
            if (user?.userStats == null || !user.userStats.Any())
                return "Nothing to see here.. yet";

            double winCount = user.userStats.Count(x => x.IsWin);
            double loseCount = user.userStats.Count - winCount;
            double winLuck = (winCount + loseCount) > 0 ? winCount / (winCount + loseCount) * 100 : 0;

            if (user.userStats.Count < 2)
            {
                return "Win count: " + winCount.ToString("F0")
                    + "\nLose count: " + loseCount.ToString("F0")
                    + "\nLuck: " + winLuck.ToString("F2")
                    + "\nTotal games played: " + user.userStats.Count;
            }
            else
            {
                int biggestBet = user.userStats.Max(x => x.Bet);
                int smallestBet = user.userStats.Min(x => x.Bet);
                return "Win count: " + winCount.ToString("F0")
                    + "\nLose count: " + loseCount.ToString("F0")
                    + "\nLuck: " + winLuck.ToString("F2") + "%"
                    + "\nBiggest bet: " + biggestBet
                    + "\nSmallest bet: " + smallestBet
                    + "\nTotal games played: " + user.userStats.Count;
            }
        }

        private static string UsersFile = "userProgress.txt";

        public void saveToFile(User thisUser)
        {
            List<User> allUsers = loadFromFile();

            int index = allUsers.FindIndex(u => u.UserName == thisUser.UserName && u.UserPassword == thisUser.UserPassword);

            if (index >= 0)
            {
                allUsers[index] = thisUser;
                string serializedFile = JsonConvert.SerializeObject(allUsers, Formatting.Indented);
                File.WriteAllText(UsersFile, serializedFile);
                Console.WriteLine("Successfully UPDATED " + thisUser.UserName + "'s data in " + UsersFile + "!");
                return;
            }

            allUsers.Add(thisUser);
            string serializedFile2 = JsonConvert.SerializeObject(allUsers, Formatting.Indented);
            File.WriteAllText(UsersFile, serializedFile2);
            Console.WriteLine("Successfully added NEW user: " + thisUser.UserName + " in " + UsersFile + "!");
        }

        public static User loadUserFromFile(string selectedUser)
        {
            List<User> allUsers = loadFromFile();
            foreach (User user in allUsers)
            {
                if (user.UserName == selectedUser)
                {
                    Console.WriteLine("User found!");
                    return user;
                }
            }
            Console.WriteLine("User NOT found!");
            return new User("", "");
        }

        public static List<User> loadFromFile()
        {
            List<User> Users = new List<User>();
            try
            {
                if (File.Exists(UsersFile))
                {
                    string readFile = File.ReadAllText(UsersFile).Trim();
                    if (string.IsNullOrEmpty(readFile))
                    {
                        Console.WriteLine("Loaded EMPTY userFile successfully!");
                        return Users;
                    }

                    if (readFile.StartsWith("["))
                    {
                        var deserializedFile = JsonConvert.DeserializeObject<List<User>>(readFile);
                        if (deserializedFile != null)
                            Users = deserializedFile;
                    }
                    else if (readFile.StartsWith("{"))
                    {
                        var single = JsonConvert.DeserializeObject<User>(readFile);
                        if (single != null)
                            Users.Add(single);

                        File.WriteAllText(UsersFile, JsonConvert.SerializeObject(Users, Formatting.Indented));
                    }
                    else
                    {
                        var deserializedFile = JsonConvert.DeserializeObject<List<User>>(readFile);
                        if (deserializedFile != null)
                            Users = deserializedFile;
                    }

                    foreach (var u in Users)
                    {
                        if (u.userStats == null)
                            u.userStats = new List<Stats>();
                    }

                    Console.WriteLine("Loaded EXISTING userFile successfully!");
                    return Users;
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error reading users file: " + ex.Message);
            }

            Console.WriteLine("Loaded EMPTY userFile successfully!");
            return Users;
        }
    }
}