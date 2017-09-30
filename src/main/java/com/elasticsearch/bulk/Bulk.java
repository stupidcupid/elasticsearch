package com.elasticsearch.bulk;

import com.elasticsearch.ESUtils;
import com.elasticsearch.model.User;
import com.elasticsearch.utils.Util;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * Created by nanzhou on 2017/9/30.
 */
public class Bulk {


    public static void main(String[] args) {


        Client client = ESUtils.getClient();
        BulkRequestBuilder builder = client.prepareBulk();
        User user = new User();


        for (int i = 0; i < 40001; i++) {

            user.setName("user_" + UUID.randomUUID());
            SecureRandom random = new SecureRandom();
            long l = Math.abs(random.nextLong());
            user.setWeight(l);
            user.setMarried(l % 1 == 0 ? true : false);
            user.setAge(l % 2 == 0 ? 28 : 82);
            IndexRequestBuilder ir = client.prepareIndex(ESUtils.getIndexName(), ESUtils.USER_TYPE,
                    String.valueOf(i)).setSource(Util.toJson(user)); builder.add(ir);

        }
        long beginTime = System.currentTimeMillis();
        BulkResponse bulkResponse = builder.execute().actionGet();
        long useTime = System.currentTimeMillis() - beginTime; //1406ms
        System.out.println("useTime:" + useTime);
        if (bulkResponse.hasFailures()) {
            // process failures by iterating through each bulk response item
        }
    }
}
