package com.fetch.receipt.processor.controller;

import com.fetch.receipt.processor.data.model.Receipt;
import com.fetch.receipt.processor.data.response.BadRequestResponse;
import com.fetch.receipt.processor.data.response.NotFoundResponse;
import com.fetch.receipt.processor.exception.UUIDNotFoundException;
import com.fetch.receipt.processor.service.ReceiptProcessorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/receipts")
@Tag(name = "Receipt Processor", description = "A simple receipt processor")
public class ReceiptController {

    @Autowired
    private ReceiptProcessorService receiptProcessorService;

    @Operation(summary = "Submits a receipt for processing.", description = "Submits a receipt for processing.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Returns the ID assigned to the receipt",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UUID.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400", description = "Bad Request",
                    content = @Content(schema = @Schema(implementation = BadRequestResponse.class))
            )
    })
    @PostMapping(value = "/process")
    public ResponseEntity<?> submitReceipt(@Valid @RequestBody Receipt receipt) {
        UUID newReceiptId = receiptProcessorService.createReceiptUUID(receipt);
        return ResponseEntity.ok(newReceiptId);
    }



    @Operation(summary = "Returns the points awarded for the receipt.", description = "Returns the points awarded for the receipt.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "The number of points awarded.",
                    content = @Content(
                            schema = @Schema(implementation = Integer.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404", description = "Not Found",
                    content = @Content(schema = @Schema(implementation = NotFoundResponse.class))
            )
    })
    @GetMapping("/{id}/points")
    public ResponseEntity<?> getPoints(@PathVariable("id") UUID id) throws UUIDNotFoundException {
        int totalPoints = receiptProcessorService.findTotalPoints(id);
        return ResponseEntity.ok(totalPoints);
    }
}
