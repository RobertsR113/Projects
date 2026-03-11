using MarketSimulator.SharedObjects;
using Microsoft.AspNetCore.Mvc;
using System.Text.RegularExpressions;

namespace MarketSimulator.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class ProductInventory : Controller
    {
        private string errorMessage = "";
        private bool isValidProduct(Product product) 
        {
            string validPrice = "^[0-9]+(\\.[0-9]+)?$";
            string validQuantity = "^[0-9]+$";
            if (string.IsNullOrWhiteSpace(product.Name) || string.IsNullOrWhiteSpace(product.Price.ToString()) || string.IsNullOrWhiteSpace(product.Quantity.ToString()))
            {
                errorMessage = "Invalid input! Please fill all fields correctly!";
                return false; 
            }
            else if (!Regex.IsMatch(product.Price.ToString(), validPrice) || !Regex.IsMatch(product.Quantity.ToString(), validQuantity))
            {
                errorMessage = "Price OR quantity is invalid!";
                return false;
            }
            else if (product.Price <= 0 || product.Quantity <= 0)
            {
                errorMessage = "Price and quantity must be more than 0!";
                return false;
            }
            return true;
        }

        [HttpPost("addProduct")]
        public IActionResult AddProduct(Product product)
        {
            if (!isValidProduct(product))
                return BadRequest(errorMessage);

            List<Product> products = Storage.loadFromProductFile();
            if (products.Any(Product => Product.Name == product.Name))
                return BadRequest("This product already exists!");

            Storage.saveToProductFile(product);
            return Ok("NEW Product saved successfully!");
        }

        [HttpPost("editProduct")]
        public IActionResult EditProduct(Product product)
        {
            if (!isValidProduct(product))
                return BadRequest(errorMessage);

            List<Product> products = Storage.loadFromProductFile();
            foreach (Product Product in products) 
            {
                if (Product.Name == product.Name && Product.Quantity == product.Quantity && Product.Price == product.Price)
                    return BadRequest("No changes made!");
            }

            Storage.saveToProductFile(product);
            return Ok("Product " + product.Name + " edited successfully!");
        }

        [HttpPost("removeProduct")]
        public IActionResult RemoveProduct(Product Product) 
        {
            List<Product> products = Storage.loadFromProductFile();
            foreach (Product product in products)
            {
                if (product.Name == Product.Name && product.Quantity == Product.Quantity && product.Price == Product.Price)
                {
                    Storage.removeFromProductFile(Product);
                    return Ok("Product " + Product.Name + " has been removed!");
                }
            }
            return BadRequest("No products yet!");
        }

        [HttpGet("listProducts")]
        public IActionResult ListProducts()
        {
            List<Product> products = Storage.loadFromProductFile();
            if (products.Any())
                return Ok(products);
            return BadRequest("No products yet!");
        }
    }
}
