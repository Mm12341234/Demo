package com.example.heuristic;

import lombok.Data;

@Data
public class Cage {
    private double height;
    private double width;
    private Point startPoint=new Point();
    private Point endPoint=new Point();
}
