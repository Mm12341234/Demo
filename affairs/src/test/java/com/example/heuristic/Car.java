package com.example.heuristic;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Car {
    private double width=1200;
    private double height=600;
    private List<Cage> cageList=new ArrayList<>();
}
