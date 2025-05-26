package com.jesse.employee_management.employee.controller;

import lombok.Getter;

@Getter
public enum Gender
{
    Male("M"), Female("F");

    private final String gender;

    Gender(String g) { this.gender = g; }
}
