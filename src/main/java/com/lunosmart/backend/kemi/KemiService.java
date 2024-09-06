package com.lunosmart.backend.kemi;
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
