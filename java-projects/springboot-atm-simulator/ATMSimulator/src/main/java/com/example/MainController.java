package com.example;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class MainController
{
    @GetMapping("/")
    public String showLoginPage()
    {
        return "login";
    }



    @GetMapping("/user")
    public String showUserPage(Model model)
    {
        model.addAttribute("message", "Welcome to the ATM!");
        return "user";
    }



    @GetMapping("/admin")
    public String showAdminPage()
    {
        return "admin";
    }

    @GetMapping("/admin/logs")
    public ResponseEntity<List<String>> getLogs()
    {
        List<String> logs = LogStorage.getLogs();
        System.out.println("Sending logs to frontend: " + logs);
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/admin/cards")
    public String showCardsPage(Model model)
    {
        List<Client> clients = ClientStorage.readClients();
        model.addAttribute("clients", clients);
        return "listClients";
    }

    @GetMapping("/admin/refill")
    public String showRefillPage()
    {
        return "refill";
    }

    @GetMapping("/admin/balance")
    public int getATMBalance()
    {
        return MoneyStorage.getTotalBalance();
    }
}
