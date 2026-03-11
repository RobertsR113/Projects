using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MarketSimulator.SharedObjects
{
    public class Order
    {
        public string Name { get; set; }
        public double TotalItemPrice { get; set; }
        public int Quantity { get; set; }
        public string Date { get; set; }
        public static string formatDate(DateTime date) 
        {
            return date.ToString("dd.MM.yyyy HH:mm");
        }
        public Order(string date, string name, int requestedAmount, double totalItemPrice)
        {
            Date = date;
            Name = name;
            TotalItemPrice = totalItemPrice;
            Quantity = requestedAmount;
        }
    }
}
