package com.fetch.receipt.processor.service;

import com.fetch.receipt.processor.data.model.Item;
import com.fetch.receipt.processor.data.model.Receipt;
import com.fetch.receipt.processor.exception.UUIDNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReceiptProcessorServiceTest {

    @Autowired
    ReceiptProcessorService receiptProcessorService;

    @Test
    void testCreateReceiptUUID() {
        Receipt receipt = getReceipt();
        UUID response = receiptProcessorService.createReceiptUUID(receipt);
        assertEquals(response, receipt.getReceiptId());
    }

    @Test
    void testFindTotalPoints() throws NoSuchFieldException, IllegalAccessException {
        Receipt receipt = getReceipt();
        UUID testId = UUID.randomUUID();

        Field field = ReceiptProcessorService.class.getDeclaredField("uuidReceiptMap");
         field.setAccessible(true);
         ((Map<UUID, Receipt>) field.get(receiptProcessorService)).put(testId, receipt);

        int points = receiptProcessorService.findTotalPoints(testId);
        int expectedPoints = 161; // for the sample input in getReceipt
        assertEquals(points, expectedPoints);
    }

    @Test
    public void testFindTotalPoints_whenIDNotFound() {
        UUID id = UUID.randomUUID();

        assertThrows(UUIDNotFoundException.class, () -> {
            receiptProcessorService.findTotalPoints(id);
        });
    }

    private static Receipt getReceipt() {
        Receipt receipt = new Receipt();
        receipt.setRetailer("Test1"); // 5
        receipt.setPurchaseDate(LocalDate.parse("2020-01-01"));
        receipt.setPurchaseTime(LocalTime.parse("14:30"));
//        receipt.setPurchaseDate(String.valueOf(LocalDate.of(2020, 1, 1)));
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
