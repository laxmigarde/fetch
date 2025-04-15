package com.fetch.receipt.processor.service;

import com.fetch.receipt.processor.exception.UUIDNotFoundException;
import com.fetch.receipt.processor.data.model.Item;
import com.fetch.receipt.processor.data.model.Receipt;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ReceiptProcessorService {

    private final Map<UUID, Receipt> uuidReceiptMap = new HashMap<>();
    private static final Pattern ALPHANUMERIC_PATTERN = Pattern.compile("[a-zA-Z0-9]");
    private static final Pattern ROUND_DOLLAR_PATTERN = Pattern.compile("^\\d+\\.\\d{2}$");


    public UUID createReceiptUUID(Receipt receipt) {
        UUID uuid = UUID.randomUUID();
        receipt.setReceiptId(uuid);
        uuidReceiptMap.put(receipt.getReceiptId(), receipt);
        return receipt.getReceiptId();
    }

    public Map<UUID, Receipt> getAllEntrySet() {
        return uuidReceiptMap;
    }

    public int findTotalPoints(UUID id) {
        int totalPoints = 0;
        if (uuidReceiptMap.containsKey(id)) {
            Receipt receipt = uuidReceiptMap.get(id);
            String retailer = receipt.getRetailer();
            String total = receipt.getTotal();
            List<Item> items = receipt.getItems();
            String day = receipt.getPurchaseDate();
            String time = receipt.getPurchaseTime();

            int alphanumericPoints = findAlphanumerics(retailer);
            int totalValuePoints = findValue(total);
            int itemPoints = findItems(items);
            int descriptionPoints = findItemDescription(items);
            int dayPoints = findDay(day);
            int timePoints = findTime(time);
            totalPoints = alphanumericPoints + totalValuePoints + itemPoints + descriptionPoints + dayPoints + timePoints;
        } else {
            throw  new UUIDNotFoundException(id);
        }
        return totalPoints;
    }

    private int findTime(String inputTime) {
        int totalTimePoints = 0;
        LocalTime time = LocalTime.parse(inputTime);
        LocalTime startTime = LocalTime.of(14, 0); // 2:00 PM
        LocalTime endTime = LocalTime.of(16, 0); // 4:00 PM

        if (time.isAfter(startTime) && time.isBefore(endTime)) {
            totalTimePoints += 10;
        }
        return totalTimePoints;
    }

    private int findDay(String inputDay) {
        int totalOddDayPoints = 0;
        LocalDate date = LocalDate.parse(inputDay);
        int day = date.getDayOfMonth();

        if (day % 2 != 0) { // odd day
            totalOddDayPoints += 6;
        }
        return totalOddDayPoints;
    }

    private int findItemDescription(List<Item> items) {
        int totalPoints = 0;
        for (Item item : items) {
            String shortDescription = item.getShortDescription();
            String trimmedDescription = shortDescription.trim();
            int points = 0;
            String price = item.getPrice();
            double priceValue = Double.parseDouble(price);
            if ((trimmedDescription.length() % 3) == 0) {
                points = (int) Math.ceil(priceValue * 0.2);
            }
            totalPoints += points;
        }
        return totalPoints;
    }

    private int findItems(List<Item> items) {
        if (items.isEmpty()) {
            return 0;
        }
        int itemPairs = (items.size() / 2);
        return  itemPairs * 5;
    }

    private int findValue(String input) {
        int roundValue = 0;
        int multipleValue = 0;
        Matcher matcher = ROUND_DOLLAR_PATTERN.matcher(input);
        if (matcher.matches()) {
            if (input.endsWith(".00")) {
                roundValue += 50;
            }
        }

        // if multiple of 0.25
        BigDecimal value = new BigDecimal(input);
        BigDecimal multiple = new BigDecimal("0.25");
        if (value.remainder(multiple).compareTo(BigDecimal.ZERO) == 0) {
            multipleValue += 25;
        }

        return roundValue + multipleValue;
    }

    private int findAlphanumerics(String retailer) {
        String[] retailerArr = retailer.split("//s");
        int lengthOfAlphaChars = 0;
        for (String retail : retailerArr) {
            Matcher matcher = ALPHANUMERIC_PATTERN.matcher(retail);
            int count = 0;
            while (matcher.find()) {
                count++;
            }
            lengthOfAlphaChars += count;
        }
        return lengthOfAlphaChars;
    }
}
