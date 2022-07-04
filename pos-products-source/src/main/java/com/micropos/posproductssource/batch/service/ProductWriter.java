package com.micropos.posproductssource.batch.service;

import com.micropos.posproductssource.batch.model.Product;
import com.micropos.posproductssource.batch.model.ProductRepository;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductWriter implements ItemWriter<Product>, StepExecutionListener {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void beforeStep(StepExecution stepExecution) {

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }

    @Override
    public void write(List<? extends Product> list) throws Exception {
        for (Product p : list) {
            productRepository.save(p);
        }
    }
}
