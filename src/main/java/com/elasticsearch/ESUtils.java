package com.elasticsearch;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

/**
 * Created by nanzhou on 2017/9/26.
 */
public class ESUtils {

    public static final String INDEX_NAME = "esindex";

    public static final String USER_TYPE = "userindex";

    public static String getIndexName() {
        return INDEX_NAME;
    }

    public static final String TYPE_NAME = "estype";

    public static String getTypeName() {
        return TYPE_NAME;
    }

    public static Client getClient() {
        Settings settings = ImmutableSettings.settingsBuilder()
                //指定集群名称
                .put("cluster.name", "elasticsearch")
                //探测集群中机器状态
                .put("client.transport.sniff", true).build();

        // 创建客户端
        Client client = new TransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress("127.0.0.1", 9300));
        return client;
    }

    public static void closeClient(Client esClient) {
        if (esClient != null) {
            esClient.close();
        }
    }

    /**
     * 增加
     */
    public static void add() {

        Client client = ESUtils.getClient();
        IndexResponse indexResponse = client.prepareIndex().setIndex(ESUtils.getIndexName())
                .setType(ESUtils.getTypeName()).setSource("{\"prodId\":1,\"prodName\":\"ipad5\",\"prodDesc\":\"比你想的更强大\",\"catId\":1}")
                .setId("1")
                .execute()
                .actionGet();
        System.out.println("添加成功,isCreated=" + indexResponse.isCreated());
        ESUtils.closeClient(client);
    }

    /**
     * 查询
     */
    public static void query() {

        Client client = ESUtils.getClient();
        GetResponse response = client.prepareGet().setIndex(ESUtils.getIndexName())
                .setType(ESUtils.getTypeName())
                .setId("1")
                .execute().actionGet();
        System.out.println("get= " + response.getSourceAsString());


    }

    /**
     * 删除
     */
    public static void delete() {

        Client client = ESUtils.getClient();
        DeleteResponse response = client.prepareDelete().setIndex(ESUtils.getIndexName())
                .setType(ESUtils.getTypeName())
                .setId("1")
                .execute().actionGet();
        System.out.println("delete is found = " + response.isFound());

    }

    /**
     *
     */
    public static void modify() {

        Client client = ESUtils.getClient();
        GetResponse getResponse = client.prepareGet().setIndex(ESUtils.getIndexName())
                .setType(ESUtils.getTypeName()).setId("1")
                .execute()
                .actionGet();
        System.out.println("berfore update version=" + getResponse.getVersion());
        UpdateResponse updateResponse = client.prepareUpdate().setIndex(ESUtils.getIndexName())
                .setType(ESUtils.getTypeName()).setDoc("{\"prodId\":1,\"prodName\":\"ipad5\",\"prodDesc\":\"比你想的更强大噢耶\",\"catId\":1}").setId("1")
                .execute()
                .actionGet();
        System.out.println("更新成功，isCreated=" + updateResponse.isCreated());
        getResponse = client.prepareGet().setIndex(ESUtils.getIndexName()).setType(ESUtils.getTypeName())
                .setId("1")
                .execute()
                .actionGet();
        System.out.println("get version=" + getResponse.getVersion());
    }

    /**
     * 查询
     */
    public static void search() {

        Client client = ESUtils.getClient();
        QueryBuilder query = QueryBuilders.queryString("ipad5");
        SearchResponse response = client.prepareSearch(ESUtils.INDEX_NAME)
                //设置查询条件
                .setQuery(query)
                .setFrom(0)
                .setSize(50)
                .execute()
                .actionGet();
        /**
         * SearchHits是SearchHit的复数形式，表示这个是一个列表 */
        SearchHits shs = response.getHits();
        System.out.println("总共有:" + shs.hits().length);
        for (SearchHit hit : shs) {
            System.out.println(hit.getSourceAsString());
        }
        client.close();
    }

    public static void main(String[] args) {

        add();
        //search();
        //delete();
        //modify();
        search();
    }

}
