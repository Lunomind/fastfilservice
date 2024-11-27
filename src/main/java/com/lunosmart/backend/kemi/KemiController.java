package com.lunosmart.backend.kemi;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin
public class KemiController {
    public final KemiService kemiService;
    private final ChatClient chatClient;

    public KemiController(KemiService kemiService,ChatClient chatClient) {
        this.kemiService = kemiService;
        this.chatClient = chatClient;

    }


    @PostMapping("/create")
    public void createTrend(@RequestBody trends trend)throws InterruptedException, ExecutionException {
        kemiService.createTrend(trend);
    }

    @GetMapping("/get")
    public trends getTrend(@RequestParam String id)throws InterruptedException, ExecutionException {
        return  kemiService.getTrend(id);

    }

    @GetMapping("/delete")
    public String deleteTrend(@RequestParam String id) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        firestore.collection("trends").document(id).delete();
        return "deleted";
    }



    @PostMapping("/send")
    public String sendNotification(
            @RequestParam String topic,
            @RequestParam String title,
            @RequestParam String body
    ) {
        kemiService.sendMessageToTopic(topic, title, body);
        return "Notification sent to topic: " + topic;
    }





    @GetMapping("/chat")
    public String chat(@RequestParam String message) throws ExecutionException, InterruptedException {
        return "deleted";
    }


    @PutMapping("/update")
    public String updateTrend(@RequestBody trends trends)throws InterruptedException, ExecutionException{
        return  kemiService.updateTrend(trends);
    }

    @GetMapping("/test")
    public ResponseEntity<String> testEndPoint(){
        return ResponseEntity.ok("Ready!");
    }
}
