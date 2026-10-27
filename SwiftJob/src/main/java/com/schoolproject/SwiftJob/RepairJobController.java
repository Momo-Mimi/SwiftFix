/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.schoolproject.SwiftJob;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author motho
 */

@RestController
@RequestMapping("/api/jobs")
public class RepairJobController 
{
    private final RepairJobService service;

    // Connects the controller to the service.
    public RepairJobController(RepairJobService service) 
    {
        this.service = service;
    }
    
    // Adds a repair job.
    @PostMapping
    public ResponseEntity<RepairJob> addJob(@Valid @RequestBody RepairJob job) 
    {
        RepairJob savedJob = service.addJob(job);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedJob);
    }
    
    // Displays all jobs.
    @GetMapping
    public List<RepairJob> getAllJobs() 
    {
        return service.getAllJobs();
    }
    
    
    // Displays one job by ID.
    @GetMapping("/{id}")
    public RepairJob getJob(@PathVariable Long id) {
        return service.getJob(id);
    }

    // Updates a job's details.
    @PutMapping("/{id}")
    public RepairJob updateJob(@PathVariable Long id,@Valid @RequestBody RepairJob job) 
    {
        return service.updateJob(id, job);
    }

    // Changes a job's status.
    @PatchMapping("/{id}/status")
    public RepairJob changeStatus( @PathVariable Long id,@RequestParam RepairStatus status) 
    {
        return service.changeStatus(id, status);
    }

    // Deletes a collected job.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) 
    {
        service.deleteJob(id);
        return ResponseEntity.noContent().build();
    }

    // Displays jobs with a selected status.
    @GetMapping("/status/{status}")
    public List<RepairJob> getJobsByStatus( @PathVariable RepairStatus status) 
    {
        return service.getJobsByStatus(status);
    }

    // Displays READY jobs older than the selected number of days.
    @GetMapping("/overdue")
    public List<RepairJob> getOverdueJobs(@RequestParam(defaultValue = "7") int days) 
    {
        return service.getOverdueJobs(days);
    }
}
