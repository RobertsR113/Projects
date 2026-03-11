using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Casino
{
    public class Stats
    {
        public Stats(bool isWin, int bet)
        {
            IsWin = isWin;
            Bet = bet;
        }
        public bool IsWin { get; set; }
        public int Bet { get; set; }
    }
}
