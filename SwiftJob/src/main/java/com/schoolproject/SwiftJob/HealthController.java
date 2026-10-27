/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.schoolproject.SwiftJob;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 *
 * @author motho
 */

@RestController
public class HealthController 
{
    // Returns a message when the application is running.
    @GetMapping("/api/health")
    public String checkHealth() 
    {
        return "SwiftFix application is running!";
    }
}
