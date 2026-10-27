/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.schoolproject.SwiftJob;

/**
 *
 * @author motho
 */
public class JobNotFoundException extends RuntimeException
{
    public JobNotFoundException(Long id) 
    {
        super("Repair job " + id + " was not found.");
    }
}
