package com.example.pixeltracker.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;

@RestController
@RequestMapping("/api/tracker")
public class TrackerController {

    @GetMapping
    public ResponseEntity<byte[]> track(
            @RequestParam(defaultValue = "Unknown") String name,
            @RequestParam(defaultValue = "0") String cpuC,
            @RequestParam(defaultValue = "0") String cpuL,
            @RequestParam(defaultValue = "0") String tRam,
            @RequestParam(defaultValue = "0") String uRam,
            @RequestParam(defaultValue = "0") String disk
    ) throws IOException {

        // 🔹 Log telemetry (later → DB / BigQuery / PubSub)
        System.out.println(
                "[" + Instant.now() + "] " +
                "Server=" + name +
                ", CPUs=" + cpuC +
                ", CPU_Load=" + cpuL +
                "%, TotalRAM=" + tRam +
                "MB, UsedRAM=" + uRam +
                "MB, DiskFree=" + disk + "MB"
        );

        // 🔹 Create image (can also be 1x1 pixel)
        BufferedImage image = new BufferedImage(300, 160, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                           RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g.setColor(new Color(44, 62, 80));
        g.fillRect(0, 0, 300, 160);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("Server: " + name, 10, 25);

        g.setFont(new Font("Courier New", Font.PLAIN, 12));
        g.drawString("CPU Count: " + cpuC, 10, 55);
        g.drawString("CPU Load:  " + cpuL + "%", 10, 75);
        g.drawString("Total RAM: " + tRam + " MB", 10, 95);
        g.drawString("Used RAM:  " + uRam + " MB", 10, 115);
        g.drawString("Free HDD:  " + disk + " MB", 10, 135);

        g.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setCacheControl("no-cache, no-store, must-revalidate");

        return new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.OK);
    }
}
