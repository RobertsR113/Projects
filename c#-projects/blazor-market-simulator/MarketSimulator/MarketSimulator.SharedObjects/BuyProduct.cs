using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MarketSimulator.SharedObjects
{
    public class BuyProduct
    {
        public Product? Product { get; set; }
        public int DesiredQuantity { get; set; }
    }
}
