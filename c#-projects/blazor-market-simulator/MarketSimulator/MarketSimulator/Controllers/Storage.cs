using MarketSimulator.SharedObjects;
using Newtonsoft.Json;

namespace MarketSimulator.Controllers
{
    public class Storage
    {
        private static string orderFile = "listOfOrders.txt";
        public static void saveToOrderFile(Order order)
        {
            List<Order> orders = loadFromOrderFile();
            orders.Add(order);
            string serialize = JsonConvert.SerializeObject(orders);
            File.WriteAllText(orderFile, serialize);
        }

        public static List<Order> loadFromOrderFile()
        {
            List<Order> Orders = new List<Order>();
            if (File.Exists(orderFile))
            {
                string readTheFile = File.ReadAllText(orderFile);
                List<Order>? orders = JsonConvert.DeserializeObject<List<Order>>(readTheFile);
                if (orders != null)
                    Orders = orders;
            }
            return Orders;
        }

        private static string productFile = "listOfProducts.txt";
        public static void saveToProductFile(Product product)
        {
            List<Product> products = loadFromProductFile();

            foreach (Product Product in products)
            {
                if (Product.Name == product.Name)
                {
                    Product.Price = product.Price;
                    Product.Quantity = product.Quantity;

                    string serialize = JsonConvert.SerializeObject(products);
                    File.WriteAllText(productFile, serialize);
                    Console.WriteLine("Successfully UPDATED PRODUCT to PRODUCT file!");
                    return;
                }
            }

            products.Add(product);

            string serializeNew = JsonConvert.SerializeObject(products);
            File.WriteAllText(productFile, serializeNew);
            Console.WriteLine("Successfully SAVED to PRODUCT file!");
        }
        public static void removeFromProductFile(Product product)
        {
            List<Product> products = loadFromProductFile();

            foreach (Product Product in products)
            {
                if (Product.Name == product.Name)
                {
                    products.Remove(Product);
                    string serialize = JsonConvert.SerializeObject(products);
                    File.WriteAllText(productFile, serialize);
                    Console.WriteLine("Successfully REMOVED PRODUCT to PRODUCT file!");
                    return;
                }
            }
            string serializeNew = JsonConvert.SerializeObject(products);
            File.WriteAllText(productFile, serializeNew);
            Console.WriteLine("Successfully SAVED to PRODUCT file!");
        }

        public static List<Product> loadFromProductFile()
        {
            List<Product> Products = new List<Product>();
            if (File.Exists(productFile))
            {
                string readTheFile = File.ReadAllText(productFile);
                List<Product>? products = JsonConvert.DeserializeObject<List<Product>>(readTheFile);
                if (products != null)
                    Products = products;
                Console.WriteLine("Product file FOUND!");
            }
            else
                Console.WriteLine("Product file NOT found!");
            return Products;
        }
    }
}

