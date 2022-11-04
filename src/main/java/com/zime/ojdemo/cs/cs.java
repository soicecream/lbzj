package com.zime.ojdemo.cs;

import com.zime.ojdemo.untils.Io;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.*;

@RestController
public class cs {


    public static void main(String[] args) throws InterruptedException {
        Io.readPro(new File("C:/Users/26444/Desktop/data/1007"));
    }

    public int[] bestCoordinate(int[][] towers, int radius) {
    int xMax = Integer.MIN_VALUE, yMax = Integer.MIN_VALUE;
        for (int[] tower : towers) {
        int x = tower[0], y = tower[1];
        xMax = Math.max(xMax, x);
        yMax = Math.max(yMax, y);
    }
    int cx = 0, cy = 0;
    int maxQuality = 0;
        for (int x = 0; x <= xMax; x++) {
        for (int y = 0; y <= yMax; y++) {
            int[] coordinate = {x, y};
            int quality = 0;
            for (int[] tower : towers) {
                int squaredDistance = getSquaredDistance(coordinate, tower);
                if (squaredDistance <= radius * radius) {
                    double distance = Math.sqrt(squaredDistance);
                    quality += (int) Math.floor(tower[2] / (1 + distance));
                }
            }
            if (quality > maxQuality) {
                cx = x;
                cy = y;
                maxQuality = quality;
            }
        }
    }
        return new int[]{cx, cy};
}

    public int getSquaredDistance(int[] coordinate, int[] tower) {
        return (tower[0] - coordinate[0]) * (tower[0] - coordinate[0]) + (tower[1] - coordinate[1]) * (tower[1] - coordinate[1]);
    }

}
