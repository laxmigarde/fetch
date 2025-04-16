package com.fetch.receipt.processor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fetch.receipt.processor.data.model.Item;
import com.fetch.receipt.processor.data.model.Receipt;
import com.fetch.receipt.processor.service.ReceiptProcessorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ReceiptProcessorControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ReceiptProcessorService receiptProcessorService;

    @Test
    void givenAValidReceipt_submitReceiptReturnsIdSuccessfully() throws Exception {
        Receipt receipt = getReceipt();

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/receipts/process")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(receipt)))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        System.out.println(response);
        String id = objectMapper.readTree(response).get("id").asText();
        assertNotNull(id);
    }

    @Test
    void submitReceipt_shouldReturn400_whenInvalidRequest() throws Exception {
        Receipt receipt = new Receipt();

        mvc.perform(MockMvcRequestBuilders.post("/receipts/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(receipt)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenValidId_getPointsCalculatesPoints() throws Exception {
        Receipt receipt = getReceipt();

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/receipts/process")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(receipt)))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        String id = objectMapper.readTree(response).get("id").asText();

        MvcResult getResult = mvc.perform(MockMvcRequestBuilders.get("/receipts/" + id + "/points"))
                .andExpect(status().isOk())
                .andReturn();

        String points = getResult.getResponse().getContentAsString();
        assertNotNull(points);
        assertFalse(points.isEmpty());
    }

    private static Receipt getReceipt() {
        Receipt receipt = new Receipt();
        receipt.setRetailer("Test1");
        receipt.setPurchaseDate(LocalDate.parse("2020-01-01"));
        receipt.setPurchaseTime(LocalTime.parse("14:30"));
//        receipt.setPurchaseDate(2020, 1, 1));
//        receipt.setPurchaseTime(String.valueOf(LocalTime.of(14, 30)));

        List<Item> list = new ArrayList<>();
        Item item = new Item();
        item.setPrice("100");
        item.setShortDescription("Test description 1");
        list.add(item);
        Item item1 = new Item();
        item1.setPrice("200");
        item1.setShortDescription("Test description 2");
        list.add(item1);
        receipt.setItems(list);
        receipt.setTotal("25.00");
        return receipt;
    }

}
