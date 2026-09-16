package com.example.springcloudsearch.service;

import java.math.BigDecimal;
import java.util.Date;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.json.JsonData;
import com.example.common.pojo.Product;
import com.example.springcloudsearch.respository.ProductRespository;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.HighlightQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightParameters;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * ProductServiceImpl<br>
 * <p>
 * 作成日：2026/9/16<br>
 * 作成者：秦振兴<br>
 */
@Service
public class ProductServiceImpl {

    @Resource
    private ProductRespository productRespository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    private static Random random = new Random();


    public Page<Product> search(String keyword, int page, int size, BigDecimal minPrice, BigDecimal maxPrice) {

        BoolQuery.Builder boolQuery = new BoolQuery.Builder();

        if (StringUtils.hasText(keyword)) {
            boolQuery.must(m -> m.multiMatch(mm -> mm
                    .fields("name", "description")
                    .query(keyword)
            ));
        }

        if (minPrice != null || maxPrice != null) {
            boolQuery.filter(f -> f.range(r -> r
                    .number(n -> {
                        n.field("price");
                        if (minPrice != null) n.gte(minPrice.doubleValue());
                        if (maxPrice != null) n.lte(maxPrice.doubleValue());
                        return n;
                    })
            ));
        }

        Query query = NativeQuery.builder()
                .withQuery(q -> q.bool(boolQuery.build()))
                .withPageable(PageRequest.of(page, size))
                .build();

        SearchHits<Product> hits = elasticsearchOperations.search(query, Product.class);

        List<Product> list = hits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());

        return new PageImpl<>(list, PageRequest.of(page, size), hits.getTotalHits());

// 高亮显示
//        BoolQuery.Builder boolQuery = new BoolQuery.Builder();
//
//        if (StringUtils.hasText(keyword)) {
//            boolQuery.must(m -> m.multiMatch(mm -> mm
//                    .fields("name", "description")
//                    .query(keyword)
//            ));
//        }
//
//        if (minPrice != null || maxPrice != null) {
//            boolQuery.filter(f -> f.range(r -> r
//                    .number(n -> {
//                        n.field("price");
//                        if (minPrice != null) n.gte(minPrice.doubleValue());
//                        if (maxPrice != null) n.lte(maxPrice.doubleValue());
//                        return n;
//                    })
//            ));
//        }
//
//        Highlight highlight = new Highlight(
//                HighlightParameters.builder()
//                        .withPreTags("<em>")
//                        .withPostTags("</em>")
//                        .build(),
//                List.of(new HighlightField("name"), new HighlightField("description"))
//        );
//
//        Query query = NativeQuery.builder()
//                .withQuery(q -> q.bool(boolQuery.build()))
//                .withHighlightQuery(new HighlightQuery(highlight, Product.class))
//                .withPageable(PageRequest.of(page, size))
//                .build();
//
//        SearchHits<Product> hits = elasticsearchOperations.search(query, Product.class);
//
//        List<Product> list = hits.getSearchHits().stream()
//                .map(hit -> {
//                    Product product = hit.getContent();
//
//                    List<String> nameHighlights = hit.getHighlightField("name");
//                    if (!nameHighlights.isEmpty()) {
//                        product.setName(nameHighlights.get(0));
//                    }
//
//                    List<String> descHighlights = hit.getHighlightField("description");
//                    if (!descHighlights.isEmpty()) {
//                        product.setDescription(descHighlights.get(0));
//                    }
//
//                    return product;
//                })
//                .collect(Collectors.toList());
//
//        return new PageImpl<>(list, PageRequest.of(page, size), hits.getTotalHits());
    }


    public void save() {

        ArrayList<Product> list = new ArrayList<>();

        String[] categories = {
                "家電", "パソコン用品", "スマートフォン用品",
                "キッチン用品", "日用品", "アウトドア用品",
                "スポーツ用品", "ファッション", "自動車用品",
                "オフィス用品", "美容用品", "ペット用品",
                "旅行用品", "家具", "生活雑貨"
        };

        String[] productTypes = {
                "ワイヤレスイヤホン",
                "Bluetoothスピーカー",
                "スマートウォッチ",
                "LEDデスクライト",
                "モバイルバッテリー",
                "USB充電器",
                "ワイヤレスマウス",
                "メカニカルキーボード",
                "ノートパソコンスタンド",
                "スマートフォンケース",
                "電動コーヒーミル",
                "ステンレスボトル",
                "保温ランチボックス",
                "収納ボックス",
                "折りたたみチェア",
                "アウトドアバックパック",
                "スポーツタオル",
                "ランニングシューズ",
                "トレーニングウェア",
                "自動車用スマートホルダー",
                "車載USB充電器",
                "カークリーナー",
                "オフィスチェア",
                "デスクオーガナイザー",
                "LEDスタンドライト",
                "トラベルポーチ",
                "キャリーケース",
                "折りたたみ傘",
                "ペット用ベッド",
                "ペット用食器"
        };

        String[] adjectives = {
                "高性能",
                "軽量",
                "コンパクト",
                "大容量",
                "便利な",
                "人気の",
                "シンプルな",
                "スタイリッシュな",
                "多機能",
                "省スペース",
                "持ち運びやすい",
                "使いやすい",
                "耐久性に優れた",
                "快適な",
                "実用的な",
                "高品質",
                "最新モデル",
                "おしゃれな",
                "便利で使いやすい",
                "日常生活におすすめの"
        };

        String[] descriptionTemplates = {
                "日常生活で使いやすい{}です。シンプルで便利なデザインを採用しており、幅広いシーンで活用できます。",
                "{}を採用した使いやすい商品です。自宅やオフィスなど、さまざまな場所で快適に使用できます。",
                "軽量で持ち運びにも便利な{}です。毎日の生活をより快適にするための実用的なアイテムです。",
                "高品質な素材を使用した{}です。耐久性に優れており、長期間安心して使用できます。",
                "シンプルでスタイリッシュなデザインの{}です。機能性と使いやすさを両立したおすすめの商品です。",
                "コンパクトなサイズで収納しやすい{}です。限られたスペースでも便利に使用できます。",
                "さまざまな用途に対応できる多機能な{}です。日常使いから旅行やアウトドアまで幅広く活用できます。",
                "初めて使用する方にもおすすめの{}です。簡単な操作で快適に使用できるよう設計されています。",
                "毎日の生活に便利な{}です。使いやすさを重視した設計で、さまざまなシーンに対応します。",
                "デザイン性と実用性を兼ね備えた{}です。自宅や職場など、さまざまな環境で活用できます。"
        };

        Random random = new Random();

        for (int i = 0; i < 1000; i++) {

            Product product = new Product();

            product.setId(i + 1L);

            String category = categories[random.nextInt(categories.length)];
            String type = productTypes[random.nextInt(productTypes.length)];
            String adjective = adjectives[random.nextInt(adjectives.length)];

            // 商品名
            String name = adjective + type + " " + (i + 1);

            // 商品説明
            String template = descriptionTemplates[random.nextInt(descriptionTemplates.length)];

            String description = template.replace("{}", type);

            product.setName(name);
            product.setDescription(description);

            // 价格
            product.setPrice(new BigDecimal(random.nextDouble(5232.02)));

            // 分类
            product.setCategory(category);

            // 库存
            product.setStock(random.nextInt(500));

            product.setCreateTime(new Date());

            list.add(product);
        }

        Iterable<Product> products = productRespository.saveAll(list);
    }

    public void deleteAll() {
        productRespository.deleteAll();
    }
}
