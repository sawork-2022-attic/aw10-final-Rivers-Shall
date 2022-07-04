package com.micropos.posproductssource.batch.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.micropos.posproductssource.batch.model.Product;
import com.micropos.posproductssource.batch.model.ProductRepository;
import com.micropos.posproductssource.batch.model.ProductStringField;
import lombok.Data;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemProcessor;

import java.text.NumberFormat;
import java.util.Locale;

public class ProductProcessor implements ItemProcessor<JsonNode, Product>, StepExecutionListener {

    static private NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());

    private ObjectMapper objectMapper;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        objectMapper = new ObjectMapper();
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }

    @Override
    public Product process(JsonNode jsonNode) throws Exception {
        ProductStringField productStringField = objectMapper.treeToValue(jsonNode, ProductStringField.class);
        Product product = new Product();
        product.setTitle(productStringField.getTitle());
        if (productStringField.getPrice().length() == 0 || !productStringField.getPrice().startsWith("$")) {
            return null;
        }

        product.setPrice(numberFormat.parse(productStringField.getPrice().substring(1)).doubleValue());
        if (product.getTitle() != null && product.getTitle().length() > 255) {
            product.setTitle(product.getTitle().substring(0, 255));
        }
        return product;
    }
}
