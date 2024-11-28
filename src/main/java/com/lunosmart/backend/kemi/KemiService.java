package com.lunosmart.backend.kemi;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class KemiService {
    public String updateTrend(trends trends) {
        Logger logger = LoggerFactory.getLogger(KemiService.class);
        Firestore firestore = FirestoreClient.getFirestore();

        logger.info("Updating trend with tag: " + trends.getTag());
        logger.info("Trend details: " + trends.toString());

        try {
            firestore.collection("trends").document(trends.getTag()).set(trends);
            logger.info("Trend updated successfully");
            return "updated";
        } catch (Exception e) {
            logger.error("Failed to update trend", e);
            return "failed";
        }
    }

   public void sendMessageToTopic(String topic, String title, String body) {
    Message message = Message.builder()
            .setTopic(topic)
            .setNotification(Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build())
            .build();
    try {
        String response = FirebaseMessaging.getInstance().send(message);
        System.out.println("Successfully sent message: " + response);
    } catch (Exception e) {
        e.printStackTrace();
        System.err.println("Error sending FCM message: " + e.getMessage());
    }
}

    public trends getTrend(String id) throws ExecutionException, InterruptedException {
        Firestore firestore= FirestoreClient.getFirestore();
        DocumentReference apiFuture=  firestore.collection("trends").document(id);

        ApiFuture<DocumentSnapshot> future=apiFuture.get();

        DocumentSnapshot documentSnapshot=future.get();


        trends trend;
        if(documentSnapshot.exists()){
            trend=documentSnapshot.toObject(trends.class);
            return  trend;
        }

        return null;
    }

    public void createTrend(trends trend) throws ExecutionException, InterruptedException {
        Firestore firestore= FirestoreClient.getFirestore();
      //  firestore.collection("trends").add(trend);




    }

    public String deleteTrend(String id) throws ExecutionException, InterruptedException {

        Firestore firestore= FirestoreClient.getFirestore();
        firestore.collection("trends").document(id).delete().get();

        return "deleted";
    }
}
