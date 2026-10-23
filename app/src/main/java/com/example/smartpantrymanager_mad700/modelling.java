package com.example.smartpantrymanager_mad700;

import java.util.*;

final class PantryItem {
    final long id;
    final String name, unit, expiry;
    final double quantity;
    PantryItem(long id, String name, double quantity, String unit, String expiry) {
        this.id=id;
        this.name=name;
        this.quantity=quantity;
        this.unit=unit;
        this.expiry=expiry;
    }
}
final class Recipe {
    final long id;
    final String name, steps;
    final ArrayList<Requirement> requirements=new ArrayList<>();
    Recipe(long id,String name,String steps) {
        this.id=id;
        this.name=name;
        this.steps=steps; }
}
final class Requirement {
    final String name, unit;
    final double quantity;
    Requirement(String name, double quantity, String unit) {
        this.name=name;
        this.quantity=quantity;
        this.unit=unit;
    }
}
