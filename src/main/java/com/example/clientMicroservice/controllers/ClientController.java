package com.example.clientMicroservice.controllers;

import com.example.clientMicroservice.Configuration.MQConfig;
import com.example.clientMicroservice.Messages.MessageModel;
import com.example.clientMicroservice.models.Client;
import com.example.clientMicroservice.service.ClientService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private RabbitTemplate template;

    @GetMapping
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        Optional<Client> client = clientService.getClientById(id);
        return client.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Client createClient(@RequestBody Client client) {

        Client newclient= clientService.saveClient(client);
        publishMessage("CREATE", newclient);
        return newclient;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client updatedClient) {
        Client updated = clientService.updateClient(id, updatedClient);
        publishMessage("UPDATE", updated);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        Optional<Client> deleteClient= clientService.getClientById(id);
        deleteClient.ifPresent(client -> publishMessage("DELETE", client));
        clientService.deleteClient(id);

        return ResponseEntity.noContent().build();
    }

    private void publishMessage(String action,Client  Client) {
        MessageModel message = new MessageModel();
        message.setAction(action);
        message.setMessageId(UUID.randomUUID().toString());
        message.setMessage(" Client: " + Client.getName()+" with id:"+Client.getId()+" have been "+ action ); // Set the Message field based on the action
        message.setMassageDate(new Date());
        template.convertAndSend(MQConfig.IMPORT_EXPORT_TOPIC_EXCHANGE, MQConfig.ROUTING_KEY, message);
    }
}