using MarketSimulator.SharedObjects;
using Microsoft.AspNetCore.Mvc;
using System.Text.RegularExpressions;

namespace MarketSimulator.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class Shop : Controller
    {
        [HttpGet("listProducts")]
        public IActionResult ListProducts()
        {
            List<Product> products = Storage.loadFromProductFile();
            if (products.Any())
                return Ok(products);
            return BadRequest("No products yet!");
        }
        [HttpPost("buy")]
        public IActionResult buyProduct(BuyProduct productElement)
        {
            Product? product = productElement.Product;
            int desiredQuantity = productElement.DesiredQuantity;

            string validQuantity = "^[0-9]+$";
            if (string.IsNullOrWhiteSpace(desiredQuantity.ToString()) && !Regex.IsMatch(desiredQuantity.ToString(), validQuantity))
                return BadRequest("Invalid quantity input!");
            else if (desiredQuantity <= 0)
                return BadRequest("Quantity must be higher than 0!");

            List<Product> products = Storage.loadFromProductFile();
            if (products.Any())
            {
                foreach (Product currentProduct in products)
                {
                    if (currentProduct.Name == product?.Name)
                    {
                        if (currentProduct.Quantity > desiredQuantity)
                        {
                            currentProduct.Quantity -= desiredQuantity;
                            Storage.saveToProductFile(currentProduct);
                            string result = (currentProduct.Price * desiredQuantity).ToString("F2");
                            Storage.saveToOrderFile(new Order(Order.formatDate(DateTime.Now), currentProduct.Name, desiredQuantity, double.Parse(result)));
                            return Ok("Successfully bought x" + desiredQuantity + " " + currentProduct.Name);
                        }
                        else
                            return BadRequest("Not enough " + currentProduct.Name + " in stock! (Available " + product.Quantity + ")");
                    }
                }
                return BadRequest("Shop is Empty!");
            }
            else
                return BadRequest("Shop is Empty!");
        }

        [HttpGet("listOrders")]
        public IActionResult ListOrders()
        {
            List<Order> orders = Storage.loadFromOrderFile();
            List<string> ordersToString = new List<string>();
            if (orders.Any())
            {
                string? previousDate = null;

                foreach (Order order in orders)
                {
                    if (previousDate == null || order.Date != previousDate)
                    {
                        ordersToString.Add(" ");
                        ordersToString.Add(order.Date);
                        previousDate = order.Date;
                    }
                    ordersToString.Add("x" + order.Quantity + " " + order.Name + " = " + order.TotalItemPrice + "$");
                }
                return Ok(ordersToString);
            }
            return Ok(ordersToString);
        }
    }
}
