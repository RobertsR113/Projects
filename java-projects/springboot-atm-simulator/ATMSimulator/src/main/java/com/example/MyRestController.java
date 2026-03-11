package com.example;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class MyRestController
{

    @GetMapping("/getBalance")
    public ResponseEntity<Map<String, Integer>> getBalance(@RequestParam String cardNumber)
    {
        return ClientStorage.readClients().stream()
                .filter(c -> c.getCardNumber().equals(cardNumber))
                .findFirst()
                .map(c -> ResponseEntity.ok(Map.of("balance", c.getBalance())))
                .orElse(ResponseEntity.badRequest().body(Map.of("balance", 0)));
    }

    @PostMapping("/atm_auth")
    public ResponseEntity<Map<String, String>> authenticateUser(@RequestBody AtmAuthRequest request)
    {
        String cardNumber = request.getCardNumber();
        String pin = request.getPin();

        if (cardNumber == null || pin == null)
        {
            return ResponseEntity.badRequest().body(Map.of("status", "error", "message", "Missing fields"));
        }

        if ("admin".equals(cardNumber) && "admin123".equals(pin))
        {
            LogStorage.addLog("Admin logged in!");
            return ResponseEntity.ok(Map.of("status", "admin"));
        }

        return ClientStorage.readClients().stream()
                .filter(c -> c.getCardNumber().equals(cardNumber) && c.isPinCorrect(pin))
                .findFirst()
                .map(c ->
                {
                    LogStorage.addLog("User " + c.getCardNumber() + " logged in!");
                    return ResponseEntity.ok(Map.of("status", "user"));
                })
                .orElseGet(() ->
                {
                    LogStorage.addLog("Failed login attempt for " + cardNumber);
                    return ResponseEntity.ok(Map.of("status", "error"));
                });
    }

    @PostMapping("/atm_register")
    public ResponseEntity<Map<String, String>> registerCard(@RequestBody Map<String, Object> body)
    {
        String cardNumber = (String) body.get("cardNumber");
        String pin = (String) body.get("pin");

        if (cardNumber == null || pin == null)
        {
            return ResponseEntity.badRequest().body(Map.of("status", "error"));
        }

        List<Client> clients = ClientStorage.readClients();

        if (clients.stream().anyMatch(c -> c.getCardNumber().equals(cardNumber)))
        {
            return ResponseEntity.ok(Map.of("status", "exists"));
        }

        clients.add(new Client(cardNumber, pin, 1000));
        ClientStorage.saveClients(clients);

        return ResponseEntity.ok(Map.of("status", "success"));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Map<String, Object>> withdraw(@RequestBody Map<String, String> request)
    {
        String cardNumber = request.get("cardNumber");
        String type = request.get("type");
        int amount;

        try
        {
            amount = Integer.parseInt(request.get("amount"));
        } catch (NumberFormatException e)
        {
            return ResponseEntity.badRequest().body(Map.of("status", "error", "message", "Invalid amount format."));
        }

        if (amount <= 0 || amount % 5 != 0)
        {
            return ResponseEntity.ok(Map.of("status", "error", "message", "Invalid amount. Must be a multiple of 5."));
        }

        List<Client> clients = ClientStorage.readClients();
        Optional<Client> clientOpt = clients.stream().filter(c -> c.getCardNumber().equals(cardNumber)).findFirst();

        if (clientOpt.isEmpty())
        {
            return ResponseEntity.ok(Map.of("status", "error", "message", "Card not found."));
        }

        Client client = clientOpt.get();
        if (client.getBalance() < amount)
        {
            LogStorage.addLog("Failed withdrawal for " + cardNumber + " due to insufficient funds.");
            return ResponseEntity.ok(Map.of("status", "error", "message", "Insufficient funds."));
        }

        List<Integer> banknoteTypes = new ArrayList<>(MoneyStorage.getBanknotes().keySet());
        if ("min".equals(type))
        {
            Collections.sort(banknoteTypes);
        } else
        {
            banknoteTypes.sort(Collections.reverseOrder());
        }

        Map<Integer, Integer> dispensedBanknotes = new LinkedHashMap<>();
        int remainingAmount = amount;

        for (int banknote : banknoteTypes)
        {
            int availableNotes = MoneyStorage.getBanknotes().get(banknote);
            int toDispense = Math.min(remainingAmount / banknote, availableNotes);
            if (toDispense > 0)
            {
                dispensedBanknotes.put(banknote, toDispense);
                remainingAmount -= toDispense * banknote;
            }
        }

        if (remainingAmount > 0)
        {
            LogStorage.addLog("Failed withdrawal: Unable to dispense exact amount.");
            return ResponseEntity.ok(Map.of("status", "error", "message", "Cannot dispense exact amount."));
        }

        dispensedBanknotes.forEach((key, value) -> MoneyStorage.updateBanknotes(key, MoneyStorage.getBanknotes().get(key) - value));
        client.setBalance(client.getBalance() - amount);
        ClientStorage.saveClients(clients);

        LogStorage.addLog("User " + cardNumber + " withdrew " + amount + " €.");
        return ResponseEntity.ok(Map.of("status", "success", "banknotes", dispensedBanknotes));
    }

    @PostMapping("/refill")
    public ResponseEntity<String> refillATM(@RequestBody Map<String, Integer> refillData)
    {
        if (refillData == null || refillData.isEmpty())
        {
            return ResponseEntity.badRequest().body("Error: No data received.");
        }

        refillData.forEach((denominationStr, count) ->
        {
            int denomination = Integer.parseInt(denominationStr);

            MoneyStorage.updateBanknotes(
                    denomination,
                    MoneyStorage.getBanknotes().getOrDefault(denomination, 0) + count
            );

            LogStorage.addLog("Refilled " + count + " banknotes of " + denomination + " €.");
        });

        return ResponseEntity.ok("success");
    }

    @GetMapping("/balance")
    public ResponseEntity<Map<String, Integer>> getATMBalance()
    {
        return ResponseEntity.ok(Map.of("balance", MoneyStorage.getTotalBalance()));
    }

    @GetMapping("/logs")
    public List<String> getLogs()
    {
        return LogStorage.getLogs();
    }

    @GetMapping("/cards")
    public List<Client> getAllClients()
    {
        return ClientStorage.readClients();
    }
}
